DELETE
FROM ORDERS;
DELETE
FROM CUSTOMERS;

INSERT INTO CUSTOMERS (id, name, surname, age, phone_number)
VALUES (1, 'Alex', 'Ivanov', 30, '123456'),
       (2, 'Petr', 'Petrov', 25, '999999');

INSERT INTO ORDERS (id, customer_id, product_name, amount)
VALUES (1, 1, 'Phone', 1),
       (2, 1, 'Laptop', 2),
       (3, 2, 'Book', 3);
