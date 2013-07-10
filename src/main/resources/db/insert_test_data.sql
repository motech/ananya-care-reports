INSERT INTO form (table_name, display_name) VALUES ('abort_form', 'abort_form');

INSERT INTO computed_field (name, type, form_id) VALUES ('abortion_type_computed_field', 'Integer', 1);

INSERT INTO indicator (type_id, area_id, computed_field_id, frequency, name) VALUES (1, 1, 1, 1, 'Test indicator');