#                          Описание проекта
REST-сервис предоставляет интерфейс для возможности загрузки файлов и вывода списка уже загруженных файлов пользователя по заранее описанной спецификации.

Все запросы к сервису авторизованы.

Заранее подготовленное веб-приложение (FRONT) подключается к разработанному сервису без доработок, а также использует функционал FRONT для авторизации, загрузки и вывода списка файлов пользователя.

Изначально FRONT доступен на порту 8080, BACKEND - на порту 5050.

Описание проекта "с картинками" смотри чуть ниже.

### Стартовые пользователи:
### Username: test
### Password: test

## Описание реализации:
1. Приложение разработано с использованием Spring Boot;
2. Использован сборщик пакетов Maven;
3. Использована база данных PostgreSql;
4. Использована система управления миграциями Liquibase;
5. Для запуска используется docker, docker-compose;
6. Код размещен на github;
7. Код покрыт unit тестами с использованием mockito;
8. Добавлены интеграционные тесты с использованием testcontainers;
9. Информация о пользователях сервиса хранится в базе данных;
10. Информация о файлах пользователей сервиса хранится в базе данных.

# Запуск приложения
## Запуск FRONT:
1. Установить nodejs (версия не ниже 14.15.0) на компьютер следуя инструкции;
2. Скачать FRONT (JavaScript);
3. Перейти в папку FRONT приложения и все команды для запуска выполнять из нее;
4. Следуя описанию README.md FRONT проекта запустить nodejs приложение (npm install...);
5. Для запуска FRONT приложения с расширенным логированием использовать команду: npm run serve.

# Запуск BACKEND:
1. Скачать проект.
2. С помощью maven собрать проект (mvn package)
3. Запустить docker-compose.yml