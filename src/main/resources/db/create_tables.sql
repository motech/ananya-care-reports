SET client_encoding = 'UTF8';

CREATE SCHEMA dashboard_app;

ALTER SCHEMA dashboard_app OWNER TO postgres;

SET check_function_bodies = false;

CREATE TABLE IF NOT EXISTS dashboard_app.comparison_symbol
(
  comparison_symbol_id serial NOT NULL,
  name character varying(10) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT comparison_symbol_pk PRIMARY KEY (comparison_symbol_id ),
  CONSTRAINT comparison_symbol_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.complex_condition
(
  complex_condition_id serial NOT NULL,
  name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT complex_condition_pk PRIMARY KEY (complex_condition_id ),
  CONSTRAINT complex_condition_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.condition
(
  condition_id serial NOT NULL,
  field_1_id integer,
  comparison_symbol_id integer,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT condition_pk PRIMARY KEY (condition_id ),
  CONSTRAINT condition_comparison_symbol_id_fk FOREIGN KEY (comparison_symbol_id)
      REFERENCES dashboard_app.comparison_symbol (comparison_symbol_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS dashboard_app.calculation_end_date_comparison
(
  calculation_end_date_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  column_name character varying NOT NULL,
  date_offset integer DEFAULT 0,
  table_name character varying,
  CONSTRAINT calculation_end_date_comparison_pk PRIMARY KEY (calculation_end_date_comparison_id ),
  CONSTRAINT calculation_end_date_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.cron_task
(
  cron_task_id serial NOT NULL,
  second character varying(100) NOT NULL,
  minute character varying(100) NOT NULL,
  hour character varying(50) NOT NULL,
  day character varying(50) NOT NULL,
  month character varying(30) NOT NULL,
  week_day character varying(20) NOT NULL,
  year character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  frequency_id integer NOT NULL,
  CONSTRAINT cron_task_pk PRIMARY KEY (cron_task_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.dashboard
(
  dashboard_id serial NOT NULL,
  name character varying(100) NOT NULL,
  tab_position smallint NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT dashboard_pk PRIMARY KEY (dashboard_id ),
  CONSTRAINT dashboard_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.date_depth
(
  date_depth timestamp without time zone
);

CREATE TABLE IF NOT EXISTS dashboard_app.select_column
(
  select_column_id serial NOT NULL,
  name character varying NOT NULL,
  function character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  table_name character varying,
  null_value character varying,
  CONSTRAINT select_column_pk PRIMARY KEY (select_column_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app."having"
(
  having_id serial NOT NULL,
  select_column_id integer NOT NULL,
  operator character varying NOT NULL,
  value character varying NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT having_pk PRIMARY KEY (having_id ),
  CONSTRAINT having_select_column_id FOREIGN KEY (select_column_id)
      REFERENCES dashboard_app.select_column (select_column_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS dashboard_app.grouped_by
(
  grouped_by_id serial NOT NULL,
  having_id integer,
  table_name character varying NOT NULL,
  field_name character varying NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT grouped_by_pk PRIMARY KEY (grouped_by_id ),
  CONSTRAINT grouped_by_having_id FOREIGN KEY (having_id)
      REFERENCES dashboard_app."having" (having_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS dashboard_app.where_group
(
  where_group_id serial NOT NULL,
  operator character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT where_group_pk PRIMARY KEY (where_group_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.where_group_condition
(
  where_group_id integer NOT NULL,
  condition_id integer NOT NULL,
  CONSTRAINT where_group_condition_pk PRIMARY KEY (where_group_id , condition_id ),
  CONSTRAINT dw_query_condition_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT where_group_condition_where_group_id FOREIGN KEY (where_group_id)
      REFERENCES dashboard_app.where_group (where_group_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.where_group_where_group
(
  where_group_id integer NOT NULL,
  where_group2_id integer NOT NULL,
  CONSTRAINT where_group_where_group_pk PRIMARY KEY (where_group_id , where_group2_id ),
  CONSTRAINT where_group_where_group_where_group2_id FOREIGN KEY (where_group2_id)
      REFERENCES dashboard_app.where_group (where_group_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT where_group_where_group_where_group_id FOREIGN KEY (where_group_id)
      REFERENCES dashboard_app.where_group (where_group_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.dw_query
(
  dw_query_id serial NOT NULL,
  combination_id integer,
  grouped_by_id integer,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  where_group_id integer,
  has_period_condition boolean,
  CONSTRAINT dw_query_pk PRIMARY KEY (dw_query_id ),
  CONSTRAINT dw_query_grouped_by_id FOREIGN KEY (grouped_by_id)
      REFERENCES dashboard_app.grouped_by (grouped_by_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT dw_query_where_group_id FOREIGN KEY (where_group_id)
      REFERENCES dashboard_app.where_group (where_group_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS dashboard_app.combination
(
  combination_id serial NOT NULL,
  dw_query_id integer NOT NULL,
  type character varying,
  foreign_key character varying,
  referenced_key character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT combination_pk PRIMARY KEY (combination_id ),
  CONSTRAINT combination_dw_query_id FOREIGN KEY (dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE dashboard_app.dw_query
  ADD CONSTRAINT dw_query_combination_id FOREIGN KEY (combination_id)
      REFERENCES dashboard_app.combination (combination_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS dashboard_app.complex_dw_query
(
  complex_dw_query_id serial NOT NULL,
  dw_query_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  dimension character varying,
  dimension_key character varying,
  fact_key character varying,
  CONSTRAINT complex_dw_query_pk PRIMARY KEY (complex_dw_query_id ),
  CONSTRAINT complex_dw_query_dw_query_id FOREIGN KEY (dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.dw_query_condition
(
  dw_query_id integer NOT NULL,
  condition_id integer NOT NULL,
  CONSTRAINT dw_query_condition_pk PRIMARY KEY (dw_query_id , condition_id ),
  CONSTRAINT dw_query_condition_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT dw_query_condition_dw_query_id FOREIGN KEY (dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.dw_query_select_column
(
  select_column_id integer NOT NULL,
  dw_query_id integer NOT NULL,
  CONSTRAINT dw_query_select_column_pk PRIMARY KEY (select_column_id , dw_query_id ),
  CONSTRAINT dw_query_select_column_dw_query_id FOREIGN KEY (dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT dw_query_select_column_select_column_id FOREIGN KEY (select_column_id)
      REFERENCES dashboard_app.select_column (select_column_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.fact
(
  fact_id serial NOT NULL,
  table_id integer NOT NULL,
  combine_type character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT fact_pk PRIMARY KEY (fact_id ),
  CONSTRAINT fact_table_id FOREIGN KEY (table_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS dashboard_app.fact_complex_dw_query
(
  complex_dw_query_id integer NOT NULL,
  fact_id integer NOT NULL,
  CONSTRAINT complex_dw_query_fact_pk PRIMARY KEY (complex_dw_query_id , fact_id ),
  CONSTRAINT complex_dw_query_fact_complex_dw_query_id FOREIGN KEY (complex_dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT complex_dw_query_fact_fact_id FOREIGN KEY (fact_id)
      REFERENCES dashboard_app.fact (fact_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.form
(
  form_id serial NOT NULL,
  table_name character varying(50) NOT NULL,
  display_name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT form_pk PRIMARY KEY (form_id ),
  CONSTRAINT form_display_name_uk UNIQUE (display_name ),
  CONSTRAINT form_table_name_uk UNIQUE (table_name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.field
(
  field_id serial NOT NULL,
  form_id integer NOT NULL,
  name character varying(100) NOT NULL,
  type character varying(50) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT field_pk PRIMARY KEY (field_id ),
  CONSTRAINT field_form_id FOREIGN KEY (form_id)
      REFERENCES dashboard_app.form (form_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT field_uk UNIQUE (form_id , name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.computed_field
(
  computed_field_id serial NOT NULL,
  form_id integer NOT NULL,
  name character varying(100) NOT NULL,
  type character varying(50) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT computed_field_pk PRIMARY KEY (computed_field_id ),
  CONSTRAINT computed_field_form_id_fk FOREIGN KEY (form_id)
      REFERENCES dashboard_app.form (form_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT computed_field_name_uk UNIQUE (form_id , name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.field_comparison
(
  field_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  field_2_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT field_comparison_pk PRIMARY KEY (field_comparison_id ),
  CONSTRAINT field_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT field_comparison_field_2_id FOREIGN KEY (field_2_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.operator_type
(
  operator_type_id serial NOT NULL,
  name character varying(10) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT operator_type_pk PRIMARY KEY (operator_type_id ),
  CONSTRAINT operator_type_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.field_operation
(
  field_operation_id serial NOT NULL,
  field_1_id integer NOT NULL,
  field_2_id integer,
  operator_type_id integer,
  computed_field_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT field_operation_pk PRIMARY KEY (field_operation_id ),
  CONSTRAINT field_operation_computed_field_id FOREIGN KEY (computed_field_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT field_operation_field_1_id FOREIGN KEY (field_1_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT field_operation_field_2_id FOREIGN KEY (field_2_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT field_operation_operator_type_id FOREIGN KEY (operator_type_id)
      REFERENCES dashboard_app.operator_type (operator_type_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT field_operation_uk UNIQUE (field_1_id , field_2_id , operator_type_id , computed_field_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.date_diff_comparison
(
  date_diff_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  field_2_id integer NOT NULL,
  value integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT date_diff_comparison_pk PRIMARY KEY (date_diff_comparison_id ),
  CONSTRAINT date_diff_comparison_field_2_id FOREIGN KEY (field_2_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT field_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.frequency
(
  frequency_id serial NOT NULL,
  frequency_name character varying(10),
  parent_frequency_id integer,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT frequency_pk PRIMARY KEY (frequency_id ),
  CONSTRAINT frequency_fk FOREIGN KEY (parent_frequency_id)
      REFERENCES dashboard_app.frequency (frequency_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT frequency_uq UNIQUE (frequency_name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.language
(
  language_id serial NOT NULL,
  code character varying(2) NOT NULL,
  name character varying(50) NOT NULL,
  defined boolean NOT NULL DEFAULT false,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT language_pk PRIMARY KEY (language_id ),
  CONSTRAINT language_code_uk UNIQUE (code ),
  CONSTRAINT language_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.level
(
  level_id serial NOT NULL,
  parent_level_id integer,
  name character varying(100) NOT NULL,
  hierarchy_depth integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT level_pk PRIMARY KEY (level_id ),
  CONSTRAINT level_parent_level_id_fk FOREIGN KEY (parent_level_id)
      REFERENCES dashboard_app.level (level_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT level_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.area
(
  area_id serial NOT NULL,
  level_id integer NOT NULL,
  parent_area_id integer,
  name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT area_pk PRIMARY KEY (area_id ),
  CONSTRAINT area_level_id_fk FOREIGN KEY (level_id)
      REFERENCES dashboard_app.level (level_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT area_parent_area_id_fk FOREIGN KEY (parent_area_id)
      REFERENCES dashboard_app.area (area_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT area_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.care_user
(
  user_id serial NOT NULL,
  username character varying(100) NOT NULL,
  password character varying(40) NOT NULL,
  email character varying(120),
  area_id integer NOT NULL,
  salt character varying(40) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  default_language_id integer NOT NULL DEFAULT 1,
  CONSTRAINT user_pk PRIMARY KEY (user_id ),
  CONSTRAINT care_user_area_id_fk FOREIGN KEY (area_id)
      REFERENCES dashboard_app.area (area_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT care_user_default_language_id_fk FOREIGN KEY (default_language_id)
      REFERENCES dashboard_app.language (language_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET DEFAULT,
  CONSTRAINT user_username_uk UNIQUE (username )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator
(
  indicator_id serial NOT NULL,
  area_id integer NOT NULL,
  frequency_id integer NOT NULL,
  name character varying NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  trend bigint,
  denominator_id integer,
  numerator_id integer NOT NULL,
  user_id integer,
  is_computed boolean DEFAULT false,
  is_additive boolean DEFAULT false,
  CONSTRAINT indicator_pk PRIMARY KEY (indicator_id ),
  CONSTRAINT frequency_fk FOREIGN KEY (frequency_id)
      REFERENCES dashboard_app.frequency (frequency_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT indicator_area_id_fk FOREIGN KEY (area_id)
      REFERENCES dashboard_app.area (area_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT indicator_denominator_id FOREIGN KEY (denominator_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT indicator_numerator_id FOREIGN KEY (numerator_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT indicator_user_id FOREIGN KEY (user_id)
      REFERENCES dashboard_app.care_user (user_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT indicator_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator_category
(
  indicator_category_id serial NOT NULL,
  dashboard_id integer NOT NULL,
  name character varying(100) NOT NULL,
  short_code character varying(4) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT indicator_category_pk PRIMARY KEY (indicator_category_id ),
  CONSTRAINT indicator_category_dashboard_id_fk FOREIGN KEY (dashboard_id)
      REFERENCES dashboard_app.dashboard (dashboard_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT indicator_category_name_uk UNIQUE (name ),
  CONSTRAINT short_code_uk UNIQUE (short_code )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator_indicator_category
(
  indicator_id integer NOT NULL,
  indicator_category_id integer NOT NULL,
  CONSTRAINT indicator_indicator_category_indicator_category_id_fk FOREIGN KEY (indicator_category_id)
      REFERENCES dashboard_app.indicator_category (indicator_category_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT indicator_indicator_category_indicator_id_fk FOREIGN KEY (indicator_id)
      REFERENCES dashboard_app.indicator (indicator_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT indicator_indicator_category_uk UNIQUE (indicator_id , indicator_category_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.report_type
(
  report_type_id serial NOT NULL,
  name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT report_type_pk PRIMARY KEY (report_type_id ),
  CONSTRAINT report_type_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.report
(
  report_id serial NOT NULL,
  indicator_id integer NOT NULL,
  report_type_id integer NOT NULL,
  label_x character varying(30),
  label_y character varying(30),
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT report_pk PRIMARY KEY (report_id ),
  CONSTRAINT indicator_id_fk FOREIGN KEY (indicator_id)
      REFERENCES dashboard_app.indicator (indicator_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT report_type_id_fk FOREIGN KEY (report_type_id)
      REFERENCES dashboard_app.report_type (report_type_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator_report
(
  indicator_id integer NOT NULL,
  report_id integer NOT NULL,
  CONSTRAINT indicator_report_indicator_id FOREIGN KEY (indicator_id)
      REFERENCES dashboard_app.indicator (indicator_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT indicator_report_report_id_fk FOREIGN KEY (report_id)
      REFERENCES dashboard_app.report (report_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT indicator_report_uk UNIQUE (indicator_id , report_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.role
(
  role_id serial NOT NULL,
  name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT role_pk PRIMARY KEY (role_id ),
  CONSTRAINT role_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.permission
(
  permission_id serial NOT NULL,
  name character varying(100) NOT NULL,
  display_name character varying(100) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT permission_pk PRIMARY KEY (permission_id ),
  CONSTRAINT permission_display_name_uk UNIQUE (display_name ),
  CONSTRAINT permission_name_uk UNIQUE (name )
);

CREATE TABLE IF NOT EXISTS dashboard_app.role_permission
(
  role_id integer NOT NULL,
  permission_id integer NOT NULL,
  CONSTRAINT role_permission_permission_id_fk FOREIGN KEY (permission_id)
      REFERENCES dashboard_app.permission (permission_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_permission_role_id_fk FOREIGN KEY (role_id)
      REFERENCES dashboard_app.role (role_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT role_permission_uk UNIQUE (role_id , permission_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator_role
(
  indicator_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT indicator_role_indicator_id_fk FOREIGN KEY (indicator_id)
      REFERENCES dashboard_app.indicator (indicator_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT indicator_role_role_id_fk FOREIGN KEY (role_id)
      REFERENCES dashboard_app.role (role_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT indicator_user_uk UNIQUE (indicator_id , role_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator_value
(
  indicator_value_id serial NOT NULL,
  indicator_id integer NOT NULL,
  area_id integer NOT NULL,
  value numeric(19,6) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  numerator numeric(19,6),
  denominator numeric(19,6),
  frequency_id integer NOT NULL,
  date timestamp with time zone,
  CONSTRAINT indicator_value_pk PRIMARY KEY (indicator_value_id ),
  CONSTRAINT indicator_value_area_id_fk FOREIGN KEY (area_id)
      REFERENCES dashboard_app.area (area_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT indicator_value_indicator_id_fk FOREIGN KEY (indicator_id)
      REFERENCES dashboard_app.indicator (indicator_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS dashboard_app.care_user_role
(
  user_id integer NOT NULL,
  role_id integer NOT NULL,
  CONSTRAINT user_role_role_id_fk FOREIGN KEY (role_id)
      REFERENCES dashboard_app.role (role_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_role_user_id_fk FOREIGN KEY (user_id)
      REFERENCES dashboard_app.care_user (user_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_role_uk UNIQUE (user_id , role_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.period_comparison
(
  period_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  column_name character varying NOT NULL,
  start_offset integer,
  table_name character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT period_comparison_pk PRIMARY KEY (period_comparison_id ),
  CONSTRAINT period_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.report_dashboard
(
  report_id integer NOT NULL,
  dashboard_id integer NOT NULL,
  CONSTRAINT report_dashboard_dashboard_id_fk FOREIGN KEY (dashboard_id)
      REFERENCES dashboard_app.dashboard (dashboard_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT report_dashboard_report_fk FOREIGN KEY (report_id)
      REFERENCES dashboard_app.report (report_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT report_dashboard_uk UNIQUE (report_id , dashboard_id )
);

CREATE TABLE IF NOT EXISTS dashboard_app.simple_dw_query
(
  simple_dw_query_id serial NOT NULL,
  dw_query_id integer NOT NULL,
  table_name character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT simple_dw_query_pk PRIMARY KEY (simple_dw_query_id ),
  CONSTRAINT simple_dw_query_dw_query_id FOREIGN KEY (dw_query_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.value_comparison
(
  value_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  value character varying(50) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT value_comparison_pk PRIMARY KEY (value_comparison_id ),
  CONSTRAINT field_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);
