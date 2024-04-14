package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.UserCityDto;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyAdded;
import ru.nsu.concertsmate.backend.services.exceptions.CityNotAdded;
import ru.nsu.concertsmate.backend.services.exceptions.UserNotFound;

import java.util.List;

public interface CitiesService {
    UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFound, CityAlreadyAdded;

    UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFound, CityNotAdded;

    List<String> getUserCities(long telegramId) throws UserNotFound;
}
