CREATE TABLE IF NOT EXISTS actuation_area (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    is_active boolean NOT NULL,
    created_at timestamp without time zone not null
);