-- Insert Clients
INSERT  INTO clients (login, password, first_name, last_name, second_last_name, dni) VALUES
('juan', '$2a$12$yvcXLIW97aTvOB1c6WnxXebfarMh8Wwu2Q9mUr33PB0JpGFxqT.eS', 'Juan', 'Perez', 'Garcia', '12345678A'),
('maria', '$2a$12$yvcXLIW97aTvOB1c6WnxXebfarMh8Wwu2Q9mUr33PB0JpGFxqT.eS', 'Maria', 'Lopez', 'Fernandez', '87654321B'),
('admin', '$2a$12$PWlcq76TG1f1m17JlLdz0.GnYx5BEVnijENz3zjFUQ91SmLkuM4Ai', 'Admin', 'User', 'System', '00000000x'),
('store', '$2a$12$PWlcq76TG1f1m17JlLdz0.GnYx5BEVnijENz3zjFUQ91SmLkuM4Ai', 'Store', 'Store', 'vetandgo','00000000L'),
('carlos', '$2a$12$yvcXLIW97aTvOB1c6WnxXebfarMh8Wwu2Q9mUr33PB0JpGFxqT.eS', 'Carlos', 'Rodriguez', 'Martinez', '11223344C');

-- Insert Bank Accounts
INSERT  INTO bank_accounts (balance, iban, client_id) VALUES
(1000.00, 'ES61 1234 3456 4204 5632 3532', 1), -- Juan's account
(500.00, 'ES61 1234 3456 4204 5632 5555', 2),  -- Maria's account (Transfer destination)
(1.00,'ES61 1234 3456 4204 5632 9483',1),
(0.00,'ES61 1234 5678 9012 3456 7890',4),
(2500.00, 'ES61 1234 3456 4204 5632 7788', 5); -- Carlos's account

-- Insert Credit Cards
INSERT  INTO credit_cards (card_number, expiration_date, cvc, full_name, bank_account_id) VALUES
('1234567890123456', '2028-12-31', '123', 'Juan Perez', 1),
('9876543210987654', '2029-06-30', '456', 'Maria Lopez', 2),
('5555666677778888', '2027-08-31', '789', 'Carlos Rodriguez', 5);
