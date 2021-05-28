CREATE TABLE IF NOT EXISTS avaliation (
    id SERIAL NOT NULL PRIMARY KEY,
    id_worker SERIAL NOT NULL references worker (id) ON DELETE CASCADE,
    id_customer SERIAL NOT NULL references customer (id) ON DELETE CASCADE,
    description VARCHAR(255) NOT NULL,
    rating INTEGER NOT NULL,
    created_at timestamp without time zone not null
);
