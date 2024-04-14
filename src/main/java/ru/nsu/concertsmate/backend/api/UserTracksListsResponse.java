package ru.nsu.concertsmate.backend.api;

import lombok.Data;

import java.util.List;

@Data
public class UserTracksListsResponse {
    private ResponseStatus status;

    private List<String> tracksLists;
}
