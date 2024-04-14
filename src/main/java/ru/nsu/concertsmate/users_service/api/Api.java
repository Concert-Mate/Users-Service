package ru.nsu.concertsmate.users_service.api;

import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/users/{telegramId}")
public interface Api {
    @PostMapping
    Response addUser(@PathVariable long telegramId);

    @DeleteMapping
    Response deleteUser(@PathVariable long telegramId);

    @GetMapping("/cities")
    UserCitiesResponse getUserCities(@PathVariable long telegramId);

    @PostMapping("/cities")
    Response addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/cities")
    Response deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @GetMapping("/tracks-lists")
    UserTracksListsResponse getUserTracksLists(@PathVariable long telegramId);

    @PostMapping("/tracks-lists")
    Response addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-lists")
    Response deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    UserConcertsResponse getUserConcerts(@PathVariable long telegramId);
}
