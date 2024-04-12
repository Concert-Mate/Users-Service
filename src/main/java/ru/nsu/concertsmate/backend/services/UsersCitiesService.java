package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.UserCityDto;

import java.util.List;

public interface UsersCitiesService {

    UserCityDto saveUserCity(long telegramId, String cityName);

    UserCityDto deleteUserCity(long telegramId, String cityName);

    List<String> getUserCities(long telegramId);

}
