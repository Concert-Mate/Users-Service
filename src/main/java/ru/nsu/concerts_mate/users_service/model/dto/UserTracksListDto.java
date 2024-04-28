package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserTracksListDto {
    @JsonProperty(value = "user_id")
    private long userId;

    @JsonProperty(value = "track_list_url")
    private String tracksListUrl;
}
