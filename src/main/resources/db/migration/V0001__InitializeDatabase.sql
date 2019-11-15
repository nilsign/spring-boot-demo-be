-- General setup.
SET client_encoding = 'UTF8';

-- Configures a global id sequence used by all primary keys of all tables.
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Creates all initialize tables an the according indices.
CREATE TABLE IF NOT EXISTS tbl_user (
    id BIGINT CONSTRAINT cstr_user_primary_key PRIMARY KEY,
    first_name VARCHAR(1024) NOT NULL,
    last_name VARCHAR(1024) NOT NULL,
    email VARCHAR(320) NOT NULL CONSTRAINT cstr_user_unique_email UNIQUE
);

CREATE TABLE IF NOT EXISTS tbl_product (
    id BIGINT CONSTRAINT cstr_product_primary_key PRIMARY KEY,
    name VARCHAR(1024) NOT NULL,
    price NUMERIC NOT NULL CONSTRAINT cstr_product_positive_price CHECK (price > 0)
);

CREATE TABLE IF NOT EXISTS tbl_address (
    id BIGINT CONSTRAINT cstr_address_primary_key PRIMARY KEY,
    address VARCHAR(1024) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_order (
    id BIGINT CONSTRAINT cstr_order_primary_key PRIMARY KEY,
    user_id BIGINT NOT NULL,
    invoice_address_id BIGINT NOT NULL,
    delivery_address_id BIGINT NOT NULL,
    CONSTRAINT cstr_user_foreign_key FOREIGN KEY(user_id)
        REFERENCES tbl_user(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_invoice_address_foreign_key FOREIGN KEY(invoice_address_id)
        REFERENCES tbl_address(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_delivery_address_foreign_key FOREIGN KEY(delivery_address_id)
        REFERENCES tbl_address(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_user_id ON tbl_order(user_id);
CREATE INDEX idx_address_id ON tbl_order(invoice_address_id, delivery_address_id);

CREATE TABLE IF NOT EXISTS tbl_order_tbl_product (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT cstr_order_foreign_key FOREIGN KEY(order_id)
        REFERENCES tbl_order(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_product_foreign_key FOREIGN KEY(product_id)
        REFERENCES tbl_product(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_order_id ON tbl_order_tbl_product(order_id);
CREATE INDEX idx_product_id ON tbl_order_tbl_product(product_id);
