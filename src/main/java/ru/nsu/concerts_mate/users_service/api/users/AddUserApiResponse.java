package ru.nsu.concerts_mate.users_service.api.users;

import lombok.Data;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatus;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;


@Data
public class AddUserApiResponse {

    private ApiResponseStatus status;

    private UserDto user;

    public AddUserApiResponse(ApiResponseStatusCode code) {
        this.status = new ApiResponseStatus(code);
        this.user = null;
    }

    public AddUserApiResponse(UserDto user) {
        this.status = new ApiResponseStatus(ApiResponseStatusCode.SUCCESS);
        this.user = user;
    }

    public AddUserApiResponse(ApiResponseStatusCode code, UserDto user){
        this.status = new ApiResponseStatus(code);
        this.user = user;
    }
}
