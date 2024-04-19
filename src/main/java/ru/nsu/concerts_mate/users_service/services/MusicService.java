package ru.nsu.concerts_mate.users_service.services;

import ru.nsu.concerts_mate.users_service.model.dto.ArtistDto;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.services.exceptions.InternalErrorException;
import ru.nsu.concerts_mate.users_service.services.exceptions.MusicServiceException;

import java.util.List;

public interface MusicService {

    List<ArtistDto> getArtistsByPlayListURL(String url) throws InternalErrorException, MusicServiceException;

    List<ConcertDto> getConcertsByArtistId(int artistId) throws InternalErrorException, MusicServiceException;

}
