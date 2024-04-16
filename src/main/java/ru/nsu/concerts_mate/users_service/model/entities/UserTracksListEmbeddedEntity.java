package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Table(name = "users_tracks_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_tracks_lists_pk", columnNames = {"user_id", "url"})
})
@EqualsAndHashCode
@AllArgsConstructor
public class UserTracksListEmbeddedEntity {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "url", nullable = false)
    private String tracksListUrl;
}
