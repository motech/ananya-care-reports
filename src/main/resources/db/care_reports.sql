--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UNICODE';
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


--
-- Data for Name: abort_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY abort_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, abortion_type, close, birth_status, date_aborted) FROM stdin;
\.


--
-- Name: abort_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('abort_form_id_seq', 1, false);


--
-- Data for Name: bp_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY bp_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, anc_latest_date, anc_latest_num, anc1_abdominal_exam, anc1_abnormalities, anc1_blood_pressure, anc1_date, anc1_facility, anc1_details, anc2_abdominal_exam, anc2_abnormalities, anc2_blood_pressure, anc2_date, anc2_facility, anc2_details, anc3_abdominal_exam, anc3_abnormalities, anc3_blood_pressure, anc3_date, anc3_facility, anc3_details, anc4_abdominal_exam, anc4_abnormalities, anc4_blood_pressure, anc4_date, anc4_facility, anc4_details, counsel_ifa, counsel_tt, eating_extra, ifa_tablets_issued, reason_no_ifa, received_tt1, received_tt2, resting, tt1_date, tt2_date, tt_booster, tt_booster_date, using_ifa, sba, sba_phone, accompany, care_of_home, clean_cloth, cord_care, counsel_home_delivery, counsel_institutional, counsel_preparation, danger_institution, danger_number, has_danger_signs, immediate_breastfeeding, inform_danger_signs, materials, maternal_danger_signs, now_institutional, phone_vehicle, play_birth_preparedness_vid, play_cord_care_vid, saving_money, skin_to_skin, vehicle, wrapping, bp_visit_num, anc_1_date, anc_2_date, anc_3_date, anc_4_date, couple_interested, date_bp_1, date_bp_2, date_bp_3, date_last_visit, date_next_bp, delivery_type, ifa_tablets, ifa_tablets_100, last_visit_type, maternal_emergency, maternal_emergency_number, tt_1_date, tt_2_date, conceive, del_fup, avail_immediate, counsel_accessible, counsel_benefits, counsel_disqualification, counsel_institution, counsel_methods, counsel_nearest, counsel_options, counsel_stay, immediate_appropriate, institution_immediate, postpone_conception, risk_of_preg, spacing_methods, stop_children, ifa_tablets_total, nextvisittype, play_family_planning_vid, postponing) FROM stdin;
1	8b7de0ec-24ec-4c98-beaa-e2801beb81c6	2013-04-24 18:09:01.072+05:30	2013-04-24 18:07:40.368+05:30	17	13	2013-04-24 18:09:01.072+05:30	2013-04-24	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	home	\N	\N	\N	\N	\N	\N	\N	\N	16002	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	30	bp	\N	\N
2	8b7de0ec-24ec-4c98-beaa-e2801bebtest	2013-04-24 18:09:01.072+05:30	2013-04-24 18:07:40.368+05:30	17	13	2013-04-24 18:09:01.072+05:30	2013-04-24	0	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	1	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	home	\N	\N	\N	\N	\N	\N	\N	\N	16002	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	30	bp	\N	\N
3	ad1f2eb2-0059-40c2-ba6c-9e2d0ecad6ff	2012-08-17 16:03:31.966+05:30	2012-08-17 15:51:33.201+05:30	18	14	2012-08-17 16:03:31.966+05:30	2012-07-06	3	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	2	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	institutional	\N	\N	\N	\N	\N	\N	\N	t	15619	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	100	bp	f	undecided
\.


--
-- Name: bp_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('bp_form_id_seq', 3, true);


--
-- Data for Name: cf_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY cf_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, add_vaccinations, amount_good, baby_bcg, baby_dpt1, baby_dpt2, baby_dpt3, baby_hep_b_0, baby_hep_b_1, baby_hep_b_2, baby_hep_b_3, baby_measles, baby_opv0, baby_opv1, baby_opv2, baby_opv3, baby_vita1, bcg_date, case_name, dpt_1_date, dpt_2_date, dpt_3_date, hep_b_0_date, hep_b_1_date, hep_b_2_date, hep_b_3_date, measles_date, opv_0_date, opv_1_date, opv_2_date, opv_3_date, vit_a_1_date, dal, eaten_cereal, egg, fish, meat, milk_curd, more_feeding_less_six, name_update, new_name, number_good, oil_ghee, recent_fever, treated_less_six, baby_dpt_booster, baby_je, baby_measles_booster, baby_opv_booster, baby_vita2, baby_vita3, date_je, date_measles_booster, dpt_booster_date, opv_booster_date, vit_a_3_date, vit_a_2_date, close) FROM stdin;
1	1f093281-233e-4afc-a235-077d2dc183e9	2013-05-16 10:20:07.932+05:30	2013-05-16 10:14:00.953+05:30	2	1	2013-05-16 10:20:07.932+05:30	t	t	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	t	f	f	f	t	\N	f	\N	t	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2	1f093281-233e-4afc-a235-077d2dc183e9	2013-05-16 10:20:07.932+05:30	2013-05-16 10:14:00.953+05:30	2	2	2013-05-16 10:20:07.932+05:30	t	t	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	t	f	f	f	t	\N	f	\N	t	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	1f093281-233e-4afc-a235-077d2dc183e9	2013-05-16 10:20:07.932+05:30	2013-05-16 10:14:00.953+05:30	2	3	2013-05-16 10:20:07.932+05:30	t	t	\N	\N	\N	\N	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	t	f	f	f	t	\N	f	\N	t	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Name: cf_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('cf_child_form_id_seq', 3, true);


