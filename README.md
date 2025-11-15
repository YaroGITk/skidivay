# PayApp — Spring Boot Microservices + MongoDB + JWT + Telegram MiniApp

Моно-репозиторий с микросервисной архитектурой:
- Spring Boot 3 (Java 21), Maven multi-module
- MongoDB (схемы + индексы в `mongo/mongo-init.js`)
- JWT-аутентификация
- Telegram Mini App авторизация по window.Telegram.WebApp.initData
- Docker Compose (gateway + 7 сервисов + mongo)

## Быстрый старт

1) Заполни `.env` на основе `.env.example`.
2) `docker compose up --build -d`
3) Фронтенд (MiniApp) стучится в `http://localhost:8080`.

## Сервисы

- `gateway-service` — входной шлюз (Spring Cloud Gateway).
- `auth-service` — логин по Telegram initData → выдаёт JWT.
- `user-service` — профиль пользователя.
- `details-service` — сохранённые реквизиты.
- `invoice-service` — счета + получатели.
- `activity-service` — активность пользователя.
- `hunted-service` — база HuntedBase.
- `session-service` — PyroSessions.

## Mongo

Инициализация коллекций и индексов — файл `mongo/mongo-init.js` (автоматически применится).

## Документация

- ER-диаграмма: `docs/ER.mmd` (Mermaid) и `docs/schema.dbml` (dbdiagram.io).


## Troubleshooting сборки в Docker
Если видите `Premature end of Content-Length` при скачивании зависимостей Maven внутри контейнера:
- Мы добавили `build/maven/settings.xml` и включили retries/таймауты.
- В Dockerfile теперь есть стадия `deps` (`dependency:go-offline`) с кэшированием `/root/.m2`.
- Рекомендуется включить BuildKit: `export DOCKER_BUILDKIT=1`
- Команда: `docker compose build` (без `--no-cache`, чтобы работал кэш зависимостей).
- При медленном интернете можно собрать на хосте: `mvn -q -DskipTests package`, а затем `docker compose build`.
