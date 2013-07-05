INSERT INTO care_user (username, password, first_name, last_name, email, salt, creation_date, modification_date)
          VALUES ('test', '51abb9636078defbf888d8457a7c76f85c8f114c', 'Care', 'Care', 'test@test.test', 'test', now(), now());
INSERT INTO role (name) VALUES ('Admin'), ('Manager');
INSERT INTO permission(name, display_name) VALUES ('CAN_CREATE_INDICATORS', 'Can create indicators'),
    ('CAN_MANAGE_SYSTEM_USERS', 'Can manage system users');
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1), (1, 2);
INSERT INTO care_user_role (user_id, role_id) VALUES (1, 1), (1, 2);

insert into form (table_name, display_name) values
('registration_mother_form', 'registration_mother_form'),
('registration_child_form', 'registration_child_form'),
('cf_child_form', 'cf_child_form'),
('bp_form', 'bp_form'),
('abort_form', 'abort_form'),
('ui_mother_form', 'ui_mother_form'),
('ui_child_form', 'ui_child_form'),
('refer_mother_form', 'refer_mother_form'),
('refer_child_form', 'refer_child_form'),
('new_form', 'new_form'),
('pnc_mother_form', 'pnc_mother_form'),
('pnc_child_form', 'pnc_child_form'),
('mi_form', 'mi_form'),
('mo_form', 'mo_form'),
('cf_mother_form', 'cf_mother_form'),
('ebf_mother_form', 'ebf_mother_form'),
('ebf_child_form', 'ebf_child_form'),
('delivery_mother_form', 'delivery_mother_form'),
('delivery_child_form', 'delivery_child_form'),
('death_mother_form', 'death_mother_form'),
('death_child_form', 'death_child_form'),
('close_mother_form', 'close_mother_form'),
('close_child_form', 'close_child_form');

insert into level (parent_level_id, name) values (null, 'State');
insert into level (parent_level_id, name) values (null, 'District');
insert into level (parent_level_id, name) values (null, 'Block');

insert into indicator_type (name) values ('Average');
insert into indicator_type (name) values ('Count');
insert into indicator_type (name) values ('Percentage');

insert into indicator_category (name) values ('Birth preparedness plan');
insert into indicator_category (name) values ('IFA Tablets');

insert into comparison_symbol (name) values ('=');
insert into comparison_symbol (name) values ('>');
insert into comparison_symbol (name) values ('<');
insert into comparison_symbol (name) values ('>=');
insert into comparison_symbol (name) values ('<=');

insert into operator_type (name) values ('ADD');
insert into operator_type (name) values ('SUB');
insert into operator_type (name) values ('MUL');
insert into operator_type (name) values ('DIV');
