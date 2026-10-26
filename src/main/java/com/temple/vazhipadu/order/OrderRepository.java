package com.temple.vazhipadu.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<TempleOrder, Long> {

    @Query(
            value = """
            SELECT o
            FROM TempleOrder o
            WHERE (:bookingDate IS NULL
                   OR o.bookingDate = :bookingDate)
              AND (:status IS NULL
                   OR o.status = :status)
              AND (:phoneNumber IS NULL
                   OR o.phoneNumber = :phoneNumber)
            """,
            countQuery = """
            SELECT COUNT(o)
            FROM TempleOrder o
            WHERE (:bookingDate IS NULL
                   OR o.bookingDate = :bookingDate)
              AND (:status IS NULL
                   OR o.status = :status)
              AND (:phoneNumber IS NULL
                   OR o.phoneNumber = :phoneNumber)
            """
    )

    Page<TempleOrder> searchOrders(
            @Param("bookingDate") LocalDate bookingDate,
            @Param("status") OrderStatus status,
            @Param("phoneNumber") String phoneNumber,
            Pageable pageable
    );
//booked quantity
@Query("""
    SELECT COALESCE(SUM(o.quantity), 0)
    FROM TempleOrder o
    WHERE o.offeringId = :offeringId
      AND o.bookingDate = :bookingDate
      AND o.status = :status
    """)
long sumBookedQuantity(
        @Param("offeringId") Long offeringId,
        @Param("bookingDate") LocalDate bookingDate,
        @Param("status") OrderStatus status
);
    @Query("""
    SELECT
        o.offeringId AS offeringId,
        COUNT(o.id) AS orderCount,
        COALESCE(SUM(o.totalAmount), 0) AS totalAmount
    FROM TempleOrder o
    WHERE o.bookingDate = :bookingDate
      AND o.status = :status
    GROUP BY o.offeringId
    ORDER BY o.offeringId
    """)
    List<DailyCollectionRow> getDailyCollection(
            @Param("bookingDate") LocalDate bookingDate,
            @Param("status") OrderStatus status
    );
}
