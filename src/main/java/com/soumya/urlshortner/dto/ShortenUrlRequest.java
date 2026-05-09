package com.soumya.urlshortner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Data
public class ShortenUrlRequest {
    @NotBlank(message = "URL cannot be empty")

    @URL(message = "Must be a valid URL")
    private String url;

    @Min(value = 1,message = "Expiry must be at least 1 minute")
    @Max(value = 129600, message = "Expiry can not exceed 90 days")
    private Long expiryMinutes;

    private String customCode;
}
