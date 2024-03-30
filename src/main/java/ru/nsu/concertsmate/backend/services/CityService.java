package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.ElasticCity;

import java.util.List;

public interface CityService {

    List<ElasticCity> getCityByName(String name);
}
