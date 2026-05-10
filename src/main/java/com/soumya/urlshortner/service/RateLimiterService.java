package com.soumya.urlshortner.service;

import com.soumya.urlshortner.exception.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;

    private static final Duration WINDOW = Duration.ofSeconds(60);

    private final RedisTemplate<String,String> redisTemplate;

    public void validateRateLimit(String ipAddress){
        try{
            String key = "rate_limit"+ipAddress;

            Long requestCount = redisTemplate.opsForValue().increment(key);
            if(requestCount != null && requestCount == 1){
                redisTemplate.expire(key,WINDOW);
            }

            if(requestCount != null && requestCount > MAX_REQUESTS){
                throw new RateLimitExceededException("Rate limit exceeded");
            }
        }
        catch (RateLimitExceededException ex){
            throw ex;
        }
        catch (Exception ex){
            System.out.println("Redis is unavailable, Skipping rate limiting");
        }

    }
}
