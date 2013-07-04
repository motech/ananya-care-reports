INSERT INTO care_user (username, password, salt, creation_date, modification_date)
          VALUES ('test', '51abb9636078defbf888d8457a7c76f85c8f114c','test', now(), now());
INSERT INTO role (name) VALUES ('Admin'), ('Manager');
INSERT INTO permission(name, display_name) VALUES ('CAN_CREATE_INDICATORS', 'Can create indicators'),
    ('CAN_MANAGE_SYSTEM_USERS', 'Can manage system users');
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1), (1, 2);
INSERT INTO care_user_role (user_id, role_id) VALUES (1, 1), (1, 2);

INSERT INTO form (table_name, display_name)
VALUES ('registration_mother_form', 'Mother registration form'),
        ('registration_child_form', 'Child registration form');

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
