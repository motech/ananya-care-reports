SET client_encoding = 'UTF8';

DROP AGGREGATE IF EXISTS mode(anyelement);
DROP AGGREGATE IF EXISTS median(numeric);

CREATE OR REPLACE FUNCTION _final_median(numeric[])
RETURNS numeric AS
$$
    SELECT AVG(val)
    FROM (
        SELECT val
        FROM unnest($1) val
        ORDER BY 1
        LIMIT  2 - MOD(array_upper($1, 1), 2)
        OFFSET CEIL(array_upper($1, 1) / 2.0) - 1
    ) sub
$$
LANGUAGE 'sql' IMMUTABLE;

CREATE AGGREGATE median(numeric) (
    SFUNC=array_append,
    STYPE=numeric[],
    FINALFUNC=_final_median,
    INITCOND='{}'
);

CREATE OR REPLACE FUNCTION _final_mode(anyarray)
RETURNS anyelement AS
$$
    SELECT a
    FROM unnest($1) a
    GROUP BY 1
    ORDER BY COUNT(1) DESC, 1
    LIMIT 1
$$
LANGUAGE 'sql' IMMUTABLE;

CREATE AGGREGATE mode(anyelement) (
    SFUNC=array_append,
    STYPE=anyarray,
    FINALFUNC=_final_mode,
    INITCOND='{}'
);

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

