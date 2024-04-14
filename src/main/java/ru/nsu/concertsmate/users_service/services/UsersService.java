package ru.nsu.concertsmate.users_service.services;

import ru.nsu.concertsmate.users_service.model.dto.UserDto;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFoundException;

import java.util.Optional;

public interface UsersService {
    UserDto addUser(long telegramId);

    UserDto deleteUser(long telegramId) throws UserNotFoundException;

    Optional<UserDto> findUser(long telegramId);
}
