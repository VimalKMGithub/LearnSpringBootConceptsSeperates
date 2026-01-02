-- liquibase formatted sql

-- changeset vimal:001-create-profiles-table
CREATE TABLE profiles
(
    id         SERIAL PRIMARY KEY,
    user_id    UUID UNIQUE  NOT NULL, -- Links to the Auth ID from Service 1
    email      VARCHAR(255) NOT NULL,
    full_name  VARCHAR(255),
    bio        TEXT,
    avatar_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);