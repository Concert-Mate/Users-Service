package ru.nsu.concertsmate.users_service.api.users;

import lombok.Data;
import ru.nsu.concertsmate.users_service.model.dto.ConcertDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserConcertsResponse {
    private UsersApiResponseStatus status;

    private List<ConcertDto> concerts;

    public UserConcertsResponse() {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.concerts = new ArrayList<>();
    }

    public UserConcertsResponse(UsersApiResponseStatusCode code) {
        this.status = new UsersApiResponseStatus(code);
        this.concerts = new ArrayList<>();
    }

    public UserConcertsResponse(List<ConcertDto> concerts) {
        this.status = new UsersApiResponseStatus(UsersApiResponseStatusCode.SUCCESS);
        this.concerts = concerts;
    }
}
