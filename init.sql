CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE public.users
(
    id          SERIAL,
    telegram_id TEXT                                NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_pk_2 UNIQUE (telegram_id)
);

CREATE TABLE public.users_tracklists
(
    user_id INTEGER NOT NULL,
    url     TEXT    NOT NULL,
    CONSTRAINT users_tracklists_pk PRIMARY KEY (user_id, url),
    CONSTRAINT users_tracklists_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users
);

CREATE TABLE public.users_cities
(
    user_id   INTEGER NOT NULL,
    city_name TEXT    NOT NULL,
    CONSTRAINT users_cities_pk PRIMARY KEY (city_name, user_id),
    CONSTRAINT users_cities_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users
);

CREATE TABLE public.shown_concerts
(
    user_id     INTEGER NOT NULL,
    concert_url TEXT    NOT NULL,
    CONSTRAINT shown_concerts_pk PRIMARY KEY (user_id, concert_url),
    CONSTRAINT shown_concerts_users_id_fk FOREIGN KEY (user_id) REFERENCES public.users
);

