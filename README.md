# claude-ai-demo

A Spring Boot 4 application paired with an **Angular 18** frontend. It ships a small Person / Address / JobFunction domain — full CRUD for persons (with criteria-based search and pagination) and read/create endpoints for addresses and job functions — and OpenAPI docs.

## Tech stack

| Layer | Technology |
|---|---|
| Language / runtime | Java 25 |
| Framework | Spring Boot 4.0.5 |
| Persistence | Spring Data JPA + in-memory H2 (`ddl-auto=create-drop`) |
| API docs | SpringDoc OpenAPI / Swagger UI |
| Build | Maven (`./mvnw`) |
| Frontend | Angular 18 + Angular Material |

## Prerequisites

- Java 25
- Node.js (for the Angular frontend)

> No external database is required — H2 runs in-memory and is recreated on every startup.

## Build & run

```bash
./mvnw clean install              # Full build with tests
./mvnw clean package -DskipTests  # Package without running tests
./mvnw spring-boot:run            # Run the application
./mvnw test                       # Run unit tests
```

## Frontend

The Angular client lives in [`frontend/`](frontend):

```bash
cd frontend
npm install
npm start        # ng serve with proxy to the backend
```

## Useful endpoints

| Endpoint | Description |
|---|---|
| `/swagger-ui.html` | Swagger UI |
| `/api-docs` | OpenAPI JSON |
| `/h2-console` | H2 database console |

## REST API

The domain is exposed under `/api/v1`:

- `GET    /api/v1/persons` — list all persons
- `POST   /api/v1/persons/search` — search by criteria with pagination (`?page=`, `?size=`, `?sort=field,(asc|desc)`)
- `GET    /api/v1/persons/{id}` — get a person
- `POST   /api/v1/persons` — create a person
- `PUT    /api/v1/persons/{id}` — update a person
- `DELETE /api/v1/persons/{id}` — delete a person

Addresses (`/api/v1/addresses`) and job functions (`/api/v1/functions`) expose `GET` (list and by-id) and `POST` endpoints.

## Project layout

```
src/main/java/com/example/claudeaidemo
├── config        # Web, OpenAPI, data initialization
├── controller    # REST controllers
├── dto           # Request/response DTOs, search criteria, paging
├── entity        # JPA entities (Person, Address, JobFunction)
├── exception     # Global exception handling
├── mapper        # Entity <-> DTO mappers
├── repository    # Spring Data repositories + JPA Specifications
└── service       # Business logic
frontend/         # Angular 18 client
```
