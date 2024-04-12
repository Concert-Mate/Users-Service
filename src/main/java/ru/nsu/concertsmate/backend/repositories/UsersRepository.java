package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.concertsmate.backend.model.entities.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByTelegramId(long telegramId);

}
