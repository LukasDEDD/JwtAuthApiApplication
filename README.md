# JWTAuthAPI

Spring Boot REST API for user registration and login using JWT authentication and PostgreSQL.

## Tech Stack

- Java 21
- Spring Boot
- Spring Security
- JWT
- PostgreSQL 16
- Spring Data JPA
- Maven
- Docker
- Docker Compose
- Kubernetes
- Helm
- GitHub Actions
- SpotBugs

## Features

- User registration
- User login
- BCrypt password hashing
- JWT token generation and validation
- Stateless Spring Security authentication
- PostgreSQL database
- Docker multi-stage build
- Docker Compose
- Kubernetes deployment with Helm
- Health checks with Spring Boot Actuator
- CI pipeline with GitHub Actions
- SpotBugs static analysis
- Docker image published to Docker Hub

## API

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Authenticate user and receive JWT |

# Run with Docker Compose

---

# Run with Docker Compose

```bash
docker compose up --build

**Application:**

http://localhost:8080

**PostgreSQL:**

localhost:5432

---

# Run tests

```bash
mvn clean verify
```

---

# Build Docker image

```bash
docker build -t jwt-app .
```

---

# Kubernetes / Helm

```bash
helm install jwt-app ./helm
```

---

# CI/CD

GitHub Actions performs:

- Maven build and tests
- SpotBugs analysis
- Docker image build
- Docker Hub login
- Docker image push

The Docker image is pushed to Docker Hub **only after a push to the master branch**.

---

# Architecture

```
Client
  |
  v
Spring Boot REST API
  |
  +---- Spring Security
  |        |
  |        +---- JWT Filter
  |
  +---- AuthService
  |        |
  |        +---- UserService
  |                 |
  |                 v
  |            UserRepository
  |
  v
PostgreSQL
```
---

## Flyway Migrations

Flyway is used to version and manage database schema changes. All SQL migration files are stored in `src/main/resources/db/migration` and follow the naming pattern `V<version>__<description>.sql`. Flyway automatically executes any new migrations on application startup and records them in the `flyway_schema_history` table. The project currently includes a migration that creates the `app_user` table used for storing user account data.

---

# JWTAuthApi – Azure Deployment

This Spring Boot application is deployed in Microsoft Azure using Azure Container Apps. The container image is stored in Azure Container Registry.

The application uses Azure Database for PostgreSQL as its primary data storage. Connection details are provided through environment variables:

SPRING_DATASOURCE_URL  
SPRING_DATASOURCE_USERNAME  
SPRING_DATASOURCE_PASSWORD
```
Datasource configuration:

spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/JWTAuthApi}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}

The application runs on port 8080.
```
---

# License

This project is for learning and portfolio purposes.
```

---