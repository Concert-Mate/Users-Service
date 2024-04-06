package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.UsersAPI;
import ru.nsu.concertsmate.backend.services.UsersService;

@RestController
public class UsersController implements UsersAPI {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public String addUser(long telegramId) {
        // TODO: add exceptions handle
        usersService.addUser(telegramId);
        return String.format("Adding user with telegram-id %s", telegramId);
    }

    @Override
    public String deleteUser(long telegramId) {
        // TODO: add exceptions handle
        usersService.deleteUser(telegramId);
        return String.format("Deleting user with telegram-id %s", telegramId);
    }

    @Override
    public String addUserCity(long telegramId, String cityName) {
        return String.format("Adding city %s for user with telegram id %d", cityName, telegramId);
    }

    @Override
    public String deleteUserCity(long telegramId, String cityName) {
        return String.format("Deleting city %s from user with telegram id %d", cityName, telegramId);
    }

    @Override
    public String addUserTracksList(long telegramId, String tracksListURL) {
        return String.format("Adding tracks-list %s for user with telegram id %d", tracksListURL, telegramId);
    }

    @Override
    public String deleteUserTracksList(long telegramId, String tracksListURL) {
        return String.format("Deleting tracks-list %s from user with telegram id %d", tracksListURL, telegramId);
    }

    @Override
    public String getUserConcerts(long telegramId) {
        return String.format("Concerts for user with telegram id %d", telegramId);
    }
}
