--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: carereporting; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA carereporting;


ALTER SCHEMA carereporting OWNER TO postgres;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = carereporting, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: abort_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE abort_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abortion_type character varying(255),
    close character varying(20),
    birth_status character varying(255),
    date_aborted date
);


ALTER TABLE carereporting.abort_form OWNER TO postgres;

--
-- Name: abort_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE abort_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.abort_form_id_seq OWNER TO postgres;

--
-- Name: abort_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE abort_form_id_seq OWNED BY abort_form.id;


--
-- Name: bp_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE bp_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    anc_latest_date date,
    anc_latest_num integer,
    anc1_abdominal_exam character varying(255),
    anc1_abnormalities boolean,
    anc1_blood_pressure character varying(255),
    anc1_date date,
    anc1_facility character varying(255),
    anc1_details boolean,
    anc2_abdominal_exam character varying(255),
    anc2_abnormalities boolean,
    anc2_blood_pressure character varying(255),
    anc2_date date,
    anc2_facility character varying(255),
    anc2_details boolean,
    anc3_abdominal_exam character varying(255),
    anc3_abnormalities boolean,
    anc3_blood_pressure character varying(255),
    anc3_date date,
    anc3_facility character varying(255),
    anc3_details boolean,
    anc4_abdominal_exam character varying(255),
    anc4_abnormalities boolean,
    anc4_blood_pressure character varying(255),
    anc4_date date,
    anc4_facility character varying(255),
    anc4_details boolean,
    counsel_ifa boolean,
    counsel_tt boolean,
    eating_extra boolean,
    ifa_tablets_issued smallint,
    reason_no_ifa character varying(255),
    received_tt1 boolean,
    received_tt2 boolean,
    resting boolean,
    tt1_date date,
    tt2_date date,
    tt_booster boolean,
    tt_booster_date date,
    using_ifa boolean,
    sba boolean,
    sba_phone boolean,
    accompany boolean,
    care_of_home boolean,
    clean_cloth boolean,
    cord_care boolean,
    counsel_home_delivery boolean,
    counsel_institutional boolean,
    counsel_preparation boolean,
    danger_institution boolean,
    danger_number boolean,
    has_danger_signs boolean,
    immediate_breastfeeding boolean,
    inform_danger_signs boolean,
    materials boolean,
    maternal_danger_signs boolean,
    now_institutional boolean,
    phone_vehicle boolean,
    play_birth_preparedness_vid boolean,
    play_cord_care_vid boolean,
    saving_money boolean,
    skin_to_skin boolean,
    vehicle boolean,
    wrapping boolean,
    bp_visit_num smallint,
    anc_1_date date,
    anc_2_date date,
    anc_3_date date,
    anc_4_date date,
    couple_interested character varying(255),
    date_bp_1 date,
    date_bp_2 date,
    date_bp_3 date,
    date_last_visit date,
    date_next_bp date,
    delivery_type character varying(255),
    ifa_tablets smallint,
    ifa_tablets_100 date,
    last_visit_type character varying(20),
    maternal_emergency boolean,
    maternal_emergency_number boolean,
    tt_1_date date,
    tt_2_date date,
    conceive boolean,
    del_fup integer,
    avail_immediate boolean,
    counsel_accessible boolean,
    counsel_benefits boolean,
    counsel_disqualification boolean,
    counsel_institution boolean,
    counsel_methods boolean,
    counsel_nearest boolean,
    counsel_options boolean,
    counsel_stay boolean,
    immediate_appropriate boolean,
    institution_immediate boolean,
    postpone_conception boolean,
    risk_of_preg boolean,
    spacing_methods boolean,
    stop_children character varying(15),
    ifa_tablets_total smallint,
    nextvisittype character varying(20),
    play_family_planning_vid boolean,
    postponing character varying(15)
);


ALTER TABLE carereporting.bp_form OWNER TO postgres;

--
-- Name: bp_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE bp_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.bp_form_id_seq OWNER TO postgres;

--
-- Name: bp_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE bp_form_id_seq OWNED BY bp_form.id;


--
-- Name: cf_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE cf_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    amount_good boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    bcg_date date,
    case_name character varying(255),
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    dal boolean,
    eaten_cereal boolean,
    egg boolean,
    fish boolean,
    meat boolean,
    milk_curd boolean,
    more_feeding_less_six boolean,
    name_update boolean,
    new_name character varying(255),
    number_good boolean,
    oil_ghee boolean,
    recent_fever boolean,
    treated_less_six boolean,
    baby_dpt_booster boolean,
    baby_je boolean,
    baby_measles_booster boolean,
    baby_opv_booster boolean,
    baby_vita2 boolean,
    baby_vita3 boolean,
    date_je date,
    date_measles_booster date,
    dpt_booster_date date,
    opv_booster_date date,
    vit_a_3_date date,
    vit_a_2_date date,
    close character varying(255)
);


ALTER TABLE carereporting.cf_child_form OWNER TO postgres;

--
-- Name: cf_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE cf_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.cf_child_form_id_seq OWNER TO postgres;

--
-- Name: cf_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE cf_child_form_id_seq OWNED BY cf_child_form.id;


--
-- Name: cf_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE cf_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    date_cf_1 date,
    date_cf_2 date,
    date_cf_3 date,
    date_cf_4 date,
    date_cf_5 date,
    date_cf_6 date,
    date_last_visit date,
    date_next_cf date,
    last_visit_type character varying(20),
    cf_visit_num smallint,
    children integer,
    num_children smallint,
    play_comp_feeding_vid boolean,
    lastvisit boolean,
    date_cf_7 date,
    confirm_close boolean,
    close boolean
);


ALTER TABLE carereporting.cf_mother_form OWNER TO postgres;

--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE cf_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.cf_mother_form_id_seq OWNER TO postgres;

--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE cf_mother_form_id_seq OWNED BY cf_mother_form.id;


--
-- Name: child_case; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE child_case (
    id integer NOT NULL,
    case_id character varying(36),
    case_name character varying(255),
    date_modified timestamp with time zone,
    server_date_modified timestamp with time zone,
    server_date_opened timestamp with time zone,
    mother_id integer,
    case_type character varying(255),
    owner_id integer,
    user_id integer,
    baby_measles boolean,
    bcg_date date,
    birth_status character varying(255),
    dob date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    gender character varying(15),
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    child_alive boolean,
    dpt_booster_date date,
    opv_booster_date date,
    date_je date,
    date_measles_booster date,
    baby_weight numeric,
    name character varying(255),
    term character varying(50),
    time_of_birth timestamp with time zone,
    vit_a_2_date date,
    vit_a_3_date date,
    closed boolean,
    date_closed date
);


ALTER TABLE carereporting.child_case OWNER TO postgres;

--
-- Name: child_case_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE child_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.child_case_id_seq OWNER TO postgres;

--
-- Name: child_case_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE child_case_id_seq OWNED BY child_case.id;


--
-- Name: close_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE close_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    child_alive boolean,
    close_child boolean,
    confirm_close boolean,
    date_death date,
    died boolean,
    died_village boolean,
    dupe_reg boolean,
    finished_continuum boolean,
    site_death character varying(255),
    place_death character varying(255)
);


ALTER TABLE carereporting.close_child_form OWNER TO postgres;

--
-- Name: close_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE close_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.close_child_form_id_seq OWNER TO postgres;

--
-- Name: close_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE close_child_form_id_seq OWNED BY close_child_form.id;


--
-- Name: close_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE close_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    children integer,
    close_mother boolean,
    confirm_close boolean,
    death_village boolean,
    died_village character varying(255),
    dupe_reg boolean,
    finished_continuum boolean,
    num_children smallint,
    mother_alive character varying(20),
    moved boolean,
    migrated boolean,
    date_learned date,
    date_left date,
    migration_note boolean,
    died boolean,
    date_death date,
    site_death character varying(255)
);


ALTER TABLE carereporting.close_mother_form OWNER TO postgres;

--
-- Name: close_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE close_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.close_mother_form_id_seq OWNER TO postgres;

--
-- Name: close_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE close_mother_form_id_seq OWNED BY close_mother_form.id;


--
-- Name: death_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE death_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    child_alive boolean,
    child_died_village boolean,
    child_place_death character varying(255),
    child_site_death character varying(255),
    chld_date_death date
);


ALTER TABLE carereporting.death_child_form OWNER TO postgres;

--
-- Name: death_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE death_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.death_child_form_id_seq OWNER TO postgres;

--
-- Name: death_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE death_child_form_id_seq OWNED BY death_child_form.id;


--
-- Name: death_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE death_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    mother_alive boolean,
    status character varying(255),
    cast_num_children smallint,
    children integer,
    date_death date,
    death_village boolean,
    num_children smallint,
    place_death character varying(255),
    site_death character varying(255)
);


ALTER TABLE carereporting.death_mother_form OWNER TO postgres;

--
-- Name: death_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE death_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.death_mother_form_id_seq OWNER TO postgres;

--
-- Name: death_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE death_mother_form_id_seq OWNED BY death_mother_form.id;


--
-- Name: delivery_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE delivery_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abnormalities boolean,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_hep_b_0 boolean,
    baby_opv0 boolean,
    breastfed_hour boolean,
    close character varying(255),
    case_name character varying(255),
    case_type character varying(255),
    baby_weight boolean,
    bcg_date date,
    birth_status character varying(255),
    dob date,
    gender character varying(25),
    hep_b_0_date date,
    opv_0_date date,
    term character varying(50),
    time_of_birth timestamp with time zone,
    child_alive boolean,
    child_breathing character varying(25),
    child_cried boolean,
    child_died_village boolean,
    child_have_a_name boolean,
    child_heartbeats character varying(25),
    child_movement boolean,
    child_name character varying(25),
    child_place_death character varying(25),
    child_site_death character varying(50),
    chld_date_death date,
    cord_applied boolean,
    cord_cut boolean,
    cord_tied boolean,
    date_first_weight date,
    date_time_feed date,
    first_weight numeric,
    skin_care boolean,
    what_applied character varying(255),
    wrapped_dried boolean
);


ALTER TABLE carereporting.delivery_child_form OWNER TO postgres;

--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE delivery_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.delivery_child_form_id_seq OWNER TO postgres;

--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE delivery_child_form_id_seq OWNED BY delivery_child_form.id;


--
-- Name: delivery_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE delivery_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    ppiud boolean,
    pptl boolean,
    abd_pain boolean,
    add date,
    close character varying(255),
    birth_place character varying(25),
    date_del_fu date,
    date_last_visit date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    family_planning_type character varying(50),
    last_visit_type character varying(255),
    mother_alive boolean,
    term character varying(50),
    cast_num_children smallint,
    complications boolean,
    date_death date,
    death_village boolean,
    delivery_nature character varying(50),
    fever boolean,
    has_delivered boolean,
    how_many_children smallint,
    ifa_tablets_given boolean,
    in_district boolean,
    jsy_money boolean,
    nextvisittype character varying(255),
    notified date,
    num_children smallint,
    other_conditions boolean,
    other_district character varying(255),
    other_village character varying(255),
    pain_urine boolean,
    place_death character varying(255),
    post_postpartum_fp boolean,
    safe boolean,
    site_death character varying(255),
    vaginal_discharge boolean,
    where_born character varying(50),
    which_hospital character varying(255),
    which_village character varying(255)
);


ALTER TABLE carereporting.delivery_mother_form OWNER TO postgres;

--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE delivery_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.delivery_mother_form_id_seq OWNER TO postgres;

--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE delivery_mother_form_id_seq OWNED BY delivery_mother_form.id;


--
-- Name: ebf_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE ebf_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    at_night boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    bcg_date date,
    breastfeeding boolean,
    case_name character varying(255),
    child_name character varying(255),
    counsel_adequate_bf boolean,
    counsel_only_milk boolean,
    counsel_stop_bottle boolean,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    eating boolean,
    emptying boolean,
    feeding_bottle boolean,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    more_feeding_less_six boolean,
    name_update boolean,
    not_breasfeeding character varying(255),
    on_demand boolean,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    recent_fever boolean,
    tea_other boolean,
    treated_less_six boolean,
    water_or_milk boolean
);


ALTER TABLE carereporting.ebf_child_form OWNER TO postgres;

--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE ebf_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.ebf_child_form_id_seq OWNER TO postgres;

--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE ebf_child_form_id_seq OWNED BY ebf_child_form.id;


--
-- Name: ebf_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE ebf_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    addval date,
    adopt_immediately boolean,
    ask_ppiud boolean,
    aware_of_failure boolean,
    bleeding boolean,
    children character varying(255),
    complications boolean,
    condoms boolean,
    counsel_follow_up_ppiud boolean,
    counsel_follow_up_pptl boolean,
    counsel_menstrual_cycle boolean,
    counsel_methods boolean,
    counsel_ppfp boolean,
    counsel_time_iud boolean,
    date_eb_1 date,
    date_eb_2 date,
    date_eb_3 date,
    date_eb_4 date,
    date_eb_5 date,
    date_eb_6 date,
    date_iud_adopted date,
    date_last_inj date,
    date_last_visit date,
    date_next_cf date,
    date_next_eb date,
    discharge boolean,
    distension boolean,
    eb_visit_num smallint,
    family_planning_type character varying(255),
    fever boolean,
    have_condoms boolean,
    headaches boolean,
    high_bp boolean,
    inj_menstrual_irregularity boolean,
    injectable boolean,
    intend_to_continue boolean,
    interval_ppfp_interest boolean,
    iud boolean,
    iud_adopted boolean,
    iud_counsel_duration boolean,
    iud_counsel_follow_up boolean,
    iud_counsel_hospital boolean,
    iud_counsel_placement boolean,
    iud_counsel_screening boolean,
    iud_counsel_side_effects boolean,
    last_visit_type character varying(20),
    menstrual_irregularity boolean,
    next_inj_calc date,
    nextvisittype character varying(20),
    num_children smallint,
    ocp boolean,
    ocp_continue boolean,
    ocp_counsel_regularity boolean,
    pain_swelling boolean,
    ppfp_interest boolean,
    ppiud_abdominal_pain boolean,
    ppiud_problems boolean,
    pptl_abdominal_pain boolean,
    pptl_pain_surgery boolean,
    pptl_problems boolean,
    regular_periods boolean,
    tablets_received boolean,
    taken_as_prescribed boolean,
    tl boolean,
    tl_adopted boolean,
    tl_consel_incentives boolean,
    tl_counsel_follow_up boolean,
    tl_counsel_hospital boolean,
    tl_counsel_irreversible boolean,
    tl_counsel_screening boolean,
    tl_counsel_side_effects boolean,
    tl_counsel_timing boolean,
    understand_tablets boolean,
    using_correctly boolean,
    where_replace boolean,
    why_no_ppffp character varying(255),
    within_42 boolean,
    date_tl_adopted date,
    abdominal_pain boolean,
    pain_urination boolean,
    ppiud_bleeding boolean,
    ppiud_discharge boolean,
    ppiud_fever boolean
);


