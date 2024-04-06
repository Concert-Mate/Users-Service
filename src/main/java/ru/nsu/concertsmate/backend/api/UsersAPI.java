package ru.nsu.concertsmate.backend.api;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/users/{telegramId}")
public interface UsersAPI {
    // TODO: change return type from String in all methods

    @PostMapping
    String addUser(@PathVariable long telegramId);

    @DeleteMapping
    String deleteUser(@PathVariable long telegramId);

    @PostMapping("/city")
    String addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/city")
    String deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @PostMapping("/tracks-list")
    String addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-list")
    String deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    String getUserConcerts(@PathVariable long telegramId);
}
