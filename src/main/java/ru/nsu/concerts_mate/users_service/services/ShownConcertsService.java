package ru.nsu.concerts_mate.users_service.services;

import ru.nsu.concerts_mate.users_service.model.dto.ShownConcertDto;
import ru.nsu.concerts_mate.users_service.services.exceptions.*;

import java.util.List;

public interface ShownConcertsService {
    ShownConcertDto saveShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertAlreadyAddedException, InternalErrorException;

    ShownConcertDto deleteShownConcert(long telegramId, String concertUrl) throws UserNotFoundException, ShownConcertNotFoundException;

    List<String> getShownConcerts(long telegramId) throws UserNotFoundException, InternalErrorException;
}
