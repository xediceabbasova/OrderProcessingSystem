package com.company.notification_service.service;

import com.company.notification_service.dto.OrderDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(OrderDto orderDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(orderDto.userEmail());
        message.setFrom(username);
        message.setSubject("Order Confirmation");
        message.setText("Your order with ID: " + orderDto.id() + " has been confirmed.");

        javaMailSender.send(message);

    }
}
