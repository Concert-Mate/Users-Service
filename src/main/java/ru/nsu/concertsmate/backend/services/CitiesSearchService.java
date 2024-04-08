package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.ElasticCity;

import java.util.List;

public interface CitiesSearchService {
    List<ElasticCity> findCityByName(String cityName);
}
