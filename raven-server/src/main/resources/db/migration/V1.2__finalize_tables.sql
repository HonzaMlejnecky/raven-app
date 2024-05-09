CREATE SEQUENCE ma01_user_post_seq INCREMENT 1 START 1;

CREATE TABLE MA01_USER_POST (
    id_ma01 BIGINT DEFAULT nextval('ma01_user_post_seq') PRIMARY KEY,
    us01_id BIGINT NOT NULL references US01_USER (id_us01),
    ig01_id BIGINT NOT NULL references IG01_POST (id_ig01),
    UNIQUE (us01_id, ig01_id)
);

ALTER TABLE US01_USER
    ADD CONSTRAINT us01_user_id_uniq_username
        UNIQUE (username);

ALTER TABLE US01_USER
    ADD CONSTRAINT us01_user_id_uniq_email
        UNIQUE (email);

ALTER TABLE IG01_POST
    ADD COLUMN us01_id BIGINT references US01_USER (id_us01)