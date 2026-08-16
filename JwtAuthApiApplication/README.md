
# JWTAuthAPI

A REST API authentication service built with Spring Boot, Spring Security, JWT and PostgreSQL.

The project demonstrates a complete backend and DevOps workflow including authentication, database persistence, automated testing, static code analysis, Docker, Docker Compose, Kubernetes and Helm.

---

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Authentication Flow](#authentication-flow)
- [API Endpoints](#api-endpoints)
- [User Registration](#user-registration)
- [User Login](#user-login)
- [JWT Authentication](#jwt-authentication)
- [Database](#database)
- [Configuration](#configuration)
- [Testing](#testing)
- [Docker](#docker)
- [Docker Compose](#docker-compose)
- [CI/CD](#cicd)
- [Kubernetes](#kubernetes)
- [Helm](#helm)
- [Health Checks](#health-checks)
- [OpenAPI / Swagger](#openapi--swagger)
- [Postman](#postman)
- [Security](#security)
- [Future Improvements](#future-improvements)

---

# Overview

JWTAuthAPI is a Spring Boot REST API that provides user registration and authentication.

The application uses JSON Web Tokens (JWT) for stateless authentication.

Passwords are stored using BCrypt hashing and are never stored as plain text.

The application also contains a DevOps setup for containerization and Kubernetes deployment.

Main components:

- Spring Boot REST API
- Spring Security
- JWT authentication
- PostgreSQL
- Spring Data JPA
- Docker
- Docker Compose
- GitHub Actions
- SpotBugs
- Kubernetes
- Helm
- Spring Boot Actuator

---

# Architecture

The application follows a layered architecture.

```text
                         +----------------------+
                         |       Client         |
                         | Postman / Browser    |
                         +----------+-----------+
                                    |
                                    | HTTP
                                    v
                         +----------------------+
                         |   AuthController     |
                         | /auth/register      |
                         | /auth/login         |
                         +----------+-----------+
                                    |
                                    v
                         +----------------------+
                         |     AuthService      |
                         +----------+-----------+
                                    |
                       +------------+------------+
                       |                         |
                       v                         v
              +----------------+        +----------------+
              |   UserService  |        |   JwtService   |
              +-------+--------+        +----------------+
                      |                         |
                      v                         v
              +---------------+          +-------------+
              | UserRepository|          | JWT Token   |
              +-------+-------+          +-------------+
                      |
                      v
              +---------------+
              |  PostgreSQL   |
              +---------------+


Protected requests
-------------------

Client
  |
  | Authorization: Bearer <JWT>
  v
JwtAuthFilter
  |
  v
JwtService
  |
  | validate token
  v
UserService
  |
  v
SecurityContext
  |
  v
Protected endpoint

```
---
Jasně, Lukáši — takže chceš **ten samý obsah**, ale **uvnitř MD rámečku**, **a zároveň aby uvnitř byly normální Markdown anotace** (nadpisy `#`, `##`, seznamy `-`, atd.).

Tady to máš **kompletně převedené do čistého Markdownu**, celé v jednom kopírovatelném bloku:

---


# Technologies

| Technology         | Purpose                         |
|--------------------|---------------------------------|
| Java 21            | Programming language             |
| Spring Boot        | Application framework            |
| Spring Web         | REST API                         |
| Spring Security    | Authentication & authorization   |
| JWT                | Stateless authentication         |
| Spring Data JPA    | Database access                  |
| Hibernate          | ORM                              |
| PostgreSQL 16      | Relational database              |
| Maven              | Build & dependency management    |
| JUnit              | Testing                          |
| Docker             | Containerization                 |
| Docker Compose     | Local multi-container environment|
| GitHub Actions     | CI/CD                            |
| SpotBugs           | Static analysis                  |
| Kubernetes         | Container orchestration          |
| Helm               | Kubernetes package management    |
| Spring Boot Actuator | Health monitoring             |

---

# Project Structure

```
JwtAuthApiApplication/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/JWTAuthAPI/
│   │   │   ├── AuthController.java
│   │   │   ├── AuthService.java
│   │   │   ├── AuthResponse.java
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegisterRequest.java
│   │   │   ├── JwtService.java
│   │   │   ├── JwtAuthFilter.java
│   │   │   ├── SecurityConfig.java
│   │   │   ├── UserEntity.java
│   │   │   ├── UserRepository.java
│   │   │   ├── UserService.java
│   │   │   ├── Role.java
│   │   │   └── JwtAuthApiApplication.java
│   │   │
│   │   └── resources/application.properties
│   │
│   └── test/java/com/example/JWTAuthAPI/
│       ├── AuthServiceTest.java
│       ├── JwtServiceTest.java
│       ├── UserServiceTest.java
│       └── JwtAuthApiApplicationTests.java
│
├── helm/
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates/
│
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md

```
---

# Authentication Flow

## Registration

```
POST /auth/register
|
v
AuthController
|
v
AuthService
|
+---- BCrypt password hashing
|
+---- Save UserEntity
|
+---- Generate JWT
|
v
AuthResponse
```

## Login

```
POST /auth/login
|
v
AuthController
|
v
AuthService
|
+---- Find user by email
|
+---- Verify BCrypt password
|
+---- Generate JWT
|
v
AuthResponse
```

## Protected Request

```
Client
|
| Authorization: Bearer <token>
v
JwtAuthFilter
|
v
JwtService
|
+---- Extract username
+---- Validate signature
+---- Validate expiration
|
v
UserService
|
v
SecurityContext
```

---

# API Endpoints

## Register

### Request

```
POST /auth/register
Content-Type: application/json
{
"firstname": "Lukas",
"lastname": "Simek",
"email": "lukas@test.com",
"password": "heslo123",
"role": "USER"
}
```

### Response

```
{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

## Login

### Request

```
POST /auth/login
Content-Type: application/json
{
"email": "lukas@test.com",
"password": "heslo123"
}
```

### Response


{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}


---

# JWT Authentication

- Tokens generated by **JwtService**
- Contains:
  - subject (email)
  - issue time
  - expiration time
  - signature
- Lifetime: **24 hours**
- Sent via:

```
Authorization: Bearer <JWT>
```

- Validated by **JwtAuthFilter**

---

# Spring Security

- Stateless authentication (`SessionCreationPolicy.STATELESS`)
- Public endpoints:

```
/auth/**
```

- Everything else requires authentication
- Passwords hashed using **BCrypt**

---

# Database

- PostgreSQL 16
- Table: `app_user`
- Entity fields:
  - id
  - firstname
  - lastname
  - email
  - password
  - role
  - createdAt
  - updatedAt

---
## Flyway Database Migrations

This project uses Flyway to manage and version database schema changes. All SQL migration files are located in `src/main/resources/db/migration` and must follow the naming convention `V<version>__<description>.sql` (for example, `V1__create_app_user.sql`). When the application starts, Flyway checks the `flyway_schema_history` table, compares the applied migrations with the available files, and automatically executes any new migrations in the correct order. This ensures that the database schema always matches the current version of the application.

The initial migration creates the `app_user` table, which stores user information such as first name, last name, email, password, role, and timestamp fields. To add a new migration, simply create another SQL file (e.g., `V2__add_refresh_token_table.sql`) in the migration directory. Flyway will detect and apply it automatically on the next application startup.

Flyway configuration is defined in `application.properties`:


---

# Configuration

### Datasource

```
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/JWTAuthApi}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:postgres}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:}
```

### Hibernate

```
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Actuator

```
management.endpoints.web.exposure.include=health
management.endpoint.health.show-details=always
```

---

# Testing

Test classes:

- AuthServiceTest
- JwtServiceTest
- UserServiceTest
- JwtAuthApiApplicationTests

Run tests:

```
mvn clean verify
```

---

# Docker

### Build

```
docker build -t jwt-app .
```

### Run

```
docker run -p 8080:8080 jwt-app
```

### Multi-stage build

- Build: `maven:3.9.6-eclipse-temurin-21`
- Runtime: `eclipse-temurin:21-jre`
- Runs as non-root user `appuser`

---

# Docker Compose

```
docker compose up --build
```

Architecture:

```
Spring Boot (:8080)
|
v
PostgreSQL (:5432)
|
v
jwt_postgres_data volume
```

---

# CI/CD (GitHub Actions)

Pipeline:

- Checkout
- Setup Java 21
- Maven build + tests
- SpotBugs
- Docker build
- Docker Hub login
- Docker push (only on master)

Image:

```
lukasdedd/jwt-app:latest
```

---

# Kubernetes

Includes:

- Deployment
- Service (ClusterIP)
- Ingress
- HPA
- Probes
- Resource limits

### Install

```
helm install jwt-app ./helm
```

### Check

```
kubectl get pods
kubectl get services
kubectl get deployments
```

---

# Helm Configuration

Example:

```
replicaCount: 1

image:
repository: docker.io/lukasdedd/jwt-app
tag: "latest"
pullPolicy: IfNotPresent

service:
type: ClusterIP
port: 8080
```

---

# Health Checks

Uses:

```
/actuator/health
```

For:

- liveness
- readiness
- startup

---

# Autoscaling

```
autoscaling:
enabled: true
minReplicas: 1
maxReplicas: 100
targetCPUUtilizationPercentage: 80
```

---

# Future Improvements

- Swagger/OpenAPI
- Postman collection
- ControllerAdvice
- Validation
- Flyway migrations
- Kubernetes Secrets
- External PostgreSQL
- Versioned Docker images
- GitHub Actions deploy
- Prometheus/Grafana
- Metrics
- Testcontainers
- Refresh tokens
- RBAC
- Secret management

---

# Project Status

Complete backend + DevOps learning project:

- Spring Boot  
- Spring Security  
- JWT  
- PostgreSQL  
- Docker  
- GitHub Actions  
- Kubernetes  
- Helm  

---

# License

Educational & portfolio project.

---


