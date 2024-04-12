package ru.nsu.concertsmate.backend.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class UserCityEmbedded implements Serializable {

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    public UserCityEmbedded(long userId, String cityName) {
        this.userId = userId;
        this.cityName = cityName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
