package ru.nsu.concerts_mate.users_service.services.users;

import ru.nsu.concerts_mate.users_service.model.dto.UserTrackListDto;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.TracksListNotAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersTrackListsService {
    UserTrackListDto saveUserTrackList(long telegramId, String cityName) throws UserNotFoundException, TracksListAlreadyAddedException, InternalErrorException;

    UserTrackListDto deleteUserTrackList(long telegramId, String cityName) throws UserNotFoundException, TracksListNotAddedException;

    List<String> getUserTrackLists(long telegramId) throws UserNotFoundException, InternalErrorException;
}
