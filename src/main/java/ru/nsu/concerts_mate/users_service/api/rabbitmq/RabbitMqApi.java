package ru.nsu.concerts_mate.users_service.api.rabbitmq;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/emit")
public interface RabbitMqApi {
    @PostMapping
    String emit(@RequestParam String message);
}
