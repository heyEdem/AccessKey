CREATE TABLE users
(
    id         UUID                  NOT NULL,
    name       VARCHAR(255)          NOT NULL,
    email      VARCHAR(255)          NOT NULL,
    password   VARCHAR(255)          NOT NULL,
    roles      VARCHAR(255),
    is_enabled BOOLEAN DEFAULT FALSE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    deleted_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- ALTER TABLE access_key
--     ADD CONSTRAINT FK_ACC poESSKEY_ON_SCHOOL FOREIGN KEY (school_id) REFERENCES users (id);

ALTER TABLE access_key
DROP
CONSTRAINT fk_accesskey_on_school;

DROP TABLE "user" CASCADE;