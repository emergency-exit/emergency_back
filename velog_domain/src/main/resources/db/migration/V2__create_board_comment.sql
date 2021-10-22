create table board_comment
(
    id            bigint auto_increment
        primary key,
    created_date  datetime(6) null,
    delete_date   datetime(6) null,
    modified_date datetime(6) null,
    board_id      bigint       not null,
    content       varchar(255) not null,
    member_id     bigint       not null
);