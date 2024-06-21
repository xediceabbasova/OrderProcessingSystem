package com.company.payment_service.service;

import com.company.payment_service.dto.converter.PaymentDtoConverter;
import com.company.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDtoConverter paymentDtoConverter;

    public PaymentService(PaymentRepository paymentRepository, PaymentDtoConverter paymentDtoConverter) {
        this.paymentRepository = paymentRepository;
        this.paymentDtoConverter = paymentDtoConverter;
    }




}
