package ru.nsu.concert_mate.user_service.api.users;

import lombok.Data;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatus;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatusCode;

@Data
public class DefaultUsersApiResponse {
    private final ApiResponseStatus status;

    public DefaultUsersApiResponse() {
        this.status = new ApiResponseStatus();
    }

    public DefaultUsersApiResponse(ApiResponseStatusCode code) {
        this.status = new ApiResponseStatus(code);
    }
}
