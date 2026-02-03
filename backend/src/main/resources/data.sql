INSERT INTO message (id, text) VALUES (1, 'Hello, world');

-- Test user for login (email: user@example.com, password: password)
INSERT INTO app_user (id, email, password_hash) VALUES (1, 'user@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
