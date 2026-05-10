package com.soumya.urlshortner.scheduler;

import com.soumya.urlshortner.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UrlCleanupScheduler {
    private final UrlRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * 0")
    @Transactional
    public void cleanExpiredUrls(){
        System.out.println("Cleaning expired urls...");
        urlRepository.deleteByExpiryDateBefore(LocalDateTime.now());
        System.out.println("Expired URLS deleted");
    }
}
