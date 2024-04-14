package ru.nsu.concertsmate.users_service.services;

import ru.nsu.concertsmate.users_service.model.dto.UserTracksListDto;
import ru.nsu.concertsmate.users_service.services.exceptions.TracksListAlreadyAdded;
import ru.nsu.concertsmate.users_service.services.exceptions.TracksListNotAdded;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFound;

import java.util.List;

public interface TracksListsService {
    UserTracksListDto saveUserTracksList(long telegramId, String cityName) throws UserNotFound, TracksListAlreadyAdded;

    UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFound, TracksListNotAdded;

    List<String> getUserTracksLists(long telegramId) throws UserNotFound;
}
