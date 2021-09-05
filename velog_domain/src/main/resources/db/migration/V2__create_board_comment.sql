create table board_comment
(
    id            bigint auto_increment
        primary key,
    created_date  timestamp,
    delete_date   timestamp,
    modified_date timestamp,
    board_id      bigint       not null,
    content       varchar(255) not null,
    member_id     bigint       not null
);