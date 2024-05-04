package ru.nsu.concert_mate.user_service.services.music;

import ru.nsu.concert_mate.user_service.model.dto.ConcertDto;
import ru.nsu.concert_mate.user_service.model.dto.TrackListDto;
import ru.nsu.concert_mate.user_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concert_mate.user_service.services.users.exceptions.InternalErrorException;

import java.util.List;

public interface MusicService {
    TrackListDto getTrackListData(String url) throws InternalErrorException, MusicServiceException;

    List<ConcertDto> getConcertsByArtistId(int artistId) throws InternalErrorException, MusicServiceException;
}
