SET search_path = dashboard_app;

CREATE OR REPLACE FUNCTION dashboard_app.fetchAreasFromReportingDB() RETURNS void as
E'DECLARE
    state_loop_var varchar(255);
    district_loop_var varchar(255);
    block_loop_var varchar(255);
BEGIN
    FOR state_loop_var IN (SELECT DISTINCT state FROM report.location_dimension)
    LOOP
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
            values (state_loop_var, (select level_id from dashboard_app.level where name = \'state\'), 1, now(), now());
    END LOOP;

    FOR district_loop_var IN (SELECT DISTINCT district FROM report.location_dimension)
    LOOP
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
            values (district_loop_var, (select level_id from dashboard_app.level where name = \'district\'),
            (select area_id from dashboard_app.area where
                name = (select distinct state from report.location_dimension where district = district_loop_var)), now(), now());
    END LOOP;

    FOR block_loop_var IN (SELECT DISTINCT block FROM report.location_dimension)
    LOOP
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
            values (block_loop_var, (select level_id from dashboard_app.level where name = \'block\'),
            (select area_id from dashboard_app.area where
                name = (select distinct district from report.location_dimension where block = block_loop_var)
                and level_id = (select level_id from dashboard_app.level where name = \'district\')), now(), now());
    END LOOP;
END'
LANGUAGE 'plpgsql';

insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('India', 1, null, now(), now());
select fetchAreasFromReportingDB();
CREATE OR REPLACE FUNCTION dashboard_app.populateAreaFunction() RETURNS trigger AS
E'BEGIN
    IF (select distinct area_id from dashboard_app.area where name = NEW.state
    and level_id = (select level_id from dashboard_app.level where name = \'state\')) IS NULL THEN
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
        VALUES (NEW.state, (select level_id from dashboard_app.level where name = \'state\'), 1, now(), now());
    END IF;
    IF (select distinct area_id from dashboard_app.area where name = NEW.district
    and level_id = (select level_id from dashboard_app.level where name = \'district\')) IS NULL THEN
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
        VALUES (NEW.district, (select level_id from dashboard_app.level where name = \'district\'),
            (select area_id from dashboard_app.area where
            name = (select distinct state from report.location_dimension
                where district = NEW.district)
            and level_id = (select level_id from dashboard_app.level where name = \'state\')),
            now(), now());
    END IF;
    IF (select distinct area_id from dashboard_app.area where name = NEW.block
    and level_id = (select level_id from dashboard_app.level where name = \'block\')) IS NULL THEN
        INSERT INTO DASHBOARD_APP.AREA (name, level_id, parent_area_id, creation_date, modification_date)
        VALUES (NEW.block, (select level_id from dashboard_app.level where name = \'block\'),
            (select area_id from dashboard_app.area where
            name = (select distinct district from report.location_dimension
                where block = NEW.block)
            and level_id = (select level_id from dashboard_app.level where name = \'district\')),
            now(), now());
    END IF;
    RETURN NEW;
END'
LANGUAGE 'plpgsql';

drop trigger if exists populateAreaTrigger on report.location_dimension;

CREATE TRIGGER populateAreaTrigger AFTER INSERT
    ON report.location_dimension
    FOR EACH ROW
    EXECUTE PROCEDURE dashboard_app.populateAreaFunction();