package com.soumya.urlshortner.scheduler;

import com.soumya.urlshortner.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class UrlCleanupScheduler {
    private final UrlRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * 0")
    @Transactional
    public void cleanExpiredUrls(){
        log.info("Started cleaning expired URLs");
        urlRepository.deleteByExpiryDateBefore(LocalDateTime.now());
        log.info("Cleaned expired URLs");
    }
}
