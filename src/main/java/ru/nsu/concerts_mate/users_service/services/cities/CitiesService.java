package ru.nsu.concerts_mate.users_service.services.cities;

import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;

public interface CitiesService {
    void findCity(String cityName);

    void findCity(CoordsDto coords);
}
