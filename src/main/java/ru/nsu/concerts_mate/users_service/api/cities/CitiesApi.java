package ru.nsu.concerts_mate.users_service.api.cities;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping(value = "/cities")
public interface CitiesApi {

    @GetMapping("/")
    CitiesApiResponse getCities(@RequestParam(name = "lat") float lat, @RequestParam(name = "lon") float lon);
}
