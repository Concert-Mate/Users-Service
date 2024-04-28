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
        this(ApiResponseStatusCode.SUCCESS, new ArrayList<>());
    }

    public UserCitiesResponse(ApiResponseStatusCode code) {
        this(code, new ArrayList<>());
    }

    public UserCitiesResponse(List<String> cities) {
        this(ApiResponseStatusCode.SUCCESS, cities);
    }

    public UserCitiesResponse(ApiResponseStatusCode code, List<String> cities){
        this.status = new ApiResponseStatus(code);
        this.cities = cities;
    }
}