--
-- Data for Name: cf_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY cf_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, date_cf_1, date_cf_2, date_cf_3, date_cf_4, date_cf_5, date_cf_6, date_last_visit, date_next_cf, last_visit_type, cf_visit_num, children, num_children, play_comp_feeding_vid, lastvisit, date_cf_7, confirm_close, close) FROM stdin;
1	1f093281-233e-4afc-a235-077d2dc183e9	2013-05-16 10:20:07.932+05:30	2013-05-16 10:14:00.953+05:30	2	2	2013-05-16 10:20:07.932+05:30	\N	\N	\N	\N	\N	\N	\N	\N	\N	1	0	3	t	\N	\N	\N	\N
\.


--
-- Name: cf_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('cf_mother_form_id_seq', 1, true);


--
-- Data for Name: child_case; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY child_case (id, case_id, case_name, date_modified, server_date_modified, server_date_opened, mother_id, case_type, owner_id, user_id, baby_measles, bcg_date, birth_status, dob, dpt_1_date, dpt_2_date, dpt_3_date, gender, hep_b_0_date, hep_b_1_date, hep_b_2_date, hep_b_3_date, measles_date, opv_0_date, opv_1_date, opv_2_date, opv_3_date, vit_a_1_date, child_alive, dpt_booster_date, opv_booster_date, date_je, date_measles_booster, baby_weight, name, term, time_of_birth, vit_a_2_date, vit_a_3_date, closed, date_closed) FROM stdin;
1	d4b48d5f-4d44-4537-8ed5-b756217f5107	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2	c898c2d0-6655-4885-bd24-6199e6db53f8	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	5728524a-8d1f-46d3-9dc2-1b5b246662eb	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
4	13c79f6b-fc8a-43b7-b07d-5076a218ef1e	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
5	0fb489c5-68ca-4132-afa5-2ef69c44c63a	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
7	a5d567e0-8919-44bc-9073-93cb0142ab2d	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
6	8442ff71-8abd-44fb-b318-c3feb891ea70	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
8	8442ff71-8abd-44fb-b318-c3febtest001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
9	a5d567e0-8919-44bc-9073-93cb0test001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
10	0331e566-25d6-43e7-9730-ebdb9d4ef95d	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
14	47b14730-cac4-4625-9f37-e2e1aea8test	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
11	815e1d04-8cc7-4f12-989f-8aba24e80381	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
12	9a1b538f-42f3-421f-9fbd-b3937c7e1bf7	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
13	18ae62cc-b04b-4775-beff-b977b6a0cf2d	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
15	47b14730-cac4-4625-9f37-e2e1aea84888	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Name: child_case_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('child_case_id_seq', 15, true);


--
-- Data for Name: close_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY close_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, close, child_alive, close_child, confirm_close, date_death, died, died_village, dupe_reg, finished_continuum, site_death, place_death) FROM stdin;
\.


--
-- Name: close_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('close_child_form_id_seq', 1, false);


--
-- Data for Name: close_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY close_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, close, children, close_mother, confirm_close, death_village, died_village, dupe_reg, finished_continuum, num_children, mother_alive, moved, migrated, date_learned, date_left, migration_note, died, date_death, site_death) FROM stdin;
1	8817c814-538b-4dc7-91bc-9f8783d3fb18	2012-10-12 14:40:24.402+05:30	2012-10-12 14:39:56.478+05:30	16	12	2012-10-12 14:40:24.402+05:30	\N	0	t	\N	\N	\N	f	f	0	\N	f	f	\N	\N	\N	f	\N	\N
\.


--
-- Name: close_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('close_mother_form_id_seq', 5, true);


--
-- Data for Name: death_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY death_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, close, child_alive, child_died_village, child_place_death, child_site_death, chld_date_death) FROM stdin;
1	e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336	2012-12-07 13:26:26.295+05:30	2012-12-07 13:25:17.03+05:30	3	4	2012-12-07 13:26:26.295+05:30	\N	f	t	\N	home	2012-11-07
2	359727ca-9bb2-4e5b-966b-8e9c13ddb4c3	2012-10-12 15:53:31.612+05:30	2012-10-12 15:52:22.615+05:30	16	7	2012-10-12 15:53:31.612+05:30	\N	f	t	\N	home	2012-06-01
3	359727ca-9bb2-4e5b-966b-8e9c13ddb4c3	2012-10-12 15:53:31.612+05:30	2012-10-12 15:52:22.615+05:30	16	6	2012-10-12 15:53:31.612+05:30	\N	f	t	\N	home	2012-06-02
\.


