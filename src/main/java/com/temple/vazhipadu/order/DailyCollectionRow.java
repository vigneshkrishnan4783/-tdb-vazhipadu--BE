package com.temple.vazhipadu.order;

import java.math.BigDecimal;

public interface DailyCollectionRow {

    Long getOfferingId();

    Long getOrderCount();

    BigDecimal getTotalAmount();
}