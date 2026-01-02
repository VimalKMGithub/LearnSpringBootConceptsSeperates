-- liquibase formatted sql

-- changeset vimal:001-create-users-table
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- changeset vimal:002-enable-cdc-replica-identity
-- This is specific to Postgres and crucial for your Redpanda CDC pipeline
ALTER TABLE users REPLICA IDENTITY FULL;