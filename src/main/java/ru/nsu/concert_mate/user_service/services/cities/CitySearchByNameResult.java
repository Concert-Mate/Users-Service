package ru.nsu.concert_mate.user_service.services.cities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.concert_mate.user_service.model.dto.CityDto;

import java.util.List;

@Data
@AllArgsConstructor
public class CitySearchByNameResult {
    private CitySearchByNameCode code;

    private List<CityDto> options;
}
