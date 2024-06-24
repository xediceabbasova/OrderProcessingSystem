package com.company.notification_service.service;

import com.company.notification_service.dto.OrderDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
public class NotificationService {

    @Value("${spring.mail.username}")
    private String username;

    @Value("classpath:templates/mail.html")
    private Resource resource;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final JavaMailSender javaMailSender;

    public NotificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMessage(OrderDto orderDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(orderDto.userEmail());
            helper.setFrom(username);
            helper.setSubject("Order " + orderDto.orderStatus());

            String html = getTemplate(resource);
            html = html.replace("{orderId}", orderDto.id())
                    .replace("{orderStatus}", orderDto.orderStatus().toString().toLowerCase())
                    .replace("{totalAmount}", orderDto.totalAmount().toString())
                    .replace("{orderDate}", orderDto.orderDate().toString());

            helper.setText(html, true);

            javaMailSender.send(mimeMessage);
            logger.info("Notification email sent to {}", orderDto.userEmail());
        } catch (MessagingException e) {
            logger.error("Failed to send email to {}", orderDto.userEmail(), e);
        }
    }

    private String getTemplate(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading the template file", e);
        }
    }
}
