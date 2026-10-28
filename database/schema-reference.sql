-- JPA creates/updates the schema automatically in development.
-- This file documents the core tables for PostgreSQL.

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  role VARCHAR(30) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE destinations (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  country VARCHAR(255) NOT NULL,
  description TEXT,
  image_url TEXT
);

CREATE TABLE trip_requests (
  id BIGSERIAL PRIMARY KEY,
  traveler_id BIGINT NOT NULL REFERENCES users(id),
  request_text TEXT NOT NULL,
  destination VARCHAR(255),
  duration_days INTEGER,
  travelers INTEGER,
  budget INTEGER,
  currency VARCHAR(20),
  interests TEXT,
  status VARCHAR(30),
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE provider_profiles (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT UNIQUE NOT NULL REFERENCES users(id),
  company_name VARCHAR(255),
  country VARCHAR(255),
  destinations TEXT,
  specialties TEXT,
  rating DOUBLE PRECISION,
  active BOOLEAN
);
