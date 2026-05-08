package com.soumya.urlshortner.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="longurl")
    private String longUrl;

    @Column(name = "shortcode", unique = true)
    private String shortCode;

    @Column(name = "createdat")
    private LocalDateTime createdAt;

    @Column(name = "expirydate")
    private LocalDateTime expiryDate;

    @Column(name = "clickcount")
    private Long clickCount = 0L;
}