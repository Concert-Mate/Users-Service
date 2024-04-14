package ru.nsu.concertsmate.backend.api;

public enum ResponseStatusCode {
    SUCCESS,
    INTERNAL_ERROR,
    USER_ALREADY_EXISTS,
    USER_NOT_FOUND,
    CITY_ALREADY_ADDED,
    CITY_NOT_ADDED,
    INVALID_CITY,
    FUZZY_CITY,
    TRACKS_LIST_ALREADY_ADDED,
    INVALID_TRACKS_LIST,
}
