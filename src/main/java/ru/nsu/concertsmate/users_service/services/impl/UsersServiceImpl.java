package ru.nsu.concertsmate.users_service.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.users_service.model.dto.UserDto;
import ru.nsu.concertsmate.users_service.model.entities.UserEntity;
import ru.nsu.concertsmate.users_service.repositories.UsersRepository;
import ru.nsu.concertsmate.users_service.services.UsersService;

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
        UserEntity user = new UserEntity(telegramId);
        user = usersRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto deleteUser(long telegramId) {
        final Optional<UserEntity> optionalUser = usersRepository.findByTelegramId(telegramId);

        if (optionalUser.isPresent()) {
            final UserEntity user = optionalUser.get();
            usersRepository.delete(user);
            return modelMapper.map(user, UserDto.class);
        } else {
            return null;
        }
    }

    @Override
    public UserDto findUser(long telegramId) {
        Optional<UserEntity> found = usersRepository.findByTelegramId(telegramId);
        return found.map(user -> modelMapper.map(user, UserDto.class)).orElse(null);
    }
}
