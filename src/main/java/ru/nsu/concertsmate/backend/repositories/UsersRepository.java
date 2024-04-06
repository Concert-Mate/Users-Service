package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.concertsmate.backend.model.entities.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByTelegramId(long telegramId);
}
