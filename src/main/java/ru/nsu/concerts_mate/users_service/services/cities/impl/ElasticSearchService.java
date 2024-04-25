package ru.nsu.concerts_mate.users_service.services.cities.impl;

import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesService;

@Service
public class ElasticSearchService implements CitiesService {
    @Override
    public void findCity(String cityName) {

    }

    @Override
    public void findCity(CoordsDto coords) {

    }
}
