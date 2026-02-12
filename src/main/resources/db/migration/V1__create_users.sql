-- UUID generation + case-insensitive email support
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE users (
  id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email         CITEXT NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE user_roles (
  user_id UUID NOT NULL,
  role    VARCHAR(50) NOT NULL,

  CONSTRAINT pk_user_roles
    PRIMARY KEY (user_id, role),

  CONSTRAINT chk_user_roles_role
    CHECK (role IN ('USER', 'ADMIN')),

  CONSTRAINT fk_user_roles_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);

-- Helpful if you query "all users with role X" frequently
CREATE INDEX IF NOT EXISTS idx_user_roles_role ON user_roles(role);