CREATE TABLE IF NOT EXISTS dashboard_app.condition
(
  condition_id serial NOT NULL,
  field_1_id integer,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT condition_pk PRIMARY KEY (condition_id )
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


CREATE TABLE IF NOT EXISTS dashboard_app.computed_field
(
  computed_field_id serial NOT NULL,
  form_id integer NOT NULL,
  origin boolean DEFAULT true,
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

CREATE TABLE IF NOT EXISTS dashboard_app.select_column
(
  select_column_id serial NOT NULL,
  computed_field_id integer,
  function character varying,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  table_name character varying,
  null_value character varying,
  dw_query_id integer not null,
  CONSTRAINT select_column_pk PRIMARY KEY (select_column_id),
  CONSTRAINT computed_field_fk FOREIGN KEY (computed_field_id)
      REFERENCES dashboard_app.computed_field(computed_field_id)
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

CREATE TABLE IF NOT EXISTS dashboard_app.dw_query
(
  dw_query_id serial NOT NULL,
  combination_id integer,
  grouped_by_id integer,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  where_group_id integer,
  has_period_condition boolean,
  table_name character varying(100),
  parent_id integer,
  name character varying(100),
  CONSTRAINT dw_query_pk PRIMARY KEY (dw_query_id ),
  CONSTRAINT dw_query_grouped_by_id FOREIGN KEY (grouped_by_id)
      REFERENCES dashboard_app.grouped_by (grouped_by_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT dw_query_where_group_id FOREIGN KEY (where_group_id)
      REFERENCES dashboard_app.where_group (where_group_id) MATCH FULL
      ON UPDATE SET NULL ON DELETE SET NULL,
  CONSTRAINT dw_query_parent_id FOREIGN KEY (parent_id)
      REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE,
  CONSTRAINT dw_query_uk UNIQUE (name)
);

ALTER TABLE dashboard_app.select_column
    ADD CONSTRAINT select_column_dw_query_id FOREIGN KEY (dw_query_id)
        REFERENCES dashboard_app.dw_query (dw_query_id) MATCH FULL
        ON UPDATE CASCADE ON DELETE CASCADE NOT DEFERRABLE;

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

CREATE TABLE IF NOT EXISTS dashboard_app.field_comparison
(
  field_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  field_2_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  comparison_symbol_id integer NOT NULL,
  offset_1 integer DEFAULT 0,
  offset_2 integer DEFAULT 0,
  CONSTRAINT field_comparison_pk PRIMARY KEY (field_comparison_id ),
  CONSTRAINT field_comparison_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
      REFERENCES dashboard_app.comparison_symbol (comparison_symbol_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
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
  comparison_symbol_id integer NOT NULL,
  offset_1 integer DEFAULT 0,
  offset_2 integer DEFAULT 0,
  CONSTRAINT date_diff_comparison_pk PRIMARY KEY (date_diff_comparison_id ),
  CONSTRAINT date_diff_comparison_field_2_id FOREIGN KEY (field_2_id)
      REFERENCES dashboard_app.computed_field (computed_field_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT field_comparison_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
      REFERENCES dashboard_app.comparison_symbol (comparison_symbol_id) MATCH FULL
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
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS dashboard_app.care_user
(
  user_id serial NOT NULL,
  username character varying(100) NOT NULL,
  area_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  default_language_id integer NOT NULL DEFAULT 1,
  default_dashboard_id integer DEFAULT 1,
  CONSTRAINT user_pk PRIMARY KEY (user_id ),
  CONSTRAINT care_user_area_id_fk FOREIGN KEY (area_id)
      REFERENCES dashboard_app.area (area_id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT care_user_default_language_id_fk FOREIGN KEY (default_language_id)
      REFERENCES dashboard_app.language (language_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE SET DEFAULT,
  CONSTRAINT default_dashboard_id_fk FOREIGN KEY (default_dashboard_id)
      REFERENCES dashboard_app.dashboard (dashboard_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT user_username_uk UNIQUE (username )
);

CREATE TABLE IF NOT EXISTS dashboard_app.indicator
(
  indicator_id serial NOT NULL,
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
  is_categorized boolean DEFAULT false,
  area_level_id bigint NOT NULL DEFAULT 1::bigint,
  CONSTRAINT indicator_pk PRIMARY KEY (indicator_id ),
  CONSTRAINT fk_indicator_area_level FOREIGN KEY (area_level_id)
      REFERENCES dashboard_app.level (level_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT frequency_fk FOREIGN KEY (frequency_id)
      REFERENCES dashboard_app.frequency (frequency_id) MATCH SIMPLE
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
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT permission_pk PRIMARY KEY (permission_id ),
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
  indicator_value_id bigserial NOT NULL,
  indicator_id integer NOT NULL,
  area_id integer NOT NULL,
  value numeric(19,6) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  numerator numeric(19,6),
  denominator numeric(19,6),
  frequency_id integer NOT NULL,
  category varchar(50),
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

CREATE TABLE IF NOT EXISTS dashboard_app.value_comparison
(
  value_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  value character varying(50) NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  comparison_symbol_id integer NOT NULL DEFAULT 1,
  CONSTRAINT value_comparison_pk PRIMARY KEY (value_comparison_id ),
  CONSTRAINT field_comparison_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
      REFERENCES dashboard_app.comparison_symbol (comparison_symbol_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT field_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.date_range_comparison
(
  date_range_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  date_1 date NOT NULL,
  date_2 date NOT NULL,
  offset_1 integer DEFAULT 0,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT date_range_comparison_pk PRIMARY KEY (date_range_comparison_id ),
  CONSTRAINT date_range_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.date_value_comparison
(
  date_value_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  comparison_symbol_id integer NOT NULL,
  value date NOT NULL,
  offset_1 integer DEFAULT 0,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT date_value_comparison_pk PRIMARY KEY (date_value_comparison_id ),
  CONSTRAINT date_value_comparison_comparison_symbol_id FOREIGN KEY (comparison_symbol_id)
      REFERENCES dashboard_app.comparison_symbol (comparison_symbol_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT date_value_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.enum_range_comparison
(
  enum_range_comparison_id serial NOT NULL,
  condition_id integer NOT NULL,
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT enum_range_comparison_pk PRIMARY KEY (enum_range_comparison_id ),
  CONSTRAINT date_value_comparison_condition_id FOREIGN KEY (condition_id)
      REFERENCES dashboard_app.condition (condition_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dashboard_app.enum_range_comparison_value
(
  enum_range_comparison_value_id serial NOT NULL,
  enum_range_comparison_id integer NOT NULL,
  value varchar(50),
  creation_date timestamp without time zone,
  modification_date timestamp without time zone,
  CONSTRAINT enum_range_comparison_value_pk PRIMARY KEY (enum_range_comparison_value_id ),
  CONSTRAINT enum_range_comparison_value_enum_range_comparison FOREIGN KEY (enum_range_comparison_id)
      REFERENCES dashboard_app.enum_range_comparison (enum_range_comparison_id) MATCH FULL
      ON UPDATE CASCADE ON DELETE CASCADE
);