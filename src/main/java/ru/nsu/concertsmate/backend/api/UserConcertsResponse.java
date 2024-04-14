package ru.nsu.concertsmate.backend.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserConcertsResponse {
    private ResponseStatus status;

    private List<ConcertDto> concerts = new ArrayList<>();

    public UserConcertsResponse(ResponseStatusCode code) {
        this.status = new ResponseStatus(code);
    }
}
