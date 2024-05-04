package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Table(name = "shown_concerts", uniqueConstraints = {
        @UniqueConstraint(name = "shown_concerts_pk", columnNames = {"user_id", "concert_url"})
})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShownConcertEmbeddedEntity {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "concert_url", nullable = false)
    private String concertUrl;

}
