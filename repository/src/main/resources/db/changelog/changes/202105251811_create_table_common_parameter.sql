CREATE TABLE IF NOT EXISTS common_parameter (
    id SERIAL NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL,
    value varchar(255) NOT NULL
);
