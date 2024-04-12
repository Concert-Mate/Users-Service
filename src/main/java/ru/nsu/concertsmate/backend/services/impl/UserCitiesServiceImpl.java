package ru.nsu.concertsmate.backend.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.dto.UserCityDto;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.model.entities.UserCity;
import ru.nsu.concertsmate.backend.repositories.UsersCitiesRepository;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.UsersCitiesService;

import java.util.List;

@Service
public class UserCitiesServiceImpl implements UsersCitiesService {
    private final UsersCitiesRepository usersCitiesRepository;
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;

    @Autowired
    public UserCitiesServiceImpl(UsersCitiesRepository usersRepository, ModelMapper modelMapper, UsersRepository usersRepository1) {
        this.usersCitiesRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository1;
    }


    @Override
    public UserCityDto saveUserCity(long telegramId, String cityName) {
        long userId = usersRepository.findByTelegramId(telegramId).get().getId();
        UserCity userCity = new UserCity(userId, cityName);
        userCity = usersCitiesRepository.save(userCity);

        return modelMapper.map(userCity, UserCityDto.class);
    }

    @Override
    public UserCityDto deleteUserCity(long telegramId, String cityName) {
        long userId = usersRepository.findByTelegramId(telegramId).get().getId();
        UserCity userCity = new UserCity(userId, cityName);
        usersCitiesRepository.delete(userCity);

        return null;
    }

    @Override
    public List<String> getUserCities(long telegramId) {
        long userId = usersRepository.findByTelegramId(telegramId).get().getId();
        return usersCitiesRepository.getUserCities(userId).get();
    }
}
