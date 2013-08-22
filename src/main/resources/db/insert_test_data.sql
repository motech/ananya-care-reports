INSERT INTO form (table_name, display_name) VALUES ('abort_form', 'abort_form');

INSERT INTO field (form_id, name, type) VALUES (1, 'field_1', 'String');

INSERT INTO computed_field (form_id, name, type) VALUES (1, 'abortion_type_computed_field', 'Number');
INSERT INTO computed_field (computed_field_id, form_id, name, type) VALUES (471, 1, 'ifa_tablets_total', 'Number');
INSERT INTO computed_field (computed_field_id, form_id, name, type) VALUES (847, 1, 'breastfed_hour', 'Boolean');

INSERT INTO field_operation (field_1_id, computed_field_id) VALUES (1, 1);
INSERT INTO care_user (username, password, email, area_id, salt, creation_date, modification_date)
VALUES ('test', '51abb9636078defbf888d8457a7c76f85c8f114c', 'test@test.test', 1, 'test', now(), now());
INSERT INTO care_user (username, password, email, area_id, salt, creation_date, modification_date)
VALUES ('soldeveloper', '51abb9636078defbf888d8457a7c76f85c8f114c', 'test@test.test', 1, 'test', now(), now());
