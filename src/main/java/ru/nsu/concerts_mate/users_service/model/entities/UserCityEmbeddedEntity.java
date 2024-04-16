package ru.nsu.concerts_mate.users_service.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Table(name = "users_cities", uniqueConstraints = {
        @UniqueConstraint(name = "users_cities_pk", columnNames = {"user_id", "city_name"})
})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCityEmbeddedEntity implements Serializable {
    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "city_name", nullable = false)
    private String cityName;
}
