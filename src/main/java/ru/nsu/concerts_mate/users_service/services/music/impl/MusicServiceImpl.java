package ru.nsu.concerts_mate.users_service.services.music.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.TracksListDto;
import ru.nsu.concerts_mate.users_service.services.music.MusicService;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.ArtistNotFoundException;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.TrackListNotFoundException;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.InternalErrorException;

import java.util.List;
import java.util.Objects;

@Service
public class MusicServiceImpl implements MusicService {
    // todo сделать нормально после релиза
    enum ErrorCodes {
        SUCCESS, INTERNAL_ERROR, ARTIST_NOT_FOUND, TRACK_LIST_NOT_FOUND
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseStatusDTO {
        private int code;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseMusicServicePlayListDTO {
        private ResponseStatusDTO status;

        @JsonProperty(value = "track_list")
        private TracksListDto trackList;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseMusicServiceConcertsDTO {
        private ResponseStatusDTO status;

        private List<ConcertDto> concerts;
    }

    @Value("${spring.music-service.host}")
    private String serviceHost;

    @Value("${spring.music-service.port}")
    private int servicePort;

    @Value("${spring.music-service.schema}")
    private String serviceSchema;

    private final RestTemplate restTemplate;

    @Autowired
    public MusicServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public TracksListDto getPlayListData(String url) throws InternalErrorException, MusicServiceException {
        String serviceUrl = "%s://%s:%d/track-lists?url=%s".formatted(serviceSchema, serviceHost, servicePort, url);
        try {
            ResponseEntity<ResponseMusicServicePlayListDTO> res = restTemplate.getForEntity(serviceUrl, ResponseMusicServicePlayListDTO.class);
            if (res.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY || !res.hasBody()) {
                throw new InternalErrorException();
            }

            if (Objects.requireNonNull(res.getBody()).status.code == ErrorCodes.SUCCESS.ordinal()) {
                return Objects.requireNonNull(res.getBody()).trackList;
            } else if (Objects.requireNonNull(res.getBody()).status.code == ErrorCodes.TRACK_LIST_NOT_FOUND.ordinal()) {
                throw new TrackListNotFoundException(Objects.requireNonNull(res.getBody()).status.message);
            }
            throw new InternalErrorException();
        } catch (RestClientException e) {
            throw new InternalErrorException();
        }
    }

    @Override
    public List<ConcertDto> getConcertsByArtistId(int artistId) throws InternalErrorException, MusicServiceException {
        String serviceUrl = "%s://%s:%d/concerts?artist_id=%d".formatted(serviceSchema, serviceHost, servicePort, artistId);
        try {
            ResponseEntity<ResponseMusicServiceConcertsDTO> res = restTemplate.getForEntity(serviceUrl, ResponseMusicServiceConcertsDTO.class);
            if (res.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY || !res.hasBody()) {
                throw new InternalErrorException();
            }

            if (Objects.requireNonNull(res.getBody()).status.code == ErrorCodes.SUCCESS.ordinal()) {
                return Objects.requireNonNull(res.getBody()).concerts;
            } else if (Objects.requireNonNull(res.getBody()).status.code == ErrorCodes.ARTIST_NOT_FOUND.ordinal()) {
                throw new ArtistNotFoundException(Objects.requireNonNull(res.getBody()).status.message);
            }
            throw new InternalErrorException();
        } catch (RestClientException e) {
            throw new InternalErrorException();
        }
    }
}
