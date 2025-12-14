# Две таблицы с Hibernate и JPA

## Описание

Проект базы данных SQL с полнофункциональным приложением Spring Boot, использующим **JPA (Java Persistence API)** и **Hibernate** для управления клиентами и заказами. Проект демонстрирует эволюцию от простых SQL-скриптов к объектно-ориентированному подходу с использованием ORM (Object-Relational Mapping), REST API с интеграционными тестами и управлением зависимостями.

## Архитектура проекта

Проект состоит из четырех основных компонентов:

1. **SQL скрипты** (в директории `docs/sql/manual/`) - Базовые DDL и DML операции для создания таблиц
2. **JPA Entities** (в директории `src/main/java/tu/netology/entity/`) - Классы сущностей, аннотированные Hibernate аннотациями
3. **Spring Data Repositories** (в директории `src/main/java/tu/netology/repository/`) - Repository интерфейсы для доступа к данным
4. **REST Controllers** (в директории `src/main/java/tu/netology/controller/`) - REST API для работы с клиентами и заказами
5. **Тесты** - Интеграционные тесты с использованием MockMvc и JUnit

## Обзор проекта

Этот проект содержит SQL-скрипты для создания и управления реляционной базой данных с двумя основными таблицами, а также полнофункциональное Java приложение на Spring Boot с использованием ORM для управления этими данными через REST API:

- **Клиенты** (CUSTOMERS): Хранение информации о клиентах, включая имя, фамилию, возраст и контактные данные
- **Заказы** (ORDERS): Управление заказами с внешними ключами, связывающими их с клиентами

## JPA Entities

### Customer Entity

```java
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String surname;
    
    @Column
    private Integer age;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}
```

### Order Entity

```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "date")
    private LocalDateTime date;
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
}
```

## Spring Data Repositories

```java
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);
    List<Customer> findByAgeGreaterThan(Integer age);
}

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByProductName(String productName);
}
```

## REST API Endpoints

### Customers

- `GET /api/customers` - Получить всех клиентов
- `GET /api/customers/{id}` - Получить клиента по ID
- `POST /api/customers` - Создать нового клиента
- `PUT /api/customers/{id}` - Обновить данные клиента
- `DELETE /api/customers/{id}` - Удалить клиента

### Orders

- `GET /api/orders` - Получить все заказы
- `GET /api/orders/{id}` - Получить заказ по ID
- `POST /api/orders` - Создать новый заказ
- `PUT /api/orders/{id}` - Обновить данные заказа
- `DELETE /api/orders/{id}` - Удалить заказ
- `GET /api/orders/customer/{customerId}` - Получить заказы по ID клиента

## Конфигурация БД

### application.yml

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/users_db
    username: postgres
    password: your_password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
        use_sql_comments: true
  sql:
    init:
      mode: always
```

Для разработки с H2 (встроенная БД):

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password: 
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
```

## Требования

- Java 17 или выше
- Maven 3.6+
- Spring Boot 3.x
- PostgreSQL 12+ (или H2 для разработки)
- JPA/Hibernate для ORM

## Установка и запуск

```bash
# Сборка проекта
mvn clean package

# Запуск приложения
mvn spring-boot:run
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
- **Add Maven Wrapper configuration** - Конфигурация Maven Wrapper
- **Configure JPA and PostgreSQL connection** - Настройка JPA и подключение к PostgreSQL

## Лицензия

MIT

## Автор

[iDolph1n](https://github.com/iDolph1n)
