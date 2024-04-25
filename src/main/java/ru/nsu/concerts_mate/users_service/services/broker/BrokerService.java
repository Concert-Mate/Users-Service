package ru.nsu.concerts_mate.users_service.services.broker;

public interface BrokerService {
    void sendEvent(BrokerEvent event) throws BrokerException;
}
