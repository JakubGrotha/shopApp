CREATE TABLE product
(
    id       bigserial not null PRIMARY KEY,
    name     text      not null,
    price    numeric   not null,
    quantity int       not null default 0
);
