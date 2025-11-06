package com.unimag.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "marketplace.topic";
    public static final String QUEUE_RESERVE = "reserve.inventory.queue";
    public static final String ROUTING_PATTERN = "reserve.inventory.#";
    public static final String DLX_EXCHANGE = "dlx.exchange";
    public static final String DLQ = "dlq.notifications.email";

    @Bean
    public TopicExchange projectsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    // Dead Letter Exchange
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    // Cola principal con DLQ configurada
    @Bean
    public Queue reserveQueue() {
        return QueueBuilder.durable(QUEUE_RESERVE)
                .withArgument("x-message-ttl", 3600000) // 1 hora
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "dlq.notifications")
                .build();
    }

    // Cola de mensajes muertos
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ, true);
    }

    @Bean
    public Binding bindEmailQueue(Queue reserveQueue, TopicExchange projectsExchange) {
        return BindingBuilder.bind(reserveQueue).to(projectsExchange).with(ROUTING_PATTERN);
    }

    @Bean
    public Binding bindDeadLetterQueue(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("dlq.notifications");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
