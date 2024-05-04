package ru.nsu.concerts_mate.users_service.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concerts_mate.users_service.model.entities.UserTrackListEmbeddedEntity;
import ru.nsu.concerts_mate.users_service.model.entities.UserTrackListEntity;

import java.util.List;
import java.util.Optional;

public interface UsersTrackListsRepository extends CrudRepository<UserTrackListEntity, UserTrackListEmbeddedEntity> {
    @Query(value = "SELECT url FROM public.users_track_lists WHERE user_id = :user_id",
            nativeQuery = true)
    Optional<List<String>> getUserTrackLists(@Param("user_id") long userId);
}
