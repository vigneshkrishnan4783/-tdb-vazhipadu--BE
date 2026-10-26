
package com.temple.vazhipadu.order;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record CreateOrderRequest(

        @NotBlank
        String devoteeName,

        @NotBlank
        @Pattern(regexp = "^[0-9]{10}$")
        String phoneNumber,

        @NotBlank
        String nakshatra,

        @NotNull
        @FutureOrPresent
        LocalDate bookingDate,

        @NotNull
        @Positive
        Long offeringId,

        @Positive
        int quantity

) {
}
