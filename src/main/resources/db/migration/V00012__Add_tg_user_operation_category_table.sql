DROP TABLE IF EXISTS tg_user_operation_category;

CREATE TABLE tg_user_operation_category
(
    tg_user_id            int REFERENCES tg_user (id) ON DELETE CASCADE,
    operation_category_id int REFERENCES operation_category (id) ON DELETE CASCADE
);
