package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concertsmate.backend.model.entities.UserCity;
import ru.nsu.concertsmate.backend.model.entities.UserCityEmbedded;

import java.util.List;
import java.util.Optional;

public interface UsersCitiesRepository extends CrudRepository<UserCity, UserCityEmbedded> {

    @Query(value = "SELECT city_name FROM public.users_cities WHERE user_id = :user_id",
    nativeQuery = true)
    Optional<List<String>> getUserCities(@Param("user_id") long userId);
//    @Query(value = "SELECT * FROM public.users_cities WHERE user_id = :user_id AND city_name = :city_name", nativeQuery = true)
//    UserCity getUserCity(@Param("user_id") long userId, @Param("city_name") String cityName);
}
