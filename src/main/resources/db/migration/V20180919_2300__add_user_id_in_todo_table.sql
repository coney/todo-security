ALTER TABLE todo
  ADD COLUMN user_id INT NOT NULL DEFAULT 0 REFERENCES user (id)