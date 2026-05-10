package com.soumya.urlshortner.service;

import com.soumya.urlshortner.entity.Url;
import com.soumya.urlshortner.exception.ShortCodeAlreadyExistsException;
import com.soumya.urlshortner.exception.ShortUrlExpiredException;
import com.soumya.urlshortner.exception.UrlNotFoundException;
import com.soumya.urlshortner.repository.UrlRepository;
import com.soumya.urlshortner.utill.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;


@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${app.url.default-expiry-minutes:43200}")
    private long defaultExpiryMinutes;

    public String createShortUrl(String longUrl, Long expiryMinutes,String customCode) {

        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setCreatedAt(LocalDateTime.now());
        if(expiryMinutes != null && expiryMinutes > 0){
            url.setExpiryDate(LocalDateTime.now().plusMinutes(expiryMinutes));
        }else{
            url.setExpiryDate(LocalDateTime.now().plusMinutes(defaultExpiryMinutes));
        }
        if(customCode != null && !customCode.isBlank()){
            if(urlRepository.existsByShortCode(customCode)){
                throw new ShortCodeAlreadyExistsException("custom code already exists");
            }
            url.setShortCode(customCode);
            urlRepository.save(url);
            return customCode;
        }

        Url savedUrl = urlRepository.save(url);
        String shortcode = Base62Encoder.encode(savedUrl.getId());
        savedUrl.setShortCode(shortcode);
        urlRepository.save(savedUrl);
        return shortcode;
    }


    public String getOriginalUrl(String shortCode){
        try{
            String cachedUrl = redisTemplate.opsForValue().get(shortCode);
            if(cachedUrl != null){
                System.out.println("Fetched from redis");
                return cachedUrl;
            }
        }catch (Exception e){
            System.out.println("Redis is unavailable, Falling back to DB");
        }

        System.out.println("Fetching from DB");
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found") );

        if(url.getExpiryDate() != null && url.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new ShortUrlExpiredException("Short URL is expired");
        }
        url.setClickCount(url.getClickCount()+1);
        urlRepository.save(url);

        try{
            redisTemplate.opsForValue().set(
                    shortCode,
                    url.getLongUrl(),
                    Duration.ofMinutes(10)
            );
        }catch (Exception e){
            System.out.println("Redis is unavailable, Failed to cache in redis");
        }
        return url.getLongUrl();
    }
}
