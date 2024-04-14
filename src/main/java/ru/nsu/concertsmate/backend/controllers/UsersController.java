package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.*;
import ru.nsu.concertsmate.backend.services.UsersCitiesService;
import ru.nsu.concertsmate.backend.services.UsersService;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyExistsException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchCityException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchUserException;

@RestController()
public class UsersController implements Api {
    private final UsersService usersService;

    private final UsersCitiesService usersCitiesService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService usersCitiesService) {
        this.usersService = usersService;
        this.usersCitiesService = usersCitiesService;
    }

    @Override
    public Response addUser(long telegramId) {
        try {
            var user = usersService.findUser(telegramId);
            if (user != null) {
                return new Response(ResponseStatusCode.USER_ALREADY_EXISTS);
            }
            usersService.addUser(telegramId);
            return new Response();
        }
        catch (Exception e){
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
        return new UserCitiesResponse();

//        UsersApiDataResponse<List<String>> res = new UsersApiDataResponse<>(null);
//        try {
//            return new UsersApiDataResponse<>(usersCitiesService.getUserCities(telegramId));
//        } catch (NoSuchUserException e) {
//            res.setStatus(ResponseStatus.USER_NOT_FOUND.ordinal());
//        }
//        catch (Exception e){
//            res.setStatus(ResponseStatus.INTERNAL_ERROR.ordinal());
//        }
//        return res;
    }

    @Override
    public Response addUserCity(long telegramId, String cityName) {
        return new Response();


//        Response res = new Response();
//        try {
//            usersCitiesService.saveUserCity(telegramId, cityName);
//        } catch (NoSuchUserException e) {
//            res.setStatus(ResponseStatusCode.USER_NOT_FOUND.ordinal());
//        } catch (CityAlreadyExistsException e) {
//            res.setStatus(ResponseStatusCode.CITY_ALREADY_ADDED.ordinal());
//        }
//        catch (Exception e){
//            res.setStatus(ResponseStatusCode.INTERNAL_ERROR.ordinal());
//        }
//        return res;
    }

    @Override
    public Response deleteUserCity(long telegramId, String cityName) {
        return new Response();

//        Response res = new Response();
//        try {
//            usersCitiesService.deleteUserCity(telegramId, cityName);
//        } catch (NoSuchUserException e) {
//            res.setStatus(ResponseStatusCode.USER_NOT_FOUND.ordinal());
//        } catch (NoSuchCityException e){
//            res.setStatus(ResponseStatusCode.INVALID_CITY.ordinal());
//        } catch (Exception e){
//            res.setStatus(ResponseStatusCode.INTERNAL_ERROR.ordinal());
//        }
//        return res;
    }

    @Override
    public UserTracksListsResponse getUserTracksLists(long telegramId) {
        return new UserTracksListsResponse();

//        return new Response();

        // TODO: implement
        //return new UsersApiDataResponse<>(new ArrayList<>());
    }

    @Override
    public Response addUserTracksList(long telegramId, String tracksListURL) {
        // TODO: implement
        return new Response();
    }

    @Override
    public Response deleteUserTracksList(long telegramId, String tracksListURL) {
        // TODO: implement
        return new Response();
    }

    @Override
    public UserConcertsResponse getUserConcerts(long telegramId) {
        return new UserConcertsResponse();

        //return new Response();

        // TODO: implement
        //return new UsersApiDataResponse<>(new ArrayList<>());
    }
}
