
DROP SEQUENCE IF EXISTS aircraft_seq;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS system;

CREATE SEQUENCE IF NOT EXISTS aircraft_seq;

CREATE TABLE IF NOT EXISTS aircraft (
    id              bigint NOT NULL DEFAULT nextval('aircraft_seq') CONSTRAINT aircraft_pk PRIMARY KEY,
    plane_name      varchar(60),
    plane_size      varchar(60),
    type_id         bigint,     -- PK for customer
    created_on      timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS system (
    id       bigint,
    switch   bigint
);

INSERT INTO system (id, switch) VALUES (1, 0);
