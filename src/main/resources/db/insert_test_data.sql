INSERT INTO form (table_name, display_name) VALUES ('abort_form', 'abort_form');

INSERT INTO field (form_id, name, type) VALUES (1, 'field_1', 'String');

INSERT INTO computed_field (form_id, name, type) VALUES (1, 'abortion_type_computed_field', 'Number');
INSERT INTO computed_field (computed_field_id, form_id, name, type) VALUES (471, 1, 'ifa_tablets_total', 'Number');
INSERT INTO computed_field (computed_field_id, form_id, name, type) VALUES (847, 1, 'breastfed_hour', 'Boolean');

INSERT INTO field_operation (field_1_id, computed_field_id) VALUES (1, 1);

INSERT INTO condition (complex_condition_id, computed_field_id, comparison_symbol_id, comparison_value)
    VALUES (1, 1, 1, 90);

INSERT INTO trend (positive_diff, negative_diff) VALUES (1, -1);

INSERT INTO indicator (type_id, area_id, computed_field_id, trend_id, frequency, name)
    VALUES (1, 1, 1, 1, 1, 'Test indicator');

INSERT INTO report (indicator_id, report_type_id, label_x, label_y)
    VALUES (1, 1, 'Label X', 'Label Y');
INSERT INTO report (indicator_id, report_type_id, label_x, label_y)
    VALUES (1, 2, 'Label X', 'Label Y');
