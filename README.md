# claude-ai-demo

A Spring Boot 4 application demonstrating **Spring AI** integration with **Anthropic Claude** models, paired with an **Angular 18** frontend. It ships a small Person / Address / JobFunction CRUD domain with criteria-based search and pagination, full observability (OpenTelemetry + Prometheus + Grafana LGTM), and OpenAPI docs.

## Tech stack

| Layer | Technology |
|---|---|
| Language / runtime | Java 25 |
| Framework | Spring Boot 4.0.5 |
| AI | Spring AI (`spring-ai-starter-model-anthropic`) |
| Persistence | Spring Data JPA + in-memory H2 (`ddl-auto=create-drop`) |
| Secrets | HashiCorp Vault (`spring-cloud-starter-vault-config`) — disabled by default locally |
| Observability | OpenTelemetry, Micrometer/Prometheus, Actuator, Grafana LGTM (via Testcontainers) |
| API docs | SpringDoc OpenAPI / Swagger UI |
| Build | Maven (`./mvnw`) |
| Frontend | Angular 18 + Angular Material |

## Prerequisites

- Java 25
- Docker (for integration tests using Testcontainers)
- An Anthropic API key (via Vault or the `SPRING_AI_ANTHROPIC_API_KEY` env var)
- Node.js (for the Angular frontend)

> No external database is required — H2 runs in-memory and is recreated on every startup.

## Build & run

```bash
./mvnw clean install              # Full build with tests
./mvnw clean package -DskipTests  # Package without running tests
./mvnw spring-boot:run            # Run the application
./mvnw test                       # Run unit tests
./mvnw verify                     # Run integration tests (requires Docker)
```

By default Vault is disabled and OTLP export is off, so the app starts cleanly on its own.

### Run with the full observability stack

`TestClaudeAiDemoApplication` starts a `grafana/otel-lgtm` container (Loki, Grafana, Tempo, Mimir) and wires the OTLP exporters to it via `@ServiceConnection`:

```bash
./mvnw test -pl . -Dspring-boot.run.main-class=com.example.claudeaidemo.TestClaudeAiDemoApplication
```

(or run `TestClaudeAiDemoApplication` directly from your IDE).

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
| `/actuator/health` | Health check |
| `/actuator/prometheus` | Prometheus metrics |

## REST API

The domain is exposed under `/api/v1`:

- `GET    /api/v1/persons` — list all persons
- `POST   /api/v1/persons/search` — search by criteria with pagination (`?page=`, `?size=`, `?sort=field,(asc|desc)`)
- `GET    /api/v1/persons/{id}` — get a person
- `POST   /api/v1/persons` — create a person
- `PUT    /api/v1/persons/{id}` — update a person
- `DELETE /api/v1/persons/{id}` — delete a person

Analogous endpoints exist for `addresses` and `job-functions`.

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
