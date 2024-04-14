package ru.nsu.concertsmate.backend.api;

import lombok.Data;

@Data
public class ResponseStatus {
    private int code;

    private String message;

    private boolean isSuccess;

    public ResponseStatus() {
        this(ResponseStatusCode.SUCCESS);
    }

    public ResponseStatus(ResponseStatusCode code) {
        this.code = code.ordinal();
        this.message = code.name();
        this.isSuccess = code == ResponseStatusCode.SUCCESS;
    }
}
