# CLAUDE.md
This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Run Commands

```bash
./mvnw clean install          # Full build with tests
./mvnw clean package -DskipTests  # Package without running tests
./mvnw spring-boot:run        # Run the application
./mvnw test                   # Run unit tests
./mvnw verify                 # Run integration tests (requires Docker for Testcontainers)
```

To run the app with the full observability stack (Grafana LGTM) locally:
```bash
./mvnw test -pl . -Dspring-boot.run.main-class=com.example.claudeaidemo.TestClaudeAiDemoApplication
# or run TestClaudeAiDemoApplication directly from your IDE
```

## Architecture Overview

This is a Spring Boot 4.0.5 application demonstrating Spring AI integration with Anthropic Claude models.

**Key integrations:**
- **Spring AI (`spring-ai-starter-model-anthropic`)** — Claude API access; configure via `spring.ai.anthropic.*` properties or Vault secrets
- **HashiCorp Vault (`spring-cloud-starter-vault-config`)** — runtime secrets (API keys, DB credentials); Vault must be available at startup unless overridden
- **OpenTelemetry + Prometheus + Actuator** — distributed tracing and metrics; auto-configured via `@ServiceConnection` in tests
- **SpringDoc OpenAPI** — Swagger UI available at `/swagger-ui.html` once endpoints exist
- **Spring Data JPA** — database layer; uses an in-memory **H2** database (schema generated from entities via `ddl-auto=create-drop`). H2 console at `/h2-console`

**Test infrastructure (`TestcontainersConfiguration`):**
- Spins up a `grafana/otel-lgtm` container providing the full LGTM observability stack (Loki, Grafana, Tempo, Mimir)
- `@ServiceConnection` auto-wires OpenTelemetry exporter endpoints to the container
- Use `TestClaudeAiDemoApplication` to run the app locally with this full stack active

**Runtime requirements:**
- Java 25
- Docker (for integration tests with Testcontainers)
- Vault instance (for secrets at startup; can be bypassed with `spring.cloud.vault.enabled=false` locally)
- Anthropic API key (via Vault or `SPRING_AI_ANTHROPIC_API_KEY` env var)
- No external database needed — H2 runs in-memory and is recreated on each startup