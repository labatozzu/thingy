INSERT INTO message (id, text) VALUES (1, 'Welcome');

-- Test user for login (email: a@a.a, password: a)
INSERT INTO app_user (id, email, password_hash) VALUES (1, 'a@a.a', '$2a$10$3xv.7/fzxoMxNC2ddIYIFOl7tCnevuT.3AckEQTNSGZBPGSlKKlu.');
-- Reset IDENTITY so new registrations get id 2, 3, ...
ALTER TABLE app_user ALTER COLUMN id RESTART WITH 2;

-- Sample garments for test user (development)
INSERT INTO garment (owner_id, title, waist, length_in, fit_estimate, visibility, status) VALUES
  (1, 'Blue jeans', 32, 32, 'TRUE_TO_SIZE', 'PRIVATE', 'NOT_AVAILABLE'),
  (1, 'Black skinny jeans', 30, 30, 'RUNS_SMALL', 'FRIENDS_ONLY', 'AVAILABLE'),
  (1, 'Light wash straight leg', 32, 34, 'TRUE_TO_SIZE', 'PUBLIC', 'AVAILABLE'),
  (1, 'Dark denim bootcut', 30, 32, 'RUNS_BIG', 'PUBLIC', 'AVAILABLE'),
  (1, 'High-rise mom jeans', 32, 30, 'TRUE_TO_SIZE', 'PRIVATE', 'NOT_AVAILABLE'),
  (1, 'Vintage Levi''s 501', 34, 34, 'RUNS_SMALL', 'PUBLIC', 'AVAILABLE'),
  (1, 'Cropped wide leg', 28, 28, 'TRUE_TO_SIZE', 'FRIENDS_ONLY', 'AVAILABLE'),
  (1, 'Distressed boyfriend jeans', 30, 32, 'RUNS_BIG', 'PUBLIC', 'AVAILABLE'),
  (1, 'White summer jeans', 32, 32, 'TRUE_TO_SIZE', 'PRIVATE', 'NOT_AVAILABLE'),
  (1, 'Grey relaxed fit', 32, 34, 'RUNS_BIG', 'PUBLIC', 'AVAILABLE');
ALTER TABLE garment ALTER COLUMN id RESTART WITH 11;
