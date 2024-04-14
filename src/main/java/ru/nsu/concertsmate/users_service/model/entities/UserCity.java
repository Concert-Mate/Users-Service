package ru.nsu.concertsmate.users_service.model.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "users_cities", uniqueConstraints={
        @UniqueConstraint( name = "users_cities_pk",  columnNames ={"user_id", "city_name"})
})
@NoArgsConstructor
public class UserCity {

    @EmbeddedId
    @Column(unique = true)
    private UserCityEmbedded userCity;


    public UserCity(long userId, String cityName) {
        userCity = new UserCityEmbedded(userId, cityName);
    }


    public long getUserId(){
        return userCity.getUserId();
    }

    public String getCityName(){
        return userCity.getCityName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCity userCity1 = (UserCity) o;
        return Objects.equals(userCity, userCity1.userCity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userCity);
    }

    @Override
    public String toString() {
        return "UserCity{" +
                "userCity=" + userCity +
                '}';
    }
}
