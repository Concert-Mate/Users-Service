package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;

@Data
public class UsersAPIDataResponse<O> {
    private UsersAPIResponseStatus status = UsersAPIResponseStatus.SUCCESS;

    private O object;

    public UsersAPIDataResponse(O object) {
        this.object = object;
    }
}
