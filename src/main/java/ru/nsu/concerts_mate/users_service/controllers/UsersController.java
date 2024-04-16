package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.users.*;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;
import ru.nsu.concerts_mate.users_service.services.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.UsersService;
import ru.nsu.concerts_mate.users_service.services.UsersTracksListsService;
import ru.nsu.concerts_mate.users_service.services.exceptions.*;

import java.util.Optional;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final UsersCitiesService citiesService;
    private final UsersTracksListsService tracksListsService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService citiesService, UsersTracksListsService tracksListsService) {
        this.usersService = usersService;
        this.citiesService = citiesService;
        this.tracksListsService = tracksListsService;
    }

    @Override
    public UsersApiResponse addUser(long telegramId) {
        try {
            final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
            if (optionalUser.isPresent()) {
                return new UsersApiResponse(UsersApiResponseStatusCode.USER_ALREADY_EXISTS);
            }
            usersService.addUser(telegramId);
            return new UsersApiResponse();
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UsersApiResponse deleteUser(long telegramId) {
        try {
            usersService.deleteUser(telegramId);
            return new UsersApiResponse();
        } catch (UserNotFoundException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserCitiesResponse getUserCities(long telegramId) {
        try {
            return new UserCitiesResponse(citiesService.getUserCities(telegramId));
        } catch (UserNotFoundException exception) {
            return new UserCitiesResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception exception) {
            return new UserCitiesResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UsersApiResponse addUserCity(long telegramId, String cityName) {
        try {
            citiesService.saveUserCity(telegramId, cityName);
            return new UsersApiResponse();
        } catch (UserNotFoundException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityAlreadyAddedException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.CITY_ALREADY_ADDED);
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UsersApiResponse deleteUserCity(long telegramId, String cityName) {
        try {
            citiesService.deleteUserCity(telegramId, cityName);
            return new UsersApiResponse();
        } catch (UserNotFoundException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityNotAddedException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.CITY_NOT_ADDED);
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTracksListsResponse getUserTracksLists(long telegramId) {
        try {
            return new UserTracksListsResponse(tracksListsService.getUserTracksLists(telegramId));
        } catch (UserNotFoundException exception) {
            return new UserTracksListsResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception exception) {
            return new UserTracksListsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UsersApiResponse addUserTracksList(long telegramId, String tracksListURL) {
        try {
            tracksListsService.saveUserTracksList(telegramId, tracksListURL);
            return new UsersApiResponse();
        } catch (UserNotFoundException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAddedException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UsersApiResponse deleteUserTracksList(long telegramId, String tracksListURL) {
        try {
            tracksListsService.deleteUserTracksList(telegramId, tracksListURL);
            return new UsersApiResponse();
        } catch (UserNotFoundException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListNotAddedException exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.TRACKS_LIST_NOT_ADDED);
        } catch (Exception exception) {
            return new UsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserConcertsResponse getUserConcerts(long telegramId) {
        try {
            final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
            if (optionalUser.isPresent()) {
                return new UserConcertsResponse(UsersApiResponseStatusCode.SUCCESS);
            }
            return new UserConcertsResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception exception) {
            return new UserConcertsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }
}
