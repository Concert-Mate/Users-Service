package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;

@Data
public class DefaultUsersApiResponse {
    private final UsersApiResponseStatus status;

    public DefaultUsersApiResponse() {
        this.status = new UsersApiResponseStatus();
    }

    public DefaultUsersApiResponse(UsersApiResponseStatusCode code) {
        this.status = new UsersApiResponseStatus(code);
    }
}
