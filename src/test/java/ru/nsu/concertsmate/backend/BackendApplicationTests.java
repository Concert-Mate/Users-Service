package ru.nsu.concertsmate.backend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.nsu.concertsmate.backend.api.Response;
import ru.nsu.concertsmate.backend.api.ResponseStatus;
import ru.nsu.concertsmate.backend.api.ResponseStatusCode;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.yaml")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackendApplicationTests {
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
        addUserSuccess(1);
    }

    @Test
    void testAddUserTwice() {
        final long telegramId = 2;
        addUserSuccess(telegramId);
        addUserAlreadyExists(telegramId);
    }

    @Test
    void testAddDeleteUser() {
        final long telegramId = 3;
        addUserSuccess(telegramId);
        deleteUserSuccess(telegramId);
    }

    @Test
    void testDeleteUnknownUser() {
        final long telegramId = 4;
        deleteUserNotFound(telegramId);
    }

    @Test
    void testAddDeleteTwiceUser() {
        final long telegramId = 5;
        addUserSuccess(telegramId);
        deleteUserSuccess(telegramId);
        deleteUserNotFound(telegramId);
    }

    void addUserSuccess(long telegramId) {
        final ResponseEntity<Response> responseEntity = addUser(telegramId);
        final Response response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        matchResponseStatus(status, ResponseStatusCode.SUCCESS);
    }

    void addUserAlreadyExists(long telegramId) {
        final ResponseEntity<Response> responseEntity = addUser(telegramId);
        final Response response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        matchResponseStatus(status, ResponseStatusCode.USER_ALREADY_EXISTS);
    }

    void deleteUserSuccess(long telegramId) {
        final ResponseEntity<Response> responseEntity = deleteUser(telegramId);
        final Response response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        matchResponseStatus(status, ResponseStatusCode.SUCCESS);
    }

    void deleteUserNotFound(long telegramId) {
        final ResponseEntity<Response> responseEntity = deleteUser(telegramId);
        final Response response = responseEntity.getBody();
        assertNotNull(response);
        final ResponseStatus status = response.getStatus();
        assertNotNull(status);
        matchResponseStatus(status, ResponseStatusCode.USER_NOT_FOUND);
    }

    void matchResponseStatus(ResponseStatus status, ResponseStatusCode code) {
        assertEquals(code.ordinal(), status.getCode());
        assertEquals(code.name(), status.getMessage());
        assertEquals(code == ResponseStatusCode.SUCCESS, status.isSuccess());
    }

    ResponseEntity<Response> addUser(long telegramId) {
        return restTemplate.postForEntity(
                baseUrl + "/users/{telegramId}",
                null,
                Response.class,
                telegramId
        );
    }

    ResponseEntity<Response> deleteUser(long telegramId) {
        return restTemplate.exchange(
                baseUrl + "/users/{telegramId}",
                HttpMethod.DELETE,
                null,
                Response.class,
                telegramId
        );
    }
}
