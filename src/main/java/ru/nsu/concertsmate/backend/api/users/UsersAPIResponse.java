package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;

@Data
public class UsersAPIResponse {
    private UsersAPIResponseStatus status = UsersAPIResponseStatus.SUCCESS;
}
