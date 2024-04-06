package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.CitiesSearchAPI;
import ru.nsu.concertsmate.backend.services.CitiesSearchService;

@RestController
public class CitiesSearchController implements CitiesSearchAPI {
    private final CitiesSearchService cityService;

    @Autowired
    public CitiesSearchController(CitiesSearchService cityService) {
        this.cityService = cityService;
    }

    @Override
    public String findCity(String cityName) {
        return cityService.findCityByName(cityName).toString();
    }
}
