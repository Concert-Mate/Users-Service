package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ConcertDto {
    private String title;

    private String afisha_url;

    private String city;

    private String place;

    private String address;

    private Date datetime;

    private String map_url;

    private List<String> images;

    private PriceDto min_price;

    private List<ArtistDto> artists;
}
