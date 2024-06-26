package com.company.order_service.service;

import com.company.order_service.dto.OrderDto;
import com.company.order_service.dto.converter.OrderDtoConverter;
import com.company.order_service.dto.request.OrderRequest;
import com.company.order_service.exception.OrderNotFoundException;
import com.company.order_service.kafka.KafkaProducer;
import com.company.order_service.model.Order;
import com.company.order_service.model.OrderStatus;
import com.company.order_service.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoConverter orderDtoConverter;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public OrderService(OrderRepository orderRepository, OrderDtoConverter orderDtoConverter, KafkaProducer kafkaProducer) {
        this.orderRepository = orderRepository;
        this.orderDtoConverter = orderDtoConverter;
        this.kafkaProducer = kafkaProducer;
    }

    public OrderDto createOrder(final OrderRequest orderRequest) {
        Order order = new Order(
                orderRequest.userEmail(),
                orderRequest.productId(),
                orderRequest.quantity(),
                orderRequest.totalAmount(),
                orderRequest.deliveryAddress()
        );
        Order savedOrder = orderRepository.save(order);
        OrderDto orderDto = orderDtoConverter.convert(savedOrder);

        String message;
        try {
            message = objectMapper.writeValueAsString(orderDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting OrderDto to JSON", e);
        }
        kafkaProducer.sendMessage(message);

        return orderDto;
    }

    public List<OrderDto> getAllOrders() {
        return orderDtoConverter.convert(orderRepository.findAll());
    }

    public OrderDto getOrderById(final String id) {
        return orderDtoConverter.convert(findOrderById(id));
    }

    public void confirmOrder(final String id) {
        Order order = findOrderById(id);
        order.updateOrderStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    public void cancelOrder(final String id) {
        Order order = findOrderById(id);
        order.updateOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public void deleteOrder(final String id) {
        findOrderById(id);
        orderRepository.deleteById(id);
    }

    private Order findOrderById(final String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

}
