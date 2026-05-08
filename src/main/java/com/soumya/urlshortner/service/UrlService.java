package com.soumya.urlshortner.service;

import com.soumya.urlshortner.entity.Url;
import com.soumya.urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String createShortUrl(String longUrl) {

        String shortcode = generateShortCode();

        Url url = new Url();
        url.setShortCode(shortcode);
        url.setLongUrl(longUrl);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);
        return shortcode;
    }

    public String getOriginalUrl(String shortCode){
        Url url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short url not found") );
        url.setClickCount(url.getClickCount()+1);
        urlRepository.save(url);
        return url.getLongUrl();
    }
}
