package ru.nsu.concerts_mate.users_service.services;

import ru.nsu.concerts_mate.users_service.services.exceptions.RabbitMqException;

public interface RabbitMqService {
    void sendEvent(RabbitMqEvent event) throws RabbitMqException;
}
