package ru.nsu.concerts_mate.user_service.api.rabbitmq;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/emit")
public interface RabbitMqApi {
    @PostMapping
    String emit();
}
