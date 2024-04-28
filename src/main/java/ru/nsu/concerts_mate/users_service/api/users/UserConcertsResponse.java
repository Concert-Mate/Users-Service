package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserConcertsResponse {
    private ApiResponseStatus status;

    private List<ConcertDto> concerts;

    public UserConcertsResponse() {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.concerts = new ArrayList<>();
    }

    public UserConcertsResponse(ApiResponseStatusCode code) {
        this.status = new ApiResponseStatus(code);
        this.concerts = new ArrayList<>();
    }

    public UserConcertsResponse(List<ConcertDto> concerts) {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.concerts = concerts;
    }
}
