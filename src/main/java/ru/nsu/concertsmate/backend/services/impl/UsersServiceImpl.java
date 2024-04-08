package ru.nsu.concertsmate.backend.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.dto.UserDto;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.UsersService;

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
        final User user = new User(telegramId);
        usersRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto deleteUser(long telegramId) {
        // TODO: handle whether user exists or not
        final Optional<User> optionalUser = usersRepository.findByTelegramId(telegramId);

        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            usersRepository.delete(user);
            return modelMapper.map(user, UserDto.class);
        } else {
            return null;
        }
    }
}
