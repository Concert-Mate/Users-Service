package ru.nsu.concerts_mate.users_service.api.users;


import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.model.dto.TrackListHeaderDto;

public class UserTrackListResponse {
    private ApiResponseStatus status;

    @JsonProperty(value = "tracks_list")
    private TrackListHeaderDto tracksList;

    public UserTrackListResponse() {
        this(ApiResponseStatusCode.SUCCESS, null);
    }

    public UserTrackListResponse(ApiResponseStatusCode code) {
        this(code, null);
    }

    public UserTrackListResponse(TrackListHeaderDto tracksList) {
        this(ApiResponseStatusCode.SUCCESS, tracksList);
    }

    public UserTrackListResponse(ApiResponseStatusCode code, TrackListHeaderDto tracksList) {
        this.status = new ApiResponseStatus(code);
        this.tracksList = tracksList;
    }
}
