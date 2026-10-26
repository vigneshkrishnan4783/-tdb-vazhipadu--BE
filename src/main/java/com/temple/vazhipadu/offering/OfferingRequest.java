package com.temple.vazhipadu.offering;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OfferingRequest(
        @NotBlank String name,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        boolean active
) {
}
