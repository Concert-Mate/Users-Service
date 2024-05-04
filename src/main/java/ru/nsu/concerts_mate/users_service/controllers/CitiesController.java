package ru.nsu.concerts_mate.users_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.cities.CitiesApi;
import ru.nsu.concerts_mate.users_service.api.cities.CitiesApiResponse;
import ru.nsu.concerts_mate.users_service.model.dto.CityDto;
import ru.nsu.concerts_mate.users_service.model.dto.CoordsDto;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesService;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesServiceException;
import ru.nsu.concerts_mate.users_service.services.cities.CitySearchByCoordsCode;
import ru.nsu.concerts_mate.users_service.services.cities.CitySearchByCoordsResult;

@RestController
@RequiredArgsConstructor
public class CitiesController implements CitiesApi {
    private final CitiesService citiesService;

    @Override
    public CitiesApiResponse getCities(float lat, float lon) {
        try {
            CitySearchByCoordsResult res = citiesService.findCity(new CoordsDto(lat, lon));
            if (res.getCode() == CitySearchByCoordsCode.SUCCESS) {
                return new CitiesApiResponse(res.getOptions().stream().map(CityDto::getName).toList());
            } else if (res.getCode() == CitySearchByCoordsCode.NOT_FOUND) {
                return new CitiesApiResponse(ApiResponseStatusCode.SUCCESS);
            } else if (res.getCode() == CitySearchByCoordsCode.INVALID_COORDS) {
                return new CitiesApiResponse(ApiResponseStatusCode.INVALID_COORDS);
            }
            return new CitiesApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        } catch (CitiesServiceException e) {
            return new CitiesApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }
}
