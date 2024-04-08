package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;

@Data
public class UsersApiDataResponse<O> {
    private UsersApiResponseStatus status = UsersApiResponseStatus.SUCCESS;

    private O result;

    public UsersApiDataResponse(O object) {
        this.result = object;
    }
}