ALTER TABLE carereporting.ebf_mother_form OWNER TO postgres;

--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE ebf_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.ebf_mother_form_id_seq OWNER TO postgres;

--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE ebf_mother_form_id_seq OWNED BY ebf_mother_form.id;


--
-- Name: flw; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE flw (
    id integer NOT NULL,
    flw_id character varying(36),
    default_phone_number character varying(20),
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number_1 character varying(20),
    phone_number_2 character varying(20),
    asset_id character varying(255),
    awc_code character varying(255),
    role character varying(255),
    subcentre character varying(255),
    user_type character varying(255),
    username character varying(255),
    population character varying(255),
    education character varying(255),
    age smallint,
    district character varying(255),
    block character varying(255),
    panchayat character varying(255),
    village character varying(255),
    ward character varying(255),
    caste character varying(255),
    dob date,
    ictcordinator character varying(255),
    remarks text
);


ALTER TABLE carereporting.flw OWNER TO postgres;

--
-- Name: flw_group; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE flw_group (
    id integer NOT NULL,
    group_id character varying(36),
    case_sharing boolean,
    domain character varying(255),
    awc_code character varying(255),
    name character varying(255),
    reporting boolean
);


ALTER TABLE carereporting.flw_group OWNER TO postgres;

--
-- Name: flw_group_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE flw_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.flw_group_id_seq OWNER TO postgres;

--
-- Name: flw_group_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE flw_group_id_seq OWNED BY flw_group.id;


--
-- Name: flw_group_map; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE flw_group_map (
    id integer NOT NULL,
    flw_id integer,
    group_id integer
);


ALTER TABLE carereporting.flw_group_map OWNER TO postgres;

--
-- Name: flw_group_map_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE flw_group_map_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.flw_group_map_id_seq OWNER TO postgres;

--
-- Name: flw_group_map_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE flw_group_map_id_seq OWNED BY flw_group_map.id;


--
-- Name: flw_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE flw_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.flw_id_seq OWNER TO postgres;

--
-- Name: flw_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE flw_id_seq OWNED BY flw.id;


--
-- Name: mi_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE mi_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    date_arrived date,
    date_learned date,
    date_of_delivery date,
    name character varying(255),
    preg_status character varying(255),
    referral_info character varying(255),
    abortion_type character varying(255),
    date_aborted date,
    migrated_status character varying(255)
);


ALTER TABLE carereporting.mi_form OWNER TO postgres;

--
-- Name: mi_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE mi_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.mi_form_id_seq OWNER TO postgres;

--
-- Name: mi_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE mi_form_id_seq OWNED BY mi_form.id;


--
-- Name: mo_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE mo_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    migrate_out_date date,
    migrated_status character varying(255),
    status character varying(255),
    date_learned date,
    date_left date,
    name character varying(255),
    note_given boolean
);


ALTER TABLE carereporting.mo_form OWNER TO postgres;

--
-- Name: mo_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE mo_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.mo_form_id_seq OWNER TO postgres;

--
-- Name: mo_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE mo_form_id_seq OWNED BY mo_form.id;


--
-- Name: mother_case; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE mother_case (
    id integer NOT NULL,
    case_id character varying(36),
    case_name character varying(255),
    case_type character varying(255),
    owner_id integer,
    user_id integer,
    date_modified timestamp with time zone,
    server_date_modified timestamp with time zone,
    server_date_opened timestamp with time zone,
    family_number integer,
    hh_number integer,
    husband_name character varying(255),
    last_visit_type integer,
    mother_alive boolean,
    mother_dob date,
    mother_name character varying(255),
    close boolean,
    case_closed boolean,
    closed_on date,
    add date,
    age smallint,
    birth_place character varying(255),
    complications boolean,
    date_next_bp date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    eats_meat boolean,
    edd date,
    enrolled_in_kilkari boolean,
    family_planning_type character varying(255),
    how_many_children smallint,
    interest_in_kilkari boolean,
    last_preg_tt boolean,
    lmp date,
    mobile_number character varying(20),
    num_boys smallint,
    date_cf_1 date,
    date_cf_2 date,
    date_cf_3 date,
    date_cf_4 date,
    date_cf_5 date,
    date_cf_6 date,
    date_eb_1 date,
    date_eb_2 date,
    date_eb_3 date,
    date_eb_4 date,
    date_eb_5 date,
    date_eb_6 date,
    all_pnc_on_time boolean,
    date_pnc_1 date,
    date_pnc_2 date,
    date_pnc_3 date,
    first_pnc_time character varying(255),
    pnc_1_days_late integer,
    pnc_2_days_late integer,
    pnc_3_days_late integer,
    tt_booster_date date,
    sba boolean,
    sba_phone boolean,
    accompany boolean,
    anc_1_date date,
    anc_2_date date,
    anc_3_date date,
    anc_4_date date,
    clean_cloth boolean,
    couple_interested character varying(15),
    date_bp_1 date,
    date_bp_2 date,
    date_bp_3 date,
    date_last_visit date,
    delivery_type character varying(255),
    ifa_tablets smallint,
    ifa_tablets_100 date,
    materials boolean,
    maternal_emergency boolean,
    maternal_emergency_number boolean,
    phone_vehicle boolean,
    saving_money boolean,
    tt_1_date date,
    tt_2_date date,
    vehicle boolean,
    birth_status character varying(255),
    migrate_out_date date,
    migrated_status character varying(255),
    status character varying(255),
    term character varying(25),
    date_cf_7 date,
    date_del_fu date,
    date_next_reg date,
    institutional boolean,
    dob date,
    closed boolean,
    date_closed date
);


ALTER TABLE carereporting.mother_case OWNER TO postgres;

--
-- Name: mother_case_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE mother_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.mother_case_id_seq OWNER TO postgres;

--
-- Name: mother_case_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE mother_case_id_seq OWNED BY mother_case.id;


--
-- Name: new_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE new_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    age_calc smallint,
    case_name character varying(255),
    case_type character varying(255),
    date_last_visit date,
    date_next_reg date,
    family_number integer,
    hh_number integer,
    husband_name character varying(255),
    last_visit_type character varying(20),
    mother_alive boolean,
    mother_dob date,
    mother_name character varying(255),
    caste character varying(255),
    dob date,
    dob_known boolean,
    full_name character varying(255),
    manual_group integer
);


ALTER TABLE carereporting.new_form OWNER TO postgres;

--
-- Name: new_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE new_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.new_form_id_seq OWNER TO postgres;

--
-- Name: new_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE new_form_id_seq OWNED BY new_form.id;


--
-- Name: pnc_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE pnc_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    able_expressed_milk boolean,
    adequate_support boolean,
    applied_to_stump boolean,
    baby_active boolean,
    breastfeeding_well boolean,
    child_alive boolean,
    child_died_village boolean,
    child_place_death character varying(255),
    child_site_death character varying(255),
    chld_date_death date,
    close character varying(255),
    cord_fallen boolean,
    correct_position boolean,
    counsel_cord_care boolean,
    counsel_exclusive_bf boolean,
    counsel_express_milk boolean,
    counsel_skin boolean,
    cousel_bf_correct boolean,
    demonstrate_expressed boolean,
    demonstrate_skin boolean,
    easy_awake boolean,
    feed_vigour boolean,
    good_latch boolean,
    improvements_bf boolean,
    observed_bf boolean,
    other_milk_to_child boolean,
    second_observation boolean,
    skin_to_skin boolean,
    warm_to_touch boolean,
    what_applied character varying(255),
    wrapped boolean
);


ALTER TABLE carereporting.pnc_child_form OWNER TO postgres;

--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE pnc_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.pnc_child_form_id_seq OWNER TO postgres;

--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE pnc_child_form_id_seq OWNED BY pnc_child_form.id;


--
-- Name: pnc_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE pnc_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abdominal_pain boolean,
    addval date,
    adopt_immediately boolean,
    all_pnc_on_time boolean,
    bleeding boolean,
    children integer,
    complications boolean,
    congested boolean,
    counsel_breast boolean,
    counsel_follow_up_ppiud boolean,
    counsel_follow_up_pptl boolean,
    counsel_increase_food_bf boolean,
    counsel_materal_comp boolean,
    counsel_methods boolean,
    counsel_neonatal_comp boolean,
    counsel_ppfp boolean,
    counsel_time_iud boolean,
    date_death date,
    date_iud_adopted date,
    date_last_visit date,
    date_next_eb date,
    date_next_pnc date,
    date_pnc_1 date,
    date_pnc_2 date,
    date_pnc_3 date,
    date_tl_adopted date,
    death_village boolean,
    discharge boolean,
    distension boolean,
    eating_well boolean,
    family_planning_type character varying(255),
    fever boolean,
    first_pnc_time character varying(255),
    interval_ppfp_interest boolean,
    iud boolean,
    iud_adopted boolean,
    iud_counsel_duration boolean,
    iud_counsel_follow_up boolean,
    iud_counsel_hospital boolean,
    iud_counsel_placement boolean,
    iud_counsel_screening boolean,
    iud_counsel_side_effects boolean,
    last_visit_type character varying(20),
    mother_alive boolean,
    mother_child_alive boolean,
    nextvisittype character varying(20),
    num_children smallint,
    other_issues boolean,
    pain_urination boolean,
    painful_nipples boolean,
    place_death character varying(255),
    pnc_1_days_late integer,
    pnc_2_days_late integer,
    pnc_3_days_late integer,
    pnc_visit_num smallint,
    ppfp_interest boolean,
    ppiud_abdominal_pain boolean,
    ppiud_bleeding boolean,
    ppiud_discharge boolean,
    ppiud_fever boolean,
    ppiud_problems boolean,
    pptl_abdominal_pain boolean,
    pptl_excessive_bleeding boolean,
    pptl_pain_surgery boolean,
    pptl_problems boolean,
    problems_breast boolean,
    safe boolean,
    site_death character varying(255),
    tl boolean,
    tl_adopted boolean,
    tl_consel_incentives boolean,
    tl_counsel_follow_up boolean,
    tl_counsel_hospital boolean,
    tl_counsel_irreversible boolean,
    tl_counsel_screening boolean,
    tl_counsel_side_effects boolean,
    tl_counsel_timing boolean,
    why_no_ppffp character varying(255)
);


ALTER TABLE carereporting.pnc_mother_form OWNER TO postgres;

--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE pnc_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.pnc_mother_form_id_seq OWNER TO postgres;

--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE pnc_mother_form_id_seq OWNED BY pnc_mother_form.id;


--
-- Name: refer_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE refer_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    refer_child boolean
);


ALTER TABLE carereporting.refer_child_form OWNER TO postgres;

--
-- Name: refer_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE refer_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.refer_child_form_id_seq OWNER TO postgres;

--
-- Name: refer_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE refer_child_form_id_seq OWNED BY refer_child_form.id;


--
-- Name: refer_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE refer_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    children integer,
    num_children smallint,
    refer_mother boolean
);


ALTER TABLE carereporting.refer_mother_form OWNER TO postgres;

--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE refer_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.refer_mother_form_id_seq OWNER TO postgres;

--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE refer_mother_form_id_seq OWNED BY refer_mother_form.id;


--
-- Name: registration_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE registration_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abnormalities boolean,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    case_name character varying(255),
    case_type character varying(255),
    bcg_date date,
    birth_status character varying(255),
    dob date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    gender character varying(15),
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    child_have_a_name boolean,
    child_name character varying(255),
    weight numeric
);


ALTER TABLE carereporting.registration_child_form OWNER TO postgres;

--
-- Name: registration_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE registration_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.registration_child_form_id_seq OWNER TO postgres;

--
-- Name: registration_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE registration_child_form_id_seq OWNED BY registration_child_form.id;


--
-- Name: registration_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE registration_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    ppiud boolean,
    pptl boolean,
    abd_pain boolean,
    age_calc smallint,
    age_calc_adj smallint,
    age_est smallint,
    age_est_trigger character varying(10),
    close character varying(10),
    add date,
    age smallint,
    birth_place character varying(255),
    complications boolean,
    date_last_visit date,
    date_next_bp date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    eats_meat boolean,
    edd date,
    enrolled_in_kilkari boolean,
    family_planning_type character varying(255),
    how_many_children smallint,
    interest_in_kilkari boolean,
    last_preg_tt boolean,
    last_visit_type character varying(20),
    lmp date,
    mobile_number character varying(20),
    mother_dob date,
    num_boys smallint,
    status character varying(255),
    child_dob date,
    children integer,
    client_no_register character varying(10),
    client_not_pregnant character varying(10),
    clinical_exam boolean,
    condoms boolean,
    continue_preg boolean,
    delivery_nature character varying(255),
    dob_est character varying(255),
    edd_calc date,
    edd_known boolean,
    education character varying(255),
    fever boolean,
    first_pregnancy boolean,
    gest_age smallint,
    good_to_register boolean,
    in_district boolean,
    injectible boolean,
    is_pregnant boolean,
    iud_used boolean,
    jsy_beneficiary boolean,
    jsy_money boolean,
    last_preg integer,
    last_preg_c_section boolean,
    last_preg_full_term boolean,
    lmp_calc date,
    lmp_known boolean,
    missed_period boolean,
    mobile_number_whose character varying(255),
    nextvisit character varying(10),
    nextvisit_bp character varying(10),
    nextvisittype character varying(20),
    num_children smallint,
    num_girls smallint,
    ocp_used boolean,
    other_conditions character varying(255),
    other_district character varying(255),
    other_village character varying(255),
    pain_urine boolean,
    post_postpartum_fp boolean,
    preg_desired boolean,
    recently_delivered boolean,
    referral_prompt character varying(255),
    resident character varying(255),
    success character varying(10),
    urine_test boolean,
    used_fp boolean,
    vaginal_discharge boolean,
    vegetarian boolean,
    where_born character varying(255),
    which_hospital character varying(255),
    which_village character varying(255)
);


ALTER TABLE carereporting.registration_mother_form OWNER TO postgres;

