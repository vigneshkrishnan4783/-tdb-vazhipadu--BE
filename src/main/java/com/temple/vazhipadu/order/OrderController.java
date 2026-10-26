
package com.temple.vazhipadu.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<TempleOrder> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        TempleOrder order = orderService.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    @GetMapping("/{id}")
    public TempleOrder getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public Page<TempleOrder> searchOrders(
            @RequestParam(required = false) LocalDate bookingDate,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        return orderService.searchOrders(
                bookingDate,
                status,
                phoneNumber,
                page,
                size
        );
    }
}
