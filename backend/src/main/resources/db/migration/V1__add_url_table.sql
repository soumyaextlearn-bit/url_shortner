CREATE TABLE urls
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    longurl VARCHAR(2048) NOT NULL,

    shortcode VARCHAR(20) NOT NULL UNIQUE,

    createdat DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    expirydate DATETIME NULL,

    clickcount BIGINT NOT NULL DEFAULT 0,

    INDEX idx_shortcode(shortcode)
);