package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;

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
