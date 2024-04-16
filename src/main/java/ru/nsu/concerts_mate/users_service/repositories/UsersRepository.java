package ru.nsu.concerts_mate.users_service.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.nsu.concerts_mate.users_service.model.entities.UserEntity;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramId(long telegramId);
}
