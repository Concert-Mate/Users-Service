package ru.nsu.concerts_mate.users_service.services.exceptions;

public class TrackListNotFound extends MusicServiceException{
    public TrackListNotFound(String s) {
        super(s);
    }
}
