*** PostgreSQL Database Script

CREATE DATABASE stock_management
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Turkish_Turkey.1254'
    LC_CTYPE = 'Turkish_Turkey.1254'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
    
CREATE SCHEMA IF NOT EXISTS stock_management
AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS stock_management.product
(
    product_id bigint NOT NULL DEFAULT nextval('stock_management.product_product_id_seq'::regclass),
    is_deleted boolean,
    price double precision,
    product_created_date timestamp without time zone,
    product_name character varying(255) COLLATE pg_catalog."default",
    product_updated_date timestamp without time zone,
    quantity integer,
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS stock_management.product
    OWNER to postgres;
