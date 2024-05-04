package ru.nsu.concerts_mate.user_service.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concerts_mate.user_service.model.entities.ShownConcertEmbeddedEntity;
import ru.nsu.concerts_mate.user_service.model.entities.ShownConcertEntity;

import java.util.List;
import java.util.Optional;

public interface ShownConcertsRepository extends CrudRepository<ShownConcertEntity, ShownConcertEmbeddedEntity> {
    @Query(value = "SELECT concert_url FROM public.shown_concerts WHERE user_id = :user_id",
            nativeQuery = true)
    Optional<List<String>> getUserShownConcerts(@Param("user_id") long userId);
}