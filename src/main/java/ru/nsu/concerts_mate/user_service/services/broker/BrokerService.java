package ru.nsu.concerts_mate.user_service.services.broker;

public interface BrokerService {
    void sendEvent(BrokerEvent event) throws BrokerException;
}
