-- Global setup.
SET client_encoding = 'UTF8';

-- Configures a global id sequence shared by all generated primary keys.
CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Initializes enums.
-- CREATE TYPE public.ROLE AS ENUM ('GLOBALADMIN', 'ADMIN', 'SELLER', 'SUPPORT', 'BUYER');

-- Creates all tables and initializes tables the required indices.
CREATE TABLE IF NOT EXISTS public.tbl_role (
    id BIGINT CONSTRAINT cstr_role_primary_key PRIMARY KEY,
    role_type VARCHAR(64) CONSTRAINT cstr_role_unique_role_type UNIQUE,
    role_name VARCHAR(1024) NOT NULL CONSTRAINT cstr_role_unique_role_name UNIQUE
);

CREATE TABLE IF NOT EXISTS tbl_address (
    id BIGINT CONSTRAINT cstr_address_primary_key PRIMARY KEY,
    address VARCHAR(1024) NOT NULL,
    city VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_customer (
    id BIGINT CONSTRAINT cstr_customer_primary_key PRIMARY KEY,
    postal_address_id BIGINT,
    terms_and_conditions_accepted BOOLEAN NOT NULL DEFAULT false,
    CONSTRAINT cstr_customer_postal_address_foreign_key FOREIGN KEY(postal_address_id)
        REFERENCES tbl_address(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_customer_postal_address_id ON tbl_customer(postal_address_id);

CREATE TABLE IF NOT EXISTS tbl_user (
    id BIGINT CONSTRAINT cstr_user_primary_key PRIMARY KEY,
    customer_id BIGINT,
    first_name VARCHAR(1024) NOT NULL,
    last_name VARCHAR(1024) NOT NULL,
    email VARCHAR(320) NOT NULL CONSTRAINT cstr_user_unique_email UNIQUE,
    CONSTRAINT cstr_user_customer_foreign_key FOREIGN KEY(customer_id)
        REFERENCES tbl_customer(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_user_customer_id ON tbl_user(customer_id);

CREATE TABLE IF NOT EXISTS tbl_product (
    id BIGINT CONSTRAINT cstr_product_primary_key PRIMARY KEY,
    product_name VARCHAR(1024) NOT NULL,
    price NUMERIC NOT NULL
        CONSTRAINT cstr_product_positive_price CHECK (price > 0)
);

CREATE TABLE IF NOT EXISTS tbl_rating (
    id BIGINT CONSTRAINT cstr_rating_primary_key PRIMARY KEY,
    user_id BIGINT NOT NULL,
    score REAL CONSTRAINT cstr_rating_score_within_range
        CHECK (score = null OR score >= 0 AND score <= 5),
    description VARCHAR(2048),
    CONSTRAINT cstr_rating_user_id_foreign_key FOREIGN KEY(user_id)
        REFERENCES tbl_user(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_rating_user_id ON tbl_rating(user_id);

CREATE TABLE IF NOT EXISTS tbl_order (
    id BIGINT CONSTRAINT cstr_order_primary_key PRIMARY KEY,
    user_id BIGINT NOT NULL,
    invoice_address_id BIGINT NOT NULL,
    CONSTRAINT cstr_order_user_foreign_key FOREIGN KEY(user_id)
        REFERENCES tbl_user(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_order_invoice_address_foreign_key FOREIGN KEY(invoice_address_id)
        REFERENCES tbl_address(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_order_user_id ON tbl_order(user_id);
CREATE INDEX idx_order_invoice_address_id ON tbl_order(invoice_address_id);

CREATE TABLE IF NOT EXISTS tbl_delivery (
    id BIGINT CONSTRAINT cstr_delivery_primary_key PRIMARY KEY,
    order_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    CONSTRAINT cstr_delivery_order_foreign_key FOREIGN KEY(order_id)
        REFERENCES tbl_order(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_delivery_address_foreign_key FOREIGN KEY(address_id)
        REFERENCES tbl_address(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_delivery_order_id ON tbl_delivery(order_id);
CREATE INDEX idx_delivery_address_id ON tbl_delivery(address_id);

-- Creates relational tables.
CREATE TABLE IF NOT EXISTS tbl_order_tbl_product (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT cstr_order_product_primary_key PRIMARY KEY (order_id, product_id),
    CONSTRAINT cstr_order_product_order_foreign_key FOREIGN KEY(order_id)
        REFERENCES tbl_order(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_order_product_product_foreign_key FOREIGN KEY(product_id)
        REFERENCES tbl_product(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_order_product_order_id ON tbl_order_tbl_product(order_id);
CREATE INDEX idx_order_product_product_id ON tbl_order_tbl_product(product_id);

CREATE TABLE IF NOT EXISTS tbl_user_tbl_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT cstr_user_role_primary_key PRIMARY KEY (user_id, role_id),
    CONSTRAINT cstr_user_role_user_foreign_key FOREIGN KEY(user_id)
        REFERENCES tbl_user(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT cstr_user_role_role_foreign_key FOREIGN KEY(role_id)
        REFERENCES tbl_role(id)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX idx_user_role_user_id ON tbl_user_tbl_role(user_id);
CREATE INDEX idx_user_role_role_id ON tbl_user_tbl_role(role_id);
