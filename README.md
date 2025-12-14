# Users Table

## Описание проекта

Это Spring Boot приложение для управления таблицей пользователей (PERSONS) в базе данных. Проект предоставляет RESTful API для создания, чтения, обновления и удаления записей о людях, а также выполнения различных запросов к этой таблице.

## Технологии

- **Java 17+** - язык программирования
- **Spring Boot 3.x** - фреймворк для создания приложений
- **Spring Data JPA** - для работы с базой данных через ORM
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
│   │   │       ├── controller/       # REST контроллеры
│   │   │       ├── service/          # Бизнес-логика
│   │   │       ├── repository/       # Доступ к БД (JPA)
│   │   │       ├── entity/           # JPA сущности
│   │   │       └── Application.java  # Точка входа
│   │   └── resources/
│   │       ├── application.yml       # Конфигурация приложения
│   │       ├── schema.sql            # Инициализация БД
│   │       └── data.sql              # Тестовые данные
│   └── test/                         # Тесты
├── pom.xml                           # Конфигурация Maven
├── mvnw / mvnw.cmd                   # Maven Wrapper
└── README.md
```

## Схема базы данных

### Таблица PERSONS

```sql
CREATE TABLE PERSONS (
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    phone_number VARCHAR(20),
    city_of_living VARCHAR(50),
    PRIMARY KEY (name, surname, age)
);
```

**Поля таблицы:**
- `name` - имя человека (строка, обязательное)
- `surname` - фамилия человека (строка, обязательное)
- `age` - возраст человека (целое число, обязательное)
- `phone_number` - номер телефона (строка, опциональное)
- `city_of_living` - город проживания (строка, опциональное)
- Первичный ключ образован полями name, surname и age

## Требования

- Java 17 или выше
- Maven 3.6+
- Любая СУБД с поддержкой SQL (PostgreSQL, MySQL, SQLite, H2 и т.д.)

## Установка и запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/iDolph1n/users_table.git
cd users_table
```

### 2. Сборка проекта

С помощью Maven:

```bash
mvn clean package
```

Ор с использованием Maven Wrapper:

```bash
./mvnw clean package
./mvnw.cmd clean package  # Windows
```

### 3. Конфигурация базы данных

Отредактируйте файл `src/main/resources/application.yml`:

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

Для разработки с H2 (база по умолчанию):

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

### Получить пользователя по id
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
```

### Удалить пользователя
```
DELETE /api/persons/{id}
```

### Найти всех жителей Москвы
```
GET /api/persons/city/Moscow
```

### Найти всех людей старше 27 лет (отсортировано по возрасту)
```
GET /api/persons/age-gt/27
```

## Тестирование

Запуск тестов:

```bash
mvn test
```

## Использование

1. Запустите приложение согласно инструкциям выше
2. Используйте REST API для управления данными
3. Данные будут автоматически сохранены в выбранную базу данных
4. При необходимости отредактируйте SQL скрипты инициализации в `src/main/resources/`

## Миграция с предыдущей версии

Если вы использовали предыдущую версию с отдельными SQL скриптами:

- `01_create_persons.sql` → встроена в JPA entity и Hibernate DDL
- `02_select_moscow.sql` → `GET /api/persons/city/Moscow`
- `03_select_age_gt_27.sql` → `GET /api/persons/age-gt/27`

## Разработка

### Добавление новых эндпоинтов

1. Создайте новый метод в `repository/PersonRepository.java` для custom запросов
2. Добавьте логику в `service/PersonService.java`
3. Создайте метод в `controller/PersonController.java`

### Пример custom запроса в JPA

```java
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByCityOfLiving(String city);
    List<Person> findByAgeGreaterThanOrderByAgeDesc(int age);
}
```

## Лицензия

MIT

## Автор

iDolph1n
