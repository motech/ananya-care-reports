SET search_path = dashboard_app;

insert into level (name, hierarchy_depth, parent_level_id, creation_date, modification_date) values ('state', 0, null, now(), now());
insert into level (name, hierarchy_depth, parent_level_id, creation_date, modification_date) values ('district', 1, 1, now(), now());
insert into level (name, hierarchy_depth, parent_level_id, creation_date, modification_date) values ('block', 2, 2, now(), now());