--
-- Name: death_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('death_child_form_id_seq', 3, true);


--
-- Data for Name: death_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY death_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, close, mother_alive, status, cast_num_children, children, date_death, death_village, num_children, place_death, site_death) FROM stdin;
1	e4b5e9f3-aa29-4fb3-a8d0-29e6356c5336	2012-12-07 13:26:26.295+05:30	2012-12-07 13:25:17.03+05:30	3	3	2012-12-07 13:26:26.295+05:30	\N	\N	\N	1	0	\N	\N	1	\N	\N
2	b615dccc-bda9-4f28-9d4e-d4988e4e1d18	2012-08-24 11:14:55.103+05:30	2012-08-24 11:14:41.198+05:30	16	12	2012-08-24 11:14:55.103+05:30	\N	\N	\N	0	0	\N	\N	0	\N	\N
3	359727ca-9bb2-4e5b-966b-8e9c13ddb4c3	2012-10-12 15:53:31.612+05:30	2012-10-12 15:52:22.615+05:30	16	12	2012-10-12 15:53:31.612+05:30	\N	\N	\N	2	0	\N	\N	2	\N	\N
\.


--
-- Name: death_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('death_mother_form_id_seq', 3, true);


--
-- Data for Name: delivery_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY delivery_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, abnormalities, add_vaccinations, baby_bcg, baby_hep_b_0, baby_opv0, breastfed_hour, close, case_name, case_type, baby_weight, bcg_date, birth_status, dob, gender, hep_b_0_date, opv_0_date, term, time_of_birth, child_alive, child_breathing, child_cried, child_died_village, child_have_a_name, child_heartbeats, child_movement, child_name, child_place_death, child_site_death, chld_date_death, cord_applied, cord_cut, cord_tied, date_first_weight, date_time_feed, first_weight, skin_care, what_applied, wrapped_dried) FROM stdin;
\.


--
-- Name: delivery_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('delivery_child_form_id_seq', 1, false);


--
-- Data for Name: delivery_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY delivery_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, ppiud, pptl, abd_pain, add, close, birth_place, date_del_fu, date_last_visit, date_next_cf, date_next_eb, date_next_pnc, family_planning_type, last_visit_type, mother_alive, term, cast_num_children, complications, date_death, death_village, delivery_nature, fever, has_delivered, how_many_children, ifa_tablets_given, in_district, jsy_money, nextvisittype, notified, num_children, other_conditions, other_district, other_village, pain_urine, place_death, post_postpartum_fp, safe, site_death, vaginal_discharge, where_born, which_hospital, which_village) FROM stdin;
\.


--
-- Name: delivery_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('delivery_mother_form_id_seq', 1, false);


--
-- Data for Name: ebf_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY ebf_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, add_vaccinations, at_night, baby_bcg, baby_dpt1, baby_dpt2, baby_dpt3, baby_hep_b_0, baby_hep_b_1, baby_hep_b_2, baby_hep_b_3, baby_opv0, baby_opv1, baby_opv2, baby_opv3, bcg_date, breastfeeding, case_name, child_name, counsel_adequate_bf, counsel_only_milk, counsel_stop_bottle, dpt_1_date, dpt_2_date, dpt_3_date, eating, emptying, feeding_bottle, hep_b_0_date, hep_b_1_date, hep_b_2_date, hep_b_3_date, more_feeding_less_six, name_update, not_breasfeeding, on_demand, opv_0_date, opv_1_date, opv_2_date, opv_3_date, recent_fever, tea_other, treated_less_six, water_or_milk) FROM stdin;
\.


--
-- Name: ebf_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ebf_child_form_id_seq', 1, false);


--
-- Data for Name: ebf_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY ebf_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, addval, adopt_immediately, ask_ppiud, aware_of_failure, bleeding, children, complications, condoms, counsel_follow_up_ppiud, counsel_follow_up_pptl, counsel_menstrual_cycle, counsel_methods, counsel_ppfp, counsel_time_iud, date_eb_1, date_eb_2, date_eb_3, date_eb_4, date_eb_5, date_eb_6, date_iud_adopted, date_last_inj, date_last_visit, date_next_cf, date_next_eb, discharge, distension, eb_visit_num, family_planning_type, fever, have_condoms, headaches, high_bp, inj_menstrual_irregularity, injectable, intend_to_continue, interval_ppfp_interest, iud, iud_adopted, iud_counsel_duration, iud_counsel_follow_up, iud_counsel_hospital, iud_counsel_placement, iud_counsel_screening, iud_counsel_side_effects, last_visit_type, menstrual_irregularity, next_inj_calc, nextvisittype, num_children, ocp, ocp_continue, ocp_counsel_regularity, pain_swelling, ppfp_interest, ppiud_abdominal_pain, ppiud_problems, pptl_abdominal_pain, pptl_pain_surgery, pptl_problems, regular_periods, tablets_received, taken_as_prescribed, tl, tl_adopted, tl_consel_incentives, tl_counsel_follow_up, tl_counsel_hospital, tl_counsel_irreversible, tl_counsel_screening, tl_counsel_side_effects, tl_counsel_timing, understand_tablets, using_correctly, where_replace, why_no_ppffp, within_42, date_tl_adopted, abdominal_pain, pain_urination, ppiud_bleeding, ppiud_discharge, ppiud_fever) FROM stdin;
\.