--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE registration_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.registration_mother_form_id_seq OWNER TO postgres;

--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE registration_mother_form_id_seq OWNED BY registration_mother_form.id;


--
-- Name: schema_version; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE schema_version (
    version character varying(20) NOT NULL,
    description character varying(100),
    type character varying(10) NOT NULL,
    script character varying(200) NOT NULL,
    checksum integer,
    installed_by character varying(30) NOT NULL,
    installed_on timestamp without time zone DEFAULT now(),
    execution_time integer,
    state character varying(15) NOT NULL,
    current_version boolean NOT NULL
);


ALTER TABLE carereporting.schema_version OWNER TO postgres;

--
-- Name: ui_child_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE ui_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    bcg_date date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    dpt_booster_date date,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    opv_booster_date date,
    vit_a_1_date date,
    baby_dpt_booster boolean,
    baby_je boolean,
    baby_measles_booster boolean,
    baby_opv_booster boolean,
    baby_vita2 boolean,
    baby_vita3 boolean,
    date_je date,
    date_measles_booster date,
    vit_a_2_date date,
    vit_a_3_date date
);


ALTER TABLE carereporting.ui_child_form OWNER TO postgres;

--
-- Name: ui_child_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE ui_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.ui_child_form_id_seq OWNER TO postgres;

--
-- Name: ui_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE ui_child_form_id_seq OWNED BY ui_child_form.id;


--
-- Name: ui_mother_form; Type: TABLE; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE TABLE ui_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    details_available boolean,
    tt_1_date date,
    tt_2_date date,
    tt_booster_date date,
    received_tt1 boolean,
    received_tt2 boolean,
    up_to_date character varying(15),
    num_children smallint,
    update_mother boolean,
    tt_booster date
);


ALTER TABLE carereporting.ui_mother_form OWNER TO postgres;

--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE; Schema: carereporting; Owner: postgres
--

CREATE SEQUENCE ui_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE carereporting.ui_mother_form_id_seq OWNER TO postgres;

--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: carereporting; Owner: postgres
--

ALTER SEQUENCE ui_mother_form_id_seq OWNED BY ui_mother_form.id;


SET search_path = public, pg_catalog;

--
-- Name: abort_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE abort_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abortion_type character varying(255),
    close character varying(20),
    birth_status character varying(255),
    date_aborted date
);


ALTER TABLE public.abort_form OWNER TO postgres;

--
-- Name: abort_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE abort_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.abort_form_id_seq OWNER TO postgres;

--
-- Name: abort_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE abort_form_id_seq OWNED BY abort_form.id;


--
-- Name: bp_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE bp_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    anc_latest_date date,
    anc_latest_num integer,
    anc1_abdominal_exam character varying(255),
    anc1_abnormalities boolean,
    anc1_blood_pressure character varying(255),
    anc1_date date,
    anc1_facility character varying(255),
    anc1_details boolean,
    anc2_abdominal_exam character varying(255),
    anc2_abnormalities boolean,
    anc2_blood_pressure character varying(255),
    anc2_date date,
    anc2_facility character varying(255),
    anc2_details boolean,
    anc3_abdominal_exam character varying(255),
    anc3_abnormalities boolean,
    anc3_blood_pressure character varying(255),
    anc3_date date,
    anc3_facility character varying(255),
    anc3_details boolean,
    anc4_abdominal_exam character varying(255),
    anc4_abnormalities boolean,
    anc4_blood_pressure character varying(255),
    anc4_date date,
    anc4_facility character varying(255),
    anc4_details boolean,
    counsel_ifa boolean,
    counsel_tt boolean,
    eating_extra boolean,
    ifa_tablets_issued smallint,
    reason_no_ifa character varying(255),
    received_tt1 boolean,
    received_tt2 boolean,
    resting boolean,
    tt1_date date,
    tt2_date date,
    tt_booster boolean,
    tt_booster_date date,
    using_ifa boolean,
    sba boolean,
    sba_phone boolean,
    accompany boolean,
    care_of_home boolean,
    clean_cloth boolean,
    cord_care boolean,
    counsel_home_delivery boolean,
    counsel_institutional boolean,
    counsel_preparation boolean,
    danger_institution boolean,
    danger_number boolean,
    has_danger_signs boolean,
    immediate_breastfeeding boolean,
    inform_danger_signs boolean,
    materials boolean,
    maternal_danger_signs boolean,
    now_institutional boolean,
    phone_vehicle boolean,
    play_birth_preparedness_vid boolean,
    play_cord_care_vid boolean,
    saving_money boolean,
    skin_to_skin boolean,
    vehicle boolean,
    wrapping boolean,
    bp_visit_num smallint,
    anc_1_date date,
    anc_2_date date,
    anc_3_date date,
    anc_4_date date,
    couple_interested character varying(255),
    date_bp_1 date,
    date_bp_2 date,
    date_bp_3 date,
    date_last_visit date,
    date_next_bp date,
    delivery_type character varying(255),
    ifa_tablets smallint,
    ifa_tablets_100 date,
    last_visit_type character varying(20),
    maternal_emergency boolean,
    maternal_emergency_number boolean,
    tt_1_date date,
    tt_2_date date,
    conceive boolean,
    del_fup integer,
    avail_immediate boolean,
    counsel_accessible boolean,
    counsel_benefits boolean,
    counsel_disqualification boolean,
    counsel_institution boolean,
    counsel_methods boolean,
    counsel_nearest boolean,
    counsel_options boolean,
    counsel_stay boolean,
    immediate_appropriate boolean,
    institution_immediate boolean,
    postpone_conception boolean,
    risk_of_preg boolean,
    spacing_methods boolean,
    stop_children character varying(15),
    ifa_tablets_total smallint,
    nextvisittype character varying(20),
    play_family_planning_vid boolean,
    postponing character varying(15)
);


ALTER TABLE public.bp_form OWNER TO postgres;

--
-- Name: bp_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE bp_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.bp_form_id_seq OWNER TO postgres;

--
-- Name: bp_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE bp_form_id_seq OWNED BY bp_form.id;


--
-- Name: cf_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cf_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    amount_good boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    bcg_date date,
    case_name character varying(255),
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    dal boolean,
    eaten_cereal boolean,
    egg boolean,
    fish boolean,
    meat boolean,
    milk_curd boolean,
    more_feeding_less_six boolean,
    name_update boolean,
    new_name character varying(255),
    number_good boolean,
    oil_ghee boolean,
    recent_fever boolean,
    treated_less_six boolean,
    baby_dpt_booster boolean,
    baby_je boolean,
    baby_measles_booster boolean,
    baby_opv_booster boolean,
    baby_vita2 boolean,
    baby_vita3 boolean,
    date_je date,
    date_measles_booster date,
    dpt_booster_date date,
    opv_booster_date date,
    vit_a_3_date date,
    vit_a_2_date date,
    close character varying(255)
);


ALTER TABLE public.cf_child_form OWNER TO postgres;

--
-- Name: cf_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cf_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cf_child_form_id_seq OWNER TO postgres;

--
-- Name: cf_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cf_child_form_id_seq OWNED BY cf_child_form.id;


--
-- Name: cf_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cf_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    date_cf_1 date,
    date_cf_2 date,
    date_cf_3 date,
    date_cf_4 date,
    date_cf_5 date,
    date_cf_6 date,
    date_last_visit date,
    date_next_cf date,
    last_visit_type character varying(20),
    cf_visit_num smallint,
    children integer,
    num_children smallint,
    play_comp_feeding_vid boolean,
    lastvisit boolean,
    date_cf_7 date,
    confirm_close boolean,
    close boolean
);


ALTER TABLE public.cf_mother_form OWNER TO postgres;

--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE cf_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cf_mother_form_id_seq OWNER TO postgres;

--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE cf_mother_form_id_seq OWNED BY cf_mother_form.id;


--
-- Name: child_case; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE child_case (
    id integer NOT NULL,
    case_id character varying(36),
    case_name character varying(255),
    date_modified timestamp with time zone,
    server_date_modified timestamp with time zone,
    server_date_opened timestamp with time zone,
    mother_id integer,
    case_type character varying(255),
    owner_id integer,
    user_id integer,
    baby_measles boolean,
    bcg_date date,
    birth_status character varying(255),
    dob date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    gender character varying(15),
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    child_alive boolean,
    dpt_booster_date date,
    opv_booster_date date,
    date_je date,
    date_measles_booster date,
    baby_weight numeric,
    name character varying(255),
    term character varying(50),
    time_of_birth timestamp with time zone,
    vit_a_2_date date,
    vit_a_3_date date,
    closed boolean,
    date_closed date
);


ALTER TABLE public.child_case OWNER TO postgres;

--
-- Name: child_case_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE child_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.child_case_id_seq OWNER TO postgres;

--
-- Name: child_case_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE child_case_id_seq OWNED BY child_case.id;


--
-- Name: close_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE close_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    child_alive boolean,
    close_child boolean,
    confirm_close boolean,
    date_death date,
    died boolean,
    died_village boolean,
    dupe_reg boolean,
    finished_continuum boolean,
    site_death character varying(255),
    place_death character varying(255)
);


ALTER TABLE public.close_child_form OWNER TO postgres;

--
-- Name: close_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE close_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.close_child_form_id_seq OWNER TO postgres;

--
-- Name: close_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE close_child_form_id_seq OWNED BY close_child_form.id;


--
-- Name: close_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE close_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    children integer,
    close_mother boolean,
    confirm_close boolean,
    death_village boolean,
    died_village character varying(255),
    dupe_reg boolean,
    finished_continuum boolean,
    num_children smallint,
    mother_alive character varying(20),
    moved boolean,
    migrated boolean,
    date_learned date,
    date_left date,
    migration_note boolean,
    died boolean,
    date_death date,
    site_death character varying(255)
);


ALTER TABLE public.close_mother_form OWNER TO postgres;

--
-- Name: close_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE close_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.close_mother_form_id_seq OWNER TO postgres;

--
-- Name: close_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE close_mother_form_id_seq OWNED BY close_mother_form.id;


--
-- Name: death_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE death_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    child_alive boolean,
    child_died_village boolean,
    child_place_death character varying(255),
    child_site_death character varying(255),
    chld_date_death date
);


ALTER TABLE public.death_child_form OWNER TO postgres;

--
-- Name: death_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE death_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.death_child_form_id_seq OWNER TO postgres;

--
-- Name: death_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE death_child_form_id_seq OWNED BY death_child_form.id;


--
-- Name: death_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE death_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    close character varying(20),
    mother_alive boolean,
    status character varying(255),
    cast_num_children smallint,
    children integer,
    date_death date,
    death_village boolean,
    num_children smallint,
    place_death character varying(255),
    site_death character varying(255)
);


ALTER TABLE public.death_mother_form OWNER TO postgres;

--
-- Name: death_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE death_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.death_mother_form_id_seq OWNER TO postgres;

--
-- Name: death_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE death_mother_form_id_seq OWNED BY death_mother_form.id;


--
-- Name: delivery_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE delivery_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abnormalities boolean,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_hep_b_0 boolean,
    baby_opv0 boolean,
    breastfed_hour boolean,
    close character varying(255),
    case_name character varying(255),
    case_type character varying(255),
    baby_weight boolean,
    bcg_date date,
    birth_status character varying(255),
    dob date,
    gender character varying(25),
    hep_b_0_date date,
    opv_0_date date,
    term character varying(50),
    time_of_birth timestamp with time zone,
    child_alive boolean,
    child_breathing character varying(25),
    child_cried boolean,
    child_died_village boolean,
    child_have_a_name boolean,
    child_heartbeats character varying(25),
    child_movement boolean,
    child_name character varying(25),
    child_place_death character varying(25),
    child_site_death character varying(50),
    chld_date_death date,
    cord_applied boolean,
    cord_cut boolean,
    cord_tied boolean,
    date_first_weight date,
    date_time_feed date,
    first_weight numeric,
    skin_care boolean,
    what_applied character varying(255),
    wrapped_dried boolean
);


ALTER TABLE public.delivery_child_form OWNER TO postgres;

--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE delivery_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.delivery_child_form_id_seq OWNER TO postgres;

--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE delivery_child_form_id_seq OWNED BY delivery_child_form.id;


--
-- Name: delivery_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE delivery_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    ppiud boolean,
    pptl boolean,
    abd_pain boolean,
    add date,
    close character varying(255),
    birth_place character varying(25),
    date_del_fu date,
    date_last_visit date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    family_planning_type character varying(50),
    last_visit_type character varying(255),
    mother_alive boolean,
    term character varying(50),
    cast_num_children smallint,
    complications boolean,
    date_death date,
    death_village boolean,
    delivery_nature character varying(50),
    fever boolean,
    has_delivered boolean,
    how_many_children smallint,
    ifa_tablets_given boolean,
    in_district boolean,
    jsy_money boolean,
    nextvisittype character varying(255),
    notified date,
    num_children smallint,
    other_conditions boolean,
    other_district character varying(255),
    other_village character varying(255),
    pain_urine boolean,
    place_death character varying(255),
    post_postpartum_fp boolean,
    safe boolean,
    site_death character varying(255),
    vaginal_discharge boolean,
    where_born character varying(50),
    which_hospital character varying(255),
    which_village character varying(255)
);


ALTER TABLE public.delivery_mother_form OWNER TO postgres;

--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE delivery_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.delivery_mother_form_id_seq OWNER TO postgres;

--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE delivery_mother_form_id_seq OWNED BY delivery_mother_form.id;


--
-- Name: ebf_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ebf_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    at_night boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    bcg_date date,
    breastfeeding boolean,
    case_name character varying(255),
    child_name character varying(255),
    counsel_adequate_bf boolean,
    counsel_only_milk boolean,
    counsel_stop_bottle boolean,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    eating boolean,
    emptying boolean,
    feeding_bottle boolean,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    more_feeding_less_six boolean,
    name_update boolean,
    not_breasfeeding character varying(255),
    on_demand boolean,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    recent_fever boolean,
    tea_other boolean,
    treated_less_six boolean,
    water_or_milk boolean
);


ALTER TABLE public.ebf_child_form OWNER TO postgres;

--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ebf_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ebf_child_form_id_seq OWNER TO postgres;

--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ebf_child_form_id_seq OWNED BY ebf_child_form.id;


