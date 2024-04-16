package ru.nsu.concerts_mate.users_service.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concerts_mate.users_service.model.entities.UserTracksListEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTracksListEntity;

import java.util.List;
import java.util.Optional;

public interface UsersTracksListsRepository extends CrudRepository<UserTracksListEntity, UserTracksListEmbeddedEntity> {
    @Query(value = "SELECT url FROM public.users_tracks_lists WHERE user_id = :user_id",
            nativeQuery = true)
    Optional<List<String>> getUserTracksLists(@Param("user_id") long userId);
}
