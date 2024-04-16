package ru.nsu.concerts_mate.users_service.api.users;

public enum UsersApiResponseStatusCode {
    SUCCESS,
    INTERNAL_ERROR,
    USER_ALREADY_EXISTS,
    USER_NOT_FOUND,
    CITY_ALREADY_ADDED,
    CITY_NOT_ADDED,
    INVALID_CITY,
    FUZZY_CITY,
    TRACKS_LIST_ALREADY_ADDED,
    TRACKS_LIST_NOT_ADDED,
    INVALID_TRACKS_LIST,
}
