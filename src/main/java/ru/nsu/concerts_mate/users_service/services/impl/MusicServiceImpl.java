package ru.nsu.concerts_mate.users_service.services.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.concerts_mate.users_service.model.dto.ArtistDto;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.TracksListDto;
import ru.nsu.concerts_mate.users_service.services.MusicService;
import ru.nsu.concerts_mate.users_service.services.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.exceptions.MusicServiceException;

import java.util.List;
import java.util.Objects;

@Service

public class MusicServiceImpl implements MusicService {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseStatusDTO{
        private int code;
        private String message;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseMusicServicePlayListDTO{
        private ResponseStatusDTO status;

        private TracksListDto track_list;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ResponseMusicServiceConcertsDTO{
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
    public List<ArtistDto> getArtistsByPlayListURL(String url) throws InternalErrorException, MusicServiceException {
        String serviceUrl = "%s://%s:%d/tracks-lists?url=%s".formatted(serviceSchema, serviceHost, servicePort, url);
        try {
            ResponseEntity<ResponseMusicServicePlayListDTO> res = restTemplate.getForEntity(serviceUrl, ResponseMusicServicePlayListDTO.class);
            if (res.getStatusCode().value() == 422 || !res.hasBody()){
                throw new InternalErrorException();
            }

            if (Objects.requireNonNull(res.getBody()).status.code != 0){
                throw new MusicServiceException(Objects.requireNonNull(res.getBody()).status.message);
            }
            return Objects.requireNonNull(res.getBody()).track_list.getArtists();
        }
        catch (RestClientException e){
            throw new InternalErrorException();
        }
    }

    @Override
    public List<ConcertDto> getConcertsByArtistId(int artistId) throws InternalErrorException, MusicServiceException {
        String serviceUrl = "%s://%s:%d/concerts?artist_id=%d".formatted(serviceSchema, serviceHost, servicePort, artistId);
        try {
            ResponseEntity<ResponseMusicServiceConcertsDTO> res = restTemplate.getForEntity(serviceUrl, ResponseMusicServiceConcertsDTO.class);
            if (res.getStatusCode().value() == 422 || !res.hasBody()){
                throw new InternalErrorException();
            }

            if (Objects.requireNonNull(res.getBody()).status.code != 0){
                throw new MusicServiceException(Objects.requireNonNull(res.getBody()).status.message);
            }
            return Objects.requireNonNull(res.getBody()).concerts;
        }
        catch (RestClientException e){
            throw new InternalErrorException();
        }
    }
}
