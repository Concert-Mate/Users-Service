package ru.nsu.concertsmate.backend.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.dto.UserCityDto;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.model.entities.UserCity;
import ru.nsu.concertsmate.backend.model.entities.UserCityEmbedded;
import ru.nsu.concertsmate.backend.repositories.UsersCitiesRepository;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.UsersCitiesService;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyExistsException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchCityException;
import ru.nsu.concertsmate.backend.services.exceptions.NoSuchUserException;

import java.util.List;
import java.util.Optional;
import java.util.logging.SocketHandler;

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
    public UserCityDto saveUserCity(long telegramId, String cityName) throws NoSuchUserException, CityAlreadyExistsException {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new NoSuchUserException();
        }
        Optional<UserCity> testUnique = usersCitiesRepository.findById(new UserCityEmbedded(user.get().getId(), cityName));
        if (testUnique.isPresent()){
            throw new CityAlreadyExistsException();
        }
        UserCity userCity = new UserCity(user.get().getId(), cityName);
        UserCity result = usersCitiesRepository.save(userCity);
        if (!result.equals(userCity)){
            throw new RuntimeException("can't save user city");
        }

        return modelMapper.map(result, UserCityDto.class);
    }

    @Override
    public UserCityDto deleteUserCity(long telegramId, String cityName) throws NoSuchUserException, NoSuchCityException {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new NoSuchUserException();
        }
        Optional<UserCity> testUnique = usersCitiesRepository.findById(new UserCityEmbedded(user.get().getId(), cityName));
        if (testUnique.isEmpty()){
            throw new NoSuchCityException();
        }
        UserCity userCity = new UserCity(user.get().getId(), cityName);
        usersCitiesRepository.delete(userCity);

        return null;
    }

    @Override
    public List<String> getUserCities(long telegramId) throws NoSuchUserException {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new NoSuchUserException();
        }
        return usersCitiesRepository.getUserCities(user.get().getId()).get();
    }
}
