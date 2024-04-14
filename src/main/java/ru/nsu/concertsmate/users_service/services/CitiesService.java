package ru.nsu.concertsmate.users_service.services;

import ru.nsu.concertsmate.users_service.model.dto.UserCityDto;
import ru.nsu.concertsmate.users_service.services.exceptions.CityAlreadyAdded;
import ru.nsu.concertsmate.users_service.services.exceptions.CityNotAdded;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFound;

import java.util.List;

public interface CitiesService {
    UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFound, CityAlreadyAdded;

    UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFound, CityNotAdded;

    List<String> getUserCities(long telegramId) throws UserNotFound;
}
