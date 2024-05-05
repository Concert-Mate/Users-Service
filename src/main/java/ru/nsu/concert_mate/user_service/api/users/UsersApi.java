package ru.nsu.concert_mate.user_service.api.users;

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
    UserCityAddResponse addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city", required = false) String cityName,
            @RequestParam(name = "lat", required = false) Float lat,
            @RequestParam(name = "lon", required = false) Float lon
    );

    @DeleteMapping("/cities")
    DefaultUsersApiResponse deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );


    @GetMapping("/track-lists")
    UserTrackListsResponse getUserTrackLists(@PathVariable long telegramId);

    @PostMapping("/track-lists")
    UserTrackListResponse addUserTrackList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String trackListURL
    );

    @DeleteMapping("/track-lists")
    UserTrackListResponse deleteUserTrackList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String trackListURL
    );

    @GetMapping("/concerts")
    UserConcertsResponse getUserConcerts(@PathVariable long telegramId);
}
