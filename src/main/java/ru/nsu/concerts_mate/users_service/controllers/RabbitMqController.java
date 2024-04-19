package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.rabbitmq.RabbitMqApi;

@RestController
public class RabbitMqController implements RabbitMqApi {
    private final AmqpTemplate template;
    private final Queue queue;

    @Autowired
    public RabbitMqController(AmqpTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    @Override
    public String emit(@RequestParam String message) {
        try {
            template.convertAndSend(queue.getName(), message);
            return "Success";
        } catch (Exception exception) {
            return "Failure: " + exception.getMessage();
        }
    }
}