--
-- Name: ebf_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ebf_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    addval date,
    adopt_immediately boolean,
    ask_ppiud boolean,
    aware_of_failure boolean,
    bleeding boolean,
    children character varying(255),
    complications boolean,
    condoms boolean,
    counsel_follow_up_ppiud boolean,
    counsel_follow_up_pptl boolean,
    counsel_menstrual_cycle boolean,
    counsel_methods boolean,
    counsel_ppfp boolean,
    counsel_time_iud boolean,
    date_eb_1 date,
    date_eb_2 date,
    date_eb_3 date,
    date_eb_4 date,
    date_eb_5 date,
    date_eb_6 date,
    date_iud_adopted date,
    date_last_inj date,
    date_last_visit date,
    date_next_cf date,
    date_next_eb date,
    discharge boolean,
    distension boolean,
    eb_visit_num smallint,
    family_planning_type character varying(255),
    fever boolean,
    have_condoms boolean,
    headaches boolean,
    high_bp boolean,
    inj_menstrual_irregularity boolean,
    injectable boolean,
    intend_to_continue boolean,
    interval_ppfp_interest boolean,
    iud boolean,
    iud_adopted boolean,
    iud_counsel_duration boolean,
    iud_counsel_follow_up boolean,
    iud_counsel_hospital boolean,
    iud_counsel_placement boolean,
    iud_counsel_screening boolean,
    iud_counsel_side_effects boolean,
    last_visit_type character varying(20),
    menstrual_irregularity boolean,
    next_inj_calc date,
    nextvisittype character varying(20),
    num_children smallint,
    ocp boolean,
    ocp_continue boolean,
    ocp_counsel_regularity boolean,
    pain_swelling boolean,
    ppfp_interest boolean,
    ppiud_abdominal_pain boolean,
    ppiud_problems boolean,
    pptl_abdominal_pain boolean,
    pptl_pain_surgery boolean,
    pptl_problems boolean,
    regular_periods boolean,
    tablets_received boolean,
    taken_as_prescribed boolean,
    tl boolean,
    tl_adopted boolean,
    tl_consel_incentives boolean,
    tl_counsel_follow_up boolean,
    tl_counsel_hospital boolean,
    tl_counsel_irreversible boolean,
    tl_counsel_screening boolean,
    tl_counsel_side_effects boolean,
    tl_counsel_timing boolean,
    understand_tablets boolean,
    using_correctly boolean,
    where_replace boolean,
    why_no_ppffp character varying(255),
    within_42 boolean,
    date_tl_adopted date,
    abdominal_pain boolean,
    pain_urination boolean,
    ppiud_bleeding boolean,
    ppiud_discharge boolean,
    ppiud_fever boolean
);


ALTER TABLE public.ebf_mother_form OWNER TO postgres;

--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ebf_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ebf_mother_form_id_seq OWNER TO postgres;

--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ebf_mother_form_id_seq OWNED BY ebf_mother_form.id;


--
-- Name: flw; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE flw (
    id integer NOT NULL,
    flw_id character varying(36),
    default_phone_number character varying(20),
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number_1 character varying(20),
    phone_number_2 character varying(20),
    asset_id character varying(255),
    awc_code character varying(255),
    role character varying(255),
    subcentre character varying(255),
    user_type character varying(255),
    username character varying(255),
    population character varying(255),
    education character varying(255),
    age smallint,
    district character varying(255),
    block character varying(255),
    panchayat character varying(255),
    village character varying(255),
    ward character varying(255),
    caste character varying(255),
    dob date,
    ictcordinator character varying(255),
    remarks text
);


ALTER TABLE public.flw OWNER TO postgres;

--
-- Name: flw_group; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE flw_group (
    id integer NOT NULL,
    group_id character varying(36),
    case_sharing boolean,
    domain character varying(255),
    awc_code character varying(255),
    name character varying(255),
    reporting boolean
);


ALTER TABLE public.flw_group OWNER TO postgres;

--
-- Name: flw_group_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE flw_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.flw_group_id_seq OWNER TO postgres;

--
-- Name: flw_group_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE flw_group_id_seq OWNED BY flw_group.id;


--
-- Name: flw_group_map; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE flw_group_map (
    id integer NOT NULL,
    flw_id integer,
    group_id integer
);


ALTER TABLE public.flw_group_map OWNER TO postgres;

--
-- Name: flw_group_map_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE flw_group_map_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.flw_group_map_id_seq OWNER TO postgres;

--
-- Name: flw_group_map_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE flw_group_map_id_seq OWNED BY flw_group_map.id;


--
-- Name: flw_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE flw_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.flw_id_seq OWNER TO postgres;

--
-- Name: flw_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE flw_id_seq OWNED BY flw.id;


--
-- Name: mi_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mi_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    date_arrived date,
    date_learned date,
    date_of_delivery date,
    name character varying(255),
    preg_status character varying(255),
    referral_info character varying(255),
    abortion_type character varying(255),
    date_aborted date,
    migrated_status character varying(255)
);


ALTER TABLE public.mi_form OWNER TO postgres;

--
-- Name: mi_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mi_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mi_form_id_seq OWNER TO postgres;

--
-- Name: mi_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mi_form_id_seq OWNED BY mi_form.id;


--
-- Name: mo_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mo_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    migrate_out_date date,
    migrated_status character varying(255),
    status character varying(255),
    date_learned date,
    date_left date,
    name character varying(255),
    note_given boolean
);


ALTER TABLE public.mo_form OWNER TO postgres;

--
-- Name: mo_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mo_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mo_form_id_seq OWNER TO postgres;

--
-- Name: mo_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mo_form_id_seq OWNED BY mo_form.id;


--
-- Name: mother_case; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mother_case (
    id integer NOT NULL,
    case_id character varying(36),
    case_name character varying(255),
    case_type character varying(255),
    owner_id integer,
    user_id integer,
    date_modified timestamp with time zone,
    server_date_modified timestamp with time zone,
    server_date_opened timestamp with time zone,
    family_number integer,
    hh_number integer,
    husband_name character varying(255),
    last_visit_type integer,
    mother_alive boolean,
    mother_dob date,
    mother_name character varying(255),
    close boolean,
    case_closed boolean,
    closed_on date,
    add date,
    age smallint,
    birth_place character varying(255),
    complications boolean,
    date_next_bp date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    eats_meat boolean,
    edd date,
    enrolled_in_kilkari boolean,
    family_planning_type character varying(255),
    how_many_children smallint,
    interest_in_kilkari boolean,
    last_preg_tt boolean,
    lmp date,
    mobile_number character varying(20),
    num_boys smallint,
    date_cf_1 date,
    date_cf_2 date,
    date_cf_3 date,
    date_cf_4 date,
    date_cf_5 date,
    date_cf_6 date,
    date_eb_1 date,
    date_eb_2 date,
    date_eb_3 date,
    date_eb_4 date,
    date_eb_5 date,
    date_eb_6 date,
    all_pnc_on_time boolean,
    date_pnc_1 date,
    date_pnc_2 date,
    date_pnc_3 date,
    first_pnc_time character varying(255),
    pnc_1_days_late integer,
    pnc_2_days_late integer,
    pnc_3_days_late integer,
    tt_booster_date date,
    sba boolean,
    sba_phone boolean,
    accompany boolean,
    anc_1_date date,
    anc_2_date date,
    anc_3_date date,
    anc_4_date date,
    clean_cloth boolean,
    couple_interested character varying(15),
    date_bp_1 date,
    date_bp_2 date,
    date_bp_3 date,
    date_last_visit date,
    delivery_type character varying(255),
    ifa_tablets smallint,
    ifa_tablets_100 date,
    materials boolean,
    maternal_emergency boolean,
    maternal_emergency_number boolean,
    phone_vehicle boolean,
    saving_money boolean,
    tt_1_date date,
    tt_2_date date,
    vehicle boolean,
    birth_status character varying(255),
    migrate_out_date date,
    migrated_status character varying(255),
    status character varying(255),
    term character varying(25),
    date_cf_7 date,
    date_del_fu date,
    date_next_reg date,
    institutional boolean,
    dob date,
    closed boolean,
    date_closed date
);


ALTER TABLE public.mother_case OWNER TO postgres;

--
-- Name: mother_case_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE mother_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.mother_case_id_seq OWNER TO postgres;

--
-- Name: mother_case_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE mother_case_id_seq OWNED BY mother_case.id;


--
-- Name: new_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE new_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    age_calc smallint,
    case_name character varying(255),
    case_type character varying(255),
    date_last_visit date,
    date_next_reg date,
    family_number integer,
    hh_number integer,
    husband_name character varying(255),
    last_visit_type character varying(20),
    mother_alive boolean,
    mother_dob date,
    mother_name character varying(255),
    caste character varying(255),
    dob date,
    dob_known boolean,
    full_name character varying(255),
    manual_group integer
);


ALTER TABLE public.new_form OWNER TO postgres;

--
-- Name: new_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE new_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.new_form_id_seq OWNER TO postgres;

--
-- Name: new_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE new_form_id_seq OWNED BY new_form.id;


--
-- Name: pnc_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pnc_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    able_expressed_milk boolean,
    adequate_support boolean,
    applied_to_stump boolean,
    baby_active boolean,
    breastfeeding_well boolean,
    child_alive boolean,
    child_died_village boolean,
    child_place_death character varying(255),
    child_site_death character varying(255),
    chld_date_death date,
    close character varying(255),
    cord_fallen boolean,
    correct_position boolean,
    counsel_cord_care boolean,
    counsel_exclusive_bf boolean,
    counsel_express_milk boolean,
    counsel_skin boolean,
    cousel_bf_correct boolean,
    demonstrate_expressed boolean,
    demonstrate_skin boolean,
    easy_awake boolean,
    feed_vigour boolean,
    good_latch boolean,
    improvements_bf boolean,
    observed_bf boolean,
    other_milk_to_child boolean,
    second_observation boolean,
    skin_to_skin boolean,
    warm_to_touch boolean,
    what_applied character varying(255),
    wrapped boolean
);


ALTER TABLE public.pnc_child_form OWNER TO postgres;

--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pnc_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pnc_child_form_id_seq OWNER TO postgres;

--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pnc_child_form_id_seq OWNED BY pnc_child_form.id;


--
-- Name: pnc_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pnc_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abdominal_pain boolean,
    addval date,
    adopt_immediately boolean,
    all_pnc_on_time boolean,
    bleeding boolean,
    children integer,
    complications boolean,
    congested boolean,
    counsel_breast boolean,
    counsel_follow_up_ppiud boolean,
    counsel_follow_up_pptl boolean,
    counsel_increase_food_bf boolean,
    counsel_materal_comp boolean,
    counsel_methods boolean,
    counsel_neonatal_comp boolean,
    counsel_ppfp boolean,
    counsel_time_iud boolean,
    date_death date,
    date_iud_adopted date,
    date_last_visit date,
    date_next_eb date,
    date_next_pnc date,
    date_pnc_1 date,
    date_pnc_2 date,
    date_pnc_3 date,
    date_tl_adopted date,
    death_village boolean,
    discharge boolean,
    distension boolean,
    eating_well boolean,
    family_planning_type character varying(255),
    fever boolean,
    first_pnc_time character varying(255),
    interval_ppfp_interest boolean,
    iud boolean,
    iud_adopted boolean,
    iud_counsel_duration boolean,
    iud_counsel_follow_up boolean,
    iud_counsel_hospital boolean,
    iud_counsel_placement boolean,
    iud_counsel_screening boolean,
    iud_counsel_side_effects boolean,
    last_visit_type character varying(20),
    mother_alive boolean,
    mother_child_alive boolean,
    nextvisittype character varying(20),
    num_children smallint,
    other_issues boolean,
    pain_urination boolean,
    painful_nipples boolean,
    place_death character varying(255),
    pnc_1_days_late integer,
    pnc_2_days_late integer,
    pnc_3_days_late integer,
    pnc_visit_num smallint,
    ppfp_interest boolean,
    ppiud_abdominal_pain boolean,
    ppiud_bleeding boolean,
    ppiud_discharge boolean,
    ppiud_fever boolean,
    ppiud_problems boolean,
    pptl_abdominal_pain boolean,
    pptl_excessive_bleeding boolean,
    pptl_pain_surgery boolean,
    pptl_problems boolean,
    problems_breast boolean,
    safe boolean,
    site_death character varying(255),
    tl boolean,
    tl_adopted boolean,
    tl_consel_incentives boolean,
    tl_counsel_follow_up boolean,
    tl_counsel_hospital boolean,
    tl_counsel_irreversible boolean,
    tl_counsel_screening boolean,
    tl_counsel_side_effects boolean,
    tl_counsel_timing boolean,
    why_no_ppffp character varying(255)
);


ALTER TABLE public.pnc_mother_form OWNER TO postgres;

--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE pnc_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pnc_mother_form_id_seq OWNER TO postgres;

--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE pnc_mother_form_id_seq OWNED BY pnc_mother_form.id;


--
-- Name: refer_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refer_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    refer_child boolean
);


ALTER TABLE public.refer_child_form OWNER TO postgres;

--
-- Name: refer_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refer_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.refer_child_form_id_seq OWNER TO postgres;

--
-- Name: refer_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refer_child_form_id_seq OWNED BY refer_child_form.id;


--
-- Name: refer_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE refer_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    children integer,
    num_children smallint,
    refer_mother boolean
);


ALTER TABLE public.refer_mother_form OWNER TO postgres;

--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE refer_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.refer_mother_form_id_seq OWNER TO postgres;

--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE refer_mother_form_id_seq OWNED BY refer_mother_form.id;


--
-- Name: registration_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE registration_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    abnormalities boolean,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    case_name character varying(255),
    case_type character varying(255),
    bcg_date date,
    birth_status character varying(255),
    dob date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    gender character varying(15),
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    vit_a_1_date date,
    child_have_a_name boolean,
    child_name character varying(255),
    weight numeric
);


ALTER TABLE public.registration_child_form OWNER TO postgres;

--
-- Name: registration_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE registration_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.registration_child_form_id_seq OWNER TO postgres;

--
-- Name: registration_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE registration_child_form_id_seq OWNED BY registration_child_form.id;


