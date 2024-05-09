CREATE SEQUENCE ig01_instagram_post_id_seq
INCREMENT 1
START 1;

CREATE TABLE IG01_POST (
    id_ig01 BIGINT PRIMARY KEY,
    poster_username VARCHAR(255),
    description VARCHAR(255),
    number_of_likes VARCHAR(255),
    number_of_comments VARCHAR(255),
    is_video bool not null default 'N' check ( is_video IN ('Y', 'N') ),
    image_url VARCHAR(500),
    shortcode VARCHAR(500),
    created_at TIMESTAMP default now()
)