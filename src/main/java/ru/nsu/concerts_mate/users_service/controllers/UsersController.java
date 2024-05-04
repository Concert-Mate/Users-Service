package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.ApiResponseStatusCode;
import ru.nsu.concerts_mate.users_service.api.users.*;
import ru.nsu.concerts_mate.users_service.model.dto.*;
import ru.nsu.concerts_mate.users_service.services.cities.*;
import ru.nsu.concerts_mate.users_service.services.music.MusicService;
import ru.nsu.concerts_mate.users_service.services.music.exceptions.MusicServiceException;
import ru.nsu.concerts_mate.users_service.services.users.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.users.UsersService;
import ru.nsu.concerts_mate.users_service.services.users.UsersShownConcertsService;
import ru.nsu.concerts_mate.users_service.services.users.UsersTrackListsService;
import ru.nsu.concerts_mate.users_service.services.users.exceptions.*;

import java.util.*;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final UsersCitiesService usersCitiesService;
    private final UsersTrackListsService usersTrackListsService;
    private final MusicService musicService;
    private final UsersShownConcertsService shownConcertsService;
    private final CitiesService citiesService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService usersCitiesService, UsersTrackListsService usersTrackListsService, MusicService musicService, UsersShownConcertsService shownConcertsService, CitiesService citiesService) {
        this.usersService = usersService;
        this.usersCitiesService = usersCitiesService;
        this.usersTrackListsService = usersTrackListsService;
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
    public UserCityAddResponse addUserCity(long telegramId, String cityName, Float lat, Float lon) {
        String cityToAdd;
        if (cityName == null && (lat == null || lon == null)){
            return new UserCityAddResponse(ApiResponseStatusCode.INVALID_COORDS);
        }
        if (cityName != null){
            try {
                var res = citiesService.findCity(cityName);
                if (res.getCode() == CitySearchByNameCode.SUCCESS) {
                    cityToAdd = res.getOptions().get(0).getName();
                } else if (res.getCode() == CitySearchByNameCode.FUZZY) {
                    return new UserCityAddResponse(ApiResponseStatusCode.FUZZY_CITY, res.getOptions().get(0).getName());
                } else if (res.getCode() == CitySearchByNameCode.NOT_FOUND) {
                    return new UserCityAddResponse(ApiResponseStatusCode.INVALID_CITY);
                } else {
                    return new UserCityAddResponse(ApiResponseStatusCode.INTERNAL_ERROR);
                }
            } catch (CitiesServiceException exception) {
                return new UserCityAddResponse(ApiResponseStatusCode.INTERNAL_ERROR);
            }
        }
        else {
            try {
                CitySearchByCoordsResult res = citiesService.findCity(new CoordsDto(lat, lon));
                if (res.getCode() == CitySearchByCoordsCode.SUCCESS) {
                    var city = res.getOptions().stream().max(Comparator.comparingInt(CityDto::getPopulation));
                    if (city.isEmpty()){
                        return new UserCityAddResponse(ApiResponseStatusCode.INVALID_CITY);
                    }
                    cityToAdd = city.get().getName();
                } else if (res.getCode() == CitySearchByCoordsCode.NOT_FOUND) {
                    return new UserCityAddResponse(ApiResponseStatusCode.INVALID_CITY);
                } else if (res.getCode() == CitySearchByCoordsCode.INVALID_COORDS) {
                    return new UserCityAddResponse(ApiResponseStatusCode.INVALID_COORDS);
                } else {
                    return new UserCityAddResponse(ApiResponseStatusCode.INTERNAL_ERROR);
                }
            } catch (CitiesServiceException e) {
                return new UserCityAddResponse(ApiResponseStatusCode.INTERNAL_ERROR);
            }
        }

        try {
            usersCitiesService.saveUserCity(telegramId, cityToAdd);
            return new UserCityAddResponse(cityToAdd);
        } catch (UserNotFoundException ignored) {
            return new UserCityAddResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (CityAlreadyAddedException ignored) {
            return new UserCityAddResponse(ApiResponseStatusCode.CITY_ALREADY_ADDED);
        } catch (Exception ignored) {
            return new UserCityAddResponse(ApiResponseStatusCode.INTERNAL_ERROR);
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
    public UserTracksListsResponse getUserTrackLists(long telegramId) {

        try {
            List<String> tracksLists = usersTrackListsService.getUserTrackLists(telegramId);
            List<TrackListHeaderDto> result = new ArrayList<>();
            for (String trackList: tracksLists){
                try {
                    TrackListDto artistDtoList = musicService.getPlayListData(trackList);
                    result.add(new TrackListHeaderDto(artistDtoList.getUrl(), artistDtoList.getTitle()));
                } catch (MusicServiceException e) {
                    usersTrackListsService.deleteUserTrackList(telegramId, trackList);
                } catch (InternalErrorException ignored){
                }
            }
            return new UserTracksListsResponse(result);
        } catch (UserNotFoundException ignored) {
            return new UserTracksListsResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserTracksListsResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTrackListResponse addUserTrackList(long telegramId, String tracksListURL) {
        try {
            var res = musicService.getPlayListData(tracksListURL);
            usersTrackListsService.saveUserTrackList(telegramId, tracksListURL);
            return new UserTrackListResponse(new TrackListHeaderDto(res.getUrl(), res.getTitle()));
        } catch (UserNotFoundException ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAddedException ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (MusicServiceException ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.INVALID_TRACKS_LIST);
        } catch (Exception ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.INTERNAL_ERROR);
        }
    }

    @Override
    public UserTrackListResponse deleteUserTrackList(long telegramId, String tracksListURL) {
        try {
            usersTrackListsService.deleteUserTrackList(telegramId, tracksListURL);
            var playListData = musicService.getPlayListData(tracksListURL);
            return new UserTrackListResponse(new TrackListHeaderDto(playListData.getUrl(), playListData.getTitle()));
        } catch (UserNotFoundException ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListNotAddedException ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.TRACKS_LIST_NOT_ADDED);
        } catch (MusicServiceException ignored) {
            return new UserTrackListResponse();
        }catch (Exception ignored) {
            return new UserTrackListResponse(ApiResponseStatusCode.INTERNAL_ERROR);
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
            List<String> userTrackLists = usersTrackListsService.getUserTrackLists(telegramId);
            if (userTrackLists.isEmpty()) {
                return new UserConcertsResponse();
            }
            HashSet<Integer> userArtists = new HashSet<>();


            for (String trackList : userTrackLists) {
                try {
                    List<ArtistDto> artistDtoList = musicService.getPlayListData(trackList).getArtists();
                    for (ArtistDto artist : artistDtoList) {
                        userArtists.add(artist.getYandexMusicId());
                    }
                } catch (MusicServiceException e) {
                    usersTrackListsService.deleteUserTrackList(telegramId, trackList);
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
