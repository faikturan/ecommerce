CREATE TABLE shipments (
    id CHAR(36) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone_no VARCHAR(20) NOT NULL,
    address VARCHAR(500) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);
