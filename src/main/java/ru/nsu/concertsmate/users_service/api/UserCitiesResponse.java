package ru.nsu.concertsmate.users_service.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserCitiesResponse {
    private ResponseStatus status;

    private List<String> cities;

    public UserCitiesResponse(ResponseStatusCode code) {
        this.status = new ResponseStatus(code);
        this.cities = new ArrayList<>();
    }

    public UserCitiesResponse(List<String> cities) {
        this.status = new ResponseStatus(ResponseStatusCode.SUCCESS);
        this.cities = cities;
    }
}
