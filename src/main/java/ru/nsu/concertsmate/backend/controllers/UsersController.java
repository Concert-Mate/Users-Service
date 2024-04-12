package ru.nsu.concertsmate.backend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.users.UsersApi;
import ru.nsu.concertsmate.backend.api.users.UsersApiResponse;
import ru.nsu.concertsmate.backend.api.users.UsersApiResponseStatus;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;
import ru.nsu.concertsmate.backend.api.users.UsersApiDataResponse;
import ru.nsu.concertsmate.backend.model.dto.UserDto;
import ru.nsu.concertsmate.backend.services.UsersCitiesService;
import ru.nsu.concertsmate.backend.services.UsersService;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyExistsException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchCityException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchUserException;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;

    private final UsersCitiesService usersCitiesService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService usersCitiesService) {
        this.usersService = usersService;
        this.usersCitiesService = usersCitiesService;
    }

    @Override
    public UsersApiDataResponse<UserDto> addUser(long telegramId) {
        try {
            var user = usersService.findUser(telegramId);
            if (user == null) {
                UsersApiDataResponse<UserDto> res = new UsersApiDataResponse<>(null);
                res.setStatus(UsersApiResponseStatus.USER_ALREADY_EXISTS.ordinal());
                return res;
            }
            user = usersService.addUser(telegramId);
            return new UsersApiDataResponse<>(user);
        }
        catch (Exception e){
            UsersApiDataResponse<UserDto> res = new UsersApiDataResponse<>(null);
            res.setStatus(UsersApiResponseStatus.INTERNAL_ERROR.ordinal());
            return res;
        }
    }

    @Override
    public UsersApiDataResponse<UserDto> deleteUser(long telegramId) {
        try {
            var user = usersService.deleteUser(telegramId);
            if (user == null) {
                UsersApiDataResponse<UserDto> res = new UsersApiDataResponse<>(null);
                res.setStatus(UsersApiResponseStatus.USER_NOT_FOUND.ordinal());
                return res;
            }
            return new UsersApiDataResponse<>(user);
        }
        catch (Exception e){
            UsersApiDataResponse<UserDto> res = new UsersApiDataResponse<>(null);
            res.setStatus(UsersApiResponseStatus.INTERNAL_ERROR.ordinal());
            return res;
        }
    }

    @Override
    public UsersApiDataResponse<List<String>> getUserCities(long telegramId) {
        UsersApiDataResponse<List<String>> res = new UsersApiDataResponse<>(null);
        try {
            return new UsersApiDataResponse<>(usersCitiesService.getUserCities(telegramId));
        } catch (NoSuchUserException e) {
            res.setStatus(UsersApiResponseStatus.USER_NOT_FOUND.ordinal());
        }
        catch (Exception e){
            res.setStatus(UsersApiResponseStatus.INTERNAL_ERROR.ordinal());
        }
        return res;
    }

    @Override
    public UsersApiResponse addUserCity(long telegramId, String cityName) {
        UsersApiResponse res = new UsersApiResponse();
        try {
            usersCitiesService.saveUserCity(telegramId, cityName);
        } catch (NoSuchUserException e) {
            res.setStatus(UsersApiResponseStatus.USER_NOT_FOUND.ordinal());
        } catch (CityAlreadyExistsException e) {
            res.setStatus(UsersApiResponseStatus.CITY_ALREADY_ADDED.ordinal());
        }
        catch (Exception e){
            res.setStatus(UsersApiResponseStatus.INTERNAL_ERROR.ordinal());
        }
        return res;
    }

    @Override
    public UsersApiResponse deleteUserCity(long telegramId, String cityName) {
        UsersApiResponse res = new UsersApiResponse();
        try {
            usersCitiesService.deleteUserCity(telegramId, cityName);
        } catch (NoSuchUserException e) {
            res.setStatus(UsersApiResponseStatus.USER_NOT_FOUND.ordinal());
        } catch (NoSuchCityException e){
            res.setStatus(UsersApiResponseStatus.INVALID_CITY.ordinal());
        } catch (Exception e){
            res.setStatus(UsersApiResponseStatus.INTERNAL_ERROR.ordinal());
        }
        return res;
    }

    @Override
    public UsersApiDataResponse<List<String>> getUserTracksLists(long telegramId) {
        // TODO: implement
        return new UsersApiDataResponse<>(new ArrayList<>());
    }

    @Override
    public UsersApiResponse addUserTracksList(long telegramId, String tracksListURL) {
        // TODO: implement
        return new UsersApiResponse();
    }

    @Override
    public UsersApiResponse deleteUserTracksList(long telegramId, String tracksListURL) {
        // TODO: implement
        return new UsersApiResponse();
    }

    @Override
    public UsersApiDataResponse<List<ConcertDto>> getUserConcerts(long telegramId) {
        // TODO: implement
        return new UsersApiDataResponse<>(new ArrayList<>());
    }
}
