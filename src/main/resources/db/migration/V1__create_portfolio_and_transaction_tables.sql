CREATE TABLE tb_portfolio_position (
    id BIGSERIAL PRIMARY KEY,
    asset_code VARCHAR(50) NOT NULL UNIQUE,
    asset_name VARCHAR(150) NOT NULL,
    asset_category VARCHAR(30) NOT NULL,
    quantity NUMERIC(19,8) NOT NULL CHECK (quantity >= 0),
    average_price NUMERIC(19,2) NOT NULL CHECK (average_price >= 0),
    total_amount NUMERIC(19,2) NOT NULL CHECK ( total_amount >= 0),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE tb_transaction_history (
    id BIGSERIAL PRIMARY KEY,
    operation_type VARCHAR(20) NOT NULL,
    asset_code VARCHAR(50) NOT NULL,
    asset_name VARCHAR(150) NOT NULL,
    asset_category VARCHAR(30) NOT NULL,
    quantity NUMERIC(19,8) NOT NULL CHECK (quantity > 0),
    unit_price NUMERIC(19,2) NOT NULL CHECK (unit_price >= 0),
    total_amount NUMERIC(19,2) NOT NULL CHECK (total_amount >= 0),
    operation_timestamp TIMESTAMP NOT NULL
);

CREATE INDEX idx_transaction_history_asset_code
    ON tb_transaction_history (asset_code);

CREATE INDEX idx_transaction_history_operation_type
    ON tb_transaction_history (operation_type);

CREATE INDEX idx_transaction_history_operation_timestamp
    ON tb_transaction_history (operation_timestamp);
