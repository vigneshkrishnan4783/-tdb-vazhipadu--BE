package com.temple.vazhipadu.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record DailyCollectionResponse(
        LocalDate bookingDate,
        List<OfferingCollection> offerings,
        BigDecimal totalAmount
) {
    public record OfferingCollection(
            Long offeringId,
            String offeringName,
            Long orderCount,
            BigDecimal totalAmount
    ) {}
}