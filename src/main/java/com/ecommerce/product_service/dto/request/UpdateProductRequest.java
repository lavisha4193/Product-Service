package com.ecommerce.product_service.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String category;

    private String brand;
}