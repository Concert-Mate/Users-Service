package ru.nsu.concerts_mate.users_service.services.cities;

import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;

public interface CitiesService {
    CitySearchByNameResult findCity(String cityName) throws CitiesServiceException;

    CitySearchByCoordsResult findCity(CoordsDto coords) throws CitiesServiceException;
}
