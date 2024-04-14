package ru.nsu.concertsmate.users_service.api.users;

import lombok.Data;

@Data
public class UsersApiResponseStatus {
    private int code;

    private String message;

    private boolean isSuccess;

    public UsersApiResponseStatus() {
        this(UsersApiResponseStatusCode.SUCCESS);
    }

    public UsersApiResponseStatus(UsersApiResponseStatusCode code) {
        this.code = code.ordinal();
        this.message = code.name();
        this.isSuccess = code == UsersApiResponseStatusCode.SUCCESS;
    }
}
