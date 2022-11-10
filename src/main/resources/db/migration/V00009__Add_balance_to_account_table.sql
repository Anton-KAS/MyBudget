ALTER TABLE account ADD COLUMN start_balance bigint NOT NULL default 0 ;
ALTER TABLE account ADD COLUMN current_balance bigint NOT NULL default 0;
ALTER TABLE account ADD COLUMN created_at timestamp NOT NULL default current_timestamp;
ALTER TABLE account ADD COLUMN updated_at timestamp NOT NULL default current_timestamp;