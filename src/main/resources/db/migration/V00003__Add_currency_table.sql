DROP TABLE IF EXISTS currency;

CREATE TABLE currency
(
    id              int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    currency_en     varchar(50) NOT NULL,
    currency_ru     varchar(50) NOT NULL,
    symbol          varchar(5)  NOT NULL,
    iso_code        varchar(5),
    number_to_basic int
);
-- 1
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('united states dollar', 'доллар США', '$', 'USD', 100);
-- 2
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('euro', 'евро', '€', 'EUR', 100);
-- 3
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('sterling', 'фунт стерлингов', '£', 'GBP', 100);
-- 4
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('swiss franc', 'швейцарский франк', '₣', 'CHF', 100);
-- 5
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('russian rouble', 'российский рубль', '₽', 'RUB', 100);
-- 6
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('belarussian ruble', 'белорусский рубль', 'Br', 'BYN', 100);
-- 7
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('hryvnia', 'гривна', '₴', 'UAH', 100);
-- 8
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('lari', 'лари', '₾', 'GEL', 100);
-- 9
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('armenian dram', 'армянский драм', '֏', 'AMD', 100);
-- 10
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('azerbaijanian manat', 'азербайджанский манат', '₼', 'AZN', 100);
-- 11
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('turkmenistan manat', 'туркменский манат', 'm', 'TMT', 100);
-- 12
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('kazakhstani tenge', 'теге', '₸', 'KZT', 100);
-- 13
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('uzbekistan sum', 'узбекский сум', 'so’m', 'UZS', 100);
-- 14
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('somoni', 'cомони', 'с.', 'TJS', 100);
-- 15
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('tugrik', 'тугрик', '₮', 'MNT', 100);
-- 16
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('turkish lira', 'турецкая лира', '₺', 'TRY', 100);
-- 17
INSERT into currency(currency_en, currency_ru, symbol, iso_code, number_to_basic)
VALUES ('thai baht', 'бат', '฿', 'THB', 100);