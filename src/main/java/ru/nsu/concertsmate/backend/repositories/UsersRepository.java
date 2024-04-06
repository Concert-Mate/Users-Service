package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nsu.concertsmate.backend.model.entities.User;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(long telegramId);
}
