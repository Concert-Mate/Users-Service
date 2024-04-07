package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;

@Data
public class UsersApiResponse {
    private UsersApiResponseStatus status = UsersApiResponseStatus.SUCCESS;
}
