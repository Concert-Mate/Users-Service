package ru.nsu.concert_mate.user_service.services.cities;

import ru.nsu.concert_mate.user_service.model.dto.CoordsDto;

public interface CitiesService {
    CitySearchByNameResult findCity(String cityName) throws CitiesServiceException;

    CitySearchByCoordsResult findCity(CoordsDto coords) throws CitiesServiceException;
}
