package ru.nsu.concerts_mate.user_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Table(name = "users_track_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_track_lists_pk", columnNames = {"user_id", "url"})
})
@EqualsAndHashCode
@AllArgsConstructor
public class UserTrackListEmbeddedEntity {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "url", nullable = false)
    private String trackListUrl;
}
