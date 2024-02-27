-- @formatter:off
drop table if exists my_entity;

create table my_entity (
    id bigint primary key ,
    path varchar(255)
);