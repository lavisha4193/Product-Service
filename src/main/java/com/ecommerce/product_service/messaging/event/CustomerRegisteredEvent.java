package com.ecommerce.product_service.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Received from customer.exchange / customer.registered
 * when a new customer registers in Customer Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRegisteredEvent {

    private Long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDateTime registeredAt;
}
