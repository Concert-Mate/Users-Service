package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.*;
import ru.nsu.concertsmate.backend.model.dto.UserDto;
import ru.nsu.concertsmate.backend.services.CitiesService;
import ru.nsu.concertsmate.backend.services.TracksListsService;
import ru.nsu.concertsmate.backend.services.UsersService;
import ru.nsu.concertsmate.backend.services.exceptions.*;

@RestController()
public class UsersController implements Api {
    private final UsersService usersService;

    private final CitiesService citiesService;

    private final TracksListsService tracksListsService;

    @Autowired
    public UsersController(UsersService usersService, CitiesService citiesService, TracksListsService tracksListsService) {
        this.usersService = usersService;
        this.citiesService = citiesService;
        this.tracksListsService = tracksListsService;
    }

    @Override
    public Response addUser(long telegramId) {
        try {
            final UserDto user = usersService.findUser(telegramId);
            if (user != null) {
                return new Response(ResponseStatusCode.USER_ALREADY_EXISTS);
            }
            usersService.addUser(telegramId);
            return new Response();
        } catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Response deleteUser(long telegramId) {
        try {
            var user = usersService.deleteUser(telegramId);
            if (user == null) {
                return new Response(ResponseStatusCode.USER_NOT_FOUND);
            }
            return new Response();
        }
        catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserCitiesResponse getUserCities(long telegramId) {
        try {
            return new UserCitiesResponse(citiesService.getUserCities(telegramId));
        } catch (UserNotFound e) {
            return new UserCitiesResponse(ResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception e){
            return new UserCitiesResponse(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Response addUserCity(long telegramId, String cityName) {
        try {
            citiesService.saveUserCity(telegramId, cityName);
            return new Response();
        } catch (UserNotFound e) {
            return new Response(ResponseStatusCode.USER_NOT_FOUND);
        } catch (CityAlreadyAdded e) {
            return new Response(ResponseStatusCode.CITY_ALREADY_ADDED);
        }
        catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Response deleteUserCity(long telegramId, String cityName) {
        try {
            citiesService.deleteUserCity(telegramId, cityName);
            return new Response();
        } catch (UserNotFound e) {
            return new Response(ResponseStatusCode.USER_NOT_FOUND);
        } catch (CityNotAdded e){
            return new Response(ResponseStatusCode.CITY_NOT_ADDED);
        } catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTracksListsResponse getUserTracksLists(long telegramId) {
        try {
            return new UserTracksListsResponse(tracksListsService.getUserTracksLists(telegramId));
        } catch (UserNotFound e) {
            return new UserTracksListsResponse(ResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception e){
            return new UserTracksListsResponse(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Response addUserTracksList(long telegramId, String tracksListURL) {
        try {
            tracksListsService.saveUserTracksList(telegramId, tracksListURL);
            return new Response();
        } catch (UserNotFound e) {
            return new Response(ResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAdded e) {
            return new Response(ResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public Response deleteUserTracksList(long telegramId, String tracksListURL) {
        try {
            tracksListsService.deleteUserTracksList(telegramId, tracksListURL);
            return new Response();
        } catch (UserNotFound e) {
            return new Response(ResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListNotAdded e){
            return new Response(ResponseStatusCode.TRACKS_LIST_NOT_ADDED);
        } catch (Exception e){
            return new Response(ResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserConcertsResponse getUserConcerts(long telegramId) {
        try {
            final UserDto user = usersService.findUser(telegramId);
            if (user != null) {
                return new UserConcertsResponse(ResponseStatusCode.SUCCESS);
            }
            return new UserConcertsResponse(ResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception e){
            return new UserConcertsResponse(ResponseStatusCode.INTERNAL_ERROR);
        }
    }
}
