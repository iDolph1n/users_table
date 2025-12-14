CREATE TABLE IF NOT EXISTS CUSTOMERS
(
    id
    SERIAL
    PRIMARY
    KEY,
    name
    VARCHAR
(
    50
) NOT NULL,
    surname VARCHAR
(
    50
) NOT NULL,
    age INT,
    phone_number VARCHAR
(
    20
)
    );

CREATE TABLE IF NOT EXISTS ORDERS
(
    id
    SERIAL
    PRIMARY
    KEY,
    date
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP,
    customer_id
    INT
    NOT
    NULL,
    product_name
    VARCHAR
(
    100
) NOT NULL,
    amount INT NOT NULL,
    CONSTRAINT fk_orders_customers
    FOREIGN KEY
(
    customer_id
) REFERENCES CUSTOMERS
(
    id
)
    );
