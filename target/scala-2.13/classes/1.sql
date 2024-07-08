DROP TABLE IF EXISTS user;

create table user (
    id           text not null,
    name         text not null,
    department   text not null,
    address      text not null
);