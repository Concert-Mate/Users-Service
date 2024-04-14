package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.UserTracksListDto;
import ru.nsu.concertsmate.backend.services.exceptions.*;

import java.util.List;

public interface TracksListsService {
    UserTracksListDto saveUserTracksList(long telegramId, String cityName) throws UserNotFound, TracksListAlreadyAdded;

    UserTracksListDto deleteUserTracksList(long telegramId, String cityName) throws UserNotFound, TracksListNotAdded;

    List<String> getUserTracksLists(long telegramId) throws UserNotFound;
}
