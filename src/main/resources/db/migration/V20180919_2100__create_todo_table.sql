CREATE TABLE IF NOT EXISTS todo (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  action   VARCHAR(128) NOT NULL,
  status   VARCHAR(16)  NOT NULL,
  due_date DATE         NOT NULL,
  deleted  INT             DEFAULT 0
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4