--
-- Name: registration_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE registration_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    ppiud boolean,
    pptl boolean,
    abd_pain boolean,
    age_calc smallint,
    age_calc_adj smallint,
    age_est smallint,
    age_est_trigger character varying(10),
    close character varying(10),
    add date,
    age smallint,
    birth_place character varying(255),
    complications boolean,
    date_last_visit date,
    date_next_bp date,
    date_next_cf date,
    date_next_eb date,
    date_next_pnc date,
    eats_meat boolean,
    edd date,
    enrolled_in_kilkari boolean,
    family_planning_type character varying(255),
    how_many_children smallint,
    interest_in_kilkari boolean,
    last_preg_tt boolean,
    last_visit_type character varying(20),
    lmp date,
    mobile_number character varying(20),
    mother_dob date,
    num_boys smallint,
    status character varying(255),
    child_dob date,
    children integer,
    client_no_register character varying(10),
    client_not_pregnant character varying(10),
    clinical_exam boolean,
    condoms boolean,
    continue_preg boolean,
    delivery_nature character varying(255),
    dob_est character varying(255),
    edd_calc date,
    edd_known boolean,
    education character varying(255),
    fever boolean,
    first_pregnancy boolean,
    gest_age smallint,
    good_to_register boolean,
    in_district boolean,
    injectible boolean,
    is_pregnant boolean,
    iud_used boolean,
    jsy_beneficiary boolean,
    jsy_money boolean,
    last_preg integer,
    last_preg_c_section boolean,
    last_preg_full_term boolean,
    lmp_calc date,
    lmp_known boolean,
    missed_period boolean,
    mobile_number_whose character varying(255),
    nextvisit character varying(10),
    nextvisit_bp character varying(10),
    nextvisittype character varying(20),
    num_children smallint,
    num_girls smallint,
    ocp_used boolean,
    other_conditions character varying(255),
    other_district character varying(255),
    other_village character varying(255),
    pain_urine boolean,
    post_postpartum_fp boolean,
    preg_desired boolean,
    recently_delivered boolean,
    referral_prompt character varying(255),
    resident character varying(255),
    success character varying(10),
    urine_test boolean,
    used_fp boolean,
    vaginal_discharge boolean,
    vegetarian boolean,
    where_born character varying(255),
    which_hospital character varying(255),
    which_village character varying(255)
);


ALTER TABLE public.registration_mother_form OWNER TO postgres;

--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE registration_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.registration_mother_form_id_seq OWNER TO postgres;

--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE registration_mother_form_id_seq OWNED BY registration_mother_form.id;


--
-- Name: schema_version; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE schema_version (
    version character varying(20) NOT NULL,
    description character varying(100),
    type character varying(10) NOT NULL,
    script character varying(200) NOT NULL,
    checksum integer,
    installed_by character varying(30) NOT NULL,
    installed_on timestamp without time zone DEFAULT now(),
    execution_time integer,
    state character varying(15) NOT NULL,
    current_version boolean NOT NULL
);


ALTER TABLE public.schema_version OWNER TO postgres;

--
-- Name: ui_child_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ui_child_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    add_vaccinations boolean,
    baby_bcg boolean,
    baby_dpt1 boolean,
    baby_dpt2 boolean,
    baby_dpt3 boolean,
    baby_hep_b_0 boolean,
    baby_hep_b_1 boolean,
    baby_hep_b_2 boolean,
    baby_hep_b_3 boolean,
    baby_measles boolean,
    baby_opv0 boolean,
    baby_opv1 boolean,
    baby_opv2 boolean,
    baby_opv3 boolean,
    baby_vita1 boolean,
    bcg_date date,
    dpt_1_date date,
    dpt_2_date date,
    dpt_3_date date,
    dpt_booster_date date,
    hep_b_0_date date,
    hep_b_1_date date,
    hep_b_2_date date,
    hep_b_3_date date,
    measles_date date,
    opv_0_date date,
    opv_1_date date,
    opv_2_date date,
    opv_3_date date,
    opv_booster_date date,
    vit_a_1_date date,
    baby_dpt_booster boolean,
    baby_je boolean,
    baby_measles_booster boolean,
    baby_opv_booster boolean,
    baby_vita2 boolean,
    baby_vita3 boolean,
    date_je date,
    date_measles_booster date,
    vit_a_2_date date,
    vit_a_3_date date
);


ALTER TABLE public.ui_child_form OWNER TO postgres;

--
-- Name: ui_child_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ui_child_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ui_child_form_id_seq OWNER TO postgres;

--
-- Name: ui_child_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ui_child_form_id_seq OWNED BY ui_child_form.id;


--
-- Name: ui_mother_form; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE ui_mother_form (
    id integer NOT NULL,
    instance_id character varying(36),
    time_end timestamp with time zone,
    time_start timestamp with time zone,
    user_id integer,
    case_id integer,
    date_modified timestamp with time zone,
    details_available boolean,
    tt_1_date date,
    tt_2_date date,
    tt_booster_date date,
    received_tt1 boolean,
    received_tt2 boolean,
    up_to_date character varying(15),
    num_children smallint,
    update_mother boolean,
    tt_booster date
);


ALTER TABLE public.ui_mother_form OWNER TO postgres;

--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE ui_mother_form_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ui_mother_form_id_seq OWNER TO postgres;

--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE ui_mother_form_id_seq OWNED BY ui_mother_form.id;


