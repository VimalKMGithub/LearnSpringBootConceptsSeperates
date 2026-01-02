-- liquibase formatted sql

-- changeset vimal:001-create-profiles-table
CREATE TABLE profiles
(
    id         BIGSERIAL PRIMARY KEY, -- <--- CHANGED FROM SERIAL TO BIGSERIAL
    user_id    UUID UNIQUE  NOT NULL,
    email      VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    bio        TEXT,
    avatar_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);