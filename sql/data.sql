-- Suppression des anciennes données pour éviter les doublons
DELETE FROM transactions;
DELETE FROM users;

-- Insertion des 5 users avec id fixes
INSERT INTO users (id, lastname, firstname, birthdate, email, password) VALUES
                                                                            (1, 'Dupont', 'Jean', '1998-04-12', 'jean.dupont@example.com', 'password123'),
                                                                            (2, 'Martin', 'Claire', '2000-09-30', 'claire.martin@example.com', 'securePass1'),
                                                                            (3, 'Bernard', 'Lucas', '1995-01-18', 'lucas.bernard@example.com', 'myPassword9'),
                                                                            (4, 'Petit', 'Emma', '2002-06-05', 'emma.petit@example.com', 'emmaPass88'),
                                                                            (5, 'Moreau', 'Thomas', '1997-11-22', 'thomas.moreau@example.com', 'thomasPwd7');

-- Insertion de 20 transactions avec id fixes
INSERT INTO transactions (id, price, date, currency, type, user_id) VALUES
                                                                        (1,  1200.0, '2025-01-01', 'EUR', 'CREDIT', 1),
                                                                        (2,  200.0,  '2025-01-03', 'EUR', 'DEBIT',  1),
                                                                        (3,  500.0,  '2025-01-10', 'EUR', 'CREDIT', 1),
                                                                        (4,  150.0,  '2025-01-15', 'EUR', 'DEBIT',  1),

                                                                        (5,  1800.0, '2025-01-02', 'EUR', 'CREDIT', 2),
                                                                        (6,  300.0,  '2025-01-06', 'EUR', 'DEBIT',  2),
                                                                        (7,  400.0,  '2025-01-12', 'EUR', 'CREDIT', 2),
                                                                        (8,  100.0,  '2025-01-20', 'EUR', 'DEBIT',  2),

                                                                        (9,  950.0,  '2025-01-04', 'EUR', 'CREDIT', 3),
                                                                        (10, 120.0,  '2025-01-08', 'EUR', 'DEBIT',  3),
                                                                        (11, 600.0,  '2025-01-14', 'EUR', 'CREDIT', 3),
                                                                        (12, 220.0,  '2025-01-18', 'EUR', 'DEBIT',  3),

                                                                        (13, 1100.0, '2025-01-05', 'EUR', 'CREDIT', 4),
                                                                        (14, 90.0,   '2025-01-07', 'EUR', 'DEBIT',  4),
                                                                        (15, 700.0,  '2025-01-16', 'EUR', 'CREDIT', 4),
                                                                        (16, 160.0,  '2025-01-22', 'EUR', 'DEBIT',  4),

                                                                        (17, 2000.0, '2025-01-01', 'EUR', 'CREDIT', 5),
                                                                        (18, 450.0,  '2025-01-09', 'EUR', 'DEBIT',  5),
                                                                        (19, 800.0,  '2025-01-17', 'EUR', 'CREDIT', 5),
                                                                        (20, 300.0,  '2025-01-25', 'EUR', 'DEBIT',  5);
