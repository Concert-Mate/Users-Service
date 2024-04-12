package ru.nsu.concertsmate.backend.services;

import ru.nsu.concertsmate.backend.model.dto.UserCityDto;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyExistsException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchCityException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchUserException;

import java.util.List;

public interface UsersCitiesService {

    UserCityDto saveUserCity(long telegramId, String cityName) throws NoSuchUserException, CityAlreadyExistsException;

    UserCityDto deleteUserCity(long telegramId, String cityName) throws NoSuchUserException, NoSuchCityException;

    List<String> getUserCities(long telegramId) throws NoSuchUserException;

}
