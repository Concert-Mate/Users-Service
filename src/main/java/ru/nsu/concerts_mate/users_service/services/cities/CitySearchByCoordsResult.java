package ru.nsu.concerts_mate.users_service.services.cities;


import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.concerts_mate.users_service.model.dto.CityDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CitySearchByCoordsResult {
    private CitySearchByCoordsCode code;

    private List<CityDto> options;
}
