CREATE TABLE functions (
    id serial primary key,
    modification varchar(32) NOT NULL ,
    hash varchar(256) NOT NULL unique,
    arr_x real[] NOT NULL,
    arr_y real[] NOT NULL
);
