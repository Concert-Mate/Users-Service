package ru.nsu.concert_mate.user_service.api.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatus;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatusCode;
import ru.nsu.concert_mate.user_service.model.dto.TrackListHeaderDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserTrackListsResponse {
    private ApiResponseStatus status;

    @JsonProperty(value = "track_lists")
    private List<TrackListHeaderDto> trackLists;

    public UserTrackListsResponse(ApiResponseStatusCode code) {
        this(code, new ArrayList<>());
    }

    public UserTrackListsResponse(List<TrackListHeaderDto> trackLists) {
        this(ApiResponseStatusCode.SUCCESS, trackLists);
    }

    public UserTrackListsResponse(ApiResponseStatusCode code, List<TrackListHeaderDto> trackLists) {
        this.status = new ApiResponseStatus(code);
        this.trackLists = trackLists;
    }
}
