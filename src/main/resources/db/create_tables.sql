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
	dashboard_id integer NOT NULL,
	name character varying(100) NOT NULL,
	short_code character varying(4) NOT NULL,
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

-- ddl-end --

-- object: reporting.report_type | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report_type(
	report_type_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_type_pk PRIMARY KEY (report_type_id),
	CONSTRAINT report_type_name_uk UNIQUE (name)
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
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT complex_condition_pk PRIMARY KEY (complex_condition_id),
	CONSTRAINT complex_condition_name_uk UNIQUE (name)
);
-- ddl-end --

-- object: reporting.condition | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.condition(
	condition_id serial NOT NULL,
	complex_condition_id integer NOT NULL,
	computed_field_id integer NOT NULL,
	comparison_symbol_id integer NOT NULL,
	comparison_value character varying(50) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT condition_pk PRIMARY KEY (condition_id),
	CONSTRAINT condition_complex_condition_id_fk FOREIGN KEY (complex_condition_id)
    REFERENCES reporting.complex_condition (complex_condition_id) MATCH FULL
    ON DELETE CASCADE ON UPDATE CASCADE NOT DEFERRABLE,
    CONSTRAINT condition_comparison_symbol_id_fk FOREIGN KEY (comparison_symbol_id)
    REFERENCES reporting.comparison_symbol (comparison_symbol_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT condition_uk UNIQUE (computed_field_id,comparison_symbol_id,comparison_value)
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

-- object: reporting.computed_field | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.computed_field(
	computed_field_id serial NOT NULL,
	form_id integer NOT NULL,
	name character varying(100) NOT NULL,
	type character varying(50) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT computed_field_pk PRIMARY KEY (computed_field_id),
    CONSTRAINT computed_field_form_id_fk FOREIGN KEY (form_id)
	REFERENCES reporting.form (form_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT computed_field_name_uk UNIQUE (form_id,name)
);
-- ddl-end --

-- object: reporting.indicator | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator(
	indicator_id serial NOT NULL,
	type_id integer NOT NULL,
	area_id integer NOT NULL,
	trend_id integer NOT NULL,
	complex_condition_id integer,
	computed_field_id integer NOT NULL,
	frequency integer NOT NULL,
	name character varying NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_pk PRIMARY KEY (indicator_id),
	CONSTRAINT indicator_type_id_fk FOREIGN KEY (type_id)
	REFERENCES reporting.indicator_type (indicator_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_area_id_fk FOREIGN KEY (area_id)
	REFERENCES reporting.area (area_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_complex_condition_id_fk FOREIGN KEY (complex_condition_id)
    REFERENCES reporting.complex_condition (complex_condition_id) MATCH FULL
    ON DELETE SET NULL ON UPDATE NO ACTION NOT DEFERRABLE,
    CONSTRAINT indicator_computed_field_id_fk FOREIGN KEY (computed_field_id)
    REFERENCES reporting.computed_field (computed_field_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.report | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report(
	report_id serial NOT NULL,
	indicator_id integer NOT NULL,
	report_type_id integer NOT NULL,
	label_x character varying(30),
	label_y character varying(30),
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_pk PRIMARY KEY (report_id),
	CONSTRAINT report_type_id_fk FOREIGN KEY (report_type_id)
	REFERENCES reporting.report_type (report_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_id_fk FOREIGN KEY (indicator_id)
    REFERENCES reporting.indicator (indicator_id) MATCH FULL
    ON DELETE CASCADE ON UPDATE NO ACTION NOT DEFERRABLE
);
-- ddl-end --

-- ddl-end --

-- object: reporting.dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.dashboard(
	dashboard_id serial NOT NULL,
	indicator_category_id integer,
	name character varying(100) NOT NULL,
	tab_position smallint NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT dashboard_pk PRIMARY KEY (dashboard_id),
	CONSTRAINT dashboard_indicator_category_fk FOREIGN KEY (indicator_category_id)
    REFERENCES reporting.indicator_category (indicator_category_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT dashboard_name_uk UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS reporting.dashboard_user(
    dashboard_id integer NOT NULL,
	user_id integer NOT NULL,
	CONSTRAINT dashboard_user_dashboard_id_fk FOREIGN KEY (dashboard_id)
	REFERENCES reporting.dashboard (dashboard_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT dashboard_user_user_id_fk FOREIGN KEY (user_id)
	REFERENCES reporting.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT dashboard_user_uk UNIQUE (dashboard_id,user_id)
);

-- object: reporting.report_dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.report_dashboard(
	report_id integer NOT NULL,
	dashboard_id integer NOT NULL,
	CONSTRAINT report_dashboard_report_fk FOREIGN KEY (report_id)
	REFERENCES reporting.report (report_id) MATCH FULL
	ON DELETE CASCADE ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_dashboard_dashboard_id_fk FOREIGN KEY (dashboard_id)
	REFERENCES reporting.dashboard (dashboard_id) MATCH FULL
	ON DELETE CASCADE ON UPDATE NO ACTION NOT DEFERRABLE,
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
	ON DELETE CASCADE ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_report_id_fk FOREIGN KEY (report_id)
	REFERENCES reporting.report (report_id) MATCH FULL
	ON DELETE CASCADE ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_uk UNIQUE (indicator_id,report_id)
);
-- ddl-end --

-- ddl-end --

-- object: reporting.indicator_value | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.indicator_value(
	indicator_value_id serial NOT NULL,
	indicator_id integer NOT NULL,
	area_id integer NOT NULL,
	date timestamp NOT NULL,
	value decimal(19,6) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_value_pk PRIMARY KEY (indicator_value_id),
	CONSTRAINT indicator_value_indicator_id_fk FOREIGN KEY (indicator_id)
	REFERENCES reporting.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_value_area_id_fk FOREIGN KEY (area_id)
	REFERENCES reporting.area (area_id) MATCH FULL
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

-- object: reporting.indicator_user | type: TABLE --
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

-- object: reporting.field | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.field(
	field_id serial NOT NULL,
	form_id integer NOT NULL,
	name character varying(100) NOT NULL,
	type character varying(50) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT field_pk PRIMARY KEY (field_id),
	CONSTRAINT field_form_id FOREIGN KEY (form_id)
	REFERENCES reporting.form (form_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT field_uk UNIQUE (form_id,name)
);
-- ddl-end --

-- object: reporting.field_operation | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.field_operation(
	field_operation_id serial NOT NULL,
	field_1_id integer NOT NULL,
	field_2_id integer,
	operator_type_id integer,
	computed_field_id integer NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT field_operation_pk PRIMARY KEY (field_operation_id),
	CONSTRAINT field_operation_field_1_id FOREIGN KEY (field_1_id)
	REFERENCES reporting.field (field_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT field_operation_field_2_id FOREIGN KEY (field_2_id)
    REFERENCES reporting.field (field_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
    CONSTRAINT field_operation_operator_type_id FOREIGN KEY (operator_type_id)
    REFERENCES reporting.operator_type (operator_type_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
    CONSTRAINT field_operation_computed_field_id FOREIGN KEY (computed_field_id)
    REFERENCES reporting.computed_field (computed_field_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT field_operation_uk UNIQUE (field_1_id,field_2_id,operator_type_id,computed_field_id)
);
-- ddl-end --

-- object: reporting.complex_condition_field | type: TABLE --
CREATE TABLE IF NOT EXISTS reporting.complex_condition_field(
	complex_condition_id integer NOT NULL,
	field_id integer NOT NULL,
	CONSTRAINT complex_condition_field_complex_condition_id_fk FOREIGN KEY (complex_condition_id)
	REFERENCES reporting.complex_condition (complex_condition_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_field_field_id_fk FOREIGN KEY (field_id)
	REFERENCES reporting.field (field_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_field_uk UNIQUE (complex_condition_id,field_id)
);
-- ddl-end -

-- CONSTRAINTS --
ALTER TABLE reporting.indicator_category
    ADD CONSTRAINT indicator_category_dashboard_id_fk FOREIGN KEY (dashboard_id)
    REFERENCES reporting.dashboard (dashboard_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;

ALTER TABLE reporting.condition
    ADD CONSTRAINT condition_computed_field_id_fk FOREIGN KEY (computed_field_id)
    REFERENCES reporting.computed_field (computed_field_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;

CREATE TABLE IF NOT EXISTS reporting.trend (
    trend_id serial NOT NULL,
    positive_diff decimal(19,6) NOT NULL,
    negative_diff decimal(19,6) NOT NULL,
    creation_date timestamp,
    modification_date timestamp,
    CONSTRAINT trend_pk PRIMARY KEY (trend_id)
);

ALTER TABLE reporting.indicator
    ADD CONSTRAINT indicator_trend_id_fk FOREIGN KEY (trend_id)
    REFERENCES reporting.trend (trend_id) MATCH FULL
    ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE;