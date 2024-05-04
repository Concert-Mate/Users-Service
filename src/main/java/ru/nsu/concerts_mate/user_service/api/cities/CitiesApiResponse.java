package ru.nsu.concerts_mate.user_service.api.cities;

import lombok.Data;
import ru.nsu.concerts_mate.user_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.user_service.api.ApiResponseStatusCode;

import java.util.ArrayList;
import java.util.List;

@Data
public class CitiesApiResponse {
    private ApiResponseStatus status;

    private List<String> cities;

    public CitiesApiResponse(ApiResponseStatusCode code) {
        this(code, new ArrayList<>());
    }

    public CitiesApiResponse(List<String> cities) {
        this(ApiResponseStatusCode.SUCCESS, cities);
    }

    public CitiesApiResponse(ApiResponseStatusCode code, List<String> cities) {
        this.status = new ApiResponseStatus(code);
        this.cities = cities;
    }
}
