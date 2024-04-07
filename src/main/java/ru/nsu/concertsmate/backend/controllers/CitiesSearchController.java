package ru.nsu.concertsmate.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concertsmate.backend.api.cities_search.CitiesSearchApi;
import ru.nsu.concertsmate.backend.services.CitiesSearchService;

@RestController
public class CitiesSearchController implements CitiesSearchApi {
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
