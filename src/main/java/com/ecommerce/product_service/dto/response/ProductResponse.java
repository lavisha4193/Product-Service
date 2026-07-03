package com.ecommerce.product_service.dto.response;

import com.ecommerce.product_service.entity.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Category category;

    private String brand;

    private boolean active;
}