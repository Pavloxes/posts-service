# Posts Service

REST API для работы с пользователями, публикациями и комментариями. Сервис написан на Java и Spring Boot, использует PostgreSQL, Flyway и Docker Compose.

После запуска:

- API доступно по адресу `http://localhost:8080`;
- Swagger UI — `http://localhost:8080/swagger-ui.html`;
- описание OpenAPI — `http://localhost:8080/v3/api-docs`.

## Что потребуется

На компьютере должны быть установлены:

- Git;
- Docker с поддержкой Docker Compose;
- JDK 17 или новее;
- Apache Maven.

PostgreSQL отдельно запускать не нужно: база данных поднимается в Docker-контейнере. По умолчанию контейнер публикует PostgreSQL на порту `5433`, поэтому обычно он не конфликтует с локальным PostgreSQL на стандартном порту `5432`.

Проверить установку можно командами:

```bash
git --version
docker --version
docker compose version
java -version
mvn -version
```

## Быстрый запуск

### 1. Склонировать проект

```bash
git clone https://github.com/Pavloxes/posts-service.git
cd posts-service
```

### 2. Создать файл с настройками

Linux и macOS:

```bash
cp .env.example .env
```

Windows PowerShell:

```powershell
Copy-Item .env.example .env
```

Содержимое `.env.example` уже подходит для локального запуска:

```dotenv
DB_HOST=localhost
DB_NAME=posts
DB_USERNAME=posts_user
DB_PASSWORD=change_me
DB_PORT=5433
```

При необходимости измените пароль или порт. Файл `.env` содержит локальные настройки и не добавляется в Git.

### 3. Запустить PostgreSQL

```bash
docker compose up -d
```

Убедитесь, что контейнер запущен и отмечен как `healthy`:

```bash
docker compose ps
```

При первом запуске Docker скачает образ PostgreSQL и создаст постоянный том `posts-postgres-data`.

### 4. Запустить сервис

```bash
mvn spring-boot:run
```

Приложение автоматически подключится к базе данных и применит миграции Flyway. Успешный запуск можно узнать по сообщению `Started PostsApplication` в консоли.

Откройте Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Проверка работы

Создать пользователя:

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{"name":"ivan","email":"ivan@example.com"}'
```

Получить пользователя с идентификатором `1`:

```bash
curl http://localhost:8080/users/1
```

Создать текстовую публикацию пользователя `1`:

```bash
curl -X POST http://localhost:8080/users/1/posts \
  -H "Content-Type: application/json" \
  -d '{"type":"TEXT","content":"Первая публикация","picturePath":null}'
```

Все доступные запросы и их форматы можно посмотреть в Swagger UI.

## Остановка

Остановите приложение сочетанием `Ctrl+C` в терминале, где выполняется Maven.

Остановить контейнер PostgreSQL:

```bash
docker compose down
```

Данные базы сохранятся и будут доступны при следующем запуске.

Чтобы также удалить локальные данные базы, выполните:

```bash
docker compose down -v
```

> Команда с флагом `-v` необратимо удаляет том с данными проекта.

## Повторный запуск

```bash
docker compose up -d
mvn spring-boot:run
```

## Возможные проблемы

### Порт `5433` уже занят

Укажите другой свободный порт в `.env`, например:

```dotenv
DB_PORT=5434
```

Затем пересоздайте контейнер:

```bash
docker compose down
docker compose up -d
```

### Порт `8080` уже занят

Запустите приложение на другом порту:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8081
```

Swagger UI в этом случае будет доступен по адресу `http://localhost:8081/swagger-ui.html`.

### Приложение не подключается к базе

Проверьте состояние и журнал PostgreSQL:

```bash
docker compose ps
docker compose logs postgres
```

Также убедитесь, что значения `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` и `DB_PORT` в `.env` не менялись после создания контейнера. Если их нужно изменить для уже созданной тестовой базы, удалите том командой `docker compose down -v` и запустите контейнер заново. Все прежние локальные данные при этом будут удалены.
