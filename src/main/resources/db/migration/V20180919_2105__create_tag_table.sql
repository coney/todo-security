CREATE TABLE IF NOT EXISTS tag (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(128) UNIQUE NOT NULL,
  deleted INT DEFAULT 0
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
