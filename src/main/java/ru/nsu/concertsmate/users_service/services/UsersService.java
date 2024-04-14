package ru.nsu.concertsmate.users_service.services;

import ru.nsu.concertsmate.users_service.model.dto.UserDto;

public interface UsersService {
    UserDto addUser(long telegramId);

    UserDto deleteUser(long telegramId);

    UserDto findUser(long telegramId);
}
