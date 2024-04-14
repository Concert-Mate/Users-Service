package ru.nsu.concertsmate.backend.api;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserTracksListsResponse {
    private ResponseStatus status;

    private List<String> tracksLists;

    public UserTracksListsResponse(ResponseStatusCode code) {
        this.status = new ResponseStatus(code);
        this.tracksLists = new ArrayList<>();
    }

    public UserTracksListsResponse(List<String> tracksLists) {
        this.status = new ResponseStatus(ResponseStatusCode.SUCCESS);
        this.tracksLists = tracksLists;
    }
}
