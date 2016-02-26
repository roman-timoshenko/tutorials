CREATE TABLE IF NOT EXISTS user_groups (user_id INT, group_id INT);
ALTER TABLE user_groups ADD FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE;
ALTER TABLE user_groups ADD FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE CASCADE;
ALTER TABLE user_groups ADD UNIQUE (user_id, group_id);