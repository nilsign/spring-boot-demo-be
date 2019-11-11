-- General setup.
SET client_encoding = 'UTF8';

-- Configures a global id sequence used by all primary keys of all tables.
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Creates all initialize tables.
CREATE TABLE Customer (
    id BIGINT NOT NULL,
    first_name VARCHAR(200) NOT NULL,
    last_name VARCHAR(200) NOT NULL,
    email VARCHAR (320) UNIQUE NOT NULL);
