CREATE TABLE order_table(
    id bigserial not null PRIMARY KEY,
    total_price numeric not null,
    date_of_order timestamp not null,
    is_delivered boolean not null default false,
    user_id bigint not null
);

CREATE TABLE ordered_item(
    id bigserial not null PRIMARY KEY,
    order_id bigint not null,
    product_id bigint not null,
    name text not null,
    quantity int not null,
    price_per_item numeric not null
);