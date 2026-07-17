package com.ecommerce.product_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Consumed from order.exchange / order.placed.
 * Product Service uses it to reduce stock for each item.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {

    private Long orderId;
    private String orderNumber;
    private Long customerId;
    private String customerEmail;
    private List<OrderItemMessage> items;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemMessage {
        private Long productId;
        private Integer quantity;
    }
}
