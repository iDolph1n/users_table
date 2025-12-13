# Две таблицы

Проект базы данных SQL, демонстрирующий создание таблиц и объединения с управлением клиентами и заказами.

## Обзор проекта

Этот проект содержит SQL-скрипты для создания и управления реляционной базой данных с двумя основными таблицами:
- **Клиенты**: Хранение информации о клиентах, включая имя, фамилию, возраст и контактные данные
- **Заказы**: Управление заказами с внешними ключами, связывающими их с клиентами

## Описание файлов

### 01_create_customers.sql
Создаёт таблицу `CUSTOMERS` со следующими колонками:
- `id` (SERIAL PRIMARY KEY) - Уникальный идентификатор клиента
- `name` (VARCHAR(50) NOT NULL) - Имя клиента
- `surname` (VARCHAR(50) NOT NULL) - Фамилия клиента
- `age` (INT) - Возраст клиента
- `phone_number` (VARCHAR(20)) - Номер телефона клиента

### 02_create_orders.sql
Создаёт таблицу `ORDERS` со следующими колонками:
- `id` (SERIAL PRIMARY KEY) - Уникальный идентификатор заказа
- `date` (TIMESTAMP DEFAULT CURRENT_TIMESTAMP) - Временная метка заказа
- `customer_id` (INT NOT NULL) - Внешний ключ ссылка на CUSTOMERS
- `product_name` (VARCHAR(100) NOT NULL) - Название заказанного товара
- `amount` (INT NOT NULL) - Сумма заказа
- **Ограничение**: `fk_orders_customers` - Внешний ключ, связывающий CUSTOMERS.id

### 03_select_products_by_alexey.sql
Запрос SELECT, демонстрирующий операцию JOIN для поиска товаров, заказанных клиентами с именем "alexey":
```sql
SELECT o.product_name
FROM ORDERS o
JOIN CUSTOMERS c ON o.customer_id = c.id
WHERE c.name LIKE 'alexey';
```

## Начало работы

1. Выполните `01_create_customers.sql` для создания таблицы CUSTOMERS
2. Выполните `02_create_orders.sql` для создания таблицы ORDERS с ограничением внешнего ключа
3. Запустите `03_select_products_by_alexey.sql` для выполнения запроса товаров по имени клиента

## Схема базы данных

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

## Технологии

- SQL (синтаксис PostgreSQL)
- Проектирование реляционной базы данных
- Ограничения внешних ключей

## Лицензия

MIT License
