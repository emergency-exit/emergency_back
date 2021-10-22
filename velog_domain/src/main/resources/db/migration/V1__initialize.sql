-- auto-generated definition
create table board
(
    id                  bigint auto_increment
        primary key,
    created_date        datetime(6) null,
    modified_date       datetime(6) null,
    board_thumbnail_url varchar(255) null,
    content             text null,
    is_private          bit         not null,
    like_count          int         not null,
    member_id           bigint null,
    series_id           bigint null,
    delete_date   datetime(6) null,
    title               varchar(50) not null
);

-- auto-generated definition
create table board_hash_tag
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    hash_tag      varchar(50) not null,
    member_id     bigint      not null,
    board_id      bigint      not null,
    delete_date   datetime(6) null,
    constraint board_hash_tag_board_id_fk
        foreign key (board_id) references board (id)
);

-- auto-generated definition
create table board_like
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    member_id     bigint null,
    board_id      bigint not null,
    delete_date   datetime(6) null,
    foreign key (board_id) references board (id)
);

-- auto-generated definition
create table member
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    description   varchar(255) null,
    email         varchar(255) not null,
    member_image  varchar(255) null,
    name          varchar(50)  not null,
    password      varchar(255) null,
    provider      varchar(255) null,
    velog_name    varchar(255) null,
    delete_date   datetime(6) null,
    unique (email)
);

-- auto-generated definition
create table series
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    modified_date datetime(6) null,
    series_name   varchar(255) not null,
    member_id     bigint       not null,
    delete_date   datetime(6) null,
    constraint series_member_id_fk
        foreign key (member_id) references member (id)
);


