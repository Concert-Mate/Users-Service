package ru.nsu.concertsmate.backend.api;

import lombok.Data;

@Data
public class Response {
    private ResponseStatus status;

    public Response() {
        this.status = new ResponseStatus();
    }

    public Response(ResponseStatusCode code) {
        this.status = new ResponseStatus(code);
    }
}
