package com.ecommerce.product_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // ── Exchange names ─────────────────────────────────────────────────────
    public static final String ORDER_EXCHANGE    = "order.exchange";
    public static final String PRODUCT_EXCHANGE  = "product.exchange";

    // ── Queue names ────────────────────────────────────────────────────────
    /** Request-reply: Order Service asks; Product Service answers. */
    public static final String PRODUCT_GET_QUEUE         = "product.get";
    /** Async: Order Service publishes; Product Service reduces stock. */
    public static final String ORDER_PLACED_QUEUE        = "order.placed";

    // ── Routing keys ───────────────────────────────────────────────────────
    public static final String PRODUCT_GET_KEY           = "product.get";
    public static final String ORDER_PLACED_KEY          = "order.placed";

    // ── Exchanges ──────────────────────────────────────────────────────────
    @Bean
    public TopicExchange orderExchange() {
        return ExchangeBuilder.topicExchange(ORDER_EXCHANGE).durable(true).build();
    }

    @Bean
    public TopicExchange productExchange() {
        return ExchangeBuilder.topicExchange(PRODUCT_EXCHANGE).durable(true).build();
    }

    // ── Queues ─────────────────────────────────────────────────────────────
    @Bean
    public Queue productGetQueue() {
        return QueueBuilder.durable(PRODUCT_GET_QUEUE).build();
    }

    @Bean
    public Queue orderPlacedQueue() {
        return QueueBuilder.durable(ORDER_PLACED_QUEUE).build();
    }

    // ── Bindings ───────────────────────────────────────────────────────────
    @Bean
    public Binding productGetBinding(Queue productGetQueue, TopicExchange productExchange) {
        return BindingBuilder.bind(productGetQueue).to(productExchange).with(PRODUCT_GET_KEY);
    }

    @Bean
    public Binding orderPlacedBinding(Queue orderPlacedQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(orderPlacedQueue).to(orderExchange).with(ORDER_PLACED_KEY);
    }

    // ── JSON converter ─────────────────────────────────────────────────────
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    /** Make @RabbitListener methods use the JSON converter automatically. */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}
