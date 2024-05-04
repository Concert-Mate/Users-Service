package ru.nsu.concert_mate.user_service.services.music.exceptions;

public class ArtistNotFoundException extends MusicServiceException {
    public ArtistNotFoundException(String message) {
        super(message);
    }
}
