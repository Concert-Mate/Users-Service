package ru.nsu.concertsmate.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import ru.nsu.concertsmate.backend.api.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// TODO: add tests for adding invalid cities or invalid tracks-lists

@TestPropertySource("/application.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationTests {
    private static class TelegramId {
        private static final AtomicLong id = new AtomicLong();

        public TelegramId() {
            id.incrementAndGet();
        }

        public long getId() {
            return id.get();
        }
    }

    private static class City {
        private static final AtomicLong cityIndex = new AtomicLong();

        public City() {
            cityIndex.incrementAndGet();
        }

        public String getCity() {
            return String.format("City-%d", cityIndex.get());
        }
    }

    private static class TracksList {
        private static final AtomicLong tracksListIndex = new AtomicLong();

        public TracksList() {
            tracksListIndex.incrementAndGet();
        }

        public String getUrl() {
            return String.format("URL-%d", tracksListIndex.get());
        }
    }

    @LocalServerPort
    private int port;

    @Autowired
    JdbcTemplate jdbcTemplate;
    private String baseUrl = "http://localhost:";

    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        restTemplate = new RestTemplate();
        baseUrl = baseUrl + port;
    }

    @Test
    void testAddUser() {
        addUserSuccess(new TelegramId().getId());
    }

    @Test
    void testAddUserTwice() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        addUserAlreadyExists(telegramId);
    }

    @Test
    void testAddDeleteUser() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        deleteUserSuccess(telegramId);
    }

    @Test
    void testDeleteUnknownUser() {
        final long telegramId = new TelegramId().getId();
        deleteUserNotFound(telegramId);
    }

    @Test
    void testAddDeleteTwiceUser() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        deleteUserSuccess(telegramId);
        deleteUserNotFound(telegramId);
    }

    @Test
    void testAddUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        addUserCitySuccess(telegramId, city);
    }

    @Test
    void testAddUserCityAlreadyAdded() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        addUserCitySuccess(telegramId, city);
        addUserCityUserAlreadyAdded(telegramId, city);
    }

    @Test
    void testAddUnknownUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserCityUserNotFound(telegramId, city);
    }

    @Test
    void deleteUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        addUserCitySuccess(telegramId, city);
        deleteUserCitySuccess(telegramId, city);
    }

    @Test
    void deleteUserCityNotAdded() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        deleteUserCityNotAdded(telegramId, city);
    }

    @Test
    void deleteUnknownUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        deleteUserCityUserNotFound(telegramId, city);
    }

    @Test
    void testGetZeroUserCities() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        getUserCitiesSuccess(telegramId, new ArrayList<>());
    }

    @Test
    void testGetOneUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        addUserCitySuccess(telegramId, city);
        getUserCitiesSuccess(telegramId, List.of(city));
    }

    @Test
    void testGetSeveralUserCities() {
        final long telegramId = new TelegramId().getId();
        final int citiesCount = 10;
        final List<String> cities = new ArrayList<>(citiesCount);
        addUserSuccess(telegramId);
        for (int i = 0; i < citiesCount; i++) {
            final String city = new City().getCity();
            cities.add(city);
            addUserCitySuccess(telegramId, city);
        }
        getUserCitiesSuccess(telegramId, cities);
    }

    @Test
    void testGetUnknownUserCities() {
        final long telegramId = new TelegramId().getId();
        getUserCitiesUserNotFound(telegramId);
    }

    @Test
    void testGetAndDeleteUserCity() {
        final long telegramId = new TelegramId().getId();
        final String city = new City().getCity();
        addUserSuccess(telegramId);
        addUserCitySuccess(telegramId, city);
        getUserCitiesSuccess(telegramId, List.of(city));
        deleteUserCitySuccess(telegramId, city);
        getUserCitiesSuccess(telegramId, new ArrayList<>());
    }

    @Test
    void testAddUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        addUserTracksList(telegramId, tracksListUrl);
    }

    @Test
    void testAddUserTracksListAlreadyAdded() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        addUserTracksListSuccess(telegramId, tracksListUrl);
        addUserTracksListAlreadyAdded(telegramId, tracksListUrl);
    }

    @Test
    void testAddUnknownUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserTracksListUserNotFound(telegramId, tracksListUrl);
    }

    @Test
    void deleteUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        addUserTracksListSuccess(telegramId, tracksListUrl);
        deleteUserTracksListSuccess(telegramId, tracksListUrl);
    }

    @Test
    void deleteUserTracksListNotAdded() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        deleteUserTracksListNotAdded(telegramId, tracksListUrl);
    }

    @Test
    void deleteUnknownUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        deleteUserTracksListUserNotFound(telegramId, tracksListUrl);
    }

    @Test
    void testGetZeroUserTracksLists() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        getUserTracksListsSuccess(telegramId, new ArrayList<>());
    }

    @Test
    void testGetOneUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        addUserTracksListSuccess(telegramId, tracksListUrl);
        getUserTracksListsSuccess(telegramId, List.of(tracksListUrl));
    }

    @Test
    void testGetSeveralUserTracksLists() {
        final long telegramId = new TelegramId().getId();
        final int tracksListsCount = 10;
        final List<String> urls = new ArrayList<>(tracksListsCount);
        addUserSuccess(telegramId);
        for (int i = 0; i < tracksListsCount; i++) {
            final String url = new TracksList().getUrl();
            urls.add(url);
            addUserTracksListSuccess(telegramId, url);
        }
        getUserTracksListsSuccess(telegramId, urls);
    }

    @Test
    void testGetUnknownUserTracksLists() {
        final long telegramId = new TelegramId().getId();
        getUserTracksListsUserNotFound(telegramId);
    }

    @Test
    void testGetAndDeleteUserTracksList() {
        final long telegramId = new TelegramId().getId();
        final String tracksListUrl = new TracksList().getUrl();
        addUserSuccess(telegramId);
        addUserTracksList(telegramId, tracksListUrl);
        getUserTracksListsSuccess(telegramId, List.of(tracksListUrl));
        deleteUserTracksListSuccess(telegramId, tracksListUrl);
        getUserTracksListsSuccess(telegramId, new ArrayList<>());
    }

    @Test
    void testGetUserConcerts() {
        final long telegramId = new TelegramId().getId();
        addUserSuccess(telegramId);
        getUserConcertsSuccess(telegramId);
    }

    @Test
    void testGetUnknownUserConcerts() {
        final long telegramId = new TelegramId().getId();
        getUserConcertsUserNotFound(telegramId);
    }

    void addUserSuccess(long telegramId) {
        addUserWithCode(telegramId, ResponseStatusCode.SUCCESS);
    }

    void addUserAlreadyExists(long telegramId) {
        addUserWithCode(telegramId, ResponseStatusCode.USER_ALREADY_EXISTS);
    }

    void deleteUserSuccess(long telegramId) {
        deleteUserWithCode(telegramId, ResponseStatusCode.SUCCESS);
    }

    void deleteUserNotFound(long telegramId) {
        deleteUserWithCode(telegramId, ResponseStatusCode.USER_NOT_FOUND);
    }

    void addUserCitySuccess(long telegramId, String cityName) {
        addUserCityWithCode(telegramId, cityName, ResponseStatusCode.SUCCESS);
    }

    void addUserCityUserNotFound(long telegramId, String cityName) {
        addUserCityWithCode(telegramId, cityName, ResponseStatusCode.USER_NOT_FOUND);
    }

    void addUserCityUserAlreadyAdded(long telegramId, String cityName) {
        addUserCityWithCode(telegramId, cityName, ResponseStatusCode.CITY_ALREADY_ADDED);
    }

    void deleteUserCitySuccess(long telegramId, String cityName) {
        deleteUserCityWithCode(telegramId, cityName, ResponseStatusCode.SUCCESS);
    }

    void deleteUserCityUserNotFound(long telegramId, String cityName) {
        deleteUserCityWithCode(telegramId, cityName, ResponseStatusCode.USER_NOT_FOUND);
    }

    void deleteUserCityNotAdded(long telegramId, String cityName) {
        deleteUserCityWithCode(telegramId, cityName, ResponseStatusCode.CITY_NOT_ADDED);
    }

    void getUserCitiesSuccess(long telegramId, List<String> expectedCities) {
        final UserCitiesResponse response = getUserCitiesAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.SUCCESS);
        final List<String> realCities = response.getCities();
        final Set<String> expectedCitiesSet = new HashSet<>(expectedCities);
        final Set<String> realCitiesSet = new HashSet<>(realCities);
        assertEquals(expectedCitiesSet, realCitiesSet);

        // Check that response doesn't contain city duplicates
        assertEquals(realCitiesSet.size(), realCities.size());
    }

    void getUserCitiesUserNotFound(long telegramId) {
        final UserCitiesResponse response = getUserCitiesAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.USER_NOT_FOUND);
    }

    void addUserTracksListSuccess(long telegramId, String url) {
        addUserTracksListWithCode(telegramId, url, ResponseStatusCode.SUCCESS);
    }

    void addUserTracksListUserNotFound(long telegramId, String url) {
        addUserTracksListWithCode(telegramId, url, ResponseStatusCode.USER_NOT_FOUND);
    }

    void addUserTracksListAlreadyAdded(long telegramId, String url) {
        addUserTracksListWithCode(telegramId, url, ResponseStatusCode.TRACKS_LIST_ALREADY_ADDED);
    }

    void deleteUserTracksListSuccess(long telegramId, String url) {
        deleteUserTracksListWithCode(telegramId, url, ResponseStatusCode.SUCCESS);
    }

    void deleteUserTracksListUserNotFound(long telegramId, String url) {
        deleteUserTracksListWithCode(telegramId, url, ResponseStatusCode.USER_NOT_FOUND);
    }

    void deleteUserTracksListNotAdded(long telegramId, String url) {
        deleteUserTracksListWithCode(telegramId, url, ResponseStatusCode.TRACKS_LIST_NOT_ADDED);
    }

    void getUserTracksListsSuccess(long telegramId, List<String> expectedTracksLists) {
        final UserTracksListsResponse response = getUserTracksListsAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.SUCCESS);
        final List<String> realTracksLists = response.getTracksLists();
        final Set<String> expectedTracksListsSet = new HashSet<>(expectedTracksLists);
        final Set<String> realTracksListsSet = new HashSet<>(realTracksLists);
        assertEquals(expectedTracksListsSet, realTracksListsSet);

        // Check that response doesn't contain tracks-lists duplicates
        assertEquals(realTracksListsSet.size(), realTracksLists.size());
    }

    void getUserTracksListsUserNotFound(long telegramId) {
        final UserTracksListsResponse response = getUserTracksListsAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.USER_NOT_FOUND);
    }

    void getUserConcertsSuccess(long telegramId) {
        final UserConcertsResponse response = getUserConcertsAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.SUCCESS);
    }

    void getUserConcertsUserNotFound(long telegramId) {
        final UserConcertsResponse response = getUserConcertsAndValidate(telegramId);
        matchResponseStatus(response.getStatus(), ResponseStatusCode.USER_NOT_FOUND);
    }

    UserCitiesResponse getUserCitiesAndValidate(long telegramId) {
        final ResponseEntity<UserCitiesResponse> responseEntity = getUserCities(telegramId);
        final UserCitiesResponse response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        assertNotNull(response.getCities());
        return response;
    }

    UserTracksListsResponse getUserTracksListsAndValidate(long telegramId) {
        final ResponseEntity<UserTracksListsResponse> responseEntity = getUserTracksLists(telegramId);
        final UserTracksListsResponse response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        assertNotNull(response.getTracksLists());
        return response;
    }

    UserConcertsResponse getUserConcertsAndValidate(long telegramId) {
        final ResponseEntity<UserConcertsResponse> responseEntity = getUserConcerts(telegramId);
        final UserConcertsResponse response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        assertNotNull(response.getConcerts());
        return response;
    }

    void addUserWithCode(long telegramId, ResponseStatusCode code) {
        final Response response = validateResponseEntity(addUser(telegramId));
        matchResponseStatus(response.getStatus(), code);
    }

    void deleteUserWithCode(long telegramId, ResponseStatusCode code) {
        final Response response = validateResponseEntity(deleteUser(telegramId));
        matchResponseStatus(response.getStatus(), code);
    }

    void addUserCityWithCode(long telegramId, String city, ResponseStatusCode code) {
        final Response response = validateResponseEntity(addUserCity(telegramId, city));
        matchResponseStatus(response.getStatus(), code);
    }

    void deleteUserCityWithCode(long telegramId, String city, ResponseStatusCode code) {
        final Response response = validateResponseEntity(deleteUserCity(telegramId, city));
        matchResponseStatus(response.getStatus(), code);
    }

    void addUserTracksListWithCode(long telegramId, String url, ResponseStatusCode code) {
        final Response response = validateResponseEntity(addUserTracksList(telegramId, url));
        matchResponseStatus(response.getStatus(), code);
    }

    void deleteUserTracksListWithCode(long telegramId, String url, ResponseStatusCode code) {
        final Response response = validateResponseEntity(deleteUserTracksList(telegramId, url));
        matchResponseStatus(response.getStatus(), code);
    }

    Response validateResponseEntity(ResponseEntity<Response> responseEntity) {
        final Response response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        return response;
    }

    void matchResponseStatus(ResponseStatus status, ResponseStatusCode code) {
        assertEquals(code.ordinal(), status.getCode());
        assertEquals(code.name(), status.getMessage());
        assertEquals(code == ResponseStatusCode.SUCCESS, status.isSuccess());
    }

    ResponseEntity<Response> addUser(long telegramId) {
        return post(
                getUserUrl(telegramId),
                Response.class
        );
    }

    ResponseEntity<Response> deleteUser(long telegramId) {
        return delete(
                getUserUrl(telegramId),
                Response.class
        );
    }

    ResponseEntity<Response> addUserCity(long telegramId, String city) {
        return post(
                getUserCityUrl(telegramId, city),
                Response.class
        );
    }

    ResponseEntity<Response> deleteUserCity(long telegramId, String city) {
        return delete(
                getUserCityUrl(telegramId, city),
                Response.class
        );
    }

    ResponseEntity<UserCitiesResponse> getUserCities(long telegramId) {
        return get(
                getUserCitiesUrl(telegramId),
                UserCitiesResponse.class
        );
    }

    ResponseEntity<Response> addUserTracksList(long telegramId, String tracksListUrl) {
        return post(
                getUserTracksListUrl(telegramId, tracksListUrl),
                Response.class
        );
    }

    ResponseEntity<Response> deleteUserTracksList(long telegramId, String tracksListUrl) {
        return delete(
                getUserTracksListUrl(telegramId, tracksListUrl),
                Response.class
        );
    }

    ResponseEntity<UserTracksListsResponse> getUserTracksLists(long telegramId) {
        return get(
                getUserTracksListsUrl(telegramId),
                UserTracksListsResponse.class
        );
    }

    ResponseEntity<UserConcertsResponse> getUserConcerts(long telegramId) {
        return get(
                getUserConcertsUrl(telegramId),
                UserConcertsResponse.class
        );
    }

    <T> ResponseEntity<T> post(String url, Class<T> clazz) {
        return restTemplate.postForEntity(
                url,
                null,
                clazz
        );
    }

    <T> ResponseEntity<T> delete(String url, Class<T> clazz) {
        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                clazz
        );
    }

    <T> ResponseEntity<T> get(String url, Class<T> clazz) {
        return restTemplate.getForEntity(
                url,
                clazz
        );
    }

    private String getUserUrl(long telegramId) {
        return baseUrl + String.format("/users/%s", telegramId);
    }

    private String getUserCitiesUrl(long telegramId) {
        return getUserUrl(telegramId) + "/cities";
    }

    private String getUserCityUrl(long telegramId, String cityName) {
        return getUserCitiesUrl(telegramId) + String.format("?city=%s", cityName);
    }

    private String getUserTracksListsUrl(long telegramId) {
        return getUserUrl(telegramId) + "/tracks-lists";
    }

    private String getUserTracksListUrl(long telegramId, String url) {
        return getUserTracksListsUrl(telegramId) + String.format("?url=%s", url);
    }

    private String getUserConcertsUrl(long telegramId) {
        return getUserUrl(telegramId) + "/concerts";
    }
}
