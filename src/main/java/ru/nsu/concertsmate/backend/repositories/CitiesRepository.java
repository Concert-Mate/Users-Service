package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concertsmate.backend.model.entities.UserCity;
import ru.nsu.concertsmate.backend.model.entities.UserCityEmbedded;

import java.util.List;
import java.util.Optional;

public interface CitiesRepository extends CrudRepository<UserCity, UserCityEmbedded> {

    @Query(value = "SELECT city_name FROM public.users_cities WHERE user_id = :user_id",
    nativeQuery = true)
    Optional<List<String>> getUserCities(@Param("user_id") long userId);
}
