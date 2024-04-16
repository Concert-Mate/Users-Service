package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;

@Data
public class UsersApiResponse {
    private final UsersApiResponseStatus status;

    public UsersApiResponse() {
        this.status = new UsersApiResponseStatus();
    }

    public UsersApiResponse(UsersApiResponseStatusCode code) {
        this.status = new UsersApiResponseStatus(code);
    }
}