--
-- Name: ebf_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ebf_mother_form_id_seq', 1, false);


--
-- Data for Name: flw; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY flw (id, flw_id, default_phone_number, email, first_name, last_name, phone_number_1, phone_number_2, asset_id, awc_code, role, subcentre, user_type, username, population, education, age, district, block, panchayat, village, ward, caste, dob, ictcordinator, remarks) FROM stdin;
1	89fda0284e008d2e0c980fb13f9b9e62	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2	89fda0284e008d2e0c980fb13f9f12b8	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	89fda0284e008d2e0c980fb13f9dc566	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
8	89fda0284e008d2e0c980fb13f9a3e49	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
9	89fda0284e008d2e0c980fuserNEW001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
10	89fda0284e008d2e0c980fReguser001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
15	89fda0284e008d2e0c980fuserNEW004	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
18	89fda0284e008d2e0c980fb13fa04709	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
16	89fda0284e008d2e0c980fb13f9c6eff	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
19	70c6ba6f55ed21716fe725c214694627	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
21	89fda0284e008d2e0c980fb13f99a86d	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
22	70c6ba6f55ed21716fe725c2146903eb	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
20	70c6ba6f55ed21716fe725c21469099f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
23	89fda0284e008d2e0c980fb13f9f74db	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
17	89fda0284e008d2e0c980fb13f98abea	\N	\N	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Data for Name: flw_group; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY flw_group (id, group_id, case_sharing, domain, awc_code, name, reporting) FROM stdin;
\.


--
-- Name: flw_group_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('flw_group_id_seq', 1, false);


--
-- Data for Name: flw_group_map; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY flw_group_map (id, flw_id, group_id) FROM stdin;
\.


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

COPY mi_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, date_arrived, date_learned, date_of_delivery, name, preg_status, referral_info, abortion_type, date_aborted, migrated_status) FROM stdin;
1	b098e949-c3f1-4b4e-9a5e-c2d76561nams	2013-03-06 15:42:13.894+05:30	2013-03-06 15:41:30.512+05:30	23	20	2013-03-06 15:42:13.894+05:30	2013-02-25	2013-02-25	\N	\N	aborted	\N	less_12_weeks	2013-02-18	\N
\.


--
-- Name: mi_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mi_form_id_seq', 1, true);


--
-- Data for Name: mo_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY mo_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, migrate_out_date, migrated_status, status, date_learned, date_left, name, note_given) FROM stdin;
1	f641a83f-ff5f-4cde-aad0-282242695d86	2012-12-08 12:08:33.158+05:30	2012-12-08 12:07:27.789+05:30	21	18	2012-12-08 12:08:33.158+05:30	\N	\N	\N	\N	2012-11-25	\N	f
\.


--
-- Name: mo_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mo_form_id_seq', 1, true);


--
-- Data for Name: mother_case; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY mother_case (id, case_id, case_name, case_type, owner_id, user_id, date_modified, server_date_modified, server_date_opened, family_number, hh_number, husband_name, last_visit_type, mother_alive, mother_dob, mother_name, close, case_closed, closed_on, add, age, birth_place, complications, date_next_bp, date_next_cf, date_next_eb, date_next_pnc, eats_meat, edd, enrolled_in_kilkari, family_planning_type, how_many_children, interest_in_kilkari, last_preg_tt, lmp, mobile_number, num_boys, date_cf_1, date_cf_2, date_cf_3, date_cf_4, date_cf_5, date_cf_6, date_eb_1, date_eb_2, date_eb_3, date_eb_4, date_eb_5, date_eb_6, all_pnc_on_time, date_pnc_1, date_pnc_2, date_pnc_3, first_pnc_time, pnc_1_days_late, pnc_2_days_late, pnc_3_days_late, tt_booster_date, sba, sba_phone, accompany, anc_1_date, anc_2_date, anc_3_date, anc_4_date, clean_cloth, couple_interested, date_bp_1, date_bp_2, date_bp_3, date_last_visit, delivery_type, ifa_tablets, ifa_tablets_100, materials, maternal_emergency, maternal_emergency_number, phone_vehicle, saving_money, tt_1_date, tt_2_date, vehicle, birth_status, migrate_out_date, migrated_status, status, term, date_cf_7, date_del_fu, date_next_reg, institutional, dob, closed, date_closed) FROM stdin;
1	21fb973a-1916-4c17-b89c-2fa80dd7abc2	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
2	12b60388-a28b-4201-97a6-e9765186e793	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
3	4829bf83-86b2-41f7-b807-708f6death01	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
4	3ed54bd4-57ba-4222-916f-19133f25fc14	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
5	a53af344-b0ad-448a-afd2-01c6a8NEW001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
6	a53af344-b0ad-448a-afd2-01c6a8Reg001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
11	a53af344-b0ad-448a-afd2-01c6a8NEW004	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
12	2e1b5877-f3e4-4fac-a2c6-e3585ab10172	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
14	a890b016-47f4-43ae-b0b9-0cfbd6ff2030	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
15	2e1b5877-f3e4-4fac-a2c6-e3585test001	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
16	ede23f83-c88e-43d2-be70-9d6b983bdb12	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
18	a53af344-b0ad-448a-afd2-01c6a8494c61	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
19	2033d85a-ee74-461d-bfb5-17e1f6bc7710	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
17	c5e5e5c1-c1a4-4cf1-b208-faf1c170b2de	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
20	0072f1d7-d5e0-4ed7-b1e7-c1de73bcnams	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
13	4a3be947-717b-4628-b84c-6d7e62d9a3f8	Test Update Name	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N
\.


