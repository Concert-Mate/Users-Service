package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.users.UsersApi;
import ru.nsu.concertsmate.backend.api.users.UsersApiResponse;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;
import ru.nsu.concertsmate.backend.api.users.UsersApiDataResponse;
import ru.nsu.concertsmate.backend.model.dto.UserDto;
import ru.nsu.concertsmate.backend.services.UsersService;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UsersApiDataResponse<UserDto> addUser(long telegramId) {
        // TODO: add exceptions handle
        return new UsersApiDataResponse<>(usersService.addUser(telegramId));
    }

    @Override
    public UsersApiDataResponse<UserDto> deleteUser(long telegramId) {
        // TODO: add exceptions handle
        return new UsersApiDataResponse<>(usersService.deleteUser(telegramId));
    }

    @Override
    public UsersApiDataResponse<List<String>> getUserCities(long telegramId) {
        // TODO: implement
        return new UsersApiDataResponse<>(new ArrayList<>());
    }

    @Override
    public UsersApiResponse addUserCity(long telegramId, String cityName) {
        // TODO: implement
        return new UsersApiResponse();
    }

    @Override
    public UsersApiResponse deleteUserCity(long telegramId, String cityName) {
        // TODO: implement
        return new UsersApiResponse();
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
