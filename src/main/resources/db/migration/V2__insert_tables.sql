-- Insert Clients
INSERT  INTO clients (login, password, first_name, last_name, second_last_name, dni, api_token) VALUES
('juan', 'password', 'Juan', 'Perez', 'Garcia', '12345678A', '5f5ca67f-4c02-47cf-8753-a7790f7f5be1'),
('maria', 'password', 'Maria', 'Lopez', 'Fernandez', '87654321B', 'api_token_maria'),
('admin', 'admin', 'Admin', 'User', 'System', '00000000x', 'admin_token');

-- Insert Bank Accounts
INSERT  INTO bank_accounts (balance, iban, client_id) VALUES
(1000.00, 'ES61 1234 3456 4204 5632 3532', 1), -- Juan's account
(500.00, 'ES61 1234 3456 4204 5632 5555', 2);  -- Maria's account (Transfer destination)

-- Insert Credit Cards
INSERT  INTO credit_cards (card_number, expiration_date, cvc, full_name, bank_account_id) VALUES
('1234567890123456', '2028-12-31', '123', 'Juan Perez', 1),
('9876543210987654', '2029-06-30', '456', 'Maria Lopez', 2);