--
-- Name: mother_case_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('mother_case_id_seq', 20, true);


--
-- Data for Name: new_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY new_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, age_calc, case_name, case_type, date_last_visit, date_next_reg, family_number, hh_number, husband_name, last_visit_type, mother_alive, mother_dob, mother_name, caste, dob, dob_known, full_name, manual_group) FROM stdin;
1	268e68c6-73c6-4f68-8cba-cdd5c53e266f	2012-08-02 07:37:52.716+05:30	2012-08-02 07:33:13.031+05:30	1	1	2012-08-02 07:37:52.716+05:30	0	\N	\N	\N	\N	2	70	सलैन पासवान	\N	\N	\N	\N	sc_st	\N	f	रोशन देवी	\N
2	5f586e64-868e-4e91-b9e5-0bc93d9a1d15	2012-07-09 15:09:43.878+05:30	2012-07-09 15:08:32.115+05:30	9	5	2012-07-09 15:09:43.878+05:30	0	\N	\N	\N	\N	8	184	विपिन झा	\N	\N	\N	\N	other	\N	f	गुङिया देवी	\N
7	5f586e64-868e-4e91-b9e5-0bc93inst004	2012-07-09 15:09:43.878+05:30	2012-07-09 15:08:32.115+05:30	15	11	2012-07-09 15:09:43.878+05:30	0	\N	\N	\N	\N	8	184	विपिन झा	\N	\N	\N	\N	other	\N	f	गुङिया देवी	\N
8	3db1b50e-b3c1-43ed-8390-4ac86750e08f	2012-08-23 13:35:07.98+05:30	2012-08-23 13:33:38.78+05:30	16	12	2012-08-23 13:35:07.98+05:30	0	\N	\N	\N	\N	5	203	बिन्देश्वर साह	\N	\N	\N	\N	other	\N	f	मीना देवी	\N
9	dc405899-b367-4f99-9d9d-3b3583425afc	2013-04-19 19:28:08.032+05:30	2013-04-19 19:21:30.981+05:30	17	13	2013-04-19 19:28:08.032+05:30	0	\N	\N	\N	\N	0	54	à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹	\N	\N	\N	\N	other	\N	f	à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥	\N
10	dc405899-b367-4f99-9d9d-3b358342test	2013-04-19 19:28:08.032+05:30	2013-04-19 19:21:30.981+05:30	17	13	2013-04-19 19:28:08.032+05:30	0	\N	\N	\N	\N	0	54	à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹	\N	\N	\N	\N	other	\N	f	à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥	\N
11	dc405899-b367-4f99-9d9d-3b35834test1	2013-04-19 19:28:08.032+05:30	2013-04-19 19:21:30.981+05:30	17	13	2013-04-19 19:28:08.032+05:30	0	\N	\N	\N	\N	0	54	à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹	\N	\N	\N	\N	other	\N	f	à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥	\N
12	dc405899-b367-4f99-9d9d-3b35834test2	2013-04-19 19:28:08.032+05:30	2013-04-19 19:21:30.981+05:30	17	13	2013-04-19 19:28:08.032+05:30	0	\N	\N	\N	\N	0	54	à¤°à¥à¤ªà¥à¤°à¤®à¥à¤¦ à¤¸à¤¾à¤¹	\N	\N	\N	\N	other	\N	f	à¤¸à¥à¤®à¤¿à¤¤à¤°à¤¾ à¤¦à¥à¤µà¥	\N
13	0367da59-c14c-44b1-b973-ba9fede46d7d	2012-09-01 20:17:49.259+05:30	2012-09-01 20:14:49.006+05:30	22	19	2012-09-01 20:17:49.259+05:30	0	\N	\N	\N	\N	2	271	जीया उद्दीन 	\N	\N	\N	\N	other	\N	f	इझारुण खातुन 	\N
14	b97a9a71-4c89-4da4-9c95-492cf114faf5	2012-08-17 13:09:51.276+05:30	2012-08-17 13:08:58.436+05:30	20	17	2012-08-17 13:09:51.276+05:30	0	\N	\N	\N	\N	2	166	बीरबल शर्मा	\N	\N	\N	\N	other	\N	f	मुलीया देवी	\N
19	dc405899-b367-4f99-9d9d-3b3583425af1	2013-04-19 19:28:08.032+05:30	2013-04-19 19:21:30.981+05:30	17	13	2013-04-19 19:28:08.032+05:30	0	\N	\N	\N	\N	0	54	र्प्रमोद साह	\N	\N	\N	\N	other	\N	f	सुमितरा देवी	\N
\.


