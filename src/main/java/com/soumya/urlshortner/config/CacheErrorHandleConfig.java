package com.soumya.urlshortner.config;

import org.jspecify.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheErrorHandleConfig {
    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception,
                                            Cache cache,
                                            Object key) {
                System.out.println("cache Get Error : " + exception.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException exception,
                                            Cache cache,
                                            Object key,
                                            @Nullable Object value) {
                System.out.println("cache Put Error : " + exception.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception,
                                              Cache cache,
                                              Object key) {
                System.out.println("cache Evict Error : " + exception.getMessage());
            }

            @Override
            public void handleCacheClearError(RuntimeException exception,
                                              Cache cache) {
                System.out.println("cache Clear Error : " + exception.getMessage());
            }

        };
    }
}
