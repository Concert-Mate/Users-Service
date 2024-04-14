package ru.nsu.concertsmate.users_service.services;

import ru.nsu.concertsmate.users_service.model.dto.UserCityDto;
import ru.nsu.concertsmate.users_service.services.exceptions.CityAlreadyAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.CityNotAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.InternalErrorException;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFoundException;

import java.util.List;

public interface UsersCitiesService {
    UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFoundException, CityAlreadyAddedException, InternalErrorException;

    UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFoundException, CityNotAddedException;

    List<String> getUserCities(long telegramId) throws UserNotFoundException, InternalErrorException;
}
