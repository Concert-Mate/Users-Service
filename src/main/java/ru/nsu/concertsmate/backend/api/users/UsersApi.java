package ru.nsu.concertsmate.backend.api.users;

import org.springframework.web.bind.annotation.*;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;
import ru.nsu.concertsmate.backend.model.dto.UserDto;

import java.util.List;

@RequestMapping(value = "/users/{telegramId}")
public interface UsersApi {
    @PostMapping
    UsersApiDataResponse<UserDto> addUser(@PathVariable long telegramId);

    @DeleteMapping
    UsersApiDataResponse<UserDto> deleteUser(@PathVariable long telegramId);

    @GetMapping("/cities")
    UsersApiDataResponse<List<String>> getUserCities(@PathVariable long telegramId);

    @PostMapping("/cities")
    UsersApiResponse addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/cities")
    UsersApiResponse deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @GetMapping("/tracks-lists")
    UsersApiDataResponse<List<String>> getUserTracksLists(@PathVariable long telegramId);

    @PostMapping("/tracks-lists")
    UsersApiResponse addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-lists")
    UsersApiResponse deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    UsersApiDataResponse<List<ConcertDto>> getUserConcerts(@PathVariable long telegramId);
}
