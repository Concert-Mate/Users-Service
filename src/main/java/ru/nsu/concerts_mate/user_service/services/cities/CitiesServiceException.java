package ru.nsu.concerts_mate.user_service.services.cities;

public class CitiesServiceException extends Exception {
    public CitiesServiceException(String message) {
        super(message);
    }

    public CitiesServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
