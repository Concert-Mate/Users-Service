package ru.nsu.concerts_mate.users_service.api.users;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users/{telegramId}")
public interface UsersApi {
    @PostMapping
    AddUserApiResponse addUser(@PathVariable long telegramId);

    @DeleteMapping
    DefaultUsersApiResponse deleteUser(@PathVariable long telegramId);

    @GetMapping("/cities")
    UserCitiesResponse getUserCities(@PathVariable long telegramId);

    @PostMapping("/cities")
    DefaultUsersApiResponse addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/cities")
    DefaultUsersApiResponse deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    // TODO: return list of DTO's: list of track-lists
    @GetMapping("/tracks-lists")
    UserTracksListsResponse getUserTracksLists(@PathVariable long telegramId);

    @PostMapping("/tracks-lists")
    DefaultUsersApiResponse addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-lists")
    DefaultUsersApiResponse deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    UserConcertsResponse getUserConcerts(@PathVariable long telegramId);
}
