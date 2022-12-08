DROP TABLE IF EXISTS operation_type;

CREATE TABLE operation_type
(
    id             int PRIMARY KEY UNIQUE NOT NULL,
    title_ru       varchar(50) UNIQUE     NOT NULL,
    description_ru varchar(200)           NOT NULL,
    symbol         varchar(10)
);


INSERT into operation_type(id, title_ru, description_ru, symbol)
VALUES (1, 'доход', 'Операции по получению дохода', '📗');

INSERT into operation_type(id, title_ru, description_ru, symbol)
VALUES (2, 'расход', 'Операции по расходу средств', '📕');

INSERT into operation_type(id, title_ru, description_ru, symbol)
VALUES (3, 'перевод', 'Операции первода средств между счетами пользователя', '📘');

INSERT into operation_type(id, title_ru, description_ru, symbol)
VALUES (4, 'коррекчия', 'Операции по коррекции баланса счета', '📙');
