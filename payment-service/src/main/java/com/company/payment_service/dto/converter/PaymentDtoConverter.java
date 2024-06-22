package com.company.payment_service.dto.converter;

import com.company.payment_service.dto.PaymentDto;
import com.company.payment_service.dto.enums.CurrencyDto;
import com.company.payment_service.dto.enums.PaymentMethodDto;
import com.company.payment_service.dto.enums.PaymentStatusDto;
import com.company.payment_service.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentDtoConverter {

    public PaymentDto convert(Payment from) {
        return new PaymentDto(
                from.getId(),
                from.getOrderId(),
                from.getPaymentDate(),
                PaymentMethodDto.valueOf(from.getPaymentMethod().getName()),
                PaymentStatusDto.valueOf(from.getPaymentStatus().name()),
                CurrencyDto.valueOf(from.getCurrency().name())
        );
    }}
