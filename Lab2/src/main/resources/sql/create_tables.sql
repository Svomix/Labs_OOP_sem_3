CREATE TABLE functions (
    id serial primary key,
    arr_id integer NOT NULL,
    modification mod_type NOT NULL ,
    hash bigint NOT NULL,


);

create type mod_type as enum ('usual','strict', 'unmodifiable', 'strict_unmodifiable');