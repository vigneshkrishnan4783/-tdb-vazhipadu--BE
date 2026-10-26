package com.temple.vazhipadu.order;

import com.temple.vazhipadu.offering.Offering;
import com.temple.vazhipadu.offering.OfferingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OfferingRepository offeringRepository;

    public OrderService(OrderRepository orderRepository,
                        OfferingRepository offeringRepository) {
        this.orderRepository = orderRepository;
        this.offeringRepository = offeringRepository;
    }
    @Transactional
    public TempleOrder createOrder(CreateOrderRequest request) {

        if (request.quantity() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than zero"
            );
        }

        Offering offering = offeringRepository
                .findByIdForUpdate(request.offeringId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Offering not found"
                ));

        if (!offering.isActive()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Offering is inactive"
            );
        }

        Integer dailyLimit = offering.getDailyLimit();

        // Validate daily booking limit
        if (dailyLimit == null || dailyLimit <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Daily limit is not configured for this offering"
            );
        }

        long bookedQuantity = orderRepository.sumBookedQuantity(
                offering.getId(),
                request.bookingDate(),
                OrderStatus.BOOKED
        );

        if (bookedQuantity + request.quantity() > dailyLimit) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Daily booking limit exceeded. Limit: "
                            + dailyLimit
                            + ", Already booked: "
                            + bookedQuantity
            );
        }

        BigDecimal total = offering.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));

        TempleOrder order = new TempleOrder(
                request.devoteeName(),
                request.phoneNumber(),
                request.bookingDate(),
                offering.getId(),
                request.quantity(),
                total
        );

        return orderRepository.save(order);
    }



    public TempleOrder getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Transactional(readOnly = true)
    public Page<TempleOrder> searchOrders(
            LocalDate bookingDate,
            OrderStatus status,
            String phoneNumber,
            int page,
            int size) {

        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Page must be >= 0 and size must be between 1 and 100"
            );
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Order.desc("bookingDate"),
                        Sort.Order.desc("id")
                )
        );

        String normalizedPhone =
                phoneNumber == null || phoneNumber.isBlank()
                        ? null
                        : phoneNumber.trim();

        return orderRepository.searchOrders(
                bookingDate,
                status,
                normalizedPhone,
                pageable
        );
    }
}
