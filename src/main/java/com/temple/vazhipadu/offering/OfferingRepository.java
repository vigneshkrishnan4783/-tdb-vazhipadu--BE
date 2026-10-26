package com.temple.vazhipadu.offering;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OfferingRepository extends JpaRepository<Offering, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM Offering o WHERE o.id = :id")
    Optional<Offering> findByIdForUpdate(@Param("id") Long id);
}
