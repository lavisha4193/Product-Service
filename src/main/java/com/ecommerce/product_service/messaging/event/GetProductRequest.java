package com.ecommerce.product_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Received from Order Service via request-reply on product.exchange / product.get.
 * Product Service returns a ProductResponse as the reply.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductRequest {

    private Long productId;
}
