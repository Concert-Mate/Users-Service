package ru.nsu.concertsmate.backend.services;

public interface UsersService {
    void addUser(long telegramId);

    void deleteUser(long telegramId);
}