--
-- Name: new_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('new_form_id_seq', 19, true);


--
-- Data for Name: pnc_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY pnc_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, able_expressed_milk, adequate_support, applied_to_stump, baby_active, breastfeeding_well, child_alive, child_died_village, child_place_death, child_site_death, chld_date_death, close, cord_fallen, correct_position, counsel_cord_care, counsel_exclusive_bf, counsel_express_milk, counsel_skin, cousel_bf_correct, demonstrate_expressed, demonstrate_skin, easy_awake, feed_vigour, good_latch, improvements_bf, observed_bf, other_milk_to_child, second_observation, skin_to_skin, warm_to_touch, what_applied, wrapped) FROM stdin;
1	ea830d5f-53b9-4c93-b099-f3878fd1e3c6	2013-04-25 16:15:14.535+05:30	2013-04-25 16:10:36.384+05:30	8	5	2013-04-25 16:15:14.535+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
2	ca543abc-252f-470a-b1cb-a5aecfd595ab	2013-05-18 14:16:43.914+05:30	2013-05-18 14:12:29.189+05:30	19	10	2013-05-18 14:16:43.914+05:30	\N	t	f	t	t	\N	\N	\N	\N	\N	\N	f	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
3	ca543abc-252f-470a-b1cb-a5aecfd5test	2013-05-18 14:16:43.914+05:30	2013-05-18 14:12:29.189+05:30	19	10	2013-05-18 14:16:43.914+05:30	\N	t	f	t	t	\N	\N	\N	\N	\N	\N	f	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
4	153191fd-5d6b-4867-b3c6-e4858bf7c17e	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	11	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
5	153191fd-5d6b-4867-b3c6-e4858bf7c17e	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	12	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
6	153191fd-5d6b-4867-b3c6-e4858bf7c17e	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	13	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
7	153191fd-5d6b-4867-b3c6-e4858bf7c17e	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	14	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
8	153191fd-5d6b-4867-b3c6-e4858bf7test	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	11	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
9	153191fd-5d6b-4867-b3c6-e4858bf7test	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	12	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
10	153191fd-5d6b-4867-b3c6-e4858bf7test	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	13	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
11	153191fd-5d6b-4867-b3c6-e4858bf7test	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	15	2012-09-26 14:44:13.663+05:30	\N	t	\N	t	t	\N	\N	\N	\N	\N	\N	t	t	t	t	\N	\N	t	\N	\N	t	t	t	\N	t	f	\N	t	\N	\N	\N
\.


--
-- Name: pnc_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('pnc_child_form_id_seq', 11, true);


