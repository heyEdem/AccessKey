CREATE TABLE access_key
(
    id         UUID NOT NULL,
    code       UUID NOT NULL,
    expiry     date NOT NULL,
    status     VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    school_id  UUID,
    CONSTRAINT pk_accesskey PRIMARY KEY (id)
);

CREATE TABLE otp
(
    id         UUID         NOT NULL,
    email      VARCHAR(255) NOT NULL,
    type       VARCHAR(255),
    code       VARCHAR(255) NOT NULL,
    expired_at TIMESTAMP WITHOUT TIME ZONE,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_otp PRIMARY KEY (id)
);

CREATE TABLE "user"
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
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_email UNIQUE (email);

ALTER TABLE access_key
    ADD CONSTRAINT FK_ACCESSKEY_ON_SCHOOL FOREIGN KEY (school_id) REFERENCES "user" (id);