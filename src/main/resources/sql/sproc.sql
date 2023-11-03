create function recalculate_balance() returns trigger
    language plpgsql
as
$$
DECLARE customer_id UUID = (
    SELECT cus.id FROM customer cus
                           INNER JOIN account_bank_customer abc ON cus.id = abc.customer
                           INNER JOIN account a on abc.account = a.id
    WHERE a.id = OLD.id
);

    DECLARE balance_sum DOUBLE PRECISION = (
    SELECT SUM(balance) FROM account a
                                 INNER JOIN account_bank_customer abc ON abc.account = a.id
    WHERE abc.customer = customer_id AND a.currency = OLD.currency
);

BEGIN
    IF NEW.balance <> OLD.balance THEN
        UPDATE statement SET
                             customer = customer_id,
                             amount = balance_sum,
                             currency = OLD.currency WHERE currency = OLD.currency;

        INSERT INTO statement (customer, amount, currency)
        SELECT customer_id, NEW.balance, OLD.currency
        WHERE NOT EXISTS (SELECT 1 FROM statement WHERE customer = customer_id)
        ;
    END IF;

    RETURN NEW;
END;
$$;

alter function recalculate_balance() owner to postgres;

