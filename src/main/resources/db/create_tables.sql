--
-- PostgreSQL database dump
--

-- Dumped from database version 9.1.9
-- Dumped by pg_dump version 9.1.9
-- Started on 2013-06-28 11:06:38 CEST

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 168 (class 3079 OID 11679)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 1942 (class 0 OID 0)
-- Dependencies: 168
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 161 (class 1259 OID 21166)
-- Dependencies: 5
-- Name: care_indicator; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE care_indicator (
    indicator_id integer NOT NULL,
    creation_date timestamp without time zone,
    modification_date timestamp without time zone,
    name character varying(255) NOT NULL
);


ALTER TABLE public.care_indicator OWNER TO postgres;

--
-- TOC entry 162 (class 1259 OID 21171)
-- Dependencies: 5
-- Name: care_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE care_user (
    user_id integer NOT NULL,
    creation_date timestamp without time zone,
    modification_date timestamp without time zone,
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.care_user OWNER TO postgres;

--
-- TOC entry 167 (class 1259 OID 21226)
-- Dependencies: 5
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- TOC entry 163 (class 1259 OID 21179)
-- Dependencies: 5
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE permission (
    permission_id integer NOT NULL,
    creation_date timestamp without time zone,
    modification_date timestamp without time zone,
    display_name character varying(255),
    name character varying(255)
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- TOC entry 164 (class 1259 OID 21187)
-- Dependencies: 5
-- Name: role; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE role (
    role_id integer NOT NULL,
    creation_date timestamp without time zone,
    modification_date timestamp without time zone,
    name character varying(255)
);


ALTER TABLE public.role OWNER TO postgres;

--
-- TOC entry 165 (class 1259 OID 21192)
-- Dependencies: 5
-- Name: role_permission; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE role_permission (
    role_id integer NOT NULL,
    permission_id integer NOT NULL
);


ALTER TABLE public.role_permission OWNER TO postgres;

--
-- TOC entry 166 (class 1259 OID 21197)
-- Dependencies: 5
-- Name: user_role; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_role (
    user_id integer NOT NULL,
    role_id integer NOT NULL
);


ALTER TABLE public.user_role OWNER TO postgres;

--
-- TOC entry 1943 (class 0 OID 0)
-- Dependencies: 167
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 3, true);

--
-- TOC entry 1909 (class 2606 OID 21170)
-- Dependencies: 161 161 1936
-- Name: care_indicator_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY care_indicator
    ADD CONSTRAINT care_indicator_pkey PRIMARY KEY (indicator_id);


--
-- TOC entry 1911 (class 2606 OID 21178)
-- Dependencies: 162 162 1936
-- Name: care_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY care_user
    ADD CONSTRAINT care_user_pkey PRIMARY KEY (user_id);


--
-- TOC entry 1915 (class 2606 OID 21205)
-- Dependencies: 163 163 1936
-- Name: name_; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT name_ UNIQUE (name);


--
-- TOC entry 1917 (class 2606 OID 21186)
-- Dependencies: 163 163 1936
-- Name: permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (permission_id);


--
-- TOC entry 1921 (class 2606 OID 21196)
-- Dependencies: 165 165 165 1936
-- Name: role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (role_id, permission_id);


--
-- TOC entry 1919 (class 2606 OID 21191)
-- Dependencies: 164 164 1936
-- Name: role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (role_id);


--
-- TOC entry 1923 (class 2606 OID 21201)
-- Dependencies: 166 166 166 1936
-- Name: user_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT user_role_pkey PRIMARY KEY (user_id, role_id);


--
-- TOC entry 1913 (class 2606 OID 21203)
-- Dependencies: 162 162 1936
-- Name: username_; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY care_user
    ADD CONSTRAINT username_ UNIQUE (username);


--
-- TOC entry 1927 (class 2606 OID 21221)
-- Dependencies: 1910 162 166 1936
-- Name: fk143bf46a4f5e391f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk143bf46a4f5e391f FOREIGN KEY (user_id) REFERENCES care_user(user_id);


--
-- TOC entry 1926 (class 2606 OID 21216)
-- Dependencies: 164 166 1918 1936
-- Name: fk143bf46aa3be08ff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_role
    ADD CONSTRAINT fk143bf46aa3be08ff FOREIGN KEY (role_id) REFERENCES role(role_id);


--
-- TOC entry 1924 (class 2606 OID 21206)
-- Dependencies: 1916 163 165 1936
-- Name: fkbd40d53894070e1f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT fkbd40d53894070e1f FOREIGN KEY (permission_id) REFERENCES permission(permission_id);


--
-- TOC entry 1925 (class 2606 OID 21211)
-- Dependencies: 165 1918 164 1936
-- Name: fkbd40d538a3be08ff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY role_permission
    ADD CONSTRAINT fkbd40d538a3be08ff FOREIGN KEY (role_id) REFERENCES role(role_id);


--
-- TOC entry 1941 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2013-06-28 11:06:38 CEST

--
-- PostgreSQL database dump complete
--

