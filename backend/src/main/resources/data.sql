INSERT INTO message (id, text) VALUES (1, 'Welcome');

-- Test user for login (email: user@example.com, password: password)
INSERT INTO app_user (id, email, password_hash) VALUES (1, 'user@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
-- Reset IDENTITY so new registrations get id 2, 3, ...
ALTER TABLE app_user ALTER COLUMN id RESTART WITH 2;

-- Sample garments for test user (development)
INSERT INTO garment (owner_id, title, waist_min, waist_max, length_in, fit_estimate, state) VALUES
  (1, 'Blue jeans', 31, 32, 32, 'TRUE_TO_SIZE', 'PRIVATE'),
  (1, 'Black skinny jeans', 30, 31, 30, 'RUNS_SMALL', 'FRIENDS_ONLY'),
  (1, 'Light wash straight leg', 32, 33, 34, 'TRUE_TO_SIZE', 'PUBLIC'),
  (1, 'Dark denim bootcut', 29, 30, 32, 'RUNS_BIG', 'GIVE_AWAY'),
  (1, 'High-rise mom jeans', 31, 32, 30, 'TRUE_TO_SIZE', 'PRIVATE'),
  (1, 'Vintage Levi''s 501', 33, 34, 34, 'RUNS_SMALL', 'PUBLIC'),
  (1, 'Cropped wide leg', 28, 29, 28, 'TRUE_TO_SIZE', 'FRIENDS_ONLY'),
  (1, 'Distressed boyfriend jeans', 30, 31, 32, 'RUNS_BIG', 'GIVE_AWAY'),
  (1, 'White summer jeans', 31, 32, 32, 'TRUE_TO_SIZE', 'PRIVATE'),
  (1, 'Grey relaxed fit', 32, 33, 34, 'RUNS_BIG', 'PUBLIC');
ALTER TABLE garment ALTER COLUMN id RESTART WITH 11;
