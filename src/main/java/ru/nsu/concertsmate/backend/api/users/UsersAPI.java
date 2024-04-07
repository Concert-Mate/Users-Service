package ru.nsu.concertsmate.backend.api.users;

import org.springframework.web.bind.annotation.*;
import ru.nsu.concertsmate.backend.model.dto.ConcertDTO;

import java.util.List;

@RequestMapping(value = "/users/{telegramId}")
public interface UsersAPI {
    @PostMapping
    UsersAPIResponse addUser(@PathVariable long telegramId);

    @DeleteMapping
    UsersAPIResponse deleteUser(@PathVariable long telegramId);

    @PostMapping("/city")
    UsersAPIResponse addUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @DeleteMapping("/city")
    UsersAPIResponse deleteUserCity(
            @PathVariable long telegramId,
            @RequestParam(name = "city") String cityName
    );

    @PostMapping("/tracks-list")
    UsersAPIResponse addUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @DeleteMapping("/tracks-list")
    UsersAPIResponse deleteUserTracksList(
            @PathVariable long telegramId,
            @RequestParam(name = "url") String tracksListURL
    );

    @GetMapping("/concerts")
    UsersAPIDataResponse<List<ConcertDTO>> getUserConcerts(@PathVariable long telegramId);
}
