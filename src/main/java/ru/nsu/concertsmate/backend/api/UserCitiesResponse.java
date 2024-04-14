package ru.nsu.concertsmate.backend.api;

import lombok.Data;

import java.util.List;

@Data
public class UserCitiesResponse {
    private ResponseStatus status;

    private List<String> cities;
}
