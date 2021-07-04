create or replace function trigger_set_timestamp()
    returns trigger as
$trigger_set_timestamp$
begin
    new.updated = now();
return new;
end;
$trigger_set_timestamp$ language plpgsql;


create trigger articles_set_timestamp
    before update
    on t_articles
    for each row
    execute procedure trigger_set_timestamp();

create trigger products_set_timestamp
    before update
    on t_products
    for each row
    execute procedure trigger_set_timestamp();