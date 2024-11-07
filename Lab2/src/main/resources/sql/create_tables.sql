CREATE TABLE functions (
    id serial primary key,
    function_type varchar(255)
);

create table points(
    id serial primary key,
    function_id integer references functions(id) not null,
    y double precision,
    x double precision
);