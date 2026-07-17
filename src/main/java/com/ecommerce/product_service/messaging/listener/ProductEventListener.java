package com.ecommerce.product_service.messaging.listener;

import com.ecommerce.product_service.config.RabbitMQConfig;
import com.ecommerce.product_service.dto.response.ProductResponse;
import com.ecommerce.product_service.messaging.event.GetProductRequest;
import com.ecommerce.product_service.messaging.event.OrderPlacedEvent;
import com.ecommerce.product_service.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventListener {

    private final ProductService productService;

    /**
     * Request-reply: Order Service asks for product details.
     * Spring AMQP routes the return value to the reply-to queue automatically.
     */
    @RabbitListener(queues = RabbitMQConfig.PRODUCT_GET_QUEUE)
    public ProductResponse handleGetProductRequest(GetProductRequest request) {
        log.info("[Product Service] Product fetch request — productId={}", request.getProductId());
        try {
            return productService.getProduct(request.getProductId());
        } catch (Exception ex) {
            log.warn("[Product Service] Product not found for id={}: {}", request.getProductId(), ex.getMessage());
            return null;
        }
    }

    /**
     * Async: reduce stock for every item in a placed order.
     * Compensating-transaction pattern — failures are logged and should
     * trigger an OrderCancelled event in production.
     */
    @RabbitListener(queues = RabbitMQConfig.ORDER_PLACED_QUEUE)
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("[Product Service] OrderPlaced — orderId={} orderNumber={}",
                event.getOrderId(), event.getOrderNumber());

        for (OrderPlacedEvent.OrderItemMessage item : event.getItems()) {
            try {
                productService.reduceStock(item.getProductId(), item.getQuantity());
                log.info("[Product Service] Stock reduced — productId={} qty={}",
                        item.getProductId(), item.getQuantity());
            } catch (Exception ex) {
                log.error("[Product Service] Stock reduction failed — productId={}: {}",
                        item.getProductId(), ex.getMessage());
            }
        }
    }

}

