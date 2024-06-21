package com.company.order_service.service;

import com.company.order_service.dto.OrderDto;
import com.company.order_service.dto.converter.OrderDtoConverter;
import com.company.order_service.dto.request.OrderRequest;
import com.company.order_service.exception.OrderNotFoundException;
import com.company.order_service.kafka.KafkaProducer;
import com.company.order_service.model.Order;
import com.company.order_service.model.OrderStatus;
import com.company.order_service.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDtoConverter orderDtoConverter;
    private final KafkaProducer kafkaProducer;

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
        kafkaProducer.sendMessage(orderDto.toString());
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
