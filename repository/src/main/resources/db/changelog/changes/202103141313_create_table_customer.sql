CREATE TABLE IF NOT EXISTS customer (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    phone_number varchar(15) NOT NULL,
    email varchar(100) NOT NULL,
    cep char(8) NOT NULL,
    city varchar(100) NOT NULL,
    latitude varchar(100) NOT NULL,
    longitude  varchar(100) NOT NULL,
    formatted_address varchar(200),
    created_at timestamp without time zone not null
);