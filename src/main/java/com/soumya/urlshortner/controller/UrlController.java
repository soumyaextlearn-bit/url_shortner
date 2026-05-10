package com.soumya.urlshortner.controller;

import com.soumya.urlshortner.dto.ShortenUrlRequest;
import com.soumya.urlshortner.dto.ShortenUrlResponse;
import com.soumya.urlshortner.service.RateLimiterService;
import com.soumya.urlshortner.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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

    @Autowired
    private RateLimiterService rateLimiterService;


    @PostMapping("/shorten")
    public ShortenUrlResponse shorten(@Valid @RequestBody ShortenUrlRequest request,
                                      HttpServletRequest httpServletRequest) {
        rateLimiterService.validateRateLimit(httpServletRequest.getRemoteAddr());

        String shortCode = urlService.createShortUrl(
                request.getUrl(),
                request.getExpiryMinutes(),
                request.getCustomCode()
        );
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
