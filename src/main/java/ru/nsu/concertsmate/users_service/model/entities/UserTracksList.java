package ru.nsu.concertsmate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users_tracks_lists", uniqueConstraints = {
        @UniqueConstraint(name = "users_tracks_lists_pk", columnNames = {"user_id", "url"})
})
@NoArgsConstructor
public class UserTracksList {
    @EmbeddedId
    @Column(unique = true)
    private UserTracksListEmbedded userTracksList;

    public UserTracksList(long userId, String tracksListUrl) {
        userTracksList = new UserTracksListEmbedded(userId, tracksListUrl);
    }

    public long getUserId() {
        return userTracksList.getUserId();
    }

    public String getTracksListUrl() {
        return userTracksList.getTracksListUrl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTracksList userTracksList1 = (UserTracksList) o;
        return Objects.equals(userTracksList, userTracksList1.userTracksList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userTracksList);
    }

    @Override
    public String toString() {
        return "UserCity{" +
                "userCity=" + userTracksList +
                '}';
    }
}
