package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.UserDto;

public interface UsersService {
    UserDto addUser(long telegramId);

    UserDto deleteUser(long telegramId);
}
