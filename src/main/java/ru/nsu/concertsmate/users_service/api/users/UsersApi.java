package ru.nsu.concertsmate.users_service.api.users;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users/{telegramId}")
public interface UsersApi {
    @PostMapping
    UsersApiResponse addUser(@PathVariable long telegramId);

    @DeleteMapping
    UsersApiResponse deleteUser(@PathVariable long telegramId);

    @GetMapping("/cities")
    UserCitiesResponse getUserCities(@PathVariable long telegramId);

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
    UserTracksListsResponse getUserTracksLists(@PathVariable long telegramId);

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
    UserConcertsResponse getUserConcerts(@PathVariable long telegramId);
}
