package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ConcertDto {
    private String title;

    @JsonProperty(value = "afisha_url")
    private String afishaUrl;

    private String city;

    private String place;

    private String address;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date datetime;

    @JsonProperty(value = "map_url")
    private String mapUrl;

    private List<String> images;

    @JsonProperty(value = "min_price")
    private PriceDto minPrice;

    private List<ArtistDto> artists;
}
