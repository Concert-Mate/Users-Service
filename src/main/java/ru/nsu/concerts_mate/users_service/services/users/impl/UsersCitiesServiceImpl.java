package ru.nsu.concerts_mate.users_service.services.users.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.UserCityDto;
import ru.nsu.concerts_mate.users_service.model.entities.UserCityEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserCityEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;
import ru.nsu.concerts_mate.users_service.repositories.UsersCitiesRepository;
import ru.nsu.concerts_mate.users_service.repositories.UsersRepository;
import ru.nsu.concerts_mate.users_service.services.users.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.CityAlreadyAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.CityNotAddedException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersCitiesServiceImpl implements UsersCitiesService {
    private final UsersRepository usersRepository;
    private final UsersCitiesRepository usersCitiesRepository;
    private final ModelMapper modelMapper;

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

        return modelMapper.map(userCity, UserCityDto.class);
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
