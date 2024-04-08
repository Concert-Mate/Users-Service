CREATE SCHEMA IF NOT EXISTS public;

create table users
(
    id          serial
        constraint users_pk
            primary key,
    telegram_id bigint                              not null
        constraint users_pk_2
            unique,
    created_at  timestamp default CURRENT_TIMESTAMP not null
);

alter table users
    owner to admin;

create table users_tracklists
(
    user_id integer not null
        constraint users_tracklists_users_id_fk
            references users,
    url     text    not null,
    constraint users_tracklists_pk
        primary key (user_id, url)
);

alter table users_tracklists
    owner to admin;

create table users_cities
(
    user_id   integer not null
        constraint users_cities_users_id_fk
            references users,
    city_name text    not null,
    constraint users_cities_pk
        primary key (city_name, user_id)
);

alter table users_cities
    owner to admin;

create table shown_concerts
(
    user_id     integer not null
        constraint shown_concerts_users_id_fk
            references users,
    concert_url text    not null,
    constraint shown_concerts_pk
        primary key (user_id, concert_url)
);

alter table shown_concerts
    owner to admin;
