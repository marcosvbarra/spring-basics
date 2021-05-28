CREATE TABLE IF NOT EXISTS job_request (
    id SERIAL NOT NULL PRIMARY KEY,
    id_customer SERIAL NOT NULL references customer (id) ON DELETE CASCADE,
    id_actuation_area SERIAL NOT NULL references actuation_area (id) ON DELETE CASCADE,
    interested_workers integer not null default 0,
    cep char(8),
    city varchar(100) NOT NULL,
    latitude varchar(100) NOT NULL,
    longitude  varchar(100) NOT NULL,
    formatted_address varchar(200),
    job_information VARCHAR(10000) NOT NULL,
    priority VARCHAR(50) NOT NULL,
    start_date timestamp without time zone,
    status VARCHAR(50) NOT NULL,
    created_at timestamp without time zone not null,
    retry_amount integer

);
