package com.company.payment_service.dto.enums;

public enum PaymentMethodDto {

    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    BANK_TRANSFER("Bank Transfer"),
    PAYPAL("PayPal"),
    GOOGLE_PAY("Google Pay"),
    APPLE_PAY("Apple Pay"),
    OTHER("Other");

    private final String name;

    PaymentMethodDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
