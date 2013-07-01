-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- PostgreSQL version: 9.2
-- Project Site: pgmodeler.com.br
-- Model Author: ---

SET check_function_bodies = false;
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: carereporting | type: DATABASE --
-- CREATE DATABASE carereporting
-- ;
-- -- ddl-end --
-- 

-- object: public.indicator_type | type: TABLE --
CREATE TABLE IF NOT EXISTS public.indicator_type(
	indicator_type_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_type_pk PRIMARY KEY (indicator_type_id),
	CONSTRAINT indicator_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.indicator_category | type: TABLE --
CREATE TABLE IF NOT EXISTS public.indicator_category(
	indicator_category_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_category_pk PRIMARY KEY (indicator_category_id),
	CONSTRAINT indicator_category_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.level | type: TABLE --
CREATE TABLE IF NOT EXISTS public.level(
	level_id serial NOT NULL,
	parent_level_id integer NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT level_pk PRIMARY KEY (level_id),
	CONSTRAINT level_parent_level_id_fk FOREIGN KEY (parent_level_id)
	REFERENCES public.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT level_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.area | type: TABLE --
CREATE TABLE IF NOT EXISTS public.area(
	area_id serial NOT NULL,
	level_id integer NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT area_pk PRIMARY KEY (area_id),
	CONSTRAINT area_level_id_fk FOREIGN KEY (area_id)
	REFERENCES public.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT area_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.chart_type | type: TABLE --
CREATE TABLE IF NOT EXISTS public.chart_type(
	chart_type_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT chart_type_pk PRIMARY KEY (chart_type_id),
	CONSTRAINT chart_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.report_type | type: TABLE --
CREATE TABLE IF NOT EXISTS public.report_type(
	report_type_id integer NOT NULL,
	chart_type_id integer,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_type_pk PRIMARY KEY (report_type_id),
	CONSTRAINT report_chart_type_id_fk FOREIGN KEY (chart_type_id)
	REFERENCES public.chart_type (chart_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.report | type: TABLE --
CREATE TABLE IF NOT EXISTS public.report(
	report_id serial NOT NULL,
	type_id integer NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT report_pk PRIMARY KEY (report_id),
	CONSTRAINT report_type_id_fk FOREIGN KEY (type_id)
	REFERENCES public.report_type (report_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.comparison_symbol | type: TABLE --
CREATE TABLE IF NOT EXISTS public.comparison_symbol(
	comparison_symbol_id serial NOT NULL,
	name character varying(10) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT comparison_symbol_pk PRIMARY KEY (comparison_symbol_id),
	CONSTRAINT comparison_symbol_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.form | type: TABLE --
CREATE TABLE IF NOT EXISTS public.form(
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

-- object: public.operator_type | type: TABLE --
CREATE TABLE IF NOT EXISTS public.operator_type(
	operator_type_id serial NOT NULL,
	name character varying(10) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT operator_type_pk PRIMARY KEY (operator_type_id),
	CONSTRAINT operator_type_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.complex_condition | type: TABLE --
CREATE TABLE IF NOT EXISTS public.complex_condition(
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
	REFERENCES public.operator_type (operator_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_form_id FOREIGN KEY (form_id)
	REFERENCES public.form (form_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT complex_condition_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
	REFERENCES public.comparison_symbol (comparison_symbol_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
);
-- ddl-end --

-- ddl-end --

-- object: public.care_user | type: TABLE --
CREATE TABLE IF NOT EXISTS public.care_user(
	user_id serial NOT NULL,
	username character varying(100) NOT NULL,
	password character varying(40) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT user_pk PRIMARY KEY (user_id),
	CONSTRAINT user_username_uk UNIQUE (username)
);
-- ddl-end --

-- ddl-end --

-- object: public.permission | type: TABLE --
CREATE TABLE IF NOT EXISTS public.permission(
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

-- object: public.role | type: TABLE --
CREATE TABLE IF NOT EXISTS public.role(
	role_id serial NOT NULL,
	name character varying(100) NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT role_pk PRIMARY KEY (role_id),
	CONSTRAINT role_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.care_user_role | type: TABLE --
CREATE TABLE IF NOT EXISTS public.care_user_role(
	user_id integer NOT NULL,
	role_id integer NOT NULL,
	CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id)
	REFERENCES public.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id)
	REFERENCES public.role (role_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT user_role_uk UNIQUE (user_id,role_id)
);
-- ddl-end --

-- ddl-end --

-- object: public.role_permission | type: TABLE --
CREATE TABLE IF NOT EXISTS public.role_permission(
	role_id integer NOT NULL,
	permission_id integer NOT NULL,
	CONSTRAINT role_permission_role_id_fk FOREIGN KEY (role_id)
	REFERENCES public.role (role_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT role_permission_permission_id_fk FOREIGN KEY (permission_id)
	REFERENCES public.permission (permission_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT role_permission_uk UNIQUE (role_id,permission_id)
);
-- ddl-end --

-- ddl-end --

-- object: public.indicator | type: TABLE --
CREATE TABLE IF NOT EXISTS public.indicator(
	indicator_id serial NOT NULL,
	type_id integer NOT NULL,
	category_id integer NOT NULL,
	top_level_id integer NOT NULL,
	owner_id integer NOT NULL,
	frequency integer NOT NULL,
	name character varying NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT indicator_pk PRIMARY KEY (indicator_id),
	CONSTRAINT indicator_type_id_fk FOREIGN KEY (type_id)
	REFERENCES public.indicator_type (indicator_type_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_category_id_fk FOREIGN KEY (category_id)
	REFERENCES public.indicator_category (indicator_category_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_top_level_id_fk FOREIGN KEY (top_level_id)
	REFERENCES public.level (level_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_owner_id_fk FOREIGN KEY (owner_id)
	REFERENCES public.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_name_uk UNIQUE (name)
);
-- ddl-end --

-- ddl-end --

-- object: public.dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS public.dashboard(
	dashboard_id serial NOT NULL,
	owner_id integer NOT NULL,
	tab_position smallint NOT NULL,
	creation_date timestamp,
	modification_date timestamp,
	CONSTRAINT dashboard_pk PRIMARY KEY (dashboard_id),
	CONSTRAINT dashboard_user_id_fk FOREIGN KEY (owner_id)
	REFERENCES public.care_user (user_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT dashboard_tab_position_uk UNIQUE (tab_position)
);
-- ddl-end --

-- ddl-end --

-- object: public.report_dashboard | type: TABLE --
CREATE TABLE IF NOT EXISTS public.report_dashboard(
	report_id integer NOT NULL,
	dashboard_id integer NOT NULL,
	CONSTRAINT report_dashboard_report_fk FOREIGN KEY (report_id)
	REFERENCES public.report (report_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_dashboard_dashboard_id_fk FOREIGN KEY (dashboard_id)
	REFERENCES public.dashboard (dashboard_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT report_dashboard_uk UNIQUE (report_id,dashboard_id)
);
-- ddl-end --

-- ddl-end --

-- object: public.indicator_report | type: TABLE --
CREATE TABLE IF NOT EXISTS public.indicator_report(
	indicator_id integer NOT NULL,
	report_id integer NOT NULL,
	CONSTRAINT indicator_report_indicator_id FOREIGN KEY (indicator_id)
	REFERENCES public.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_report_id_fk FOREIGN KEY (report_id)
	REFERENCES public.report (report_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_report_uk UNIQUE (indicator_id,report_id)
);
-- ddl-end --

-- ddl-end --

-- object: public.indicator_value | type: TABLE --
CREATE TABLE IF NOT EXISTS public.indicator_value(
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
	REFERENCES public.indicator (indicator_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_value_area_id_fk FOREIGN KEY (area_id)
	REFERENCES public.area (area_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT indicator_value_condition_id_fk FOREIGN KEY (condition_id)
	REFERENCES public.complex_condition (complex_condition_id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
);
-- ddl-end --

-- ddl-end --


