package ru.nsu.concerts_mate.users_service.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApiResponseStatus {
    private int code;

    private String message;

    @JsonProperty(value = "is_success")
    private boolean isSuccess;

    public ApiResponseStatus() {
        this(ApiResponseStatusCode.SUCCESS);
    }

    public ApiResponseStatus(ApiResponseStatusCode code) {
        this.code = code.ordinal();
        this.message = code.name();
        this.isSuccess = code == ApiResponseStatusCode.SUCCESS;
    }
}
