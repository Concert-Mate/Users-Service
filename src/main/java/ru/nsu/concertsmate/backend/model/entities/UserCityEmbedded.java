package ru.nsu.concertsmate.backend.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
@NoArgsConstructor
@Table(name = "users_cities", uniqueConstraints={
        @UniqueConstraint( name = "users_cities_pk",  columnNames ={"user_id", "city_name"})
})
public class UserCityEmbedded implements Serializable {

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    public UserCityEmbedded(long userId, String cityName) {
        this.userId = userId;
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCityEmbedded that = (UserCityEmbedded) o;
        return userId == that.userId && Objects.equals(cityName, that.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, cityName);
    }

    @Override
    public String toString() {
        return "UserCityEmbedded{" +
                "userId=" + userId +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
