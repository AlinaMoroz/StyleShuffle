CREATE TABLE IF NOT EXISTS news_lines
(
    id        BIGSERIAL PRIMARY KEY,
    post_date TIMESTAMP,
    like_count      int,
    dislike_count   int
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(128),
    email    VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    avatar   VARCHAR(255),
    size     VARCHAR(16),
    surname VARCHAR(128),
    username VARCHAR(128)
);

CREATE TABLE IF NOT EXISTS looks
(
    id           BIGSERIAL PRIMARY KEY,
    description  VARCHAR(255),
    news_line_id BIGINT REFERENCES news_lines (id),
    name         VARCHAR(128),
    user_id BIGINT REFERENCES users(id)
);



CREATE TABLE IF NOT EXISTS comments
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT REFERENCES users(id),
    text         VARCHAR(512),
    date_post    TIMESTAMP,
    news_line_id BIGINT REFERENCES news_lines(id)
);

CREATE TABLE IF NOT EXISTS clothes
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT REFERENCES users(id) ,
    link_photo varchar(255),
    season     varchar(32),
    type       varchar(64),
    brand      varchar(128),
    color      varchar(64)
);

CREATE TABLE IF NOT EXISTS looks_clothes
(
    id       BIGSERIAL PRIMARY KEY ,
    look_id   BIGINT REFERENCES looks(id),
    cloth_id BIGINT REFERENCES clothes(id)
);


