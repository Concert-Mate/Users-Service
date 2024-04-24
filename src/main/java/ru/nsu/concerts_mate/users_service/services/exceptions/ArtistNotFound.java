package ru.nsu.concerts_mate.users_service.services.exceptions;

public class ArtistNotFound extends MusicServiceException{
    public ArtistNotFound(String e){
        super(e);
    }
}
