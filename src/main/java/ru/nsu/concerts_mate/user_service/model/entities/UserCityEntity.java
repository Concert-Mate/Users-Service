package ru.nsu.concerts_mate.user_service.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_cities", uniqueConstraints = {
        @UniqueConstraint(name = "users_cities_pk", columnNames = {"user_id", "city_name"})
})
@NoArgsConstructor
@EqualsAndHashCode
public class UserCityEntity {
    @EmbeddedId
    @Column(unique = true)
    private UserCityEmbeddedEntity userCity;

    public UserCityEntity(long userId, String cityName) {
        userCity = new UserCityEmbeddedEntity(userId, cityName);
    }
}
