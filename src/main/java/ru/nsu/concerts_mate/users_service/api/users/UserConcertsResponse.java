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
        this(ApiResponseStatusCode.SUCCESS, new ArrayList<>());
    }

    public UserConcertsResponse(ApiResponseStatusCode code) {
        this(code, new ArrayList<>());
    }

    public UserConcertsResponse(List<ConcertDto> concerts) {
        this(ApiResponseStatusCode.SUCCESS, concerts);
    }

    public UserConcertsResponse(ApiResponseStatusCode code, List<ConcertDto> concerts){
        this.status = new ApiResponseStatus(code);
        this.concerts = concerts;
    }
}
