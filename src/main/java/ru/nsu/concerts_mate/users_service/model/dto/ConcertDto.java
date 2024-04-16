package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ConcertDto {
    private String title;

    private String afishaUrl;

    private String city;

    private String place;

    private String address;

    private Date datetime;

    private String mapUrl;

    private List<String> images;

    private PriceDto minPrice;

    private List<ArtistDto> artists;
}
