package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserTracksListsResponse {
    private UsersApiResponseStatus status;

    private List<String> tracksLists;

    public UserTracksListsResponse() {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.tracksLists = new ArrayList<>();
    }

    public UserTracksListsResponse(UsersApiResponseStatusCode code) {
        this.status = new UsersApiResponseStatus(code);
        this.tracksLists = new ArrayList<>();
    }

    public UserTracksListsResponse(List<String> tracksLists) {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.tracksLists = tracksLists;
    }
}
