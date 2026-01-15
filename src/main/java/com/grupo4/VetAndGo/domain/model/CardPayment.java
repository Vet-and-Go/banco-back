package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;
import java.time.YearMonth;

public class CardPayment {
    private String destinationIban;
    private BigDecimal amount;
    private String concept;
    private String cardNumber;
    private String cardExpirationDate;
    private String cardCvc;
    private String cardHolderName;

    public CardPayment() {
    }

    public CardPayment(String destinationIban, BigDecimal amount, String concept, String cardNumber, String cardExpirationDate, String cardCvc, String cardHolderName) {
        this.destinationIban = destinationIban;
        this.amount = amount;
        this.concept = concept;
        this.cardNumber = cardNumber;
        this.cardExpirationDate = cardExpirationDate;
        this.cardCvc = cardCvc;
        this.cardHolderName = cardHolderName;
    }

    public static CardPaymentBuilder builder() {
        return new CardPaymentBuilder();
    }

    public String getDestinationIban() {
        return destinationIban;
    }

    public void setDestinationIban(String destinationIban) {
        this.destinationIban = destinationIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getCardCvc() {
        return cardCvc;
    }

    public void setCardCvc(String cardCvc) {
        this.cardCvc = cardCvc;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public static class CardPaymentBuilder {
        private String destinationIban;
        private BigDecimal amount;
        private String concept;
        private String cardNumber;
        private String cardExpirationDate;
        private String cardCvc;
        private String cardHolderName;

        CardPaymentBuilder() {
        }

        public CardPaymentBuilder destinationIban(String destinationIban) {
            this.destinationIban = destinationIban;
            return this;
        }

        public CardPaymentBuilder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public CardPaymentBuilder concept(String concept) {
            this.concept = concept;
            return this;
        }

        public CardPaymentBuilder cardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public CardPaymentBuilder cardExpirationDate(String cardExpirationDate) {
            this.cardExpirationDate = cardExpirationDate;
            return this;
        }

        public CardPaymentBuilder cardCvc(String cardCvc) {
            this.cardCvc = cardCvc;
            return this;
        }

        public CardPaymentBuilder cardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
            return this;
        }

        public CardPayment build() {
            return new CardPayment(destinationIban, amount, concept, cardNumber, cardExpirationDate, cardCvc, cardHolderName);
        }
    }
    public YearMonth getParsedExpirationDate() {
        if (cardExpirationDate == null || cardExpirationDate.isEmpty()) {
            throw new IllegalArgumentException("Payment validation failed: Expiration Date is required.");
        }

        int year, month;
        if (cardExpirationDate.matches("^\\d{4}-\\d{2}$")) {
            year = Integer.parseInt(cardExpirationDate.substring(0, 4));
            month = Integer.parseInt(cardExpirationDate.substring(5, 7));
        } else if (cardExpirationDate.matches("^\\d{2}/\\d{2}$")) {
            month = Integer.parseInt(cardExpirationDate.substring(0, 2));
            year = 2000 + Integer.parseInt(cardExpirationDate.substring(3, 5));
        } else if (cardExpirationDate.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            year = Integer.parseInt(cardExpirationDate.substring(0, 4));
            month = Integer.parseInt(cardExpirationDate.substring(5, 7));
        } else {
            throw new IllegalArgumentException("Payment validation failed: Invalid expiration date format. Expected MM/YY or YYYY-MM.");
        }
        return YearMonth.of(year, month);
    }

    public void validateCredentials(CreditCard card) {
        if (!card.getCvc().equals(this.cardCvc)) {
            throw new IllegalArgumentException("Credit card authentication failed: Invalid CVC.");
        }
        if (!card.getYearMonth().equals(this.getParsedExpirationDate())) {
            throw new IllegalArgumentException("Credit card authentication failed: Incorrect expiration date.");
        }
        if (this.cardHolderName == null || !this.cardHolderName.equalsIgnoreCase(card.getFullName())) {
            throw new IllegalArgumentException("Credit card authentication failed: Cardholder name does not match.");
        }
    }
}
