package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserTrackListDto {
    @JsonProperty(value = "user_id")
    private long userId;

    @JsonProperty(value = "track_list_url")
    private String trackListUrl;
}
