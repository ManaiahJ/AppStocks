CREATE TABLE IF NOT EXISTS stock_user
(
id bigint NOT NULL,
created_date timestamp with out time zone,
updated_date timestamp with out time zone,
email character varying(255),
username character varying(255) NOT NULL,
password character varying(255),
mobile_number bigint,
lost_login_time timestamp with out time zone,
last_password_change_date timestamp without time zone,
enable_email_updates boolean,
password_change boolean,
CONSTRAINT stock_user_pkey PRIMARY KEY (id)
CONSTRAINT uk_stock_user_username UNIQUE (username)
);
CREATE INDEX idx_stock_user_username ON stock_user (username);

CREATE SEQUENCE stock_user_sequence
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;