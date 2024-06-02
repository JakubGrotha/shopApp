CREATE TYPE app_user_role AS ENUM('CUSTOMER', 'ADMIN');

CREATE TABLE app_user(
    id bigserial not null PRIMARY KEY,
    email text not null,
    password text not null,
    role app_user_role not null
);

CREATE TABLE address(
    id bigserial not null PRIMARY KEY,
    street_address text not null,
    postal_code text not null,
    city text not null,
    country text not null,
    user_id bigint not null
);