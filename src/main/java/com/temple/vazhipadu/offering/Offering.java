package com.temple.vazhipadu.offering;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "offerings")
public class Offering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "daily_limit")
    private Integer dailyLimit;
    protected Offering() {
    }

    public Offering(String name, BigDecimal price, boolean active) {
        this.name = name;
        this.price = price;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }
    public Integer getDailyLimit() {
        return dailyLimit;
    }

    public void update(String name, BigDecimal price, boolean active) {
        this.name = name;
        this.price = price;
        this.active = active;
    }
}
