package ru.nsu.concertsmate.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/cities")
public interface CitiesSearchAPI {
    // TODO: change return type from String to something else
    @GetMapping
    String findCity(@RequestParam(name = "city") String cityName);
}
