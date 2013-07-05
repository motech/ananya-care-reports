SET client_encoding = 'UTF8';

CREATE SCHEMA reporting;

ALTER SCHEMA reporting OWNER TO postgres;

SET search_path = reporting;

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: carereporting | type: DATABASE --
-- CREATE DATABASE carereporting
-- ;
-- -- ddl-end --
-- 

-- object: reporting.indicator_type | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_type(
	indicator_type_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_type_pk PRIMARY KEY (indicator_type_id),
	CONSTRAINT indicator_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator_category | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_category(
	indicator_category_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_category_pk PRIMARY KEY (indicator_category_id),
	CONSTRAINT indicator_category_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.level | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.level(
	level_id serial NOT NULL,
	parent_level_id integer,
	name character varying(100) NOT NULL,
	hierarchy_depth integer NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT level_pk PRIMARY KEY (level_id),
	CONSTRAINT level_parent_level_id_fk FOREIGN KEY (parent_level_id)
	REFERENCES reporting.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT level_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.area | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.area(
	area_id serial NOT NULL,
	level_id integer NOT NULL,
	parent_area_id integer,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT area_pk PRIMARY KEY (area_id),
	CONSTRAINT area_level_id_fk FOREIGN KEY (level_id)
	REFERENCES reporting.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT area_parent_area_id_fk FOREIGN KEY (parent_area_id)
    REFERENCES reporting.area (area_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT area_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.chart_type | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.chart_type(
	chart_type_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT chart_type_pk PRIMARY KEY (chart_type_id),
	CONSTRAINT chart_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.report_type | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report_type(
	report_type_id integer NOT NULL,
	chart_type_id integer,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_type_pk PRIMARY KEY (report_type_id),
	CONSTRAINT report_chart_type_id_fk FOREIGN KEY (chart_type_id)
	REFERENCES reporting.chart_type (chart_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.report | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report(
	report_id serial NOT NULL,
	type_id integer NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_pk PRIMARY KEY (report_id),
	CONSTRAINT report_type_id_fk FOREIGN KEY (type_id)
	REFERENCES reporting.report_type (report_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.comparison_symbol | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.comparison_symbol(
	comparison_symbol_id serial NOT NULL,
	name character varying(10) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT comparison_symbol_pk PRIMARY KEY (comparison_symbol_id),
	CONSTRAINT comparison_symbol_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.form | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.form(
	form_id serial NOT NULL,
	table_name character varying(50) NOT NULL,
	display_name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT form_pk PRIMARY KEY (form_id),
	CONSTRAINT form_table_name_uk UNIQUE (table_name),
	CONSTRAINT form_display_name_uk UNIQUE (display_name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.operator_type | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.operator_type(
	operator_type_id serial NOT NULL,
	name character varying(10) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT operator_type_pk PRIMARY KEY (operator_type_id),
	CONSTRAINT operator_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.complex_condition | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.complex_condition(
	complex_condition_id serial NOT NULL,
	operator_type_id integer NOT NULL,
	form_id integer NOT NULL,
	comparison_symbol_id integer NOT NULL,
	field character varying(100) NOT NULL,
	comparison_value decimal(19,2) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT complex_condition_pk PRIMARY KEY (complex_condition_id),
	CONSTRAINT complex_condition_operator_type_id FOREIGN KEY (operator_type_id)
	REFERENCES reporting.operator_type (operator_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_form_id FOREIGN KEY (form_id)
	REFERENCES reporting.form (form_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
	REFERENCES reporting.comparison_symbol (comparison_symbol_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
);
-- ddl-end --

-- ddl-end --

-- object: reporting.care_user | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.care_user(
	user_id serial NOT NULL,
	username character varying(100) NOT NULL,
	password character varying(40) NOT NULL,
	first_name character varying(40) NOT NULL,
    last_name character varying(40) NOT NULL,
    email character varying(120),
    area_id integer NOT NULL,
	salt character varying(40) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT user_pk PRIMARY KEY (user_id),
	CONSTRAINT user_username_uk UNIQUE (username),
    CONSTRAINT care_user_area_id_fk FOREIGN KEY (area_id)
    REFERENCES reporting.area (area_id) MATCH FULL
);
-- ddl-end --

-- ddl-end --

-- object: reporting.permission | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.permission(
	permission_id serial NOT NULL,
	name character varying(100) NOT NULL,
	display_name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT permission_pk PRIMARY KEY (permission_id),
	CONSTRAINT permission_name_uk UNIQUE (name),
	CONSTRAINT permission_display_name_uk UNIQUE (display_name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.role | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.role(
	role_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT role_pk PRIMARY KEY (role_id),
	CONSTRAINT role_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.care_user_role | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.care_user_role(
	user_id integer NOT NULL,
	role_id integer NOT NULL,
	CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id)
	REFERENCES reporting.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id)
	REFERENCES reporting.role (role_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT user_role_uk UNIQUE (user_id,role_id)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.role_permission | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.role_permission(
	role_id integer NOT NULL,
	permission_id integer NOT NULL,
	CONSTRAINT role_permission_role_id_fk FOREIGN KEY (role_id)
	REFERENCES reporting.role (role_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT role_permission_permission_id_fk FOREIGN KEY (permission_id)
	REFERENCES reporting.permission (permission_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT role_permission_uk UNIQUE (role_id,permission_id)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator(
	indicator_id serial NOT NULL,
	type_id integer NOT NULL,
	top_level_id integer NOT NULL,
	frequency integer NOT NULL,
	name character varying NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_pk PRIMARY KEY (indicator_id),
	CONSTRAINT indicator_type_id_fk FOREIGN KEY (type_id)
	REFERENCES reporting.indicator_type (indicator_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_top_level_id_fk FOREIGN KEY (top_level_id)
	REFERENCES reporting.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.dashboard(
	dashboard_id serial NOT NULL,
	owner_id integer NOT NULL,
	tab_position smallint NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT dashboard_pk PRIMARY KEY (dashboard_id),
	CONSTRAINT dashboard_user_id_fk FOREIGN KEY (owner_id)
	REFERENCES reporting.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT dashboard_tab_position_uk UNIQUE (tab_position)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.report_dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report_dashboard(
	report_id integer NOT NULL,
	dashboard_id integer NOT NULL,
	CONSTRAINT report_dashboard_report_fk FOREIGN KEY (report_id)
	REFERENCES reporting.report (report_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_dashboard_dashboard_id_fk FOREIGN KEY (dashboard_id)
	REFERENCES reporting.dashboard (dashboard_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_dashboard_uk UNIQUE (report_id,dashboard_id)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator_report | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_report(
	indicator_id integer NOT NULL,
	report_id integer NOT NULL,
	CONSTRAINT indicator_report_indicator_id FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_report_id_fk FOREIGN KEY (report_id)
	REFERENCES reporting.report (report_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_uk UNIQUE (indicator_id,report_id)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator_value | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_value(
	indicator_value_id serial NOT NULL,
	indicator_id integer NOT NULL,
	area_id integer NOT NULL,
	condition_id integer NOT NULL,
	date timestamp NOT NULL,
	condition_value decimal(19,2) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_value_pk PRIMARY KEY (indicator_value_id),
	CONSTRAINT indicator_value_indicator_id_fk FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_value_area_id_fk FOREIGN KEY (area_id)
	REFERENCES reporting.area (area_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_value_condition_id_fk FOREIGN KEY (condition_id)
	REFERENCES reporting.complex_condition (complex_condition_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator_indicator_category | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_indicator_category(
	indicator_id integer NOT NULL,
	indicator_category_id integer NOT NULL,
	CONSTRAINT indicator_indicator_category_indicator_id_fk FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_indicator_category_indicator_category_id_fk FOREIGN KEY (indicator_category_id)
	REFERENCES reporting.indicator_category (indicator_category_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_indicator_category_uk UNIQUE (indicator_id,indicator_category_id)
);
-- ddl-end --

-- object: reporting.indicator_indicator_category | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_user(
	indicator_id integer NOT NULL,
	user_id integer NOT NULL,
	CONSTRAINT indicator_user_indicator_id_fk FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_user_user_id_fk FOREIGN KEY (user_id)
	REFERENCES reporting.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_user_uk UNIQUE (indicator_id,user_id)
);
-- ddl-end --

-- object: reporting.role_permission | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_complex_condition(
	indicator_id integer NOT NULL,
	complex_condition_id integer NOT NULL,
	CONSTRAINT indicator_complex_condition_indicator_id_fk FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_complex_condition_complex_condition_id_fk FOREIGN KEY (complex_condition_id)
	REFERENCES reporting.complex_condition (complex_condition_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_complex_condition_uk UNIQUE (indicator_id,complex_condition_id)
);
-- ddl-end --
