ALTER TABLE tg_user ADD COLUMN last_message_id bigint;
ALTER TABLE tg_user ADD COLUMN last_message_text varchar;
ALTER TABLE tg_user ADD COLUMN last_message_timestamp timestamp;