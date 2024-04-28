package ru.nsu.concerts_mate.users_service.api.cities;

import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;

import java.util.List;

@Data
public class CitiesApiResponse {
    private ApiResponseStatus status;

    private List<String> cities;

    public CitiesApiResponse(ApiResponseStatusCode code){
        this.status = new ApiResponseStatus(code);
        this.cities = List.of();
    }

    public CitiesApiResponse(List<String> cities){
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.cities = cities;
    }
}
