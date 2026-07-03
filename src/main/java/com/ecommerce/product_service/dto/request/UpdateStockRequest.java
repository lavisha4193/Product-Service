package com.ecommerce.product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateStockRequest {

    @NotNull(message = "Quantity is required")
    @Min(value = 1)
    private Integer quantity;

}