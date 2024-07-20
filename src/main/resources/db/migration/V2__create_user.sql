CREATE TABLE app_user(
    id bigserial not null PRIMARY KEY,
    email text not null,
    password text not null,
    role text not null
);

CREATE TABLE address(
    id bigserial not null PRIMARY KEY,
    street_address text not null,
    postal_code text not null,
    city text not null,
    country text not null,
    user_id bigint not null
);