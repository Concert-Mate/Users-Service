package ru.nsu.concerts_mate.users_service.services.exceptions;

public class RabbitMqException extends Exception {
    public RabbitMqException(String message, Throwable cause) {
        super(message, cause);
    }
}
