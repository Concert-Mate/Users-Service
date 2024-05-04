package ru.nsu.concert_mate.user_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private final AmqpAdmin amqpAdmin;

    private final Queue queue;

    @Value("${spring.rabbitmq.queue}")
    private String queueName;

    @Autowired
    public RabbitMqConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
        this.queue = new Queue(queueName);
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(queue);
    }

    @Bean
    public Queue queue() {
        return queue;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
