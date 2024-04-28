package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.users.*;
import ru.nsu.concerts_mate.users_service.model.dto.*;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesService;
import ru.nsu.concerts_mate.users_service.services.cities.CitiesServiceException;
import ru.nsu.concerts_mate.users_service.services.cities.CitySearchByNameCode;
import ru.nsu.concerts_mate.users_service.services.music.MusicService;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concerts_mate.users_service.services.users.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.users.UsersService;
import ru.nsu.concerts_mate.users_service.services.users.UsersShownConcertsService;
import ru.nsu.concerts_mate.users_service.services.users.UsersTracksListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final UsersCitiesService usersCitiesService;
    private final UsersTracksListsService usersTracksListsService;
    private final MusicService musicService;
    private final UsersShownConcertsService shownConcertsService;
    private final CitiesService citiesService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService usersCitiesService, UsersTracksListsService usersTracksListsService, MusicService musicService, UsersShownConcertsService shownConcertsService, CitiesService citiesService) {
        this.usersService = usersService;
        this.usersCitiesService = usersCitiesService;
        this.usersTracksListsService = usersTracksListsService;
        this.musicService = musicService;
        this.shownConcertsService = shownConcertsService;
        this.citiesService = citiesService;
    }

    @Override
    public AddUserApiResponse addUser(long telegramId) {
        try {
            final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
            if (optionalUser.isPresent()) {
                return new AddUserApiResponse(ApiResponseStatusCode.USER_ALREADY_EXISTS, optionalUser.get());
            }
            UserDto res = usersService.addUser(telegramId);
            return new AddUserApiResponse(res);
        } catch (Exception ignored) {
            return new AddUserApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUser(long telegramId) {
        try {
            usersService.deleteUser(telegramId);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserCitiesResponse getUserCities(long telegramId) {
        try {
            return new UserCitiesResponse(usersCitiesService.getUserCities(telegramId));
        } catch (UserNotFoundException ignored) {
            return new UserCitiesResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserCitiesResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse addUserCity(long telegramId, String cityName) {
        String cityToAdd;
        try {
            var res = citiesService.findCity(cityName);
            if (res.getCode() == CitySearchByNameCode.SUCCESS){
                cityToAdd = res.getOptions().get(0).getName();
            }
            else if (res.getCode() == CitySearchByNameCode.FUZZY){
                return new DefaultUsersApiResponse(ApiResponseStatusCode.FUZZY_CITY);
            }
            else if (res.getCode() == CitySearchByNameCode.NOT_FOUND){
                return new DefaultUsersApiResponse(ApiResponseStatusCode.INVALID_CITY);
            }
            else {
                return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
            }
        } catch (CitiesServiceException exception) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }

        try {
            usersCitiesService.saveUserCity(telegramId, cityToAdd);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityAlreadyAddedException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.CITY_ALREADY_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUserCity(long telegramId, String cityName) {
        try {
            usersCitiesService.deleteUserCity(telegramId, cityName);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityNotAddedException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.CITY_NOT_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTracksListsResponse getUserTracksLists(long telegramId) {
        // TODO: return list of DTO's: list of track-lists

        try {
            return new UserTracksListsResponse(usersTracksListsService.getUserTracksLists(telegramId));
        } catch (UserNotFoundException ignored) {
            return new UserTracksListsResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserTracksListsResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse addUserTracksList(long telegramId, String tracksListURL) {
        try {
            musicService.getArtistsByPlayListURL(tracksListURL);
            usersTracksListsService.saveUserTracksList(telegramId, tracksListURL);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAddedException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (MusicServiceException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INVALID_TRACKS_LIST);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public DefaultUsersApiResponse deleteUserTracksList(long telegramId, String tracksListURL) {
        try {
            usersTracksListsService.deleteUserTracksList(telegramId, tracksListURL);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListNotAddedException ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.TRACKS_LIST_NOT_ADDED);
        } catch (Exception ignored) {
            return new DefaultUsersApiResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserConcertsResponse getUserConcerts(long telegramId) {
        final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
        if (optionalUser.isEmpty()) {
            return new UserConcertsResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        }
        try {
            List<String> userCities = usersCitiesService.getUserCities(optionalUser.get().getTelegramId());
            if (userCities.isEmpty()) {
                return new UserConcertsResponse();
            }
            List<String> userTrackLists = usersTracksListsService.getUserTracksLists(telegramId);
            if (userTrackLists.isEmpty()) {
                return new UserConcertsResponse();
            }
            HashSet<Integer> userArtists = new HashSet<>();


            for (String trackList : userTrackLists) {
                try {
                    List<ArtistDto> artistDtoList = musicService.getArtistsByPlayListURL(trackList);
                    for (ArtistDto artist : artistDtoList) {
                        userArtists.add(artist.getYandexMusicId());
                    }
                } catch (MusicServiceException e) {
                    usersTracksListsService.deleteUserTracksList(telegramId, trackList);
                }
            }

            HashSet<String> userCitiesSet = new HashSet<>(userCities);
            List<ConcertDto> ret = new ArrayList<>();
            for (int artistId : userArtists) {
                List<ConcertDto> artistConcerts = musicService.getConcertsByArtistId(artistId);
                for (ConcertDto concert : artistConcerts) {
                    if (userCitiesSet.contains(concert.getCity())) {
                        ret.add(concert);
                        saveShownConcertNoException(telegramId, concert.getAfishaUrl());
                    }
                }
            }

            return new UserConcertsResponse(ret);
        } catch (Exception ignored) {
            return new UserConcertsResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    private void saveShownConcertNoException(long telegramId, String concertUrl) {
        try {
            shownConcertsService.saveShownConcert(telegramId, concertUrl);
        } catch (Exception ignored) {
        }
    }
}
