package ru.nsu.concertsmate.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.UsersService;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void addUser(long telegramId) {
        final User user = new User(telegramId);
        usersRepository.save(user);
    }

    @Override
    public void deleteUser(long telegramId) {
        final Optional<User> optionalUser = usersRepository.findByTelegramId(telegramId);
        optionalUser.ifPresent(usersRepository::delete);
    }
}
