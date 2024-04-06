package ru.nsu.concertsmate.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.model.entities.User;
import ru.nsu.concertsmate.backend.repositories.UsersRepository;
import ru.nsu.concertsmate.backend.services.UsersService;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void addUser(long telegramId) {
       usersRepository.save(new User(telegramId));
    }

    @Override
    public void deleteUser(long telegramId) {
        final User user = usersRepository.findUserByTelegramId(telegramId);
        usersRepository.delete(user);
    }
}
