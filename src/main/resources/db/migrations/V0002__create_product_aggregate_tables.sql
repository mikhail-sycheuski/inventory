drop table if exists t_products;
create table t_products
(
    id          serial,
    name        varchar(200),
    created     timestamp        not null default now(),
    updated     timestamp        not null default now(),

    constraint pk_product primary key (id),
    constraint ui_product_name unique (name)
);


drop table if exists t_products_contained_items;
create table t_products_contained_items
(
    product_id                    serial,
    article_id                    bigint      not null,
    amount                        int         not null default 0,

    constraint fk_product_id foreign key (product_id) references t_products (id),
    constraint fk_article_id foreign key (article_id) references t_articles (id)
);

