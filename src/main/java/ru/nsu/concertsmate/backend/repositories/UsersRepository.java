package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.nsu.concertsmate.backend.model.entities.User;

public interface UsersRepository extends CrudRepository<User, Long> {
    @Query(value = """
            select * from users
            where telegram_id = :telegramId
            """, nativeQuery = true)
    User findUserByTelegramId(long telegramId);
}
