package com.temple.vazhipadu.order;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "temple_orders")
public class TempleOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String devoteeName;

    @Column(nullable = false, length = 10)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate bookingDate;

    @Column(nullable = false)
    private Long offeringId;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    protected TempleOrder() {
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.BOOKED;
    public TempleOrder(String devoteeName, String phoneNumber,
                       LocalDate bookingDate, Long offeringId,
                       int quantity, BigDecimal totalAmount) {
        this.devoteeName = devoteeName;
        this.phoneNumber = phoneNumber;
        this.bookingDate = bookingDate;
        this.offeringId = offeringId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public Long getId() { return id; }
    public String getDevoteeName() { return devoteeName; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getBookingDate() { return bookingDate; }
    public Long getOfferingId() { return offeringId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
