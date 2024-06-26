package com.company.payment_service.dto.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum PaymentMethodDto {

    @JsonProperty("Credit Card")
    CREDIT_CARD("Credit Card"),

    @JsonProperty("Debit Card")
    DEBIT_CARD("Debit Card"),

    @JsonProperty("Bank Transfer")
    BANK_TRANSFER("Bank Transfer"),

    @JsonProperty("PayPal")
    PAYPAL("PayPal"),

    @JsonProperty("Google Pay")
    GOOGLE_PAY("Google Pay"),

    @JsonProperty("Apple Pay")
    APPLE_PAY("Apple Pay"),

    @JsonProperty("Other")
    OTHER("Other");

    private final String name;

    PaymentMethodDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
