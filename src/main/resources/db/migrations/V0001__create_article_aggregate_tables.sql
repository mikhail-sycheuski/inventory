drop table if exists t_articles;
create table t_articles
(
    id                                          SERIAL,
    name                                        varchar(200),
    stock                                       bigint,
    created                                     timestamp   not null default now(),
    updated                                     timestamp   not null default now(),

    constraint pk_articles primary key (id),
    constraint ui_articles_name unique (name)
);

