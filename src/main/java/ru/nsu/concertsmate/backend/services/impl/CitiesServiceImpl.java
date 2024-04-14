package ru.nsu.concertsmate.backend.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.dto.UserCityDto;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.model.entities.UserCity;
import ru.nsu.concertsmate.backend.model.entities.UserCityEmbedded;
import ru.nsu.concertsmate.backend.repositories.CitiesRepository;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.CitiesService;
import ru.nsu.concertsmate.backend.services.exceptions.CityAlreadyAdded;
import ru.nsu.concertsmate.backend.services.exceptions.CityNotAdded;
import ru.nsu.concertsmate.backend.services.exceptions.UserNotFound;

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
    public UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFound, CityAlreadyAdded {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new UserNotFound();
        }
        Optional<UserCity> testUnique = usersCitiesRepository.findById(new UserCityEmbedded(user.get().getId(), cityName));
        if (testUnique.isPresent()){
            throw new CityAlreadyAdded();
        }
        UserCity userCity = new UserCity(user.get().getId(), cityName);
        UserCity result = usersCitiesRepository.save(userCity);
        if (!result.equals(userCity)){
            throw new RuntimeException("can't save user city");
        }

        return modelMapper.map(result, UserCityDto.class);
    }

    @Override
    public UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFound, CityNotAdded {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new UserNotFound();
        }
        Optional<UserCity> testUnique = usersCitiesRepository.findById(new UserCityEmbedded(user.get().getId(), cityName));
        if (testUnique.isEmpty()){
            throw new CityNotAdded();
        }
        UserCity userCity = new UserCity(user.get().getId(), cityName);
        usersCitiesRepository.delete(userCity);

        return null;
    }

    @Override
    public List<String> getUserCities(long telegramId) throws UserNotFound {
        Optional<User> user = usersRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new UserNotFound();
        }
        return usersCitiesRepository.getUserCities(user.get().getId()).get();
    }
}
