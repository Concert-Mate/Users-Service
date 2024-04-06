create table users
(
    id          serial,
    telegram_id text                                not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    constraint users_pk
        primary key (id),
    constraint users_pk_2
        unique (telegram_id)
);

create table users_tracklists
(
    user_id integer not null,
    url     text    not null,
    constraint users_tracklists_pk
        primary key (user_id, url),
    constraint users_tracklists_users_id_fk
        foreign key (user_id) references users
);

create table users_cities
(
    user_id   integer not null,
    city_name text    not null,
    constraint users_cities_pk
        primary key (city_name, user_id),
    constraint users_cities_users_id_fk
        foreign key (user_id) references users
);

create table shown_concerts
(
    user_id     integer not null,
    concert_url text    not null,
    constraint shown_concerts_pk
        primary key (user_id, concert_url),
    constraint shown_concerts_users_id_fk
        foreign key (user_id) references users
);


