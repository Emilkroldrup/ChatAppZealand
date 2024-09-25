-- Insert predefined users
INSERT INTO users (username, password) VALUES ('admin', '$2a$10$g4HxJ9OtCqDhv7FH9cTkCeCk9YnlqnbRCRjNZ02CXJkYYm6d9Ueyy');  -- password is 'admin123'
INSERT INTO users (username, password) VALUES ('user', '$2a$10$g4HxJ9OtCqDhv7FH9cTkCeCk9YnlqnbRCRjNZ02CXJkYYm6d9Ueyy');  -- password is 'user123';


INSERT INTO chats (chat_name) VALUES ('General');
INSERT INTO chats (chat_name) VALUES ('Development');
INSERT INTO chats (chat_name) VALUES ('Support');