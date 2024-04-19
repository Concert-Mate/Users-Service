package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ConcertDto {
    private String title;

    private String afishaUrl;

    private String city;

    private String place;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date datetime;

    private String mapUrl;

    private List<String> images;

    private PriceDto minPrice;

    private List<ArtistDto> artists;
}
