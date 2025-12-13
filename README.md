# Two Tables

A SQL database project demonstrating table creation and joins with customer and order management.

## Project Overview

This project contains SQL scripts for creating and managing a relational database with two main tables:
- **Customers**: Store customer information including name, surname, age, and contact details
- **Orders**: Manage orders with foreign key relationships to customers

## Files Description

### 01_create_customers.sql
Creates the `CUSTOMERS` table with the following columns:
- `id` (SERIAL PRIMARY KEY) - Unique identifier for each customer
- `name` (VARCHAR(50) NOT NULL) - Customer's first name
- `surname` (VARCHAR(50) NOT NULL) - Customer's last name
- `age` (INT) - Customer's age
- `phone_number` (VARCHAR(20)) - Customer's phone number

### 02_create_orders.sql
Creates the `ORDERS` table with:
- `id` (SERIAL PRIMARY KEY) - Unique identifier for each order
- `date` (TIMESTAMP DEFAULT CURRENT_TIMESTAMP) - Order timestamp
- `customer_id` (INT NOT NULL) - Foreign key reference to CUSTOMERS
- `product_name` (VARCHAR(100) NOT NULL) - Name of ordered product
- `amount` (INT NOT NULL) - Order amount
- **Constraint**: `fk_orders_customers` - Foreign key linking to CUSTOMERS.id

### 03_select_products_by_alexey.sql
A SELECT query that demonstrates a JOIN operation to find products ordered by customers named "alexey":
```sql
SELECT o.product_name
FROM ORDERS o
JOIN CUSTOMERS c ON o.customer_id = c.id
WHERE c.name LIKE 'alexey';
```

## Getting Started

1. Execute `01_create_customers.sql` to create the CUSTOMERS table
2. Execute `02_create_orders.sql` to create the ORDERS table with foreign key constraint
3. Run `03_select_products_by_alexey.sql` to query products by customer name

## Database Schema

```
CUSTOMERS
├── id (PK)
├── name
├── surname
├── age
└── phone_number

ORDERS
├── id (PK)
├── date
├── customer_id (FK -> CUSTOMERS.id)
├── product_name
└── amount
```

## Technologies

- SQL (PostgreSQL syntax)
- Relational Database Design
- Foreign Key Constraints

## License

MIT License
