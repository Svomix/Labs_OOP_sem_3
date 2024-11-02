CREATE TABLE functions (
    id serial primary key,
    arr_id integer NOT NULL,
    modification mod_type NOT NULL ,
    hash bigint NOT NULL,
    FOREIGN KEY (arr_id) REFERENCES arrays (id) ON DELETE CASCADE

);
create type mod_type as enum ('usual','strict', 'unmodifiable', 'strict_unmodifiable');

CREATE TABLE arrays
(
    id serial primary key ,
    arr_x real[] NOT NULL ,
    arr_y real[] NOT NULL
);