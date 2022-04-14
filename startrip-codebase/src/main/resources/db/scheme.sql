drop table if exists event_comment cascade;

drop table if exists event_review cascade;

drop table if exists event cascade;

drop table if exists event_trip cascade;

drop table if exists notice_comment cascade;

drop table if exists notice cascade;

drop table if exists category cascade;

drop table if exists place cascade;

drop table if exists place_info cascade;

drop table if exists place_review cascade;

drop table if exists place_trip cascade;

drop table if exists "user" cascade;

drop table if exists state cascade;

create table "user"
(
    user_id       bigserial
        primary key,
    authorities   varchar(255),
    email         varchar(255)
        constraint uk_ob8kqyqqgmefl0aco34akdtpe
            unique,
    name          varchar(255) not null,
    nickname      varchar(255),
    password      varchar(255),
    picture_url   varchar(255),
    receive_email boolean
);

create table category
(
    category_id        UUID    not null
        primary key,
    category_name      varchar(255)
        constraint uk_lroeo5fvfdeg4hpicn4lw7x9b
            unique,
    depth              integer not null,
    category_parent_id UUID
        constraint fkgbowg38afm73793kwnokn0203
            references category
);

create table event_comment
(
    comment_id    integer not null
        primary key,
    comment_text  varchar(255),
    comment_up_id integer,
    created_time  bytea,
    event_id      uuid,
    is_updated    boolean,
    user_id       bigint
);

create table notice
(
    notice_id    bigserial
        primary key,
    attachment   varchar(255),
    created_time timestamp,
    like_count   integer,
    text         varchar(255),
    title        varchar(255),
    updated_time timestamp,
    view_count   integer,
    category_id  UUID
        constraint fk4sj4tjb6t1o2l62o7tm44gtk5
            references category,
    user_id      bigint
        constraint fkt50p3k4qbieq9c9l1d0dfx4or
            references "user"
);

create table notice_comment
(
    comment_id    bigserial
        primary key,
    comment_text  varchar(255),
    comment_up_id integer,
    created_time  timestamp,
    is_updated    boolean,
    notice_id     bigint
        constraint fkax7qcww5twiq84y8dvvpaketx
            references notice,
    user_id       bigint
        constraint fkf7i8ty091pascnx20nl5vj7fe
            references "user"
);

create table place
(
    place_id          UUID
        primary key,
    address           varchar(255),
    category_id       UUID,
    latitude          double precision,
    longitude         double precision,
    phone_number      varchar(255),
    place_description varchar(255),
    place_name        varchar(255),
    place_photo       varchar(255)
);

create table event
(
    event_id         bigserial
        primary key,
    contact          varchar(255),
    created_date     timestamp,
    description      varchar(255),
    end_date         timestamp,
    event_cycle      integer,
    event_cycle_unit varchar(255),
    event_owner      varchar(255),
    event_picture    varchar(255),
    event_title      varchar(255),
    spend_time       time,
    spend_time_unit  varchar(255),
    start_date       timestamp,
    updated_date     timestamp,
    category_id      UUID
        constraint fk751x8cp2x1h1fay38u2p5gpkr
            references category,
    place_id         UUID
        constraint fkpuvix4lexrakgdlt8si1tbtxv
            references place
);

create table event_review
(
    review_id          bigserial
        primary key,
    created_date       timestamp,
    creator_id         bigint,
    event_review_title varchar(255),
    review_picture     varchar(255),
    review_rate        double precision,
    text               varchar(255),
    updated_date       timestamp,
    event_id           bigint
        constraint fk2oftuxrumnaofyvoqgc1kdqb2
            references event
);

create table place_info
(
    placeinfo_id    bigserial
        primary key,
    is_entrance_fee boolean,
    is_parking_lot  boolean,
    is_rest_room    boolean,
    place_id        UUID  not null
);

create table place_review
(
    review_id    uuid not null
        primary key,
    created_date timestamp,
    creator_id   bigint,
    place_id     uuid,
    review_photo varchar(255),
    review_star  double precision,
    review_text  varchar(255),
    updated_date timestamp
);

create table state
(
    state_id  uuid not null
        primary key,
    state_num integer
);

create table event_trip
(
    trip_id        uuid not null
        primary key,
    end_time       timestamp,
    event_id       uuid,
    start_time     timestamp,
    title          varchar(255),
    transportation varchar(255),
    user_partner   varchar(255),
    state_id       uuid
        constraint fk8hq4oeqsc4hhyd6mxw8j1f1iu
            references state,
    user_id        bigint
        constraint fkspu9ip08xrts9xtg7p3mh4rrm
            references "user"
);

create table place_trip
(
    trip_id        uuid not null
        primary key,
    end_time       timestamp,
    place_id       uuid,
    start_time     timestamp,
    title          varchar(255),
    transportation varchar(255),
    user_partner   varchar(255),
    state_id       uuid
        constraint fkliv25fbd5gv7kt2o350jnyh3i
            references state,
    user_id        bigint
        constraint fkt3kd0m8byio984ki6c35p6aw0
            references "user"
);
