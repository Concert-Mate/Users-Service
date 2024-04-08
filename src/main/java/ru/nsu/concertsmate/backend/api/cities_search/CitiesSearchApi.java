package ru.nsu.concertsmate.backend.api.cities_search;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/cities")
public interface CitiesSearchApi {
    // TODO: change return type from String to something else
    @GetMapping
    String findCity(@RequestParam(name = "city") String cityName);
}
