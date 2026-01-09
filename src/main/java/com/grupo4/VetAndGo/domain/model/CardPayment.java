package com.grupo4.VetAndGo.domain.model;

import java.math.BigDecimal;

public class CardPayment {

    private String destinationIban;
    private BigDecimal amount;
    private String concept;
    private String cardNumber;
    private String cardExpirationDate;
    private String cardCvv;

    public CardPayment() {
    }

    public CardPayment(String destinationIban, BigDecimal amount, String concept, String cardNumber, String cardExpirationDate, String cardCvv) {
        this.destinationIban = destinationIban;
        this.amount = amount;
        this.concept = concept;
        this.cardNumber = cardNumber;
        this.cardExpirationDate = cardExpirationDate;
        this.cardCvv = cardCvv;
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

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public static class CardPaymentBuilder {
        private String destinationIban;
        private BigDecimal amount;
        private String concept;
        private String cardNumber;
        private String cardExpirationDate;
        private String cardCvv;

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

        public CardPaymentBuilder cardCvv(String cardCvv) {
            this.cardCvv = cardCvv;
            return this;
        }

        public CardPayment build() {
            return new CardPayment(destinationIban, amount, concept, cardNumber, cardExpirationDate, cardCvv);
        }
    }
}
