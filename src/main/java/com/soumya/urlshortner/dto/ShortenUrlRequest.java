package com.soumya.urlshortner.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Data
@Schema(description = "Request payload for URL shortening")
public class ShortenUrlRequest {
    @NotBlank(message = "URL cannot be empty")

    @URL(message = "Must be a valid URL")
    @Schema(
            description = "Original URL",
            example = "https://google.com"
    )
    private String url;

    @Min(value = 1,message = "Expiry must be at least 1 minute")
    @Max(value = 129600, message = "Expiry can not exceed 90 days")
    private Long expiryMinutes;
    @Schema(
            description = "Optional custom short code",
            example = "google"
    )
    private String customCode;
}
