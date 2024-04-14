package ru.nsu.concertsmate.users_service.api.users;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserCitiesResponse {
    private UsersApiResponseStatus status;

    private List<String> cities;

    public UserCitiesResponse() {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.cities = new ArrayList<>();
    }

    public UserCitiesResponse(UsersApiResponseStatusCode code) {
        this.status = new UsersApiResponseStatus(code);
        this.cities = new ArrayList<>();
    }

    public UserCitiesResponse(List<String> cities) {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.cities = cities;
    }
}