SET search_path = carereporting, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY abort_form ALTER COLUMN id SET DEFAULT nextval('abort_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY bp_form ALTER COLUMN id SET DEFAULT nextval('bp_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_child_form ALTER COLUMN id SET DEFAULT nextval('cf_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form ALTER COLUMN id SET DEFAULT nextval('cf_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY child_case ALTER COLUMN id SET DEFAULT nextval('child_case_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_child_form ALTER COLUMN id SET DEFAULT nextval('close_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_mother_form ALTER COLUMN id SET DEFAULT nextval('close_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_child_form ALTER COLUMN id SET DEFAULT nextval('death_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_mother_form ALTER COLUMN id SET DEFAULT nextval('death_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form ALTER COLUMN id SET DEFAULT nextval('delivery_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form ALTER COLUMN id SET DEFAULT nextval('delivery_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form ALTER COLUMN id SET DEFAULT nextval('ebf_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form ALTER COLUMN id SET DEFAULT nextval('ebf_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY flw ALTER COLUMN id SET DEFAULT nextval('flw_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY flw_group ALTER COLUMN id SET DEFAULT nextval('flw_group_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY flw_group_map ALTER COLUMN id SET DEFAULT nextval('flw_group_map_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mi_form ALTER COLUMN id SET DEFAULT nextval('mi_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mo_form ALTER COLUMN id SET DEFAULT nextval('mo_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mother_case ALTER COLUMN id SET DEFAULT nextval('mother_case_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY new_form ALTER COLUMN id SET DEFAULT nextval('new_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form ALTER COLUMN id SET DEFAULT nextval('pnc_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form ALTER COLUMN id SET DEFAULT nextval('pnc_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_child_form ALTER COLUMN id SET DEFAULT nextval('refer_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form ALTER COLUMN id SET DEFAULT nextval('refer_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_child_form ALTER COLUMN id SET DEFAULT nextval('registration_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form ALTER COLUMN id SET DEFAULT nextval('registration_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_child_form ALTER COLUMN id SET DEFAULT nextval('ui_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form ALTER COLUMN id SET DEFAULT nextval('ui_mother_form_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY abort_form ALTER COLUMN id SET DEFAULT nextval('abort_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bp_form ALTER COLUMN id SET DEFAULT nextval('bp_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_child_form ALTER COLUMN id SET DEFAULT nextval('cf_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form ALTER COLUMN id SET DEFAULT nextval('cf_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY child_case ALTER COLUMN id SET DEFAULT nextval('child_case_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_child_form ALTER COLUMN id SET DEFAULT nextval('close_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_mother_form ALTER COLUMN id SET DEFAULT nextval('close_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_child_form ALTER COLUMN id SET DEFAULT nextval('death_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_mother_form ALTER COLUMN id SET DEFAULT nextval('death_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form ALTER COLUMN id SET DEFAULT nextval('delivery_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form ALTER COLUMN id SET DEFAULT nextval('delivery_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form ALTER COLUMN id SET DEFAULT nextval('ebf_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form ALTER COLUMN id SET DEFAULT nextval('ebf_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY flw ALTER COLUMN id SET DEFAULT nextval('flw_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY flw_group ALTER COLUMN id SET DEFAULT nextval('flw_group_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY flw_group_map ALTER COLUMN id SET DEFAULT nextval('flw_group_map_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mi_form ALTER COLUMN id SET DEFAULT nextval('mi_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mo_form ALTER COLUMN id SET DEFAULT nextval('mo_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mother_case ALTER COLUMN id SET DEFAULT nextval('mother_case_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY new_form ALTER COLUMN id SET DEFAULT nextval('new_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form ALTER COLUMN id SET DEFAULT nextval('pnc_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form ALTER COLUMN id SET DEFAULT nextval('pnc_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_child_form ALTER COLUMN id SET DEFAULT nextval('refer_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form ALTER COLUMN id SET DEFAULT nextval('refer_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_child_form ALTER COLUMN id SET DEFAULT nextval('registration_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form ALTER COLUMN id SET DEFAULT nextval('registration_mother_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_child_form ALTER COLUMN id SET DEFAULT nextval('ui_child_form_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form ALTER COLUMN id SET DEFAULT nextval('ui_mother_form_id_seq'::regclass);


SET search_path = carereporting, pg_catalog;

--
-- Data for Name: abort_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: abort_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('abort_form_id_seq', 1, false);


--
-- Data for Name: bp_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO bp_form VALUES (1, '8b7de0ec-24ec-4c98-beaa-e2801beb81c6', '2013-04-24 14:39:01.072+02', '2013-04-24 14:37:40.368+02', 17, 13, '2013-04-24 14:39:01.072+02', '2013-04-24', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 16002, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 30, 'bp', NULL, NULL);
INSERT INTO bp_form VALUES (2, '8b7de0ec-24ec-4c98-beaa-e2801bebtest', '2013-04-24 14:39:01.072+02', '2013-04-24 14:37:40.368+02', 17, 13, '2013-04-24 14:39:01.072+02', '2013-04-24', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 16002, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 30, 'bp', NULL, NULL);
INSERT INTO bp_form VALUES (3, 'ad1f2eb2-0059-40c2-ba6c-9e2d0ecad6ff', '2012-08-17 12:33:31.966+02', '2012-08-17 12:21:33.201+02', 18, 14, '2012-08-17 12:33:31.966+02', '2012-07-06', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'institutional', NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 15619, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 100, 'bp', false, 'undecided');


--
-- Name: bp_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('bp_form_id_seq', 3, true);


--
-- Data for Name: cf_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO cf_child_form VALUES (1, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 1, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO cf_child_form VALUES (2, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 2, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO cf_child_form VALUES (3, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 3, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: cf_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('cf_child_form_id_seq', 3, true);


--
-- Data for Name: cf_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO cf_mother_form VALUES (1, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 2, '2013-05-16 06:50:07.932+02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, 3, true, NULL, NULL, NULL, NULL);


--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('cf_mother_form_id_seq', 1, true);


--
-- Data for Name: child_case; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO child_case VALUES (1, 'd4b48d5f-4d44-4537-8ed5-b756217f5107', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (2, 'c898c2d0-6655-4885-bd24-6199e6db53f8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (3, '5728524a-8d1f-46d3-9dc2-1b5b246662eb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (4, '13c79f6b-fc8a-43b7-b07d-5076a218ef1e', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (5, '0fb489c5-68ca-4132-afa5-2ef69c44c63a', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (7, 'a5d567e0-8919-44bc-9073-93cb0142ab2d', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (6, '8442ff71-8abd-44fb-b318-c3feb891ea70', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (8, '8442ff71-8abd-44fb-b318-c3febtest001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (9, 'a5d567e0-8919-44bc-9073-93cb0test001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (10, '0331e566-25d6-43e7-9730-ebdb9d4ef95d', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (14, '47b14730-cac4-4625-9f37-e2e1aea8test', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (11, '815e1d04-8cc7-4f12-989f-8aba24e80381', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (12, '9a1b538f-42f3-421f-9fbd-b3937c7e1bf7', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (13, '18ae62cc-b04b-4775-beff-b977b6a0cf2d', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (15, '47b14730-cac4-4625-9f37-e2e1aea84888', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: child_case_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('child_case_id_seq', 15, true);


--
-- Data for Name: close_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: close_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('close_child_form_id_seq', 1, false);


--
-- Data for Name: close_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO close_mother_form VALUES (1, '8817c814-538b-4dc7-91bc-9f8783d3fb18', '2012-10-12 11:10:24.402+02', '2012-10-12 11:09:56.478+02', 16, 12, '2012-10-12 11:10:24.402+02', NULL, 0, true, NULL, NULL, NULL, false, false, 0, NULL, false, false, NULL, NULL, NULL, false, NULL, NULL);


--
-- Name: close_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('close_mother_form_id_seq', 5, true);


--
-- Data for Name: death_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO death_child_form VALUES (1, 'e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336', '2012-12-07 08:56:26.295+01', '2012-12-07 08:55:17.03+01', 3, 4, '2012-12-07 08:56:26.295+01', NULL, false, true, NULL, 'home', '2012-11-07');
INSERT INTO death_child_form VALUES (2, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 7, '2012-10-12 12:23:31.612+02', NULL, false, true, NULL, 'home', '2012-06-01');
INSERT INTO death_child_form VALUES (3, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 6, '2012-10-12 12:23:31.612+02', NULL, false, true, NULL, 'home', '2012-06-02');


--
-- Name: death_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('death_child_form_id_seq', 3, true);


--
-- Data for Name: death_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO death_mother_form VALUES (1, 'e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336', '2012-12-07 08:56:26.295+01', '2012-12-07 08:55:17.03+01', 3, 3, '2012-12-07 08:56:26.295+01', NULL, NULL, NULL, 1, 0, NULL, NULL, 1, NULL, NULL);
INSERT INTO death_mother_form VALUES (2, 'b615dccc-bda9-4f28-9d4e-d4988e4e1d18', '2012-08-24 07:44:55.103+02', '2012-08-24 07:44:41.198+02', 16, 12, '2012-08-24 07:44:55.103+02', NULL, NULL, NULL, 0, 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO death_mother_form VALUES (3, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 12, '2012-10-12 12:23:31.612+02', NULL, NULL, NULL, 2, 0, NULL, NULL, 2, NULL, NULL);


--
-- Name: death_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('death_mother_form_id_seq', 3, true);


--
-- Data for Name: delivery_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('delivery_child_form_id_seq', 1, false);


--
-- Data for Name: delivery_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('delivery_mother_form_id_seq', 1, false);


--
-- Data for Name: ebf_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ebf_child_form_id_seq', 1, false);


--
-- Data for Name: ebf_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ebf_mother_form_id_seq', 1, false);


--
-- Data for Name: flw; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO flw VALUES (1, '89fda0284e008d2e0c980fb13f9b9e62', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (2, '89fda0284e008d2e0c980fb13f9f12b8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (3, '89fda0284e008d2e0c980fb13f9dc566', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (8, '89fda0284e008d2e0c980fb13f9a3e49', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (9, '89fda0284e008d2e0c980fuserNEW001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (10, '89fda0284e008d2e0c980fReguser001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (15, '89fda0284e008d2e0c980fuserNEW004', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (18, '89fda0284e008d2e0c980fb13fa04709', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (16, '89fda0284e008d2e0c980fb13f9c6eff', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (19, '70c6ba6f55ed21716fe725c214694627', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (21, '89fda0284e008d2e0c980fb13f99a86d', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (22, '70c6ba6f55ed21716fe725c2146903eb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (20, '70c6ba6f55ed21716fe725c21469099f', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (23, '89fda0284e008d2e0c980fb13f9f74db', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (17, '89fda0284e008d2e0c980fb13f98abea', NULL, NULL, 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Data for Name: flw_group; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: flw_group_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('flw_group_id_seq', 1, false);


--
-- Data for Name: flw_group_map; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: flw_group_map_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('flw_group_map_id_seq', 1, false);


--
-- Name: flw_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('flw_id_seq', 23, true);


--
-- Data for Name: mi_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO mi_form VALUES (1, 'b098e949-c3f1-4b4e-9a5e-c2d76561nams', '2013-03-06 11:12:13.894+01', '2013-03-06 11:11:30.512+01', 23, 20, '2013-03-06 11:12:13.894+01', '2013-02-25', '2013-02-25', NULL, NULL, 'aborted', NULL, 'less_12_weeks', '2013-02-18', NULL);


--
-- Name: mi_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mi_form_id_seq', 1, true);


--
-- Data for Name: mo_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO mo_form VALUES (1, 'f641a83f-ff5f-4cde-aad0-282242695d86', '2012-12-08 07:38:33.158+01', '2012-12-08 07:37:27.789+01', 21, 18, '2012-12-08 07:38:33.158+01', NULL, NULL, NULL, NULL, '2012-11-25', NULL, false);


--
-- Name: mo_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mo_form_id_seq', 1, true);


--
-- Data for Name: mother_case; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO mother_case VALUES (1, '21fb973a-1916-4c17-b89c-2fa80dd7abc2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (2, '12b60388-a28b-4201-97a6-e9765186e793', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (3, '4829bf83-86b2-41f7-b807-708f6death01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (4, '3ed54bd4-57ba-4222-916f-19133f25fc14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (5, 'a53af344-b0ad-448a-afd2-01c6a8NEW001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (6, 'a53af344-b0ad-448a-afd2-01c6a8Reg001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (11, 'a53af344-b0ad-448a-afd2-01c6a8NEW004', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (12, '2e1b5877-f3e4-4fac-a2c6-e3585ab10172', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (14, 'a890b016-47f4-43ae-b0b9-0cfbd6ff2030', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (15, '2e1b5877-f3e4-4fac-a2c6-e3585test001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (16, 'ede23f83-c88e-43d2-be70-9d6b983bdb12', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (18, 'a53af344-b0ad-448a-afd2-01c6a8494c61', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (19, '2033d85a-ee74-461d-bfb5-17e1f6bc7710', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (17, 'c5e5e5c1-c1a4-4cf1-b208-faf1c170b2de', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (20, '0072f1d7-d5e0-4ed7-b1e7-c1de73bcnams', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (13, '4a3be947-717b-4628-b84c-6d7e62d9a3f8', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: mother_case_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mother_case_id_seq', 20, true);


--
-- Data for Name: new_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO new_form VALUES (1, '268e68c6-73c6-4f68-8cba-cdd5c53e266f', '2012-08-02 04:07:52.716+02', '2012-08-02 04:03:13.031+02', 1, 1, '2012-08-02 04:07:52.716+02', 0, NULL, NULL, NULL, NULL, 2, 70, 'सलैन पासवान', NULL, NULL, NULL, NULL, 'sc_st', NULL, false, 'रोशन देवी', NULL);
INSERT INTO new_form VALUES (2, '5f586e64-868e-4e91-b9e5-0bc93d9a1d15', '2012-07-09 11:39:43.878+02', '2012-07-09 11:38:32.115+02', 9, 5, '2012-07-09 11:39:43.878+02', 0, NULL, NULL, NULL, NULL, 8, 184, 'विपिन झा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'गुङिया देवी', NULL);
INSERT INTO new_form VALUES (7, '5f586e64-868e-4e91-b9e5-0bc93inst004', '2012-07-09 11:39:43.878+02', '2012-07-09 11:38:32.115+02', 15, 11, '2012-07-09 11:39:43.878+02', 0, NULL, NULL, NULL, NULL, 8, 184, 'विपिन झा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'गुङिया देवी', NULL);
INSERT INTO new_form VALUES (8, '3db1b50e-b3c1-43ed-8390-4ac86750e08f', '2012-08-23 10:05:07.98+02', '2012-08-23 10:03:38.78+02', 16, 12, '2012-08-23 10:05:07.98+02', 0, NULL, NULL, NULL, NULL, 5, 203, 'बिन्देश्वर साह', NULL, NULL, NULL, NULL, 'other', NULL, false, 'मीना देवी', NULL);
INSERT INTO new_form VALUES (9, 'dc405899-b367-4f99-9d9d-3b3583425afc', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (10, 'dc405899-b367-4f99-9d9d-3b358342test', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (11, 'dc405899-b367-4f99-9d9d-3b35834test1', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (12, 'dc405899-b367-4f99-9d9d-3b35834test2', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (13, '0367da59-c14c-44b1-b973-ba9fede46d7d', '2012-09-01 16:47:49.259+02', '2012-09-01 16:44:49.006+02', 22, 19, '2012-09-01 16:47:49.259+02', 0, NULL, NULL, NULL, NULL, 2, 271, 'जीया उद्दीन ', NULL, NULL, NULL, NULL, 'other', NULL, false, 'इझारुण खातुन ', NULL);
INSERT INTO new_form VALUES (14, 'b97a9a71-4c89-4da4-9c95-492cf114faf5', '2012-08-17 09:39:51.276+02', '2012-08-17 09:38:58.436+02', 20, 17, '2012-08-17 09:39:51.276+02', 0, NULL, NULL, NULL, NULL, 2, 166, 'बीरबल शर्मा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'मुलीया देवी', NULL);
INSERT INTO new_form VALUES (19, 'dc405899-b367-4f99-9d9d-3b3583425af1', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'र्प्रमोद साह', NULL, NULL, NULL, NULL, 'other', NULL, false, 'सुमितरा देवी', NULL);


--
-- Name: new_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('new_form_id_seq', 19, true);


--
-- Data for Name: pnc_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO pnc_child_form VALUES (1, 'ea830d5f-53b9-4c93-b099-f3878fd1e3c6', '2013-04-25 12:45:14.535+02', '2013-04-25 12:40:36.384+02', 8, 5, '2013-04-25 12:45:14.535+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (2, 'ca543abc-252f-470a-b1cb-a5aecfd595ab', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 10, '2013-05-18 10:46:43.914+02', NULL, true, false, true, true, NULL, NULL, NULL, NULL, NULL, NULL, false, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (3, 'ca543abc-252f-470a-b1cb-a5aecfd5test', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 10, '2013-05-18 10:46:43.914+02', NULL, true, false, true, true, NULL, NULL, NULL, NULL, NULL, NULL, false, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (4, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 11, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (5, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 12, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (6, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 13, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (7, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 14, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (8, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 11, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (9, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 12, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (10, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 13, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (11, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 15, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);


--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('pnc_child_form_id_seq', 11, true);


--
-- Data for Name: pnc_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO pnc_mother_form VALUES (1, 'ea830d5f-53b9-4c93-b099-f3878fd1e3c6', '2013-04-25 12:45:14.535+02', '2013-04-25 12:40:36.384+02', 8, 4, '2013-04-25 12:45:14.535+02', true, '2013-04-23', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, true, NULL, NULL, NULL, NULL, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'refused');
INSERT INTO pnc_mother_form VALUES (6, 'ca543abc-252f-470a-b1cb-a5aecfd595ab', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 16, '2013-05-18 10:46:43.914+02', false, '2013-05-16', NULL, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, true, false, true, true, true, true, true, true, true, 'other');
INSERT INTO pnc_mother_form VALUES (7, 'ca543abc-252f-470a-b1cb-a5aecfd5test', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 16, '2013-05-18 10:46:43.914+02', false, '2013-05-16', NULL, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, true, false, true, true, true, true, true, true, true, 'other');
INSERT INTO pnc_mother_form VALUES (8, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 17, '2012-09-26 11:14:13.663+02', false, '2012-09-22', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 4, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'incentive_related');
INSERT INTO pnc_mother_form VALUES (9, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 17, '2012-09-26 11:14:13.663+02', false, '2012-09-22', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 4, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'incentive_related');


--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('pnc_mother_form_id_seq', 9, true);


--
-- Data for Name: refer_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: refer_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('refer_child_form_id_seq', 1, false);


--
-- Data for Name: refer_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('refer_mother_form_id_seq', 1, false);


--
-- Data for Name: registration_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO registration_child_form VALUES (1, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 6, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (2, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 7, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (3, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 8, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (4, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 9, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);


--
-- Name: registration_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('registration_child_form_id_seq', 4, true);


--
-- Data for Name: registration_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO registration_mother_form VALUES (1, '92b7bcda-3829-4ab9-a189-bbdb59ab5794', '2012-09-03 11:48:05.099+02', '2012-09-03 11:46:13.446+02', 10, 6, '2012-09-03 11:48:05.099+02', NULL, NULL, NULL, 23, 0, 23, 'OK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2012-10-13', false, 'middle', NULL, NULL, 8, true, NULL, NULL, true, NULL, true, NULL, 4, false, true, '2012-01-07', false, NULL, NULL, NULL, 'OK', 'bp', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'resident', 'OK', true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO registration_mother_form VALUES (2, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 12, '2012-10-12 12:21:14.226+02', NULL, NULL, NULL, 40, 0, 40, 'OK', NULL, NULL, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, 6, NULL, '2012-05-12', 0, NULL, NULL, NULL, NULL, NULL, 'vaginal', NULL, NULL, NULL, 'illiterate', NULL, NULL, NULL, true, true, NULL, false, NULL, false, true, NULL, false, true, NULL, NULL, NULL, 'mobile_asha', 'OK', NULL, 'cf', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, 'resident', 'OK', NULL, false, NULL, true, 'hospital', 'block_phc', NULL);
INSERT INTO registration_mother_form VALUES (3, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 15, '2012-10-12 12:21:14.226+02', NULL, NULL, NULL, 40, 0, 40, 'OK', NULL, NULL, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, 6, NULL, '2012-05-12', 0, NULL, NULL, NULL, NULL, NULL, 'vaginal', NULL, NULL, NULL, 'illiterate', NULL, NULL, NULL, true, true, NULL, false, NULL, false, true, NULL, false, true, NULL, NULL, NULL, 'mobile_asha', 'OK', NULL, 'cf', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, 'resident', 'OK', NULL, false, NULL, true, 'hospital', 'block_phc', NULL);


--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('registration_mother_form_id_seq', 3, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

INSERT INTO schema_version VALUES ('0', 'Base setup', 'INIT', 'Base setup', NULL, 'postgres', '2013-06-07 15:05:54.201319', 0, 'SUCCESS', false);
INSERT INTO schema_version VALUES ('1.0', NULL, 'SQL', 'V1_0.sql', -141017564, 'postgres', '2013-06-07 15:05:54.948092', 732, 'SUCCESS', true);


--
-- Data for Name: ui_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: ui_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ui_child_form_id_seq', 1, false);


--
-- Data for Name: ui_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--



--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ui_mother_form_id_seq', 1, false);


SET search_path = public, pg_catalog;

--
-- Data for Name: abort_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: abort_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('abort_form_id_seq', 1, false);


--
-- Data for Name: bp_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO bp_form VALUES (1, '8b7de0ec-24ec-4c98-beaa-e2801beb81c6', '2013-04-24 14:39:01.072+02', '2013-04-24 14:37:40.368+02', 17, 13, '2013-04-24 14:39:01.072+02', '2013-04-24', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 16002, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 30, 'bp', NULL, NULL);
INSERT INTO bp_form VALUES (2, '8b7de0ec-24ec-4c98-beaa-e2801bebtest', '2013-04-24 14:39:01.072+02', '2013-04-24 14:37:40.368+02', 17, 13, '2013-04-24 14:39:01.072+02', '2013-04-24', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'home', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 16002, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 30, 'bp', NULL, NULL);
INSERT INTO bp_form VALUES (3, 'ad1f2eb2-0059-40c2-ba6c-9e2d0ecad6ff', '2012-08-17 12:33:31.966+02', '2012-08-17 12:21:33.201+02', 18, 14, '2012-08-17 12:33:31.966+02', '2012-07-06', 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'institutional', NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 15619, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 100, 'bp', false, 'undecided');


--
-- Name: bp_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('bp_form_id_seq', 3, true);


--
-- Data for Name: cf_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cf_child_form VALUES (1, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 1, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO cf_child_form VALUES (2, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 2, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO cf_child_form VALUES (3, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 3, '2013-05-16 06:50:07.932+02', true, true, NULL, NULL, NULL, NULL, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, true, false, false, false, true, NULL, false, NULL, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: cf_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cf_child_form_id_seq', 3, true);


--
-- Data for Name: cf_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cf_mother_form VALUES (1, '1f093281-233e-4afc-a235-077d2dc183e9', '2013-05-16 06:50:07.932+02', '2013-05-16 06:44:00.953+02', 2, 2, '2013-05-16 06:50:07.932+02', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 0, 3, true, NULL, NULL, NULL, NULL);


--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('cf_mother_form_id_seq', 1, true);


--
-- Data for Name: child_case; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO child_case VALUES (1, 'd4b48d5f-4d44-4537-8ed5-b756217f5107', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (2, 'c898c2d0-6655-4885-bd24-6199e6db53f8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (3, '5728524a-8d1f-46d3-9dc2-1b5b246662eb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (4, '13c79f6b-fc8a-43b7-b07d-5076a218ef1e', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (5, '0fb489c5-68ca-4132-afa5-2ef69c44c63a', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (7, 'a5d567e0-8919-44bc-9073-93cb0142ab2d', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (6, '8442ff71-8abd-44fb-b318-c3feb891ea70', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (8, '8442ff71-8abd-44fb-b318-c3febtest001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (9, 'a5d567e0-8919-44bc-9073-93cb0test001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (10, '0331e566-25d6-43e7-9730-ebdb9d4ef95d', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (14, '47b14730-cac4-4625-9f37-e2e1aea8test', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (11, '815e1d04-8cc7-4f12-989f-8aba24e80381', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (12, '9a1b538f-42f3-421f-9fbd-b3937c7e1bf7', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (13, '18ae62cc-b04b-4775-beff-b977b6a0cf2d', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO child_case VALUES (15, '47b14730-cac4-4625-9f37-e2e1aea84888', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: child_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('child_case_id_seq', 15, true);


--
-- Data for Name: close_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: close_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('close_child_form_id_seq', 1, false);


--
-- Data for Name: close_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO close_mother_form VALUES (1, '8817c814-538b-4dc7-91bc-9f8783d3fb18', '2012-10-12 11:10:24.402+02', '2012-10-12 11:09:56.478+02', 16, 12, '2012-10-12 11:10:24.402+02', NULL, 0, true, NULL, NULL, NULL, false, false, 0, NULL, false, false, NULL, NULL, NULL, false, NULL, NULL);


--
-- Name: close_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('close_mother_form_id_seq', 5, true);


--
-- Data for Name: death_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO death_child_form VALUES (1, 'e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336', '2012-12-07 08:56:26.295+01', '2012-12-07 08:55:17.03+01', 3, 4, '2012-12-07 08:56:26.295+01', NULL, false, true, NULL, 'home', '2012-11-07');
INSERT INTO death_child_form VALUES (2, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 7, '2012-10-12 12:23:31.612+02', NULL, false, true, NULL, 'home', '2012-06-01');
INSERT INTO death_child_form VALUES (3, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 6, '2012-10-12 12:23:31.612+02', NULL, false, true, NULL, 'home', '2012-06-02');


--
-- Name: death_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('death_child_form_id_seq', 3, true);


--
-- Data for Name: death_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO death_mother_form VALUES (1, 'e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336', '2012-12-07 08:56:26.295+01', '2012-12-07 08:55:17.03+01', 3, 3, '2012-12-07 08:56:26.295+01', NULL, NULL, NULL, 1, 0, NULL, NULL, 1, NULL, NULL);
INSERT INTO death_mother_form VALUES (2, 'b615dccc-bda9-4f28-9d4e-d4988e4e1d18', '2012-08-24 07:44:55.103+02', '2012-08-24 07:44:41.198+02', 16, 12, '2012-08-24 07:44:55.103+02', NULL, NULL, NULL, 0, 0, NULL, NULL, 0, NULL, NULL);
INSERT INTO death_mother_form VALUES (3, '359727ca-9bb2-4e5b-966b-8e9c13ddb4c3', '2012-10-12 12:23:31.612+02', '2012-10-12 12:22:22.615+02', 16, 12, '2012-10-12 12:23:31.612+02', NULL, NULL, NULL, 2, 0, NULL, NULL, 2, NULL, NULL);


--
-- Name: death_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('death_mother_form_id_seq', 3, true);


--
-- Data for Name: delivery_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('delivery_child_form_id_seq', 1, false);


--
-- Data for Name: delivery_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('delivery_mother_form_id_seq', 1, false);


--
-- Data for Name: ebf_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ebf_child_form_id_seq', 1, false);


--
-- Data for Name: ebf_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ebf_mother_form_id_seq', 1, false);


--
-- Data for Name: flw; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO flw VALUES (1, '89fda0284e008d2e0c980fb13f9b9e62', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (2, '89fda0284e008d2e0c980fb13f9f12b8', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (3, '89fda0284e008d2e0c980fb13f9dc566', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (8, '89fda0284e008d2e0c980fb13f9a3e49', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (9, '89fda0284e008d2e0c980fuserNEW001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (10, '89fda0284e008d2e0c980fReguser001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (15, '89fda0284e008d2e0c980fuserNEW004', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (18, '89fda0284e008d2e0c980fb13fa04709', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (16, '89fda0284e008d2e0c980fb13f9c6eff', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (19, '70c6ba6f55ed21716fe725c214694627', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (21, '89fda0284e008d2e0c980fb13f99a86d', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (22, '70c6ba6f55ed21716fe725c2146903eb', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (20, '70c6ba6f55ed21716fe725c21469099f', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (23, '89fda0284e008d2e0c980fb13f9f74db', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO flw VALUES (17, '89fda0284e008d2e0c980fb13f98abea', NULL, NULL, 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Data for Name: flw_group; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: flw_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('flw_group_id_seq', 1, false);


--
-- Data for Name: flw_group_map; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: flw_group_map_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('flw_group_map_id_seq', 1, false);


--
-- Name: flw_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('flw_id_seq', 23, true);


--
-- Data for Name: mi_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO mi_form VALUES (1, 'b098e949-c3f1-4b4e-9a5e-c2d76561nams', '2013-03-06 11:12:13.894+01', '2013-03-06 11:11:30.512+01', 23, 20, '2013-03-06 11:12:13.894+01', '2013-02-25', '2013-02-25', NULL, NULL, 'aborted', NULL, 'less_12_weeks', '2013-02-18', NULL);


--
-- Name: mi_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mi_form_id_seq', 1, true);


--
-- Data for Name: mo_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO mo_form VALUES (1, 'f641a83f-ff5f-4cde-aad0-282242695d86', '2012-12-08 07:38:33.158+01', '2012-12-08 07:37:27.789+01', 21, 18, '2012-12-08 07:38:33.158+01', NULL, NULL, NULL, NULL, '2012-11-25', NULL, false);


--
-- Name: mo_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mo_form_id_seq', 1, true);


--
-- Data for Name: mother_case; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO mother_case VALUES (1, '21fb973a-1916-4c17-b89c-2fa80dd7abc2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (2, '12b60388-a28b-4201-97a6-e9765186e793', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (3, '4829bf83-86b2-41f7-b807-708f6death01', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (4, '3ed54bd4-57ba-4222-916f-19133f25fc14', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (5, 'a53af344-b0ad-448a-afd2-01c6a8NEW001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (6, 'a53af344-b0ad-448a-afd2-01c6a8Reg001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (11, 'a53af344-b0ad-448a-afd2-01c6a8NEW004', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (12, '2e1b5877-f3e4-4fac-a2c6-e3585ab10172', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (14, 'a890b016-47f4-43ae-b0b9-0cfbd6ff2030', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (15, '2e1b5877-f3e4-4fac-a2c6-e3585test001', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (16, 'ede23f83-c88e-43d2-be70-9d6b983bdb12', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (18, 'a53af344-b0ad-448a-afd2-01c6a8494c61', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (19, '2033d85a-ee74-461d-bfb5-17e1f6bc7710', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (17, 'c5e5e5c1-c1a4-4cf1-b208-faf1c170b2de', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (20, '0072f1d7-d5e0-4ed7-b1e7-c1de73bcnams', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO mother_case VALUES (13, '4a3be947-717b-4628-b84c-6d7e62d9a3f8', 'Test Update Name', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- Name: mother_case_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('mother_case_id_seq', 20, true);


--
-- Data for Name: new_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO new_form VALUES (1, '268e68c6-73c6-4f68-8cba-cdd5c53e266f', '2012-08-02 04:07:52.716+02', '2012-08-02 04:03:13.031+02', 1, 1, '2012-08-02 04:07:52.716+02', 0, NULL, NULL, NULL, NULL, 2, 70, 'सलैन पासवान', NULL, NULL, NULL, NULL, 'sc_st', NULL, false, 'रोशन देवी', NULL);
INSERT INTO new_form VALUES (2, '5f586e64-868e-4e91-b9e5-0bc93d9a1d15', '2012-07-09 11:39:43.878+02', '2012-07-09 11:38:32.115+02', 9, 5, '2012-07-09 11:39:43.878+02', 0, NULL, NULL, NULL, NULL, 8, 184, 'विपिन झा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'गुङिया देवी', NULL);
INSERT INTO new_form VALUES (7, '5f586e64-868e-4e91-b9e5-0bc93inst004', '2012-07-09 11:39:43.878+02', '2012-07-09 11:38:32.115+02', 15, 11, '2012-07-09 11:39:43.878+02', 0, NULL, NULL, NULL, NULL, 8, 184, 'विपिन झा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'गुङिया देवी', NULL);
INSERT INTO new_form VALUES (8, '3db1b50e-b3c1-43ed-8390-4ac86750e08f', '2012-08-23 10:05:07.98+02', '2012-08-23 10:03:38.78+02', 16, 12, '2012-08-23 10:05:07.98+02', 0, NULL, NULL, NULL, NULL, 5, 203, 'बिन्देश्वर साह', NULL, NULL, NULL, NULL, 'other', NULL, false, 'मीना देवी', NULL);
INSERT INTO new_form VALUES (9, 'dc405899-b367-4f99-9d9d-3b3583425afc', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (10, 'dc405899-b367-4f99-9d9d-3b358342test', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (11, 'dc405899-b367-4f99-9d9d-3b35834test1', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (12, 'dc405899-b367-4f99-9d9d-3b35834test2', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹', NULL, NULL, NULL, NULL, 'other', NULL, false, 'à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥', NULL);
INSERT INTO new_form VALUES (13, '0367da59-c14c-44b1-b973-ba9fede46d7d', '2012-09-01 16:47:49.259+02', '2012-09-01 16:44:49.006+02', 22, 19, '2012-09-01 16:47:49.259+02', 0, NULL, NULL, NULL, NULL, 2, 271, 'जीया उद्दीन ', NULL, NULL, NULL, NULL, 'other', NULL, false, 'इझारुण खातुन ', NULL);
INSERT INTO new_form VALUES (14, 'b97a9a71-4c89-4da4-9c95-492cf114faf5', '2012-08-17 09:39:51.276+02', '2012-08-17 09:38:58.436+02', 20, 17, '2012-08-17 09:39:51.276+02', 0, NULL, NULL, NULL, NULL, 2, 166, 'बीरबल शर्मा', NULL, NULL, NULL, NULL, 'other', NULL, false, 'मुलीया देवी', NULL);
INSERT INTO new_form VALUES (19, 'dc405899-b367-4f99-9d9d-3b3583425af1', '2013-04-19 15:58:08.032+02', '2013-04-19 15:51:30.981+02', 17, 13, '2013-04-19 15:58:08.032+02', 0, NULL, NULL, NULL, NULL, 0, 54, 'र्प्रमोद साह', NULL, NULL, NULL, NULL, 'other', NULL, false, 'सुमितरा देवी', NULL);


--
-- Name: new_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('new_form_id_seq', 19, true);


--
-- Data for Name: pnc_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pnc_child_form VALUES (1, 'ea830d5f-53b9-4c93-b099-f3878fd1e3c6', '2013-04-25 12:45:14.535+02', '2013-04-25 12:40:36.384+02', 8, 5, '2013-04-25 12:45:14.535+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (2, 'ca543abc-252f-470a-b1cb-a5aecfd595ab', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 10, '2013-05-18 10:46:43.914+02', NULL, true, false, true, true, NULL, NULL, NULL, NULL, NULL, NULL, false, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (3, 'ca543abc-252f-470a-b1cb-a5aecfd5test', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 10, '2013-05-18 10:46:43.914+02', NULL, true, false, true, true, NULL, NULL, NULL, NULL, NULL, NULL, false, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (4, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 11, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (5, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 12, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (6, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 13, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (7, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 14, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (8, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 11, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (9, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 12, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (10, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 13, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO pnc_child_form VALUES (11, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 15, '2012-09-26 11:14:13.663+02', NULL, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, true, true, true, true, NULL, NULL, true, NULL, NULL, true, true, true, NULL, true, false, NULL, true, NULL, NULL, NULL);


--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pnc_child_form_id_seq', 11, true);


--
-- Data for Name: pnc_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pnc_mother_form VALUES (1, 'ea830d5f-53b9-4c93-b099-f3878fd1e3c6', '2013-04-25 12:45:14.535+02', '2013-04-25 12:40:36.384+02', 8, 4, '2013-04-25 12:45:14.535+02', true, '2013-04-23', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, true, NULL, NULL, NULL, NULL, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'refused');
INSERT INTO pnc_mother_form VALUES (6, 'ca543abc-252f-470a-b1cb-a5aecfd595ab', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 16, '2013-05-18 10:46:43.914+02', false, '2013-05-16', NULL, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, true, false, true, true, true, true, true, true, true, 'other');
INSERT INTO pnc_mother_form VALUES (7, 'ca543abc-252f-470a-b1cb-a5aecfd5test', '2013-05-18 10:46:43.914+02', '2013-05-18 10:42:29.189+02', 19, 16, '2013-05-18 10:46:43.914+02', false, '2013-05-16', NULL, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, true, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, true, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 1, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, true, false, true, true, true, true, true, true, true, 'other');
INSERT INTO pnc_mother_form VALUES (8, '153191fd-5d6b-4867-b3c6-e4858bf7c17e', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 17, '2012-09-26 11:14:13.663+02', false, '2012-09-22', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 4, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'incentive_related');
INSERT INTO pnc_mother_form VALUES (9, '153191fd-5d6b-4867-b3c6-e4858bf7test', '2012-09-26 11:14:13.663+02', '2012-09-26 11:09:14.377+02', 20, 17, '2012-09-26 11:14:13.663+02', false, '2012-09-22', false, NULL, false, 0, NULL, NULL, NULL, NULL, NULL, true, true, NULL, true, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, true, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, 'pnc', 4, NULL, false, NULL, NULL, NULL, NULL, NULL, 1, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'incentive_related');


--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('pnc_mother_form_id_seq', 9, true);


--
-- Data for Name: refer_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refer_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refer_child_form_id_seq', 1, false);


--
-- Data for Name: refer_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('refer_mother_form_id_seq', 1, false);


--
-- Data for Name: registration_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO registration_child_form VALUES (1, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 6, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (2, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 7, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (3, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 8, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);
INSERT INTO registration_child_form VALUES (4, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 9, '2012-10-12 12:21:14.226+02', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'male', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 3.0);


--
-- Name: registration_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('registration_child_form_id_seq', 4, true);


--
-- Data for Name: registration_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO registration_mother_form VALUES (1, '92b7bcda-3829-4ab9-a189-bbdb59ab5794', '2012-09-03 11:48:05.099+02', '2012-09-03 11:46:13.446+02', 10, 6, '2012-09-03 11:48:05.099+02', NULL, NULL, NULL, 23, 0, 23, 'OK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2012-10-13', false, 'middle', NULL, NULL, 8, true, NULL, NULL, true, NULL, true, NULL, 4, false, true, '2012-01-07', false, NULL, NULL, NULL, 'OK', 'bp', 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, NULL, 'resident', 'OK', true, false, NULL, true, NULL, NULL, NULL);
INSERT INTO registration_mother_form VALUES (2, '41b30323-67f0-4291-8309-09d771b5fee7', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 12, '2012-10-12 12:21:14.226+02', NULL, NULL, NULL, 40, 0, 40, 'OK', NULL, NULL, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, 6, NULL, '2012-05-12', 0, NULL, NULL, NULL, NULL, NULL, 'vaginal', NULL, NULL, NULL, 'illiterate', NULL, NULL, NULL, true, true, NULL, false, NULL, false, true, NULL, false, true, NULL, NULL, NULL, 'mobile_asha', 'OK', NULL, 'cf', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, 'resident', 'OK', NULL, false, NULL, true, 'hospital', 'block_phc', NULL);
INSERT INTO registration_mother_form VALUES (3, '41b30323-67f0-4291-8309-09d771btest1', '2012-10-12 12:21:14.226+02', '2012-10-12 12:15:54.726+02', 16, 15, '2012-10-12 12:21:14.226+02', NULL, NULL, NULL, 40, 0, 40, 'OK', NULL, NULL, NULL, NULL, false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL, 2, false, NULL, NULL, NULL, NULL, NULL, 6, NULL, '2012-05-12', 0, NULL, NULL, NULL, NULL, NULL, 'vaginal', NULL, NULL, NULL, 'illiterate', NULL, NULL, NULL, true, true, NULL, false, NULL, false, true, NULL, false, true, NULL, NULL, NULL, 'mobile_asha', 'OK', NULL, 'cf', 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, true, NULL, 'resident', 'OK', NULL, false, NULL, true, 'hospital', 'block_phc', NULL);


--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('registration_mother_form_id_seq', 3, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO schema_version VALUES ('0', 'Base setup', 'INIT', 'Base setup', NULL, 'postgres', '2013-06-07 15:05:54.201319', 0, 'SUCCESS', false);
INSERT INTO schema_version VALUES ('1.0', NULL, 'SQL', 'V1_0.sql', -141017564, 'postgres', '2013-06-07 15:05:54.948092', 732, 'SUCCESS', true);


--
-- Data for Name: ui_child_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: ui_child_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ui_child_form_id_seq', 1, false);


--
-- Data for Name: ui_mother_form; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('ui_mother_form_id_seq', 1, false);


SET search_path = carereporting, pg_catalog;

--
-- Name: abort_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_instance_id_key UNIQUE (instance_id);


--
-- Name: abort_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_pkey PRIMARY KEY (id);


--
-- Name: bp_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_instance_id_key UNIQUE (instance_id);


--
-- Name: bp_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_pkey PRIMARY KEY (id);


--
-- Name: cf_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: cf_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_pkey PRIMARY KEY (id);


--
-- Name: cf_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: cf_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_pkey PRIMARY KEY (id);


--
-- Name: child_case_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_pkey PRIMARY KEY (id);


--
-- Name: close_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: close_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_pkey PRIMARY KEY (id);


--
-- Name: close_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: close_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_pkey PRIMARY KEY (id);


--
-- Name: death_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: death_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_pkey PRIMARY KEY (id);


--
-- Name: death_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: death_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_pkey PRIMARY KEY (id);


--
-- Name: delivery_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: delivery_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_pkey PRIMARY KEY (id);


--
-- Name: delivery_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: delivery_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_pkey PRIMARY KEY (id);


--
-- Name: ebf_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: ebf_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_pkey PRIMARY KEY (id);


--
-- Name: ebf_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: ebf_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_pkey PRIMARY KEY (id);


--
-- Name: flw_flw_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw
    ADD CONSTRAINT flw_flw_id_key UNIQUE (flw_id);


--
-- Name: flw_group_group_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group
    ADD CONSTRAINT flw_group_group_id_key UNIQUE (group_id);


--
-- Name: flw_group_map_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_pkey PRIMARY KEY (id);


--
-- Name: flw_group_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group
    ADD CONSTRAINT flw_group_pkey PRIMARY KEY (id);


--
-- Name: flw_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw
    ADD CONSTRAINT flw_pkey PRIMARY KEY (id);


--
-- Name: mi_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_instance_id_key UNIQUE (instance_id);


--
-- Name: mi_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_pkey PRIMARY KEY (id);


--
-- Name: mo_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_instance_id_key UNIQUE (instance_id);


--
-- Name: mo_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_pkey PRIMARY KEY (id);


--
-- Name: mother_case_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_case_id_key UNIQUE (case_id);


--
-- Name: mother_case_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_pkey PRIMARY KEY (id);


--
-- Name: new_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_instance_id_key UNIQUE (instance_id);


--
-- Name: new_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_pkey PRIMARY KEY (id);


--
-- Name: pnc_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: pnc_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_pkey PRIMARY KEY (id);


--
-- Name: pnc_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: pnc_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_pkey PRIMARY KEY (id);


--
-- Name: refer_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: refer_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_pkey PRIMARY KEY (id);


--
-- Name: refer_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: refer_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_pkey PRIMARY KEY (id);


--
-- Name: registration_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: registration_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_pkey PRIMARY KEY (id);


--
-- Name: registration_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: registration_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_pkey PRIMARY KEY (id);


--
-- Name: schema_version_primary_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_primary_key PRIMARY KEY (version);


--
-- Name: schema_version_script_unique; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_script_unique UNIQUE (script);


--
-- Name: ui_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: ui_child_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_pkey PRIMARY KEY (id);


--
-- Name: ui_mother_form_instance_id_key; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: ui_mother_form_pkey; Type: CONSTRAINT; Schema: carereporting; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_pkey PRIMARY KEY (id);


SET search_path = public, pg_catalog;

--
-- Name: abort_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_instance_id_key UNIQUE (instance_id);


--
-- Name: abort_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_pkey PRIMARY KEY (id);


--
-- Name: bp_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_instance_id_key UNIQUE (instance_id);


--
-- Name: bp_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_pkey PRIMARY KEY (id);


--
-- Name: cf_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: cf_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_pkey PRIMARY KEY (id);


--
-- Name: cf_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: cf_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_pkey PRIMARY KEY (id);


--
-- Name: child_case_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_pkey PRIMARY KEY (id);


--
-- Name: close_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: close_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_pkey PRIMARY KEY (id);


--
-- Name: close_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: close_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_pkey PRIMARY KEY (id);


--
-- Name: death_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: death_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_pkey PRIMARY KEY (id);


--
-- Name: death_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: death_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_pkey PRIMARY KEY (id);


--
-- Name: delivery_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: delivery_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_pkey PRIMARY KEY (id);


--
-- Name: delivery_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: delivery_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_pkey PRIMARY KEY (id);


--
-- Name: ebf_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: ebf_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_pkey PRIMARY KEY (id);


--
-- Name: ebf_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: ebf_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_pkey PRIMARY KEY (id);


--
-- Name: flw_flw_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw
    ADD CONSTRAINT flw_flw_id_key UNIQUE (flw_id);


--
-- Name: flw_group_group_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group
    ADD CONSTRAINT flw_group_group_id_key UNIQUE (group_id);


--
-- Name: flw_group_map_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_pkey PRIMARY KEY (id);


--
-- Name: flw_group_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw_group
    ADD CONSTRAINT flw_group_pkey PRIMARY KEY (id);


--
-- Name: flw_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY flw
    ADD CONSTRAINT flw_pkey PRIMARY KEY (id);


--
-- Name: mi_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_instance_id_key UNIQUE (instance_id);


--
-- Name: mi_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_pkey PRIMARY KEY (id);


--
-- Name: mo_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_instance_id_key UNIQUE (instance_id);


--
-- Name: mo_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_pkey PRIMARY KEY (id);


--
-- Name: mother_case_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_case_id_key UNIQUE (case_id);


--
-- Name: mother_case_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_pkey PRIMARY KEY (id);


--
-- Name: new_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_instance_id_key UNIQUE (instance_id);


--
-- Name: new_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_pkey PRIMARY KEY (id);


--
-- Name: pnc_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: pnc_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_pkey PRIMARY KEY (id);


--
-- Name: pnc_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: pnc_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_pkey PRIMARY KEY (id);


--
-- Name: refer_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: refer_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_pkey PRIMARY KEY (id);


--
-- Name: refer_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: refer_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_pkey PRIMARY KEY (id);


--
-- Name: registration_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: registration_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_pkey PRIMARY KEY (id);


--
-- Name: registration_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: registration_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_pkey PRIMARY KEY (id);


--
-- Name: schema_version_primary_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_primary_key PRIMARY KEY (version);


--
-- Name: schema_version_script_unique; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY schema_version
    ADD CONSTRAINT schema_version_script_unique UNIQUE (script);


--
-- Name: ui_child_form_instance_id_case_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_instance_id_case_id_key UNIQUE (instance_id, case_id);


--
-- Name: ui_child_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_pkey PRIMARY KEY (id);


--
-- Name: ui_mother_form_instance_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_instance_id_key UNIQUE (instance_id);


--
-- Name: ui_mother_form_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_pkey PRIMARY KEY (id);


SET search_path = carereporting, pg_catalog;

--
-- Name: schema_version_current_version_index; Type: INDEX; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE INDEX schema_version_current_version_index ON schema_version USING btree (current_version);


SET search_path = public, pg_catalog;

--
-- Name: schema_version_current_version_index; Type: INDEX; Schema: public; Owner: postgres; Tablespace: 
--

CREATE INDEX schema_version_current_version_index ON schema_version USING btree (current_version);


SET search_path = carereporting, pg_catalog;

--
-- Name: abort_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: abort_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: bp_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: bp_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: cf_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: cf_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: cf_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: cf_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: child_case_mother_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_mother_id_fkey FOREIGN KEY (mother_id) REFERENCES mother_case(id);


--
-- Name: child_case_owner_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES flw_group(id);


--
-- Name: child_case_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: close_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: close_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: close_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: close_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: death_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: death_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: death_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: death_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: delivery_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: delivery_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: delivery_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: delivery_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ebf_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: ebf_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ebf_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: ebf_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: flw_group_map_flw_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_flw_id_fkey FOREIGN KEY (flw_id) REFERENCES flw(id);


--
-- Name: flw_group_map_group_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_group_id_fkey FOREIGN KEY (group_id) REFERENCES flw_group(id);


--
-- Name: mi_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: mi_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: mo_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: mo_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: mother_case_owner_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES flw_group(id);


--
-- Name: mother_case_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: new_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: new_form_manual_group_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_manual_group_fkey FOREIGN KEY (manual_group) REFERENCES flw_group(id);


--
-- Name: new_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: pnc_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: pnc_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: pnc_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: pnc_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: refer_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: refer_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: refer_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: refer_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: registration_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: registration_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: registration_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: registration_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ui_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: ui_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ui_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: ui_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: carereporting; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


SET search_path = public, pg_catalog;

--
-- Name: abort_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: abort_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY abort_form
    ADD CONSTRAINT abort_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: bp_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: bp_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY bp_form
    ADD CONSTRAINT bp_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: cf_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: cf_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_child_form
    ADD CONSTRAINT cf_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: cf_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: cf_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cf_mother_form
    ADD CONSTRAINT cf_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: child_case_mother_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_mother_id_fkey FOREIGN KEY (mother_id) REFERENCES mother_case(id);


--
-- Name: child_case_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES flw_group(id);


--
-- Name: child_case_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY child_case
    ADD CONSTRAINT child_case_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: close_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: close_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_child_form
    ADD CONSTRAINT close_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: close_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: close_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY close_mother_form
    ADD CONSTRAINT close_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: death_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: death_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_child_form
    ADD CONSTRAINT death_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: death_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: death_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY death_mother_form
    ADD CONSTRAINT death_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: delivery_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: delivery_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_child_form
    ADD CONSTRAINT delivery_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: delivery_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: delivery_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY delivery_mother_form
    ADD CONSTRAINT delivery_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ebf_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: ebf_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_child_form
    ADD CONSTRAINT ebf_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ebf_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: ebf_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ebf_mother_form
    ADD CONSTRAINT ebf_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: flw_group_map_flw_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_flw_id_fkey FOREIGN KEY (flw_id) REFERENCES flw(id);


--
-- Name: flw_group_map_group_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY flw_group_map
    ADD CONSTRAINT flw_group_map_group_id_fkey FOREIGN KEY (group_id) REFERENCES flw_group(id);


--
-- Name: mi_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: mi_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mi_form
    ADD CONSTRAINT mi_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: mo_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: mo_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mo_form
    ADD CONSTRAINT mo_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: mother_case_owner_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_owner_id_fkey FOREIGN KEY (owner_id) REFERENCES flw_group(id);


--
-- Name: mother_case_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mother_case
    ADD CONSTRAINT mother_case_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: new_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: new_form_manual_group_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_manual_group_fkey FOREIGN KEY (manual_group) REFERENCES flw_group(id);


--
-- Name: new_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY new_form
    ADD CONSTRAINT new_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: pnc_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: pnc_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_child_form
    ADD CONSTRAINT pnc_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: pnc_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: pnc_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pnc_mother_form
    ADD CONSTRAINT pnc_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: refer_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: refer_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_child_form
    ADD CONSTRAINT refer_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: refer_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: refer_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY refer_mother_form
    ADD CONSTRAINT refer_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: registration_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: registration_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_child_form
    ADD CONSTRAINT registration_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: registration_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: registration_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY registration_mother_form
    ADD CONSTRAINT registration_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ui_child_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES child_case(id);


--
-- Name: ui_child_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_child_form
    ADD CONSTRAINT ui_child_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: ui_mother_form_case_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_case_id_fkey FOREIGN KEY (case_id) REFERENCES mother_case(id);


--
-- Name: ui_mother_form_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY ui_mother_form
    ADD CONSTRAINT ui_mother_form_user_id_fkey FOREIGN KEY (user_id) REFERENCES flw(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

