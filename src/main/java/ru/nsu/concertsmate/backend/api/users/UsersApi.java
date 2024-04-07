package ru.nsu.concertsmate.backend.api.users;

import org.springframework.web.bind.annotation.*;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;

import java.util.List;

@RequestMapping(value = "/users/{telegramId}")
public interface UsersApi {
    @PostMapping
    UsersApiResponse addUser(@PathVariable long telegramId);

    @DeleteMapping
    UsersApiResponse deleteUser(@PathVariable long telegramId);

    @PostMapping("/city")
    UsersApiResponse addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/city")
    UsersApiResponse deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @PostMapping("/tracks-list")
    UsersApiResponse addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-list")
    UsersApiResponse deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    UsersApiDataResponse<List<ConcertDto>> getUserConcerts(@PathVariable long telegramId);
}
