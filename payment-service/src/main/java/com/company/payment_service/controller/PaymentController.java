package com.company.payment_service.controller;

import com.company.payment_service.dto.PaymentDto;
import com.company.payment_service.dto.request.PaymentRequest;
import com.company.payment_service.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentDto> processPayment(@PathVariable String orderId, @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.processPayment(orderId, request));
    }
}
