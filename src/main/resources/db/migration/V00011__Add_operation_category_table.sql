DROP TABLE IF EXISTS operation_category;
CREATE SEQUENCE IF NOT EXISTS operation_category_id_seq START WITH 1000;

CREATE TABLE operation_category
(
    id             int                NOT NULL DEFAULT nextval('operation_category_id_seq'),
    title_ru       varchar(50) UNIQUE NOT NULL,
    description_ru varchar(200)       NOT NULL,
    symbol         varchar(10),
    "level"        int,
    type_id        int REFERENCES operation_type (id) ON DELETE CASCADE,
    category_id    int REFERENCES operation_category (id) ON DELETE CASCADE,
    "default"      boolean,
    user_id        int REFERENCES tg_user (id) ON DELETE CASCADE
);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (1, 'постоянный', 'постоянный, стабильный доход: зарплата, степендии и пр.', '🔄', 0, 1, null, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (100, 'зарплата', 'заработная плата', '👨‍💻', 1, 1, 1, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (101, 'степендия', 'получение различных видов степендий', '🎓', 1, 1, 1, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (102, 'пенсия', 'получение различных видов пенсий', '👨‍🦳', 1, 1, 1, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (103, 'аренда', 'сдача недвижимости в аренду', '🚪', 1, 1, 1, true, null);


INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (2, 'случайые', 'однократные, нестабильные поступления: премии, подработки и пр.', '🔀', 0, 1, null, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (200, 'премии', 'премии, бонусы, любые негарантированные выплаты от работодателя', '✉️', 1, 1, 2, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (201, 'подработки', 'оплата сдельной работы, гонорары, вообще любые подработки', '💸', 1, 1, 2, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (202, 'продажи', 'доходы от продажи собственности (машины, квартиры и т. д.)', '🤝', 1, 1, 2, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (203, 'инвестиции', 'доход от продажи ценных бумаг, дивиденды', 1, 1, 2, '📈', true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (204, 'подарки', 'доход от получения денежных средств в качестве подарка', '🎁', 1, 1, 2, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (204, 'налоговые вычеты', 'налоговые вычеты предоставляемые сударством', '📑', 1, 1, 2, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (205, 'Прочее', 'различные, небольшие поступления в бюджет', '🧲', 1, 1, 2, true, null);


INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (3, 'обязательные', 'это такие платежи, повлиять на сроки и размер которых мы не можем или не хотим', '‼️', 0, 2,
        null, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (300, 'налоги', 'на недвижимость, автомобиль и пр.', '', 1, 2, 3, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (301, 'кредиты', 'выплаты по кредитам', '', 1, 2, 3, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (302, 'комуналка', 'плата за коммунальные услуги, в том числе электричество, вода и пр.', '', 1, 2, 3, true,
        null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (303, 'аренда', 'аренда жилья', '', 1, 2, 3, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (304, 'обучение', 'плата за обучение, школу и пр.', '', 1, 2, 3, true, null);


INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (4, 'необязательные',
        'сюда можно отнести еду, одежду, транспорт и все то от чего мы можем отказаться или сэкономить', '🏪', 0, 2,
        null, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (401, 'продукты', 'продукты питания', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (402, 'подписки', 'различные подписки на сервисы фильмов, игр и пр.', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (403, 'хозтовары', 'хозяйственные товары', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (404, 'одежда', 'одежда', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (405, 'покупки', 'различные покупки: техникаб квартира, автомобиль и пр.', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (406, 'развлечения', 'кино, бары, театры и пр.', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (407, 'путешествия', 'рассходы на путешествия', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (408, 'транспорт', 'рассходы на общественный транспорт, такси и пр.', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (409, 'питание', 'если приходится покушать не дома', '', 1, 2, 4, true, null);

INSERT into operation_category(id, title_ru, description_ru, symbol, "level", type_id, category_id, "default", user_id)
VALUES (409, 'хобби', 'затраты на хобби', '', 1, 2, 4, true, null);
