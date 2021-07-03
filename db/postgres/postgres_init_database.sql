CREATE DATABASE warehouse
    WITH
    OWNER = admin
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE USER flyway WITH password 'Flyw@y1234!';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA 'public' TO flyway;
GRANT USAGE ON ALL SEQUENCES IN SCHEMA 'public' TO flyway;