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
CREATE TABLE tbl_user (
    id BIGINT NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR (320) UNIQUE NOT NULL);

CREATE TABLE tbl_product (
    id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    price MONEY NOT NULL);

CREATE TABLE tbl_address (
    id BIGINT NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL);

CREATE TABLE tbl_order (
    id BIGINT NOT NULL UNIQUE,
    fk_user BIGINT NOT NULL REFERENCES tbl_user(id),
    fk_invoice_address BIGINT NOT NULL REFERENCES tbl_address(id),
    fk_delivery_address BIGINT NOT NULL REFERENCES tbl_address(id),
    fk_product BIGINT NOT NULL REFERENCES tbl_product(id));
CREATE INDEX idx_id_user ON public.tbl_order (fk_user);
CREATE INDEX idx_id_address ON tbl_order (fk_invoice_address, fk_delivery_address);
CREATE INDEX idx_id_product ON tbl_order (fk_product);
