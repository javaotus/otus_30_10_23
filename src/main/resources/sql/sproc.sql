create function recalculate_balance() returns trigger
    language plpgsql
as
$$

DECLARE customer_id UUID = (
    SELECT cus.id FROM customer cus
                           INNER JOIN account_bank_customer abc ON cus.id = abc.customer
                           INNER JOIN account a on abc.account = a.id
    WHERE a.id = NEW.id
);

    DECLARE balance_sum DOUBLE PRECISION = (
    SELECT SUM(balance) FROM account a
                                 INNER JOIN account_bank_customer abc ON abc.account = a.id
    WHERE abc.customer = customer_id AND a.currency = NEW.currency
);

BEGIN
    IF (NEW.balance <> OLD.balance) OR (NEW.currency <> OLD.currency) THEN
        INSERT INTO statement (customer, amount, currency) VALUES (customer_id, balance_sum, NEW.currency)
        ON CONFLICT (customer, currency)
            DO UPDATE SET amount = balance_sum, currency = NEW.currency;
    END IF;
    RETURN NEW;
END;
$$;

alter function recalculate_balance() owner to postgres;

