-- =========================================================
-- USERS & AUTHENTICATION
-- Scouter DataGateway
-- PostgreSQL / Supabase
-- =========================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;


-- =========================================================
-- USERS
-- =========================================================

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    username VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,

    password_hash TEXT NOT NULL,

    role VARCHAR(20) NOT NULL DEFAULT 'SCOUT',
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT users_username_unique
        UNIQUE (username),

    CONSTRAINT users_email_unique
        UNIQUE (email),

    CONSTRAINT users_role_check
        CHECK (role IN ('SCOUT', 'ADMIN'))
);


-- =========================================================
-- USER SESSIONS
-- =========================================================

CREATE TABLE user_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    user_id UUID NOT NULL,

    token_hash TEXT NOT NULL,

    expires_at TIMESTAMPTZ NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    last_used_at TIMESTAMPTZ,

    CONSTRAINT user_sessions_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT user_sessions_token_unique
        UNIQUE (token_hash)
);


-- =========================================================
-- USER PREFERENCES
-- =========================================================

CREATE TABLE user_preferences (
    user_id UUID PRIMARY KEY,

    last_event_id BIGINT,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT user_preferences_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- =========================================================
-- FAVORITE EVENTS
-- =========================================================

CREATE TABLE user_favorite_events (
    user_id UUID NOT NULL,
    event_id BIGINT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    PRIMARY KEY (user_id, event_id),

    CONSTRAINT favorite_events_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- =========================================================
-- FAVORITE TEAMS
-- =========================================================

CREATE TABLE user_favorite_teams (
    user_id UUID NOT NULL,
    team_id BIGINT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    PRIMARY KEY (user_id, team_id),

    CONSTRAINT favorite_teams_user_fk
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


-- =========================================================
-- INDEXES
-- =========================================================

CREATE INDEX idx_user_sessions_user_id
    ON user_sessions(user_id);

CREATE INDEX idx_user_sessions_expires_at
    ON user_sessions(expires_at);

CREATE INDEX idx_favorite_events_user_id
    ON user_favorite_events(user_id);

CREATE INDEX idx_favorite_events_event_id
    ON user_favorite_events(event_id);

CREATE INDEX idx_favorite_teams_user_id
    ON user_favorite_teams(user_id);

CREATE INDEX idx_favorite_teams_team_id
    ON user_favorite_teams(team_id);


-- =========================================================
-- UPDATED_AT
-- =========================================================

CREATE OR REPLACE FUNCTION update_updated_at()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();


CREATE TRIGGER user_preferences_updated_at
BEFORE UPDATE ON user_preferences
FOR EACH ROW
EXECUTE FUNCTION update_updated_at();