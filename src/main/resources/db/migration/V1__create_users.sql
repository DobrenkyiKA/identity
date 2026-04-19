CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

CREATE SCHEMA IF NOT EXISTS public;

CREATE SEQUENCE users_id_sequence START WITH 1 INCREMENT BY 50 CACHE 50;
CREATE TABLE users
(
    id            BIGINT,
    auth_id        BIGINT       NOT NULL,
    email         CITEXT       NOT NULL,
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT pk_users_id PRIMARY KEY (id),
    CONSTRAINT uq_users_authId UNIQUE (auth_id),
    CONSTRAINT uq_users_email UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id BIGINT      NOT NULL,
    role    VARCHAR(50) NOT NULL,
    CONSTRAINT pk_user_roles PRIMARY KEY (user_id, role),
    CONSTRAINT chk_user_roles_role CHECK (role IN ('USER', 'ADMIN')),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_user_roles_role ON user_roles (role);