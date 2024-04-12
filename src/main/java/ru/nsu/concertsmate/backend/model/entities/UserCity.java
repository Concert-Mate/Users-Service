package ru.nsu.concertsmate.backend.model.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_cities", uniqueConstraints={
        @UniqueConstraint( name = "users_cities_pk",  columnNames ={"user_id", "city_name"})
})
@NoArgsConstructor
public class UserCity {

    @EmbeddedId
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
}
