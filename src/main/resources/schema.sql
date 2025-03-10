CREATE TABLE IF NOT EXISTS users (
    user_id INT NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    username VARCHAR(32) NOT NULL UNIQUE,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    activated BOOLEAN NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS verification_tokens (
    verification_token UUID NOT NULL UNIQUE,
    user_id INT NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    PRIMARY KEY (verification_token),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INTEGER NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    title VARCHAR(64) NOT NULL UNIQUE,
    PRIMARY KEY (category_id)
);

DO ' BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = ''operation_type'') THEN
        CREATE TYPE operation_type AS ENUM (''INCOME'', ''EXPENSE'');
    END IF;
END ';

CREATE TABLE IF NOT EXISTS operations (
    operation_id BIGINT NOT NULL UNIQUE GENERATED BY DEFAULT AS IDENTITY,
    user_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    amount DECIMAL(12, 2) NOT NULL,
    description VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    type operation_type NOT NULL,
    PRIMARY KEY(operation_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);
