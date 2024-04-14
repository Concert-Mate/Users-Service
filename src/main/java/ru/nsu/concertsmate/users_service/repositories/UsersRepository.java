package ru.nsu.concertsmate.users_service.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.concertsmate.users_service.model.entities.UserEntity;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramId(long telegramId);
}
