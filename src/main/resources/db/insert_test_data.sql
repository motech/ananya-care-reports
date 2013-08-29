INSERT INTO care_user (username, password, email, area_id, salt, creation_date, modification_date)
VALUES ('test', '51abb9636078defbf888d8457a7c76f85c8f114c', 'test@test.test', 1, 'test', now(), now());

INSERT INTO care_user (username, password, email, area_id, salt, creation_date, modification_date)
VALUES ('soldeveloper', '51abb9636078defbf888d8457a7c76f85c8f114c', 'test@test.test', 1, 'test', now(), now());

INSERT INTO dashboard_app.where_group(operator, creation_date, modification_date)
VALUES (null, now(), now());

INSERT INTO dashboard_app.dw_query(combination_id, grouped_by_id, creation_date, modification_date, where_group_id, has_period_condition)
VALUES (null, null, now(), now(), 1, 't');

INSERT INTO dashboard_app.simple_dw_query(dw_query_id, table_name, creation_date, modification_date)
VALUES ( 1, 'mother_case', now(), now());

INSERT INTO dashboard_app.indicator(area_id, frequency_id, name, creation_date, modification_date, trend, denominator_id, numerator_id, user_id, is_computed, is_additive)
VALUES (1, 5, 'Number of mothers who actually delivered during the period', now(), now(), 3, null, 1, 1, 't', 't');

INSERT INTO dashboard_app.select_column(name, function, creation_date, modification_date, table_name, null_value)
VALUES ('*', 'Count', now(), now(), null, null);

INSERT INTO dashboard_app.condition(field_1_id, comparison_symbol_id, creation_date, modification_date)
VALUES (390, null, now(), now());

INSERT INTO dashboard_app.period_comparison(condition_id, column_name, start_offset, table_name, creation_date, modification_date)
VALUES (3, 'add', null, 'mother_case', now(), now());

INSERT INTO dashboard_app.dw_query_select_column(select_column_id, dw_query_id)
VALUES (1, 1);

INSERT INTO dashboard_app.where_group_condition(where_group_id, condition_id)
VALUES (1, 3);

INSERT INTO dashboard_app.report(indicator_id, report_type_id, label_x, label_y, creation_date, modification_date)
VALUES (1, 2, 'Time', 'Value', now(), now());

INSERT INTO dashboard_app.indicator_indicator_category(indicator_id, indicator_category_id)
VALUES (1, 1);