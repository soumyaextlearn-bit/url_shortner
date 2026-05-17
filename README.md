# URL Shortener 🚀

A production-style URL Shortener backend application built using Spring Boot, Redis, MySQL, Docker, and automated API testing.

---

# Features

## Core Features

- Create short URLs
- Redirect to original URLs
- Custom short codes
- URL expiry support
- Click tracking

## Backend Engineering Features

- Redis caching
- Graceful Redis fallback
- Rate limiting using Redis
- Scheduled cleanup jobs
- Global exception handling
- Structured logging using SLF4J

## DevOps & Infrastructure

- Dockerized application
- Docker Compose setup
- MySQL + Redis containers
- Health checks
- Environment variable configuration

## API Documentation & Testing

- Swagger/OpenAPI documentation
- Automated API sanity suite
- Rest Assured + TestNG
- HTML reports using Extent Reports

---

# Tech Stack

| Technology | Usage |
|---|---|
| Java 21 | Backend Language |
| Spring Boot | REST APIs |
| MySQL | Persistent Storage |
| Redis | Caching & Rate Limiting |
| Docker | Containerization |
| Docker Compose | Multi-container orchestration |
| Swagger | API Documentation |
| Rest Assured | API Automation |
| TestNG | Testing Framework |

---

# Frontend

| Technology |
|---|---|
| REACT |
| Vite | 
| Tailwind CSS |
| Axios |

---

# Architecture

```text
                ┌──────────────────┐
                │     Client       │
                └────────┬─────────┘
                         │
                         ▼
                ┌──────────────────┐
                │ Spring Boot API  │
                └───────┬──────────┘
                        │
         ┌──────────────┴──────────────┐
         ▼                             ▼
 ┌──────────────┐              ┌──────────────┐
 │    Redis     │              │    MySQL     │
 │ Cache + Rate │              │ Persistent   │
 │   Limiting   │              │   Storage    │
 └──────────────┘              └──────────────┘
```

---

# API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | /shorten | Create short URL |
| GET | /{shortCode} | Redirect to original URL |

---

# Swagger Documentation

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Docker Setup

## Start Application

```bash
docker compose up --build
```

## Stop Application

```bash
docker compose down
```

## View Logs

```bash
docker compose logs -f
```

---

# Local Development

```bash
mvn spring-boot:run
```

---

# Rate Limiting

Implemented using Redis fixed-window counter algorithm.

- Maximum 5 requests per minute per IP
- Automatic TTL expiry
- Graceful degradation if Redis is unavailable

---

# Redis Caching

- Short URL caching
- Faster redirects
- Reduced DB load
- Graceful fallback support

---

# Automated API Testing

Built using:
- Rest Assured
- TestNG
- Extent Reports

Generate reports using:

```bash
mvn test
```

Generated report:

```text
test-output/ExtentReport.html
```

# Author

Soumya Ranjan Parida
