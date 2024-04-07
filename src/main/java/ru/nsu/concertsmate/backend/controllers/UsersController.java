package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.users.UsersAPI;
import ru.nsu.concertsmate.backend.api.users.UsersAPIResponse;
import ru.nsu.concertsmate.backend.model.dto.ConcertDTO;
import ru.nsu.concertsmate.backend.api.users.UsersAPIDataResponse;
import ru.nsu.concertsmate.backend.services.UsersService;

import java.util.ArrayList;
import java.util.List;

@RestController()
public class UsersController implements UsersAPI {
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UsersAPIResponse addUser(long telegramId) {
        // TODO: add exceptions handle
        usersService.addUser(telegramId);
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIResponse deleteUser(long telegramId) {
        // TODO: add exceptions handle
        usersService.deleteUser(telegramId);
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIResponse addUserCity(long telegramId, String cityName) {
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIResponse deleteUserCity(long telegramId, String cityName) {
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIResponse addUserTracksList(long telegramId, String tracksListURL) {
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIResponse deleteUserTracksList(long telegramId, String tracksListURL) {
        return new UsersAPIResponse();
    }

    @Override
    public UsersAPIDataResponse<List<ConcertDTO>> getUserConcerts(long telegramId) {
        return new UsersAPIDataResponse<>(new ArrayList<>());
    }
}
