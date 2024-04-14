package ru.nsu.concertsmate.users_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Table(name = "users_tracks_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_tracks_lists_pk", columnNames = {"user_id", "url"})
})
@Getter
@Setter
public class UserTracksListEmbedded {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "url", nullable = false)
    private String tracksListUrl;

    public UserTracksListEmbedded(long userId, String tracksListUrl) {
        this.userId = userId;
        this.tracksListUrl = tracksListUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTracksListEmbedded that = (UserTracksListEmbedded) o;
        return userId == that.userId && Objects.equals(tracksListUrl, that.tracksListUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, tracksListUrl);
    }

    @Override
    public String toString() {
        return "UserCityEmbedded{" +
                "userId=" + userId +
                ", tracksListUrl='" + tracksListUrl + '\'' +
                '}';
    }
}
