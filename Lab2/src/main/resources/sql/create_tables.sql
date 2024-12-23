CREATE TABLE functions
(
    id            serial primary key,
    function_type varchar(255),
    function_hash varchar(255)
);

create table points
(
    id          serial primary key,
    function_id integer,
    y           double precision,
    x           double precision,
    FOREIGN KEY (function_id) references functions (id) ON DELETE CASCADE
);

CREATE TABLE log
(
    id        SERIAL PRIMARY KEY,
    timestamp TIMESTAMP    NOT NULL,
    message   VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL unique,
    password varchar(256) NOT NULL
)