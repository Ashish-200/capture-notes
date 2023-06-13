-- SEQUENCE: lms_masters.notes_seq

-- DROP SEQUENCE IF EXISTS lms_masters.notes_seq;

CREATE SEQUENCE IF NOT EXISTS lms_masters.notes_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


-- Table: lms_masters.notes

-- DROP TABLE IF EXISTS lms_masters.notes;

CREATE TABLE IF NOT EXISTS lms_masters.notes
(
    notes_id bigint NOT NULL,
    created_by character varying(30) COLLATE pg_catalog."default",
    creation_date_time timestamp without time zone,
    creation_remarks character varying(150) COLLATE pg_catalog."default",
    decision_by character varying(30) COLLATE pg_catalog."default",
    decision_date_time timestamp without time zone,
    decision_remarks character varying(150) COLLATE pg_catalog."default",
    field_1 character varying(150) COLLATE pg_catalog."default",
    field_2 character varying(150) COLLATE pg_catalog."default",
    field_3 character varying(150) COLLATE pg_catalog."default",
    field_4 character varying(150) COLLATE pg_catalog."default",
    field_5 character varying(150) COLLATE pg_catalog."default",
    field_6 character varying(150) COLLATE pg_catalog."default",
    field_7 character varying(150) COLLATE pg_catalog."default",
    last_update_on timestamp without time zone,
    record_date_time timestamp without time zone,
    status_id character varying(1) COLLATE pg_catalog."default",
    feature_id bigint,
    is_deleted character varying(1) COLLATE pg_catalog."default",
    notes character varying(4000) COLLATE pg_catalog."default",
    notes_creation_time timestamp without time zone,
    lms_id bigint,
    CONSTRAINT notes_pkey PRIMARY KEY (notes_id)
)

-- Table: lms_masters.notes_a

-- DROP TABLE IF EXISTS lms_masters.notes_a;

CREATE TABLE IF NOT EXISTS lms_masters.notes_a
(
    notes_id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    created_by character varying(30) COLLATE pg_catalog."default",
    creation_date_time timestamp without time zone,
    creation_remarks character varying(150) COLLATE pg_catalog."default",
    decision_by character varying(30) COLLATE pg_catalog."default",
    decision_date_time timestamp without time zone,
    decision_remarks character varying(150) COLLATE pg_catalog."default",
    field_1 character varying(150) COLLATE pg_catalog."default",
    field_2 character varying(150) COLLATE pg_catalog."default",
    field_3 character varying(150) COLLATE pg_catalog."default",
    field_4 character varying(150) COLLATE pg_catalog."default",
    field_5 character varying(150) COLLATE pg_catalog."default",
    field_6 character varying(150) COLLATE pg_catalog."default",
    field_7 character varying(150) COLLATE pg_catalog."default",
    last_update_on timestamp without time zone,
    record_date_time timestamp without time zone,
    status_id character varying(1) COLLATE pg_catalog."default",
    feature_id bigint,
    is_deleted character varying(1) COLLATE pg_catalog."default",
    notes character varying(4000) COLLATE pg_catalog."default",
    notes_creation_time timestamp without time zone,
    lms_id bigint,
    CONSTRAINT notes_a_pkey PRIMARY KEY (notes_id, rev),
    CONSTRAINT fk27peli21q9me87254tew05p1y FOREIGN KEY (rev)
        REFERENCES lms_masters.revinfo (rev) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

