INSERT INTO message (id, text) VALUES (1, 'Welcome');

-- Test user for login (email: user@example.com, password: password)
INSERT INTO app_user (id, email, password_hash) VALUES (1, 'user@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
-- Reset IDENTITY so new registrations get id 2, 3, ...
ALTER TABLE app_user ALTER COLUMN id RESTART WITH 2;
