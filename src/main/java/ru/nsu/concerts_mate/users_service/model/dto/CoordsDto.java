package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordsDto {
    private float lat;
    private float lon;
}