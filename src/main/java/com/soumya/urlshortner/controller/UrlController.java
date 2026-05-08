package com.soumya.urlshortner.controller;

import com.soumya.urlshortner.dto.ShortenUrlResponse;
import com.soumya.urlshortner.entity.Url;
import com.soumya.urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UrlController {
    @Autowired
    private UrlService urlService;
    @PostMapping("/shorten")
    public ShortenUrlResponse shorten(@RequestParam String url){
        String shortCode = urlService.createShortUrl(url);
        String shortUrl = "http://localhost:8080/"+shortCode;
        
        return new ShortenUrlResponse(shortUrl);
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, originalUrl)
                .build();
    }
}
