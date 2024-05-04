package ru.nsu.concert_mate.user_service.services.users;

import ru.nsu.concert_mate.user_service.model.dto.UserTrackListDto;
import ru.nsu.concert_mate.user_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concert_mate.user_service.services.users.exceptions.TrackListAlreadyAddedException;
import ru.nsu.concert_mate.user_service.services.users.exceptions.TrackListNotAddedException;
import ru.nsu.concert_mate.user_service.services.users.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersTrackListsService {
    UserTrackListDto saveUserTrackList(long telegramId, String cityName) throws UserNotFoundException, TrackListAlreadyAddedException, InternalErrorException;

    UserTrackListDto deleteUserTrackList(long telegramId, String cityName) throws UserNotFoundException, TrackListNotAddedException;

    List<String> getUserTrackLists(long telegramId) throws UserNotFoundException, InternalErrorException;
}
