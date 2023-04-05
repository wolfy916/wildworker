drop table if exists wild_worker.title_awarded;

drop table if exists wild_worker.transaction_log;

drop table if exists wild_worker.dominator_log;

drop table if exists wild_worker.mini_game_log;

drop table if exists wild_worker.mini_game;

drop table if exists wild_worker.title;

drop table if exists wild_worker.user;

drop table if exists wild_worker.station;

create table wild_worker.user
(
    id                        bigint auto_increment
        primary key,
    created_at                datetime     not null,
    updated_at                datetime     not null,
    balance                   bigint unsigned not null,
    character_id              int          not null,
    deleted                   bit          not null,
    deleted_at                datetime null,
    email                     varchar(255) not null,
    name                      varchar(255) not null,
    number_of_collected_paper int          not null,
    role                      int          not null,
    title_show_type           int          not null,
    wallet                    varchar(255) not null,
    wallet_password           varchar(255) not null,
    title_id                  bigint       not null,
    constraint UK_gj2fy3dcix7ph7k8684gka40c
        unique (name),
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email),
    constraint UK_paytqh8mgo2xqajt50xl8bhb1
        unique (wallet),
    constraint UK_pdohmdeyedqy30eu4dff58pf0
        unique (wallet_password)
);


create table wild_worker.title
(
    id         bigint auto_increment
        primary key,
    created_at datetime     not null,
    updated_at datetime     not null,
    condition1 int          not null,
    condition2 int null,
    condition3 int null,
    condition4 int null,
    condition5 int null,
    name       varchar(255) not null,
    constraint UK_3j9cwwapic6mk5najgy5t2smu
        unique (name)
) charset = utf8mb3;

create table wild_worker.title_awarded
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    title_id   bigint   not null,
    user_id    bigint   not null,
    constraint UK_title_awarded_user_id_title_id
        unique (user_id, title_id),
    constraint FK9kah5e1gv7l4ffe46xsiut18o
        foreign key (title_id) references wild_worker.title (id),
    constraint FKd88b3swyfjjc33mremekbfdsj
        foreign key (user_id) references wild_worker.user (id)
);



create table wild_worker.station
(
    id              bigint auto_increment
        primary key,
    created_at      datetime     not null,
    updated_at      datetime     not null,
    address         varchar(255),
    balance         bigint unsigned not null,
    commission      bigint unsigned not null,
    location_lat    double       not null,
    location_lon    double       not null,
    name            varchar(255) not null,
    prev_commission bigint unsigned not null
) charset = utf8mb3;

create table wild_worker.transaction_log
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null,
    applied_at datetime null,
    type       int      not null,
    value      bigint   not null,
    station_id bigint   not null,
    user_id    bigint   not null,
    constraint FK93964qs1at0why96bpr0uku70
        foreign key (station_id) references wild_worker.station (id),
    constraint FKlqgqoq51khx3lef6wyi5bf3op
        foreign key (user_id) references wild_worker.user (id)
);
create table wild_worker.dominator_log
(
    id                  bigint auto_increment
        primary key,
    created_at          datetime     not null,
    updated_at          datetime     not null,
    dominate_start_time varchar(255) not null,
    station_id          bigint       not null,
    user_id             bigint       not null,
    constraint UK_dominator_log_station_id_dominate_start_time
        unique (station_id, dominate_start_time),
    constraint FKntq4033w899kot4b2k5v7pnbj
        foreign key (user_id) references wild_worker.user (id),
    constraint FKsr9j1ey56wx3ep8bx9qjia60r
        foreign key (station_id) references wild_worker.station (id)
);
create table wild_worker.mini_game
(
    id         bigint auto_increment
        primary key,
    created_at datetime     not null,
    updated_at datetime     not null,
    code       varchar(255) not null,
    name       varchar(255) not null,
    constraint UK_8nuavq4jnxumo024msnd9gbqb
        unique (name),
    constraint UK_hj7ppwlsi8sqntfuklh92bdri
        unique (code)
);

create table wild_worker.mini_game_log
(
    id          bigint auto_increment
        primary key,
    created_at  datetime not null,
    updated_at  datetime not null,
    result_code int      not null,
    run_code    int      not null,
    game_id     bigint null,
    user1_id    bigint   not null,
    user2_id    bigint   not null,
    constraint FK4grnnlflv3lg3txbeyy1fqjr8
        foreign key (user2_id) references wild_worker.user (id),
    constraint FK4th16idsdjkmi5g9q09wyb4wv
        foreign key (game_id) references wild_worker.mini_game (id),
    constraint FK5mqfrlrbexr4y3bsrhug91qrp
        foreign key (user1_id) references wild_worker.user (id)
);

