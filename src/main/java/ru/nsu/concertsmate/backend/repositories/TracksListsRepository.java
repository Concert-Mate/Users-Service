package ru.nsu.concertsmate.backend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.nsu.concertsmate.backend.model.entities.UserTracksList;
import ru.nsu.concertsmate.backend.model.entities.UserTracksListEmbedded;

import java.util.List;
import java.util.Optional;

public interface TracksListsRepository extends CrudRepository<UserTracksList, UserTracksListEmbedded> {
    @Query(value = "SELECT url FROM public.users_tracklists WHERE user_id = :user_id",
            nativeQuery = true)
    Optional<List<String>> getUserTracksLists(@Param("user_id") long userId);
}
