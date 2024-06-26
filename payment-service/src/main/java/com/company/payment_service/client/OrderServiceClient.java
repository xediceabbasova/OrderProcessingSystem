package com.company.payment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "order-service", path = "/v1/orders")
public interface OrderServiceClient {

    @PutMapping("/{id}/confirm")
    ResponseEntity<Void> confirmOrder(@PathVariable(value = "id") String id);

    @PutMapping("/{id}/cancel")
    ResponseEntity<Void> cancelOrder(@PathVariable(value = "id") String id);

}