--
-- Data for Name: pnc_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY pnc_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, abdominal_pain, addval, adopt_immediately, all_pnc_on_time, bleeding, children, complications, congested, counsel_breast, counsel_follow_up_ppiud, counsel_follow_up_pptl, counsel_increase_food_bf, counsel_materal_comp, counsel_methods, counsel_neonatal_comp, counsel_ppfp, counsel_time_iud, date_death, date_iud_adopted, date_last_visit, date_next_eb, date_next_pnc, date_pnc_1, date_pnc_2, date_pnc_3, date_tl_adopted, death_village, discharge, distension, eating_well, family_planning_type, fever, first_pnc_time, interval_ppfp_interest, iud, iud_adopted, iud_counsel_duration, iud_counsel_follow_up, iud_counsel_hospital, iud_counsel_placement, iud_counsel_screening, iud_counsel_side_effects, last_visit_type, mother_alive, mother_child_alive, nextvisittype, num_children, other_issues, pain_urination, painful_nipples, place_death, pnc_1_days_late, pnc_2_days_late, pnc_3_days_late, pnc_visit_num, ppfp_interest, ppiud_abdominal_pain, ppiud_bleeding, ppiud_discharge, ppiud_fever, ppiud_problems, pptl_abdominal_pain, pptl_excessive_bleeding, pptl_pain_surgery, pptl_problems, problems_breast, safe, site_death, tl, tl_adopted, tl_consel_incentives, tl_counsel_follow_up, tl_counsel_hospital, tl_counsel_irreversible, tl_counsel_screening, tl_counsel_side_effects, tl_counsel_timing, why_no_ppffp) FROM stdin;
1	ea830d5f-53b9-4c93-b099-f3878fd1e3c6	2013-04-25 16:15:14.535+05:30	2013-04-25 16:10:36.384+05:30	8	4	2013-04-25 16:15:14.535+05:30	t	2013-04-23	f	\N	f	0	\N	\N	\N	\N	\N	t	t	\N	t	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	t	\N	t	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	pnc	1	\N	t	\N	\N	\N	\N	\N	2	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	refused
6	ca543abc-252f-470a-b1cb-a5aecfd595ab	2013-05-18 14:16:43.914+05:30	2013-05-18 14:12:29.189+05:30	19	16	2013-05-18 14:16:43.914+05:30	f	2013-05-16	\N	\N	f	0	\N	\N	\N	\N	\N	t	t	t	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	t	\N	f	\N	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	pnc	1	\N	f	\N	\N	\N	\N	\N	1	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	t	\N	t	f	t	t	t	t	t	t	t	other
7	ca543abc-252f-470a-b1cb-a5aecfd5test	2013-05-18 14:16:43.914+05:30	2013-05-18 14:12:29.189+05:30	19	16	2013-05-18 14:16:43.914+05:30	f	2013-05-16	\N	\N	f	0	\N	\N	\N	\N	\N	t	t	t	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	t	\N	f	\N	t	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	pnc	1	\N	f	\N	\N	\N	\N	\N	1	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	t	\N	t	f	t	t	t	t	t	t	t	other
8	153191fd-5d6b-4867-b3c6-e4858bf7c17e	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	17	2012-09-26 14:44:13.663+05:30	f	2012-09-22	f	\N	f	0	\N	\N	\N	\N	\N	t	t	\N	t	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	t	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	pnc	4	\N	f	\N	\N	\N	\N	\N	1	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	incentive_related
9	153191fd-5d6b-4867-b3c6-e4858bf7test	2012-09-26 14:44:13.663+05:30	2012-09-26 14:39:14.377+05:30	20	17	2012-09-26 14:44:13.663+05:30	f	2012-09-22	f	\N	f	0	\N	\N	\N	\N	\N	t	t	\N	t	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	t	\N	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	t	pnc	4	\N	f	\N	\N	\N	\N	\N	1	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	t	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	incentive_related
\.


--
-- Name: pnc_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('pnc_mother_form_id_seq', 9, true);


--
-- Data for Name: refer_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY refer_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, refer_child) FROM stdin;
\.


--
-- Name: refer_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('refer_child_form_id_seq', 1, false);


--
-- Data for Name: refer_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY refer_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, children, num_children, refer_mother) FROM stdin;
\.


--
-- Name: refer_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('refer_mother_form_id_seq', 1, false);


--
-- Data for Name: registration_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY registration_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, abnormalities, add_vaccinations, baby_bcg, baby_dpt1, baby_dpt2, baby_dpt3, baby_hep_b_0, baby_hep_b_1, baby_hep_b_2, baby_hep_b_3, baby_measles, baby_opv0, baby_opv1, baby_opv2, baby_opv3, baby_vita1, case_name, case_type, bcg_date, birth_status, dob, dpt_1_date, dpt_2_date, dpt_3_date, gender, hep_b_0_date, hep_b_1_date, hep_b_2_date, hep_b_3_date, measles_date, opv_0_date, opv_1_date, opv_2_date, opv_3_date, vit_a_1_date, child_have_a_name, child_name, weight) FROM stdin;
1	41b30323-67f0-4291-8309-09d771b5fee7	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	6	2012-10-12 15:51:14.226+05:30	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	male	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	3.0
2	41b30323-67f0-4291-8309-09d771b5fee7	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	7	2012-10-12 15:51:14.226+05:30	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	male	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	3.0
3	41b30323-67f0-4291-8309-09d771btest1	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	8	2012-10-12 15:51:14.226+05:30	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	male	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	3.0
4	41b30323-67f0-4291-8309-09d771btest1	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	9	2012-10-12 15:51:14.226+05:30	f	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	male	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	3.0
\.


--
-- Name: registration_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('registration_child_form_id_seq', 4, true);


