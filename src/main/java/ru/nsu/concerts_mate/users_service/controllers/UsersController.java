package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.users.*;
import ru.nsu.concerts_mate.users_service.model.dto.*;
import ru.nsu.concerts_mate.users_service.services.music.MusicService;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concerts_mate.users_service.services.users.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.users.UsersService;
import ru.nsu.concerts_mate.users_service.services.users.UsersShownConcertsService;
import ru.nsu.concerts_mate.users_service.services.users.UsersTracksListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.*;


import java.util.*;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final UsersCitiesService citiesService;
    private final UsersTracksListsService tracksListsService;
    private final MusicService musicService;
    private final UsersShownConcertsService shownConcertsService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService citiesService, UsersTracksListsService tracksListsService, MusicService musicService, UsersShownConcertsService shownConcertsService) {
        this.usersService = usersService;
        this.citiesService = citiesService;
        this.tracksListsService = tracksListsService;
        this.musicService = musicService;
        this.shownConcertsService = shownConcertsService;
    }

    @Override
    public DefaultUsersApiResponse addUser(long telegramId) {
        try {
            final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
            if (optionalUser.isPresent()) {
                return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_ALREADY_EXISTS);
            }
            usersService.addUser(telegramId);
            return new DefaultUsersApiResponse();
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUser(long telegramId) {
        try {
            usersService.deleteUser(telegramId);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserCitiesResponse getUserCities(long telegramId) {
        try {
            return new UserCitiesResponse(citiesService.getUserCities(telegramId));
        } catch (UserNotFoundException ignored) {
            return new UserCitiesResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserCitiesResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse addUserCity(long telegramId, String cityName) {
        try {
            citiesService.saveUserCity(telegramId, cityName);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityAlreadyAddedException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.CITY_ALREADY_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUserCity(long telegramId, String cityName) {
        try {
            citiesService.deleteUserCity(telegramId, cityName);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityNotAddedException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.CITY_NOT_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTracksListsResponse getUserTracksLists(long telegramId) {
        // TODO: return list of DTO's: list of track-lists

        try {
            return new UserTracksListsResponse(tracksListsService.getUserTracksLists(telegramId));
        } catch (UserNotFoundException ignored) {
            return new UserTracksListsResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserTracksListsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse addUserTracksList(long telegramId, String tracksListURL) {
        try {
            musicService.getArtistsByPlayListURL(tracksListURL);
            tracksListsService.saveUserTracksList(telegramId, tracksListURL);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAddedException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (MusicServiceException ignored){
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INVALID_TRACKS_LIST);
        }
        catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUserTracksList(long telegramId, String tracksListURL) {
        try {
            tracksListsService.deleteUserTracksList(telegramId, tracksListURL);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListNotAddedException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.TRACKS_LIST_NOT_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserConcertsResponse getUserConcerts(long telegramId) {
        final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
        if (optionalUser.isEmpty()){
            return new UserConcertsResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        }
        try {
            List<String> userCities = citiesService.getUserCities(optionalUser.get().getTelegramId());
            if (userCities.isEmpty()){
                return new UserConcertsResponse();
            }
            List<String> userTrackLists = tracksListsService.getUserTracksLists(telegramId);
            if (userTrackLists.isEmpty()){
                return new UserConcertsResponse();
            }
            HashSet<Integer> userArtists = new HashSet<>();


            for (String trackList: userTrackLists){
                try {
                    List<ArtistDto> artistDtoList = musicService.getArtistsByPlayListURL(trackList);
                    for (ArtistDto artist: artistDtoList){
                        userArtists.add(artist.getYandexMusicId());
                    }
                } catch (MusicServiceException e){
                    tracksListsService.deleteUserTracksList(telegramId, trackList);
                }
            }

            HashSet<String> userCitiesSet = new HashSet<>(userCities);
            List<ConcertDto> ret = new ArrayList<>();
            for (int artistId: userArtists){
                List<ConcertDto> artistConcerts = musicService.getConcertsByArtistId(artistId);
                for (ConcertDto concert: artistConcerts){
                    if (userCitiesSet.contains(concert.getCity())){
                        ret.add(concert);
                        saveShownConcertNoException(telegramId, concert.getAfishaUrl());
                    }
                }
            }

            return new UserConcertsResponse(ret);
        } catch (Exception ignored) {
            return new UserConcertsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    private void saveShownConcertNoException(long telegramId, String concertUrl){
        try{
            shownConcertsService.saveShownConcert(telegramId, concertUrl);
        }
        catch (Exception ignored){}
    }
}
