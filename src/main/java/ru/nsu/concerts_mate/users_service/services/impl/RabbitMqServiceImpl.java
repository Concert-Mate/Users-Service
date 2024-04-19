package ru.nsu.concerts_mate.users_service.services.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.services.RabbitMqEvent;
import ru.nsu.concerts_mate.users_service.services.RabbitMqService;

import java.awt.image.RasterFormatException;

@Service
public class RabbitMqServiceImpl implements RabbitMqService {
    private final AmqpTemplate template;
    private final Queue queue;

    @Autowired
    public RabbitMqServiceImpl(AmqpTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    @Override
    public void sendEvent(RabbitMqEvent event) throws RasterFormatException {
        template.convertAndSend(queue.getName(), event);
    }
}
