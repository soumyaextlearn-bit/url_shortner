package com.soumya.urlshortner.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ShortenUrlRequest {
    @NotBlank(message = "URL cannot be empty")
    private String url;
}
