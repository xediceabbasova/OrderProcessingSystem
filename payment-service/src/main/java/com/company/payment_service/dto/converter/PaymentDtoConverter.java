package com.company.payment_service.dto.converter;

import com.company.payment_service.dto.PaymentDto;
import com.company.payment_service.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoConverter {

    public PaymentDto convert(Payment from) {
        return new PaymentDto(
                from.getId(),
                from.getOrderId(),
                from.getAmount(),
                from.getPaymentDate(),
                PaymentMethodDtoConverter.convertToDto(from.getPaymentMethod()),
                PaymentStatusDtoConverter.convertToDto(from.getPaymentStatus()),
                CurrencyDtoConverter.convertToDto(from.getCurrency())
        );
    }
}
