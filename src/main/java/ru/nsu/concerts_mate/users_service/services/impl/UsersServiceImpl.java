package ru.nsu.concerts_mate.users_service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;
import ru.nsu.concerts_mate.users_service.repositories.UsersRepository;
import ru.nsu.concerts_mate.users_service.services.UsersService;
import ru.nsu.concerts_mate.users_service.services.exceptions.UserNotFoundException;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto addUser(long telegramId) {
        UserEntity userEntity = new UserEntity(telegramId);
        usersRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto deleteUser(long telegramId) throws UserNotFoundException {
        final Optional<UserEntity> optionalUser = usersRepository.findByTelegramId(telegramId);

        if (optionalUser.isPresent()) {
            final UserEntity userEntity = optionalUser.get();
            usersRepository.delete(userEntity);
            return modelMapper.map(userEntity, UserDto.class);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public Optional<UserDto> findUser(long telegramId) {
        final Optional<UserEntity> optionalUserEntity = usersRepository.findByTelegramId(telegramId);
        if (optionalUserEntity.isPresent()) {
            return Optional.ofNullable(modelMapper.map(optionalUserEntity, UserDto.class));
        } else {
            return Optional.empty();
        }
    }
}
