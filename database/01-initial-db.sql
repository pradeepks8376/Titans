use definitions;

CREATE TABLE IF NOT EXISTS `dashboards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` TIMESTAMP NOT NULL DEFAULT NOW(),
  `updated_at` TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
  `title` TEXT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
