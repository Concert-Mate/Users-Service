package ru.nsu.concerts_mate.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.concerts_mate.users_service.api.users.*;
import ru.nsu.concerts_mate.users_service.model.dto.ArtistDto;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.PriceDto;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;
import ru.nsu.concerts_mate.users_service.services.UsersCitiesService;
import ru.nsu.concerts_mate.users_service.services.UsersService;
import ru.nsu.concerts_mate.users_service.services.UsersTracksListsService;
import ru.nsu.concerts_mate.users_service.services.exceptions.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController()
public class UsersController implements UsersApi {
    private final UsersService usersService;
    private final UsersCitiesService citiesService;
    private final UsersTracksListsService tracksListsService;

    @Autowired
    public UsersController(UsersService usersService, UsersCitiesService citiesService, UsersTracksListsService tracksListsService) {
        this.usersService = usersService;
        this.citiesService = citiesService;
        this.tracksListsService = tracksListsService;
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
            tracksListsService.saveUserTracksList(telegramId, tracksListURL);
            return new DefaultUsersApiResponse();
        } catch (UserNotFoundException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (TracksListAlreadyAddedException ignored) {
            return new DefaultUsersApiResponse(UsersApiResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
        } catch (Exception ignored) {
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
        try {
            final Optional<UserDto> optionalUser = usersService.findUser(telegramId);
            if (optionalUser.isPresent()) {
                // TODO: fix
                final Random random = new Random();
                if (random.nextDouble() >= 0.95) {
                    return new UserConcertsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
                }

                final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
                final List<ConcertDto> concerts = new ArrayList<>();
                concerts.add(new ConcertDto(
                        "Дора",
                        "https://afisha.yandex.ru/novosibirsk/concert/dora-2024-05-03",
                        "Новосибирск",
                        "Подземка",
                        "Красный просп., 161",
                        dateFormat.parse("2024-05-03 20:00:00+07:00"),
                        "https://maps.yandex.ru/?z=16&ll=82.911402,55.061446&pt=82.911402,55.061446",
                        List.of("https://avatars.mds.yandex.net/get-afishanew/31447/9bd67f14763a75218410d90c07a10708/orig"),
                        new PriceDto(1500, "RUB"),
                        List.of(new ArtistDto("Дора", 6826935))
                ));
                concerts.add(new ConcertDto(
                        "Б.А.У.",
                        "https://afisha.yandex.ru/cheboksary/concert/b-a-u-tour",
                        "Чебоксары",
                        "SK Bar",
                        "ул. Карла Маркса, 47",
                        dateFormat.parse("2024-04-16 19:00:00+03:00"),
                        "https://maps.yandex.ru/?z=16&ll=47.247889,56.134405&pt=47.247889,56.134405",
                        List.of("https://avatars.mds.yandex.net/get-afishanew/28638/b8b59918da3749ff123d7530fc1d6850/orig"),
                        new PriceDto(1500, "RUB"),
                        List.of(new ArtistDto("Б.А.У.", 3680757))
                ));

                return new UserConcertsResponse(concerts);
            }
            return new UserConcertsResponse(UsersApiResponseStatusCode.USER_NOT_FOUND);
        } catch (Exception ignored) {
            return new UserConcertsResponse(UsersApiResponseStatusCode.INTERNAL_ERROR);
        }
    }
}
