# Users Table (JPA Repository Version)

## Описание проекта

Это Spring Boot приложение для управления таблицей пользователей (PERSONS) в базе данных. Данная версия реализована с использованием **Spring Data JPA Repository** для абстракции слоя доступа к данным. Проект предоставляет RESTful API для создания, чтения, обновления и удаления записей о людях, а также выполнения различных запросов к этой таблице.

## Ключевые отличия JPA Repository версии

- **Spring Data JPA Repository** - вместо прямого использования EntityManager или SQL запросов
- **Entity-based подход** - сущности отображаются на таблицы БД через аннотации
- **Hibernate DDL** - автоматическое создание и управление схемой БД
- **Query methods** - использование @Query аннотаций для кастомных запросов
- **Lazy Loading** - оптимизированная загрузка данных через JPA
- **Transaction Management** - встроенная поддержка транзакций

## Технологии

- **Java 17+** - язык программирования
- **Spring Boot 3.x** - фреймворк для создания приложений
- **Spring Data JPA** - для работы с базой данных через ORM
- **Hibernate** - ORM фреймворк для маппирования сущностей
- **H2 Database** - встроенная база данных (разработка)
- **PostgreSQL/MySQL** - поддерживаемые СУБД для production
- **Maven** - система управления зависимостями
- **Lombok** - для уменьшения boilerplate кода

## Структура проекта

```
users_table/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/users_table/
│   │   │       ├── controller/        # REST контроллеры
│   │   │       ├── service/           # Бизнес-логика
│   │   │       ├── repository/        # JPA Repository интерфейсы
│   │   │       ├── entity/            # JPA сущности (@Entity)
│   │   │       ├── dto/               # Data Transfer Objects
│   │   │       ├── exception/         # Кастомные исключения
│   │   │       └── Application.java   # Точка входа
│   │   └── resources/
│   │       ├── application.yml        # Конфигурация приложения
│   │       ├── schema.sql             # Инициализация БД (опционально)
│   │       └── data.sql               # Тестовые данные
│   └── test/                          # Unit тесты
├── pom.xml                            # Конфигурация Maven
├── mvnw / mvnw.cmd                    # Maven Wrapper
└── README.md                          # Документация
```

## Схема базы данных

### Таблица PERSONS

```sql
CREATE TABLE persons (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    phone_number VARCHAR(20),
    city_of_living VARCHAR(50)
);
```

**Поля таблицы:**

- `id` - идентификатор записи (автоинкремент)
- `name` - имя человека (строка, обязательное)
- `surname` - фамилия человека (строка, обязательное)
- `age` - возраст человека (целое число, обязательное)
- `phone_number` - номер телефона (строка, опциональное)
- `city_of_living` - город проживания (строка, опциональное)

### JPA Entity 

```java
@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String surname;
    
    @Column(nullable = false)
    private int age;
    
    @Column(length = 20)
    private String phoneNumber;
    
    @Column(length = 50)
    private String cityOfLiving;
}
```

## Требования

- Java 17 или выше
- Maven 3.6+
- Любая СУБД с поддержкой SQL (PostgreSQL, MySQL, SQLite, H2)

## Установка и запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/iDolph1n/users_table.git
cd users_table
git checkout jpa-repository
```

### 2. Сборка проекта

С помощью Maven:

```bash
mvn clean package
```

Ор с использованием Maven Wrapper:

```bash
./mvnw clean package       # Linux/Mac
mvnw.cmd clean package     # Windows
```

### 3. Конфигурация базы данных

Отредактируйте файл `src/main/resources/application.yml`:

**Для разработки с H2 (по умолчанию):**

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: create-drop
    show-sql: false
```

**Для MySQL:**

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/users_db
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL8Dialect
```

**Для PostgreSQL:**

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
        dialect: org.hibernate.dialect.PostgreSQL10Dialect
```

### 4. Запуск приложения

```bash
mvn spring-boot:run
```

Приложение будет доступно по адресу: `http://localhost:8080`

Консоль H2 (если используется): `http://localhost:8080/h2-console`

## API Endpoints

### Получить всех пользователей

```
GET /api/persons
```

### Получить пользователя по ID

```
GET /api/persons/{id}
```

### Создать нового пользователя

```
POST /api/persons
Content-Type: application/json

{
  "name": "John",
  "surname": "Doe",
  "age": 30,
  "phoneNumber": "+1234567890",
  "cityOfLiving": "Moscow"
}
```

### Обновить пользователя

```
PUT /api/persons/{id}
Content-Type: application/json

{
  "name": "John",
  "surname": "Smith",
  "age": 31,
  "phoneNumber": "+1234567890",
  "cityOfLiving": "Saint Petersburg"
}
```

### Удалить пользователя

```
DELETE /api/persons/{id}
```

### Найти людей по городу

```
GET /api/persons/city/{city}
```

Пример:
```
GET /api/persons/city/Moscow
```

### Найти людей старше определённого возраста

```
GET /api/persons/age-gt/{age}
```

Пример:
```
GET /api/persons/age-gt/27
```

Результат отсортирован по возрасту (убывание).

## Тестирование

Запуск тестов:

```bash
mvn test
```

Запуск конкретного тестового класса:

```bash
mvn test -Dtest=PersonServiceTest
```

## Использование

1. Запустите приложение согласно инструкциям выше
2. Используйте REST API для управления данными (через Postman, curl или встроенный тестировщик)
3. Данные будут автоматически сохранены в выбранную базу данных
4. При необходимости отредактируйте SQL скрипты инициализации в `src/main/resources/`

## Разработка

### Добавление новых JPA Query Methods

Откройте `repository/PersonRepository.java` и добавьте новый метод:

```java
public interface PersonRepository extends JpaRepository<Person, Long> {
    // Встроенные методы:
    // - findAll()
    // - findById(Long id)
    // - save(Person person)
    // - delete(Person person)
    // - deleteById(Long id)
    
    // Кастомные Query Methods (автоматически генерируются):
    List<Person> findByName(String name);
    List<Person> findByCityOfLiving(String city);
    List<Person> findByAgeGreaterThan(int age);
    List<Person> findByAgeGreaterThanOrderByAgeDesc(int age);
}
```

### Пример использования @Query для сложных запросов

```java
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p WHERE p.age > :age ORDER BY p.age DESC")
    List<Person> findAdultsSorted(@Param("age") int age);
    
    @Query(value = "SELECT * FROM persons WHERE city_of_living = ?1", nativeQuery = true)
    List<Person> findByCityNative(String city);
}
```

### Слои приложения

**Entity Layer:**
- Аннотированные классы с @Entity
- Маппирование на таблицы БД

**Repository Layer:**
- JpaRepository интерфейсы
- Query methods и @Query аннотации

**Service Layer:**
- Бизнес-логика
- Валидация и обработка ошибок
- Координация между репозиториями

**Controller Layer:**
- REST endpoints
- HTTP методы (GET, POST, PUT, DELETE)
- Маршрутизация запросов

## Миграция с предыдущей версии

Если вы использовали предыдущую версию с SQL скриптами и JdbcTemplate:

- `JdbcTemplate` → `JpaRepository`
- SQL скрипты → JPA Query Methods
- Результаты запросов → Entity объекты
- Ручное маппирование → Автоматическое через @Column аннотации

## Лицензия

[MIT License](LICENSE)

## Автор

[iDolph1n](https://github.com/iDolph1n)
