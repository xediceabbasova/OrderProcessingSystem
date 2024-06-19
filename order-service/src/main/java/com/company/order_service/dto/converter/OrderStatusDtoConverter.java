package com.company.order_service.dto.converter;

import com.company.order_service.dto.OrderStatusDto;
import com.company.order_service.model.OrderStatus;

public class OrderStatusDtoConverter {

    public static OrderStatusDto convertToDto(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PENDING -> OrderStatusDto.PENDING;
            case CONFIRMED -> OrderStatusDto.CONFIRMED;
            case CANCELLED -> OrderStatusDto.CANCELLED;
        };
    }
}
