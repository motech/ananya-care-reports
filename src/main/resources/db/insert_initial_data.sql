insert into role (name, creation_date, modification_date) values ('Admin', now(), now());
insert into role (name, creation_date, modification_date) values ('Operational Manager', now(), now());
insert into role (name, creation_date, modification_date) values ('Program Manager', now(), now());
insert into role (name, creation_date, modification_date) values ('Read Only', now(), now());

insert into permission(name, creation_date, modification_date) values ('CAN_MANAGE_DASHBOARDS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_VIEW_PERFORMANCE_SUMMARY', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_VIEW_MAP_REPORT', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_CREATE_INDICATORS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_EDIT_INDICATORS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_REMOVE_INDICATORS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_CREATE_CLASSIFICATIONS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_EDIT_CLASSIFICATIONS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_REMOVE_CLASSIFICATIONS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_CREATE_COMPUTED_FIELDS',  now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_EDIT_FORMS', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_CREATE_LANGUAGES', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_EDIT_LANGUAGES', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_REMOVE_LANGUAGES', now(), now());
insert into permission(name, creation_date, modification_date) values ('CAN_EDIT_CALCULATION', now(), now());

insert into role_permission (role_id, permission_id) values (1, 1);
insert into role_permission (role_id, permission_id) values (1, 2);
insert into role_permission (role_id, permission_id) values (1, 3);
insert into role_permission (role_id, permission_id) values (1, 4);
insert into role_permission (role_id, permission_id) values (1, 5);
insert into role_permission (role_id, permission_id) values (1, 6);
insert into role_permission (role_id, permission_id) values (1, 7);
insert into role_permission (role_id, permission_id) values (1, 8);
insert into role_permission (role_id, permission_id) values (1, 9);
insert into role_permission (role_id, permission_id) values (1, 10);
insert into role_permission (role_id, permission_id) values (1, 11);
insert into role_permission (role_id, permission_id) values (1, 12);
insert into role_permission (role_id, permission_id) values (1, 13);
insert into role_permission (role_id, permission_id) values (1, 14);
insert into role_permission (role_id, permission_id) values (1, 15);
insert into role_permission (role_id, permission_id) values (2, 1);
insert into role_permission (role_id, permission_id) values (2, 2);
insert into role_permission (role_id, permission_id) values (2, 3);
insert into role_permission (role_id, permission_id) values (2, 4);
insert into role_permission (role_id, permission_id) values (2, 5);
insert into role_permission (role_id, permission_id) values (2, 6);
insert into role_permission (role_id, permission_id) values (3, 1);
insert into role_permission (role_id, permission_id) values (3, 2);
insert into role_permission (role_id, permission_id) values (3, 3);
insert into role_permission (role_id, permission_id) values (3, 4);
insert into role_permission (role_id, permission_id) values (3, 5);
insert into role_permission (role_id, permission_id) values (3, 6);
insert into role_permission (role_id, permission_id) values (4, 1);
insert into role_permission (role_id, permission_id) values (4, 2);
insert into role_permission (role_id, permission_id) values (4, 3);

insert into dashboard (name, tab_position, creation_date, modification_date) values ('Performance summary', 0, now(), now());
insert into dashboard (name, tab_position, creation_date, modification_date) values ('Map report', 1, now(), now());
insert into dashboard (name, tab_position, creation_date, modification_date) values ('Birth preparedness plan', 2, now(), now());
insert into dashboard (name, tab_position, creation_date, modification_date) values ('IFA Tablets', 3, now(), now());
insert into dashboard (name, tab_position, creation_date, modification_date) values ('Mortality', 4, now(), now());
insert into dashboard (name, tab_position, creation_date, modification_date) values ('Essential Newborn Care', 5, now(), now());

insert into indicator_classification (name, dashboard_id, creation_date, modification_date) values ('Birth preparedness plan', 3, now(), now());
insert into indicator_classification (name, dashboard_id, creation_date, modification_date) values ('IFA Tablets', 4, now(), now());
insert into indicator_classification (name, dashboard_id, creation_date, modification_date) values ('Mortality', 5, now(), now());
insert into indicator_classification (name, dashboard_id, creation_date, modification_date) values ('Essential Newborn Care', 6, now(), now());

insert into comparison_symbol (name, creation_date, modification_date) values ('=', now(), now());
insert into comparison_symbol (name, creation_date, modification_date) values ('>', now(), now());
insert into comparison_symbol (name, creation_date, modification_date) values ('<', now(), now());
insert into comparison_symbol (name, creation_date, modification_date) values ('>=', now(), now());
insert into comparison_symbol (name, creation_date, modification_date) values ('<=', now(), now());

insert into operator_type (name, creation_date, modification_date) values ('ADD', now(), now());
insert into operator_type (name, creation_date, modification_date) values ('SUB', now(), now());
insert into operator_type (name, creation_date, modification_date) values ('MUL', now(), now());
insert into operator_type (name, creation_date, modification_date) values ('DIV', now(), now());

insert into report_type (name, creation_date, modification_date) values ('Line Chart', now(), now());
insert into report_type (name, creation_date, modification_date) values ('Bar Chart', now(), now());
insert into report_type (name, creation_date, modification_date) values ('Pie Chart', now(), now());

insert into condition(field_1_id, creation_date, modification_date) values (471, now(), now());
insert into condition(field_1_id, creation_date, modification_date) values (847, now(), now());

insert into frequency(frequency_name, parent_frequency_id, creation_date, modification_date) values ('yearly', null, now(), now());
insert into frequency(frequency_name, parent_frequency_id, creation_date, modification_date) values ('quarterly', 1, now(), now());
insert into frequency(frequency_name, parent_frequency_id, creation_date, modification_date) values ('monthly', 2, now(), now());
insert into frequency(frequency_name, parent_frequency_id, creation_date, modification_date) values ('weekly', 3, now(), now());
insert into frequency(frequency_name, parent_frequency_id, creation_date, modification_date) values ('daily', 4, now(), now());

insert into cron_task(second, minute, hour, day, month, week_day, year, frequency_id, creation_date, modification_date) values ('0', '0', '0', '1', '1', '?', '', 1, now(), now());
insert into cron_task(second, minute, hour, day, month, week_day, year, frequency_id, creation_date, modification_date) values ('0', '0', '0', '1', '1/3', '?', '', 2, now(), now());
insert into cron_task(second, minute, hour, day, month, week_day, year, frequency_id, creation_date, modification_date) values ('0', '0', '0', '1', '*', '?', '', 3, now(), now());
insert into cron_task(second, minute, hour, day, month, week_day, year, frequency_id, creation_date, modification_date) values ('0', '0', '0', '?', '*', 'MON', '', 4, now(), now());
insert into cron_task(second, minute, hour, day, month, week_day, year, frequency_id, creation_date, modification_date) values ('0', '00', '00', '*', '*', '?', '', 5, now(), now());

insert into date_depth(date_depth) values ('2012-01-01');

insert into language(code, name, creation_date, modification_date) values ('df', 'Default English', now(), now());

insert into value_comparison(condition_id, value, creation_date, modification_date, comparison_symbol_id) values (1, '90', now(), now(), 4);
insert into value_comparison(condition_id, value, creation_date, modification_date, comparison_symbol_id) values (2, 'true', now(), now(), 1);