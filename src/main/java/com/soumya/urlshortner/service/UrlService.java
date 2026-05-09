package com.soumya.urlshortner.service;

import com.soumya.urlshortner.entity.Url;
import com.soumya.urlshortner.exception.ShortCodeAlreadyExistsException;
import com.soumya.urlshortner.exception.ShortUrlExpiredException;
import com.soumya.urlshortner.exception.UrlNotFoundException;
import com.soumya.urlshortner.repository.UrlRepository;
import com.soumya.urlshortner.utill.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;
    private String generateShortCode(){
        return UUID.randomUUID().toString().substring(0,6);
    }

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
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found") );

        if(url.getExpiryDate() != null && url.getExpiryDate().isBefore(LocalDateTime.now())){
            throw new ShortUrlExpiredException("Short URL is expired");
        }
        url.setClickCount(url.getClickCount()+1);
        urlRepository.save(url);
        return url.getLongUrl();
    }
}
