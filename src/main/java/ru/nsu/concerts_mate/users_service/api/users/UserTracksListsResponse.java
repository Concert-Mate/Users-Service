package ru.nsu.concerts_mate.users_service.api.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.model.dto.TrackListHeaderDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserTracksListsResponse {
    private ApiResponseStatus status;

    @JsonProperty(value = "tracks_lists")
    private List<TrackListHeaderDto> tracksLists;

    public UserTracksListsResponse() {
        this(ApiResponseStatusCode.SUCCESS, new ArrayList<>());
    }

    public UserTracksListsResponse(ApiResponseStatusCode code) {
        this(code, new ArrayList<>());
    }

    public UserTracksListsResponse(List<TrackListHeaderDto> tracksLists) {
        this(ApiResponseStatusCode.SUCCESS, tracksLists);
    }

    public UserTracksListsResponse(ApiResponseStatusCode code, List<TrackListHeaderDto> tracksLists) {
        this.status = new ApiResponseStatus(code);
        this.tracksLists = tracksLists;
    }
}
