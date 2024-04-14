package ru.nsu.concertsmate.users_service.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEmbeddedEntity;
import ru.nsu.concertsmate.users_service.model.entities.UserCityEntity;

import java.util.List;
import java.util.Optional;

public interface UsersCitiesRepository extends CrudRepository<UserCityEntity, UserCityEmbeddedEntity> {
    @Query(value = "SELECT city_name FROM public.users_cities WHERE user_id = :user_id",
            nativeQuery = true)
    Optional<List<String>> getUserCities(@Param("user_id") long userId);
}
