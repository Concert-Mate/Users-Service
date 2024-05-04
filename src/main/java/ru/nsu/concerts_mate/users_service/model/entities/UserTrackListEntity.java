package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_track_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_track_lists_pk", columnNames = {"user_id", "url"})
})
@NoArgsConstructor
@EqualsAndHashCode
public class UserTrackListEntity {
    @EmbeddedId
    @Column(unique = true)
    private UserTrackListEmbeddedEntity userTrackList;

    public UserTrackListEntity(long userId, String tracksListUrl) {
        userTrackList = new UserTrackListEmbeddedEntity(userId, tracksListUrl);
    }
}
