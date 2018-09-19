CREATE TABLE IF NOT EXISTS todo_tag (
  todo_id INT REFERENCES todo (id),
  tag_id  INT REFERENCES tag (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4