package ru.nsu.concertsmate.users_service.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.users_service.model.dto.UserCityDto;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEmbeddedEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserEntity;
import ru.nsu.concertsmate.users_service.repositories.CitiesRepository;
import ru.nsu.concertsmate.users_service.repositories.UsersRepository;
import ru.nsu.concertsmate.users_service.services.CitiesService;
import ru.nsu.concertsmate.users_service.services.exceptions.CityAlreadyAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.CityNotAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CitiesServiceImpl implements CitiesService {
    private final CitiesRepository usersCitiesRepository;
    private final ModelMapper modelMapper;
    private final UsersRepository usersRepository;

    @Autowired
    public CitiesServiceImpl(CitiesRepository usersRepository, ModelMapper modelMapper, UsersRepository usersRepository1) {
        this.usersCitiesRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.usersRepository = usersRepository1;
    }


    @Override
    public UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFoundException, CityAlreadyAddedException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<UserCityEntity> testUnique = usersCitiesRepository.findById(new UserCityEmbeddedEntity(user.get().getId(), cityName));
        if (testUnique.isPresent()) {
            throw new CityAlreadyAddedException();
        }
        UserCityEntity userCity = new UserCityEntity(user.get().getId(), cityName);
        UserCityEntity result = usersCitiesRepository.save(userCity);
        if (!result.equals(userCity)) {
            throw new RuntimeException("can't save user city");
        }

        return modelMapper.map(result, UserCityDto.class);
    }

    @Override
    public UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFoundException, CityNotAddedException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        Optional<UserCityEntity> testUnique = usersCitiesRepository.findById(new UserCityEmbeddedEntity(user.get().getId(), cityName));
        if (testUnique.isEmpty()) {
            throw new CityNotAddedException();
        }
        UserCityEntity userCity = new UserCityEntity(user.get().getId(), cityName);
        usersCitiesRepository.delete(userCity);

        return null;
    }

    @Override
    public List<String> getUserCities(long telegramId) throws UserNotFoundException {
        Optional<UserEntity> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }
        return usersCitiesRepository.getUserCities(user.get().getId()).get();
    }
}
