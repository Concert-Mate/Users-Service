package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCitiesResponse {
    private ApiResponseStatus status;

    private List<String> cities;

    public UserCitiesResponse() {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.cities = new ArrayList<>();
    }

    public UserCitiesResponse(ApiResponseStatusCode code) {
        this.status = new ApiResponseStatus(code);
        this.cities = new ArrayList<>();
    }

    public UserCitiesResponse(List<String> cities) {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.cities = cities;
    }

    public UserCitiesResponse(ApiResponseStatusCode code, List<String> cities){
        this.status = new ApiResponseStatus(code);
        this.cities = cities;
    }
}
