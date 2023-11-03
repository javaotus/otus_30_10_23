-- auto-generated definition
create trigger update_balance
    after update
    on account
    for each row
execute procedure recalculate_balance();