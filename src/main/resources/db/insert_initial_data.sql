INSERT INTO level (name, hierarchy_depth) values ('City', 0);
INSERT INTO level (name, hierarchy_depth, parent_level_id) values ('District', 1, 1);
INSERT INTO area (name, level_id) values ('Gdynia', 1), ('Gdańsk', 1);
INSERT INTO area (name, level_id, parent_area_id) values ('Chylonia', 2, 1), ('Grabówek', 2, 1), ('Stogi', 2, 2), ('Osowa', 2, 2);

INSERT INTO care_user (username, password, first_name, last_name, email, area_id, salt, creation_date, modification_date)
          VALUES ('test', '51abb9636078defbf888d8457a7c76f85c8f114c', 'Care', 'Care', 'test@test.test', 1, 'test', now(), now());
INSERT INTO role (name) VALUES ('Admin'), ('Manager');
INSERT INTO permission(name, display_name) VALUES ('CAN_CREATE_INDICATORS', 'Can create indicators'),
    ('CAN_MANAGE_SYSTEM_USERS', 'Can manage system users'), ('CAN_MANAGE_REPORTS', 'Can manage reports');
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1), (1, 2), (1, 3);
INSERT INTO care_user_role (user_id, role_id) VALUES (1, 1), (1, 2);

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
