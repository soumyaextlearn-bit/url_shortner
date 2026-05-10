package com.soumya.urlshortner.config;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CacheErrorHandleConfig {
    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception,
                                            Cache cache,
                                            Object key) {
                log.error(exception.getMessage(), exception);
            }

            @Override
            public void handleCachePutError(RuntimeException exception,
                                            Cache cache,
                                            Object key,
                                            @Nullable Object value) {
                log.error(exception.getMessage(), exception);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception,
                                              Cache cache,
                                              Object key) {
                log.error(exception.getMessage(), exception);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception,
                                              Cache cache) {
                log.error(exception.getMessage(), exception);
            }

        };
    }
}
