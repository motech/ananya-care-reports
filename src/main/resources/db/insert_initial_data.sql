INSERT INTO care_user (user_id, username, password,  creation_date, modification_date)
          VALUES (1, 'test', '51abb9636078defbf888d8457a7c76f85c8f114c', now(), now());
INSERT INTO role (role_id, name) VALUES (1, 'Admin');
INSERT INTO permission(permission_id, name, display_name) VALUES (1, 'CAN_CREATE_INDICATORS', 'Can create indicators');
INSERT INTO role_permission (role_id, permission_id) VALUES (1, 1);
INSERT INTO care_user_role (user_id, role_id) VALUES (1, 1);
