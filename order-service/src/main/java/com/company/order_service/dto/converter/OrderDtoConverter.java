package com.company.order_service.dto.converter;

import com.company.order_service.dto.OrderDto;
import com.company.order_service.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoConverter {

    public OrderDto convert(Order from) {
        return new OrderDto(
                from.getId(),
                from.getUserEmail(),
                from.getProductId(),
                from.getQuantity(),
                from.getTotalAmount(),
                from.getOrderDate(),
                from.getDeliveryAddress(),
                OrderStatusDtoConverter.convertToDto(from.getOrderStatus()));
    }

    public List<OrderDto> convert(List<Order> from) {
        return from.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
