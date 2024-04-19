package ru.nsu.concerts_mate.users_service.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private final AmqpAdmin amqpAdmin;

    private final Queue queue;

    @Autowired
    public RabbitMqConfig(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
        this.queue = new Queue("new-concerts-queue");
    }

    @PostConstruct
    public void createQueues() {
        amqpAdmin.declareQueue(queue);
    }

    @Bean
    public Queue queue() {
        return queue;
    }
}
