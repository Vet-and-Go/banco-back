-- Insert Clients
INSERT  INTO clients (login, password, first_name, last_name, second_last_name, dni) VALUES
('juan', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'Juan', 'Perez', 'Garcia', '12345678A'),
('maria', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'Maria', 'Lopez', 'Fernandez', '87654321B'),
('admin', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6', 'Admin', 'User', 'System', '00000000x');

-- Insert Bank Accounts
INSERT  INTO bank_accounts (balance, iban, client_id) VALUES
(1000.00, 'ES61 1234 3456 4204 5632 3532', 1), -- Juan's account
(500.00, 'ES61 1234 3456 4204 5632 5555', 2),  -- Maria's account (Transfer destination)
(1.00,'ES61 1234 3456 4204 5632 9483',1);

-- Insert Credit Cards
INSERT  INTO credit_cards (card_number, expiration_date, cvc, full_name, bank_account_id) VALUES
('1234567890123456', '2028-12-31', '123', 'Juan Perez', 1),
('9876543210987654', '2029-06-30', '456', 'Maria Lopez', 2);
