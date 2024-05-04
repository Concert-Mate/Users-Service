package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordsDto {
    private float lat;

    private float lon;
}
