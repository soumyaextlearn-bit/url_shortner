package com.soumya.urlshortner.repository;

import com.soumya.urlshortner.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
   Optional<Url> findByShortCode(String shortCode);
   boolean existsByShortCode(String shortCode);
   void deleteByExpiryDateBefore(LocalDateTime time);
}
