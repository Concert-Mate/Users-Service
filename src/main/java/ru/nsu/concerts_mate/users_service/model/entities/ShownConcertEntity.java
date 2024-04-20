package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shown_concerts", uniqueConstraints = {
        @UniqueConstraint(name = "shown_concerts_pk", columnNames = {"user_id", "concert_url"})
})
@NoArgsConstructor
@EqualsAndHashCode
public class ShownConcertEntity {
    @EmbeddedId
    @Column(unique = true)
    private ShownConcertEmbeddedEntity userConcert;

    public ShownConcertEntity(long userId, String concertUrl) {
        userConcert = new ShownConcertEmbeddedEntity(userId, concertUrl);
    }
}
