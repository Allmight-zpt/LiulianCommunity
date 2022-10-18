create table comment
(
    id           bigint primary key auto_increment,
    parent_id    bigint not null,
    comment_type int    not null,
    commentator  int    not null,
    gmt_create   bigint not null,
    gmt_modified bigint not null,
    like_count   bigint default 0
);

