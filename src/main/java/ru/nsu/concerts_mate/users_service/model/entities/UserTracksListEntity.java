package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_tracks_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_tracks_lists_pk", columnNames = {"user_id", "url"})
})
@NoArgsConstructor
@EqualsAndHashCode
public class UserTracksListEntity {
    @EmbeddedId
    @Column(unique = true)
    private UserTracksListEmbeddedEntity userTracksList;

    public UserTracksListEntity(long userId, String tracksListUrl) {
        userTracksList = new UserTracksListEmbeddedEntity(userId, tracksListUrl);
    }
}
