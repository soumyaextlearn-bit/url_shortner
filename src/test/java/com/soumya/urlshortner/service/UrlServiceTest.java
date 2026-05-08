package com.soumya.urlshortner.service;

import com.soumya.urlshortner.entity.Url;
import com.soumya.urlshortner.repository.UrlRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {
    @Mock
    private UrlRepository urlRepository;
    @InjectMocks
    private UrlService urlService;
    @Test
    void shouldCreateShortUrl(){
        String longUrl = "https://google.com";

        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        String shortCode = urlService.createShortUrl(longUrl);

        assertNotNull(shortCode);
        assertEquals(6, shortCode.length());
        verify(urlRepository,times(1))
                .save(any(Url.class));
    }

    @Test
    void shouldReturnOriginalUrl(){

        Url url = new Url();
        url.setShortCode("abc123");
        url.setLongUrl("https://google.com");
        url.setClickCount(0L);

        when(urlRepository.findByShortCode("abc123")).thenReturn(Optional.of(url));

        String result = urlService.getOriginalUrl("abc123");
        assertEquals("https://google.com", result);
        assertEquals(1L, url.getClickCount());
        verify(urlRepository,times(1)).save(url);
    }

    @Test
    void shouldThrowExceptionWhenUrlNotFound(){
        when(urlRepository.findByShortCode("wrong")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> urlService.getOriginalUrl("wrong"));

        assertEquals("Short url not found", exception.getMessage());

    }
}
