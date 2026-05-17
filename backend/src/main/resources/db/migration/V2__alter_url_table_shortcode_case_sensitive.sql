ALTER TABLE urls
    MODIFY shortcode VARCHAR(255)
    COLLATE utf8mb4_bin
    NULL;