CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    second_last_name VARCHAR(255),
    dni VARCHAR(50)
);

CREATE TABLE bank_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(19, 2),
    iban VARCHAR(255) UNIQUE NOT NULL,
    client_id BIGINT,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE credit_cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(255) UNIQUE NOT NULL,
    expiration_date VARCHAR(20),
    cvc VARCHAR(10),
    full_name VARCHAR(255),
    bank_account_id BIGINT,
    CONSTRAINT fk_bank_account_cc FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);

CREATE TABLE bank_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date VARCHAR(50),
    amount DECIMAL(19, 2),
    concept VARCHAR(255),
    type VARCHAR(50),
    origin VARCHAR(50),
    card_number VARCHAR(255),
    bank_account_id BIGINT,
    CONSTRAINT fk_bank_account_bt FOREIGN KEY (bank_account_id) REFERENCES bank_accounts(id)
);


CREATE TABLE sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    token VARCHAR(512) NOT NULL UNIQUE,
    client_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id) ON DELETE CASCADE
);
