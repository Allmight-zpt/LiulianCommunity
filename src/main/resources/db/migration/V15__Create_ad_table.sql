create table AD
(
    ID           bigint auto_increment primary key not null ,
    title        varchar(256) default null,
    url          varchar(512) default null,
    image        varchar(256) default null,
    TOKEN        varchar(36),
    GMT_START   BIGINT,
    GMT_END BIGINT,
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    status       int,
    position     varchar(10) not null
);