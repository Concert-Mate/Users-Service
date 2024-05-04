package ru.nsu.concerts_mate.user_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CityDto {
    private CoordsDto coords;

    private String district;

    private String name;

    private int population;

    private String subject;
}
