drop table if exists t_product_specification;
create table t_product_specification
(
    id          serial,
    name        varchar(200),
    created     timestamp        not null default now(),
    updated     timestamp        not null default now(),

    constraint pk_product_specification primary key (id),
    constraint ui_product_specification_name unique (name)
);


drop table if exists t_product_part_items;
create table t_product_specification_part_items
(
    product_specification_id      serial,
    article_id                    bigint      not null,
    amount                        int         not null default 0,

    constraint fk_product_specification_id foreign key (product_specification_id) references t_product_specification (id),
    constraint fk_article_id foreign key (article_id) references t_articles (id)
);

