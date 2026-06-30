# CLAUDE.md
This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./mvnw clean install          # Full build with tests
./mvnw clean package -DskipTests  # Package without running tests
./mvnw spring-boot:run        # Run the application
./mvnw test                   # Run unit tests
```

## Architecture Overview

This is a Spring Boot 4.0.5 application exposing a small Person / Address / JobFunction REST domain.

**Key integrations:**
- **SpringDoc OpenAPI** — Swagger UI available at `/swagger-ui.html` once endpoints exist
- **Spring Data JPA** — database layer; uses an in-memory **H2** database (schema generated from entities via `ddl-auto=create-drop`). H2 console at `/h2-console`

**Runtime requirements:**
- Java 25
- No external database needed — H2 runs in-memory and is recreated on each startup