package ru.nsu.concerts_mate.users_service.api.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserTracksListsResponse {
    private ApiResponseStatus status;

    @JsonProperty(value = "tracks_lists")
    private List<String> tracksLists;

    public UserTracksListsResponse() {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.tracksLists = new ArrayList<>();
    }

    public UserTracksListsResponse(ApiResponseStatusCode code) {
        this.status = new ApiResponseStatus(code);
        this.tracksLists = new ArrayList<>();
    }

    public UserTracksListsResponse(List<String> tracksLists) {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.tracksLists = tracksLists;
    }
}
