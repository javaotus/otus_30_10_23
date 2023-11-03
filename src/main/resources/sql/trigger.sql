CREATE TRIGGER update_balance
    BEFORE UPDATE
    ON account
    FOR EACH ROW
EXECUTE PROCEDURE recalculate_balance();