package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;

@Data
public class UsersApiDataResponse<O> {
    private UsersApiResponseStatus status = UsersApiResponseStatus.SUCCESS;

    private O object;

    public UsersApiDataResponse(O object) {
        this.object = object;
    }
}
