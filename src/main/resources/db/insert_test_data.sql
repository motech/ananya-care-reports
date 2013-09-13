insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('Bihar', 1, null, now(), now());
insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('Begusarai', 2, 1, now(), now());
insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('Champaran East', 2, 1, now(), now());
insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('Patna', 2, 1, now(), now());
insert into area (name, level_id, parent_area_id, creation_date, modification_date) values ('Saharsa', 2, 1, now(), now());

INSERT INTO care_user (username, area_id, creation_date, modification_date)
VALUES ('test', 1, now(), now());

INSERT INTO care_user (username, area_id, creation_date, modification_date)
VALUES ('soldeveloper', 1, now(), now());

INSERT INTO dashboard_app.where_group(operator, creation_date, modification_date)
VALUES (null, now(), now());

INSERT INTO dashboard_app.dw_query(combination_id, grouped_by_id, creation_date, modification_date, where_group_id, has_period_condition, name)
VALUES (null, null, now(), now(), 1, 't', 'query');

INSERT INTO dashboard_app.indicator(area_level_id, frequency_id, name, creation_date, modification_date, trend, denominator_id, numerator_id, user_id, is_computed, is_additive)
VALUES (1, 5, 'Number of mothers who actually delivered during the period', now(), now(), 3, null, 1, 1, 't', 't');

INSERT INTO dashboard_app.select_column(function, creation_date, modification_date, table_name, null_value, dw_query_id)
VALUES ('Count', now(), now(), null, null, 1);

INSERT INTO dashboard_app.condition(field_1_id, creation_date, modification_date)
VALUES (390, now(), now());

INSERT INTO dashboard_app.period_comparison(condition_id, column_name, start_offset, table_name, creation_date, modification_date)
VALUES (3, 'add', null, 'mother_case', now(), now());

INSERT INTO dashboard_app.where_group_condition(where_group_id, condition_id)
VALUES (1, 3);

INSERT INTO dashboard_app.report(indicator_id, report_type_id, label_x, label_y, creation_date, modification_date)
VALUES (1, 2, 'Time', 'Value', now(), now());

INSERT INTO dashboard_app.indicator_indicator_classification(indicator_id, indicator_classification_id)
VALUES (1, 1);