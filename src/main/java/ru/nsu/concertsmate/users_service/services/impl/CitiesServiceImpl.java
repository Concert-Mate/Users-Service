package ru.nsu.concertsmate.users_service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.users_service.model.dto.UserCityDto;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEmbeddedEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserEntity;
import ru.nsu.concertsmate.users_service.repositories.UsersCitiesRepository;
import ru.nsu.concertsmate.users_service.repositories.UsersRepository;
import ru.nsu.concertsmate.users_service.services.UsersCitiesService;
import ru.nsu.concertsmate.users_service.services.exceptions.CityAlreadyAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.CityNotAddedException;
import ru.nsu.concertsmate.users_service.services.exceptions.InternalErrorException;
import ru.nsu.concertsmate.users_service.services.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class CitiesServiceImpl implements UsersCitiesService {
    private final UsersRepository usersRepository;
    private final UsersCitiesRepository usersCitiesRepository;
    private final ModelMapper modelMapper;

    public CitiesServiceImpl(UsersRepository usersRepository, UsersCitiesRepository usersCitiesRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.usersCitiesRepository = usersCitiesRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserCityDto saveUserCity(long telegramId, String cityName) throws UserNotFoundException, CityAlreadyAddedException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserCityEntity> testUnique = usersCitiesRepository.findById(
                new UserCityEmbeddedEntity(foundUser.get().getId(), cityName)
        );
        if (testUnique.isPresent()) {
            throw new CityAlreadyAddedException();
        }

        final UserCityEntity userCity = new UserCityEntity(foundUser.get().getId(), cityName);
        final UserCityEntity savingResult = usersCitiesRepository.save(userCity);
        if (!savingResult.equals(userCity)) {
            throw new InternalErrorException();
        }

        return modelMapper.map(savingResult, UserCityDto.class);
    }

    @Override
    public UserCityDto deleteUserCity(long telegramId, String cityName) throws UserNotFoundException, CityNotAddedException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final Optional<UserCityEntity> testUnique = usersCitiesRepository.findById(
                new UserCityEmbeddedEntity(foundUser.get().getId(), cityName)
        );
        if (testUnique.isEmpty()) {
            throw new CityNotAddedException();
        }

        final UserCityEntity userCity = new UserCityEntity(foundUser.get().getId(), cityName);
        usersCitiesRepository.delete(userCity);

        return null;
    }

    @Override
    public List<String> getUserCities(long telegramId) throws UserNotFoundException, InternalErrorException {
        final Optional<UserEntity> foundUser = usersRepository.findByTelegramId(telegramId);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        final var userCities = usersCitiesRepository.getUserCities(foundUser.get().getId());
        if (userCities.isEmpty()) {
            throw new InternalErrorException();
        }

        return userCities.get();
    }
}
