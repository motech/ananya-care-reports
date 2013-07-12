INSERT INTO level (name, hierarchy_depth) values ('State', 0);
INSERT INTO level (name, hierarchy_depth, parent_level_id) values ('District', 1, 1);
INSERT INTO level (name, hierarchy_depth, parent_level_id) values ('Block', 2, 2);
INSERT INTO level (name, hierarchy_depth, parent_level_id) values ('HSC', 3, 3);
INSERT INTO level (name, hierarchy_depth, parent_level_id) values ('AWC', 4, 4);
INSERT INTO area (name, level_id) values ('State 1', 1), ('State 2', 1);
INSERT INTO area (name, level_id, parent_area_id) values ('District 1', 2, 1), ('District 2', 2, 1),
    ('District 3', 2, 2), ('District 4', 2, 2);
INSERT INTO area (name, level_id, parent_area_id) values ('Block 1', 3, 3), ('Block 2', 3, 4),
    ('Block 3', 3, 5), ('Block 4', 3, 6);
INSERT INTO area (name, level_id, parent_area_id) values ('HSC 1', 4, 7), ('HSC 2', 4, 8),
    ('HSC 3', 4, 9), ('HSC 4', 4, 10);
INSERT INTO area (name, level_id, parent_area_id) values ('AWC 1', 5, 11), ('AWC 2', 5, 12),
    ('AWC 3', 5, 13), ('AWC 4', 5, 14);

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

insert into dashboard (name, tab_position) values ('Performance summary', 0);
insert into dashboard (name, tab_position) values ('Map report', 1);
insert into dashboard (name, tab_position) values ('Birth preparedness plan', 2);
insert into dashboard (name, tab_position) values ('IFA Tablets', 3);

insert into indicator_category (name, dashboard_id) values ('Birth preparedness plan', 3);
insert into indicator_category (name, dashboard_id) values ('IFA Tablets', 4);

update dashboard set indicator_category_id = 1 where dashboard_id = 3;
update dashboard set indicator_category_id = 2 where dashboard_id = 4;

insert into dashboard_user (dashboard_id, user_id) values (1, 1);
insert into dashboard_user (dashboard_id, user_id) values (2, 1);
insert into dashboard_user (dashboard_id, user_id) values (3, 1);
insert into dashboard_user (dashboard_id, user_id) values (4, 1);

insert into comparison_symbol (name) values ('=');
insert into comparison_symbol (name) values ('>');
insert into comparison_symbol (name) values ('<');
insert into comparison_symbol (name) values ('>=');
insert into comparison_symbol (name) values ('<=');

insert into operator_type (name) values ('ADD');
insert into operator_type (name) values ('SUB');
insert into operator_type (name) values ('MUL');
insert into operator_type (name) values ('DIV');

INSERT INTO report_type (name) VALUES ('Bar Chart'), ('Line Chart'), ('Pie Chart'), ('Map'), ('Performance');
