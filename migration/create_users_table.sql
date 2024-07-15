CREATE TABLE users (
                       id BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       date_of_birth DATE NOT NULL,
                       insertion_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE INDEX idx_users_email ON users (email);
