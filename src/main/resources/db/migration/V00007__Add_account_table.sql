DROP TABLE IF EXISTS account CASCADE;

CREATE TABLE account
(
    id          int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    tg_user_id  int REFERENCES tg_user (id) ON DELETE CASCADE,
    currency_id int          REFERENCES currency (id) ON DELETE SET NULL,
    title       varchar(30)  NOT NULL,
    description varchar(100),
    type_id     int          REFERENCES account_type (id) ON DELETE SET NULL,
    bank_id     int          REFERENCES bank (id) ON DELETE SET NULL
);
