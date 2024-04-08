package ru.nsu.concertsmate.backend.api.users;

public enum UsersApiResponseStatus {
    SUCCESS,
    USER_ALREADY_EXISTS,
    TRACKS_LIST_ALREADY_ADDED,
    INVALID_TRACKS_LIST,
    INVALID_CITY,
    USER_NOT_FOUND,
    CITY_ALREADY_ADDED,
}
