CREATE SEQUENCE us01_user_id_seq INCREMENT 1 START 1;

CREATE TABLE US01_USER (
    id_us01 BIGINT DEFAULT nextval('us01_user_id_seq') PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255)
)