CREATE TABLE  IF NOT EXISTS worker (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(255) NOT NULL,
    phone_number varchar(15) NOT NULL,
    email varchar(100) NOT NULL,
    cep char(8),
    city varchar(100) NOT NULL,
    plan varchar(50) NOT NULL,
    latitude varchar(100) NOT NULL,
    longitude  varchar(100) NOT NULL,
    formatted_address varchar(200),
    cpf char(11) NOT NULL,
    description VARCHAR(255) NOT NULL,
    rating numeric(6,2),
    is_active boolean NOT NULL,
    remaining_contacts integer NOT NULL default 0,
    revealed_contacts integer NOT NULL default 0,
    renewal_date timestamp without time zone not null,
    created_at timestamp without time zone not null,
    document_photo bytea

);