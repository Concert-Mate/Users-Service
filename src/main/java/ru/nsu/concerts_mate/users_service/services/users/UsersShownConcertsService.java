package ru.nsu.concerts_mate.users_service.services.users;

import ru.nsu.concerts_mate.users_service.model.dto.ShownConcertDto;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.ShownConcertAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.ShownConcertNotFoundException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersShownConcertsService {
    ShownConcertDto saveShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertAlreadyAddedException, InternalErrorException;

    ShownConcertDto deleteShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertNotFoundException;

    List<String> getShownConcerts(long telegramId) throws UserNotFoundException, InternalErrorException;

    boolean hasShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, InternalErrorException;
}
