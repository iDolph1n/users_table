# Две таблицы

## Описание

Проект базы данных SQL с полноценным приложением Spring Boot для управления клиентами и заказами. Проект демонстрирует эволюцию от простых SQL-скриптов к полноценному REST API с интеграционными тестами и управлением зависимостями.

## Архитектура проекта

Проект состоит из трех основных компонентов:

1. **SQL скрипты** (в директории `docs/sql/manual/`) - Базовые DDL и DML операции
2. **Spring Boot приложение** (в директории `src/`) - REST API для работы с клиентами и заказами
3. **Тесты** - Интеграционные тесты с использованием MockMvc и JUnit

## Обзор проекта

Этот проект содержит SQL-скрипты для создания и управления реляционной базой данных с двумя основными таблицами, а также полноценное Java приложение на Spring Boot для управления этими данными через REST API:

- **Клиенты** (CUSTOMERS): Хранение информации о клиентах, включая имя, фамилию, возраст и контактные данные
- **Заказы** (ORDERS): Управление заказами с внешними ключами, связывающими их с клиентами

## Описание файлов

### SQL скрипты

#### 01_create_customers.sql

Создаёт таблицу `CUSTOMERS` со следующими колонками:

- `id` (SERIAL PRIMARY KEY) - Уникальный идентификатор клиента
- `name` (VARCHAR(50) NOT NULL) - Имя клиента
- `surname` (VARCHAR(50) NOT NULL) - Фамилия клиента
- `age` (INT) - Возраст клиента
- `phone_number` (VARCHAR(20)) - Номер телефона клиента

#### 02_create_orders.sql

Создаёт таблицу `ORDERS` со следующими колонками:

- `id` (SERIAL PRIMARY KEY) - Уникальный идентификатор заказа
- `date` (TIMESTAMP DEFAULT CURRENT_TIMESTAMP) - Временная метка заказа
- `customer_id` (INT NOT NULL) - Внешний ключ ссылка на CUSTOMERS
- `product_name` (VARCHAR(100) NOT NULL) - Название заказанного товара
- `amount` (INT NOT NULL) - Сумма заказа
- **Ограничение**: `fk_orders_customers` - Внешний ключ, связывающий CUSTOMERS.id

#### 03_select_products_by_alexey.sql

Запрос SELECT, демонстрирующий операцию JOIN для поиска товаров, заказанных клиентами с именем "alexey".

### Java приложение Spring Boot

Приложение предоставляет REST API для работы с данными:

- **Controller** - REST контроллер для обработки HTTP запросов
- **Service** - Бизнес-логика приложения
- **Repository** - Слой доступа к данным (Spring Data JPA)
- **Model** - Классы для представления сущностей
- **Tests** - Интеграционные тесты с MockMvc и repository тесты

## Начало работы

### Требования

- Java 11+
- Maven 3.6+
- PostgreSQL или другая реляционная БД

### SQL часть

1. Выполните `01_create_customers.sql` для создания таблицы CUSTOMERS
2. Выполните `02_create_orders.sql` для создания таблицы ORDERS с ограничением внешнего ключа
3. Запустите `03_select_products_by_alexey.sql` для выполнения запроса товаров по имени клиента

### Spring Boot приложение

1. Установите зависимости:
   ```bash
   mvn clean install
   ```

2. Запустите приложение:
   ```bash
   mvn spring-boot:run
   ```

3. Приложение будет доступно на `http://localhost:8080`

## Технологический стек

### Backend

- **Spring Boot** - Фреймворк для создания REST API
- **Spring Data JPA** - ORM для работы с базой данных
- **Spring Test** - Тестирование приложения
- **JUnit** - Фреймворк для модульного тестирования
- **MockMvc** - Для тестирования REST контроллеров
- **Maven** - Управление зависимостями и сборкой

### Базы данных

- **PostgreSQL** - Основная реляционная база данных
- **H2** (опционально для тестов) - Встроенная база для интеграционных тестов

### SQL

- **SQL (PostgreSQL синтаксис)** - Язык запросов к БД
- **DDL операции** - Создание таблиц и ограничений
- **DML операции** - Вставка, обновление, удаление данных
- **JOIN операции** - Объединение таблиц

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

## Структура проекта

```
src/
├── main/
│   ├── java/
│   │   └── com/example/twotables/
│   │       ├── controller/     # REST контроллеры
│   │       ├── service/        # Бизнес-логика
│   │       ├── repository/     # Data Access Layer
│   │       ├── model/          # Модели данных
│   │       └── TwoTablesApplication.java
│   └── resources/
│       └── application.properties
├── test/
│   └── java/
│       └── com/example/twotables/
│           ├── controller/     # Тесты контроллеров
│           └── repository/     # Тесты репозитория
docs/
└── sql/
    └── manual/                 # SQL скрипты
        ├── 01_create_customers.sql
        ├── 02_create_orders.sql
        └── 03_select_products_by_alexey.sql
```

## Примеры API

### Получить всех клиентов
```
GET /api/customers
```

### Получить клиента по ID
```
GET /api/customers/{id}
```

### Получить все заказы
```
GET /api/orders
```

### Получить заказы клиента
```
GET /api/orders/customer/{customerId}
```

## Тестирование

Проект включает интеграционные тесты:

- Тесты контроллеров с использованием MockMvc
- Repository интеграционные тесты
- Тесты с использованием H2 базы данных для тестирования

Для запуска тестов:
```bash
mvn test
```

## История коммитов

- **Update dependencies** - Обновление зависимостей проекта
- **Extract SQL filename constant** - Рефакторинг кода
- **Add repository integration tests** - Добавление интеграционных тестов
- **Add controller tests with MockMvc** - Добавление тестов контроллеров
- **Add Spring Boot application** - Создание основного приложения
- **Add seed data for local/tests** - Добавление тестовых данных
- **Add manual SQL scripts** - Добавление SQL скриптов
- **add Maven Wrapper configuration** - Конфигурация Maven Wrapper

## Лицензия

MIT License
