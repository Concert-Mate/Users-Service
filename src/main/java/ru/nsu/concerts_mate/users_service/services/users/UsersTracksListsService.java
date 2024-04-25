package ru.nsu.concerts_mate.users_service.services.users;

import ru.nsu.concerts_mate.users_service.model.dto.UserTracksListDto;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListNotAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersTracksListsService {
    UserTracksListDto saveUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListAlreadyAddedException, InternalErrorException;

    UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException;

    List<String> getUserTracksLists(long telegramId) throws UserNotFoundException, InternalErrorException;
}
