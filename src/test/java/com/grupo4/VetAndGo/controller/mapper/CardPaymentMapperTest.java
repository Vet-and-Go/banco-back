package com.grupo4.VetAndGo.controller.mapper;

import com.grupo4.VetAndGo.controller.dto.*;
import com.grupo4.VetAndGo.domain.model.CardPayment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CardPaymentMapperTest {

    @Test
    void toDomainCardPayment_ShouldMapPaymentToCardPayment_WhenValidPaymentProvided() {
        // Arrange
        Authorization authorization = new Authorization("testUser", "testToken");
        Origin origin = new Origin(
                "1234567890123456",
                "12/25",
                "123",
                "John Doe"
        );
        Destination destination = new Destination("ES1234567890123456789012");
        PaymentDetails paymentDetails = new PaymentDetails(
                new BigDecimal("100.50"),
                "Test payment"
        );
        Payment payment = new Payment(authorization, origin, destination, paymentDetails);

        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(payment);

        // Assert
        assertNotNull(result);
        assertEquals("ES1234567890123456789012", result.getDestinationIban());
        assertEquals(new BigDecimal("100.50"), result.getAmount());
        assertEquals("Test payment", result.getConcept());
        assertEquals("1234567890123456", result.getCardNumber());
        assertEquals("12/25", result.getCardExpirationDate());
        assertEquals("123", result.getCardCvc());
        assertEquals("John Doe", result.getCardHolderName());
    }

    @Test
    void toDomainCardPayment_ShouldReturnNull_WhenPaymentIsNull() {
        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toDomainCardPayment_ShouldMapWithNullConcept_WhenConceptIsNull() {
        // Arrange
        Authorization authorization = new Authorization("testUser", "testToken");
        Origin origin = new Origin(
                "1234567890123456",
                "12/25",
                "123",
                "John Doe"
        );
        Destination destination = new Destination("ES1234567890123456789012");
        PaymentDetails paymentDetails = new PaymentDetails(
                new BigDecimal("50.00"),
                null  // concept is null
        );
        Payment payment = new Payment(authorization, origin, destination, paymentDetails);

        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(payment);

        // Assert
        assertNotNull(result);
        assertNull(result.getConcept());
        assertEquals("ES1234567890123456789012", result.getDestinationIban());
        assertEquals(new BigDecimal("50.00"), result.getAmount());
    }

    @Test
    void toDomainCardPayment_ShouldMapWithNullFullName_WhenFullNameIsNull() {
        // Arrange
        Authorization authorization = new Authorization("testUser", "testToken");
        Origin origin = new Origin(
                "1234567890123456",
                "12/25",
                "123",
                null  // fullName is null
        );
        Destination destination = new Destination("ES1234567890123456789012");
        PaymentDetails paymentDetails = new PaymentDetails(
                new BigDecimal("75.25"),
                "Payment without name"
        );
        Payment payment = new Payment(authorization, origin, destination, paymentDetails);

        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(payment);

        // Assert
        assertNotNull(result);
        assertNull(result.getCardHolderName());
        assertEquals("1234567890123456", result.getCardNumber());
        assertEquals("12/25", result.getCardExpirationDate());
    }

    @Test
    void toDomainCardPayment_ShouldMapWithAlternativeExpirationDateFormat_WhenYearMonthFormatProvided() {
        // Arrange
        Authorization authorization = new Authorization("testUser", "testToken");
        Origin origin = new Origin(
                "9876543210987654",
                "2025-12",  // alternative format YYYY-MM
                "456",
                "Jane Smith"
        );
        Destination destination = new Destination("ES9876543210987654321098");
        PaymentDetails paymentDetails = new PaymentDetails(
                new BigDecimal("200.00"),
                "Alternative date format"
        );
        Payment payment = new Payment(authorization, origin, destination, paymentDetails);

        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(payment);

        // Assert
        assertNotNull(result);
        assertEquals("2025-12", result.getCardExpirationDate());
        assertEquals("9876543210987654", result.getCardNumber());
        assertEquals("456", result.getCardCvc());
        assertEquals("Jane Smith", result.getCardHolderName());
    }

    @Test
    void toDomainCardPayment_ShouldMapWithMinimumAmount_WhenSmallAmountProvided() {
        // Arrange
        Authorization authorization = new Authorization("testUser", "testToken");
        Origin origin = new Origin(
                "1111222233334444",
                "01/26",
                "789",
                "Test User"
        );
        Destination destination = new Destination("ES1111222233334444555566");
        PaymentDetails paymentDetails = new PaymentDetails(
                new BigDecimal("0.01"),  // minimum amount
                "Minimum payment"
        );
        Payment payment = new Payment(authorization, origin, destination, paymentDetails);

        // Act
        CardPayment result = CardPaymentMapper.toDomainCardPayment(payment);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("0.01"), result.getAmount());
        assertEquals("Minimum payment", result.getConcept());
    }
}