--
-- Data for Name: registration_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY registration_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, ppiud, pptl, abd_pain, age_calc, age_calc_adj, age_est, age_est_trigger, close, add, age, birth_place, complications, date_last_visit, date_next_bp, date_next_cf, date_next_eb, date_next_pnc, eats_meat, edd, enrolled_in_kilkari, family_planning_type, how_many_children, interest_in_kilkari, last_preg_tt, last_visit_type, lmp, mobile_number, mother_dob, num_boys, status, child_dob, children, client_no_register, client_not_pregnant, clinical_exam, condoms, continue_preg, delivery_nature, dob_est, edd_calc, edd_known, education, fever, first_pregnancy, gest_age, good_to_register, in_district, injectible, is_pregnant, iud_used, jsy_beneficiary, jsy_money, last_preg, last_preg_c_section, last_preg_full_term, lmp_calc, lmp_known, missed_period, mobile_number_whose, nextvisit, nextvisit_bp, nextvisittype, num_children, num_girls, ocp_used, other_conditions, other_district, other_village, pain_urine, post_postpartum_fp, preg_desired, recently_delivered, referral_prompt, resident, success, urine_test, used_fp, vaginal_discharge, vegetarian, where_born, which_hospital, which_village) FROM stdin;
1	92b7bcda-3829-4ab9-a189-bbdb59ab5794	2012-09-03 15:18:05.099+05:30	2012-09-03 15:16:13.446+05:30	10	6	2012-09-03 15:18:05.099+05:30	\N	\N	\N	23	0	23	OK	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	\N	f	\N	\N	f	\N	\N	\N	\N	\N	0	\N	\N	0	\N	\N	\N	\N	\N	\N	\N	2012-10-13	f	middle	\N	\N	8	t	\N	\N	t	\N	t	\N	4	f	t	2012-01-07	f	\N	\N	\N	OK	bp	0	1	\N	\N	\N	\N	\N	\N	t	\N	\N	resident	OK	t	f	\N	t	\N	\N	\N
2	41b30323-67f0-4291-8309-09d771b5fee7	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	12	2012-10-12 15:51:14.226+05:30	\N	\N	\N	40	0	40	OK	\N	\N	\N	\N	f	\N	\N	\N	\N	\N	\N	\N	f	\N	2	f	\N	\N	\N	\N	\N	6	\N	2012-05-12	0	\N	\N	\N	\N	\N	vaginal	\N	\N	\N	illiterate	\N	\N	\N	t	t	\N	f	\N	f	t	\N	f	t	\N	\N	\N	mobile_asha	OK	\N	cf	2	1	\N	\N	\N	\N	\N	\N	\N	t	\N	resident	OK	\N	f	\N	t	hospital	block_phc	\N
3	41b30323-67f0-4291-8309-09d771btest1	2012-10-12 15:51:14.226+05:30	2012-10-12 15:45:54.726+05:30	16	15	2012-10-12 15:51:14.226+05:30	\N	\N	\N	40	0	40	OK	\N	\N	\N	\N	f	\N	\N	\N	\N	\N	\N	\N	f	\N	2	f	\N	\N	\N	\N	\N	6	\N	2012-05-12	0	\N	\N	\N	\N	\N	vaginal	\N	\N	\N	illiterate	\N	\N	\N	t	t	\N	f	\N	f	t	\N	f	t	\N	\N	\N	mobile_asha	OK	\N	cf	2	1	\N	\N	\N	\N	\N	\N	\N	t	\N	resident	OK	\N	f	\N	t	hospital	block_phc	\N
\.


--
-- Name: registration_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('registration_mother_form_id_seq', 3, true);


--
-- Data for Name: schema_version; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY schema_version (version, description, type, script, checksum, installed_by, installed_on, execution_time, state, current_version) FROM stdin;
0	Base setup	INIT	Base setup	\N	postgres	2013-06-07 15:05:54.201319	0	SUCCESS	f
1.0	\N	SQL	V1_0.sql	-141017564	postgres	2013-06-07 15:05:54.948092	732	SUCCESS	t
\.


--
-- Data for Name: ui_child_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY ui_child_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, add_vaccinations, baby_bcg, baby_dpt1, baby_dpt2, baby_dpt3, baby_hep_b_0, baby_hep_b_1, baby_hep_b_2, baby_hep_b_3, baby_measles, baby_opv0, baby_opv1, baby_opv2, baby_opv3, baby_vita1, bcg_date, dpt_1_date, dpt_2_date, dpt_3_date, dpt_booster_date, hep_b_0_date, hep_b_1_date, hep_b_2_date, hep_b_3_date, measles_date, opv_0_date, opv_1_date, opv_2_date, opv_3_date, opv_booster_date, vit_a_1_date, baby_dpt_booster, baby_je, baby_measles_booster, baby_opv_booster, baby_vita2, baby_vita3, date_je, date_measles_booster, vit_a_2_date, vit_a_3_date) FROM stdin;
\.


--
-- Name: ui_child_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ui_child_form_id_seq', 1, false);


--
-- Data for Name: ui_mother_form; Type: TABLE DATA; Schema: carereporting; Owner: postgres
--

COPY ui_mother_form (id, instance_id, time_end, time_start, user_id, case_id, date_modified, details_available, tt_1_date, tt_2_date, tt_booster_date, received_tt1, received_tt2, up_to_date, num_children, update_mother, tt_booster) FROM stdin;
\.


--
-- Name: ui_mother_form_id_seq; Type: SEQUENCE SET; Schema: carereporting; Owner: postgres
--

SELECT pg_catalog.setval('ui_mother_form_id_seq', 1, false);


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


--
-- Name: schema_version_current_version_index; Type: INDEX; Schema: carereporting; Owner: postgres; Tablespace: 
--

CREATE INDEX schema_version_current_version_index ON schema_version USING btree (current_version);


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

