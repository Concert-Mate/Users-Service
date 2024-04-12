package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UsersApiDataResponse<O> {
    @Setter @Getter
    private int status = UsersApiResponseStatus.SUCCESS.ordinal();

    private O result;

    public UsersApiDataResponse(O object) {
        this.result = object;
    }
}
