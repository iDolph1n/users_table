# Users Table - Migration to Hibernate with Flyway

## Описание ветки

Эта ветка (`migration-hibernate`) содержит реализацию миграции проекта Users Table на использование **Flyway** для управления миграциями базы данных вместо встроенных скриптов Hibernate/Spring.

## Основные изменения

### 1. Flyway интеграция

- Добавлена зависимость `spring-boot-starter-flyway` в `pom.xml`
- Настроена конфигурация Flyway в `application.yml`
- SQL миграции размещены в `src/main/resources/db/migration/`

### 2. Структура миграций

```
src/main/resources/db/migration/
├── V1__Create_persons_table.sql       # Создание таблицы PERSONS
├── V2__Add_sample_data.sql            # Добавление тестовых данных
└── V3__Configure_persons_endpoint.sql # Настройка эндпоинта (если необходимо)
```

### 3. Именование файлов миграций

Файлы миграций следуют соглашению Flyway:
- **V** - версионированная миграция
- **1**, **2**, **3** - порядковый номер версии
- **__** - разделитель
- Описание миграции в snake_case
- **.sql** - расширение файла

## Конфигурация

### application.yml

```yaml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
```

### pom.xml зависимость

```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```

## Процесс работы

1. При запуске приложения Flyway автоматически проверяет версию БД
2. Применяет все неиспользованные миграции в порядке возрастания версии
3. Ведёт лог выполненных миграций в таблице `flyway_schema_history`
4. Предотвращает повторное выполнение уже примененных миграций

## Преимущества Flyway

✅ **Версионирование** - четкий контроль версий БД  
✅ **Воспроизводимость** - одинаковые результаты на любой машине  
✅ **Отслеживание** - история всех примененных миграций  
✅ **Откат поддержки** - возможность отката (для платной версии)  
✅ **Независимость** - не зависит от Hibernate DDL автоматизации  

## Тестирование

### Локальный запуск

```bash
# Сборка проекта
mvn clean package

# Запуск приложения с применением миграций
mvn spring-boot:run
```

### Проверка миграций

После запуска приложения проверьте таблицу `flyway_schema_history`:

```sql
SELECT * FROM flyway_schema_history ORDER BY installed_rank;
```

## API Endpoints

Все эндпоинты из основной ветки `main` остаются неизменными:

- `GET /api/persons` - получить всех пользователей
- `POST /api/persons` - создать нового пользователя
- `GET /api/persons/{id}` - получить пользователя по ID
- `PUT /api/persons/{id}` - обновить пользователя
- `DELETE /api/persons/{id}` - удалить пользователя
- `GET /api/persons/city/{city}` - найти по городу
- `GET /api/persons/age-gt/{age}` - найти старше определённого возраста

## Сравнение с main веткой

| Аспект | main | migration-hibernate |
|--------|------|---------------------|
| Управление миграциями | Встроенные SQL скрипты | Flyway |
| Версионирование БД | Implicit | Explicit |
| История миграций | Нет | Да (flyway_schema_history) |
| Откат | Ручной | Управляемый |
| Надежность | Базовая | Продвинутая |

## Требования

- Java 17 или выше
- Maven 3.6+
- Spring Boot 3.x
- H2/PostgreSQL/MySQL

## Дальнейшее развитие

- [ ] Добавить R (Repeatable) миграции для функций и представлений
- [ ] Настроить Flyway callback'и для логирования
- [ ] Интегрировать Flyway с CI/CD пайплайном
- [ ] Добавить документацию по откату миграций

## Pull Request

Эта ветка готова к созданию Pull Request для слияния с `main`.

**Статус:** ✅ Готово к ревью

---

Для вопросов или предложений создавайте Issue или обращайтесь к [автору](https://github.com/iDolph1n).
