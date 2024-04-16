package ru.nsu.concerts_mate.users_service.services;

import ru.nsu.concerts_mate.users_service.model.dto.UserTracksListDto;
import ru.nsu.concerts_mate.users_service.services.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.exceptions.TracksListNotAddedException;
import ru.nsu.concerts_mate.users_service.services.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersTracksListsService {
    UserTracksListDto saveUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListAlreadyAddedException, InternalErrorException;

    UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException;

    List<String> getUserTracksLists(long telegramId) throws UserNotFoundException, InternalErrorException;
}
