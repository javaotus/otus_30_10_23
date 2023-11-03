CREATE TRIGGER update_balance
    AFTER UPDATE
    ON account
    FOR EACH ROW
EXECUTE PROCEDURE recalculate_balance();