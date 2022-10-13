DROP TABLE IF EXISTS tg_user;

CREATE TABLE tg_user
(
    id            bigint PRIMARY KEY,
    web_user_id   int       REFERENCES web_user (id) ON DELETE SET NULL,
    chat_id       bigint    NOT NULL,
    username      varchar(100),
    first_name    varchar(100),
    last_name     varchar(100),
    language_code varchar(3),
    is_premium    boolean,
    created_at    timestamp NOT NULL,
    last_active   timestamp NOT NULL,
    active        boolean   NOT NULL
);

INSERT into tg_user(id, web_user_id, chat_id, username, first_name, last_name, language_code, is_premium, created_at,
                    last_active, active)
VALUES (5003774, 2, 5003774, 'Anton_KAS', 'Anton', 'KAS', 'ru', null, current_timestamp, current_timestamp, true);