ALTER TABLE access_key
    ADD name VARCHAR(255);

ALTER TABLE access_key
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE access_key
DROP
COLUMN code;

ALTER TABLE access_key
    ADD code VARCHAR(255) NOT NULL;

ALTER TABLE access_key
    ALTER COLUMN school_id SET NOT NULL;