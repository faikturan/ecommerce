CREATE TABLE payments (
    id CHAR(36) NOT NULL,
    order_id CHAR(36) NOT NULL,
    amount INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);
