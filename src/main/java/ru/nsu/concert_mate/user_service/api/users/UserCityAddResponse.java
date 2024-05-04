package ru.nsu.concert_mate.user_service.api.users;

import lombok.Data;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatus;
import ru.nsu.concert_mate.user_service.api.ApiResponseStatusCode;

@Data
public class UserCityAddResponse {
    private ApiResponseStatus status;

    private String city;

    public UserCityAddResponse() {
        this(ApiResponseStatusCode.SUCCESS, null);
    }

    public UserCityAddResponse(ApiResponseStatusCode code) {
        this(code, null);
    }

    public UserCityAddResponse(String city) {
        this(ApiResponseStatusCode.SUCCESS, city);
    }

    public UserCityAddResponse(ApiResponseStatusCode code, String city) {
        this.status = new ApiResponseStatus(code);
        this.city = city;
    }
}
