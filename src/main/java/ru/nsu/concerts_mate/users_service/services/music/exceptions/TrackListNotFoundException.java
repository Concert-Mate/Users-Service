package ru.nsu.concerts_mate.users_service.services.music.exceptions;

public class TrackListNotFoundException extends MusicServiceException {
    public TrackListNotFoundException(String message) {
        super(message);
    }
}
