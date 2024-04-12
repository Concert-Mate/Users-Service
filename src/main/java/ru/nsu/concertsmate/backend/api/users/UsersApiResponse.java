package ru.nsu.concertsmate.backend.api.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UsersApiResponse {
    private int status = UsersApiResponseStatus.SUCCESS.ordinal();
}
