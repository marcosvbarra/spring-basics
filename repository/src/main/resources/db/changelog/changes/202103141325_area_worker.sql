CREATE TABLE IF NOT EXISTS area_worker (
    id SERIAL NOT NULL PRIMARY KEY,
    id_worker SERIAL NOT NULL references worker (id) ON DELETE CASCADE,
    id_area SERIAL NOT NULL references actuation_area (id) ON DELETE CASCADE,
    is_active boolean NOT NULL
);
