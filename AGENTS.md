# AGENTS.md

## Overview

This repository implements a Java 21 + Spring Boot 4 backend for portfolio management with MCP (Model Context Protocol) integration.

The project is structured to maximize:

- Explicit architecture
- Predictable navigation
- Clear separation of concerns
- Domain-first design
- MCP as an external adapter layer

---

## Project Structure Overview

```text
project-root
├── docs/                  # Architecture, business, context, and rules documentation
│   ├── architecture/
│   ├── business/
│   ├── context/
│   └── rules/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/demo/investments_wallet/
│   │   │       ├── config/     # Spring configuration (currently present)
│   │   │       ├── domain/     # Business logic (core; add as implementation grows)
│   │   │       ├── mcp/        # MCP tools, resources, prompts (add as implementation grows)
│   │   │       ├── dto/        # Data transfer objects (add as implementation grows)
│   │   │       └── util/       # Shared utilities (add as implementation grows)
│   │   │
│   │   └── resources/
│   │       ├── db/migration/    # Flyway migrations
│   │       └── application.yaml
│   │
│   └── test/                   # Spring Boot test scaffolding
│
├── pom.xml
├── AGENTS.md
└── HELP.md
```

---

## Documentation Map (/docs)

The `/docs` folder contains system-level definitions split by concern:

- `architecture/overview.md` → Global architecture and layering rules
- `architecture/persistence.md` → Database model and JPA rules
- `architecture/mcp-tools.md` → MCP tools design rules
- `architecture/mcp-resources.md` → MCP resources design rules
- `architecture/mcp-prompts.md` → MCP prompts design rules
- `business/` → Domain-level business behavior and MCP-facing business definitions
- `context/` → Execution behavior and workflow definitions
- `rules/` → Implementation rules and conventions

---

## Rules Map (/docs/rules)

The `/docs/rules` folder defines strict project conventions:

- `coding-guideline.md`
- `java-guidelines.md`
- `spring-guidelines.md`
- `mcp-guidelines.md`
- `git-commit-guidelines.md`
- `instruction-authoring.md`

These rules are mandatory and must be followed in all implementations.

---

## Context Map (/docs/context)

The `/docs/context` folder defines how work should be executed:

- `stack.md` → Technology constraints and environment assumptions
- `conventions.md` → Naming and structural conventions
- `workflow.md` → Development workflow and process rules

---

## Architecture Principles

### 1. Domain First

The `domain` package (under `com.demo.investments_wallet`) is the single source of truth for business logic.

It contains:

- Entities
- Services
- Repositories
- Business rules
- Domain exceptions

It must NOT depend on MCP.

---

### 2. MCP as Adapter Layer

The `mcp` package (under `com.demo.investments_wallet`) is strictly an integration layer.

It contains:

- Tools (actions)
- Resources (read-only data)
- Prompts (context generation)

Rules:

- No business logic
- No persistence logic
- No domain rule duplication
- Only delegation to domain services

---

### 3. Configuration Layer

The `config` package is responsible for:

- Spring configuration
- MCP bean registration
- ModelMapper configuration
- Infrastructure wiring

Current example: `src/main/java/com/demo/investments_wallet/config/Logger.java` configures `CommonsRequestLoggingFilter`.

---

### 4. DTO Layer

The `dto` package contains:

- Input models
- Output models
- MCP external models (if needed)

Rules:

- Must not contain business logic
- Must not be JPA entities

---

### 5. Utilities

The `util` package contains:

- Stateless helpers
- Formatting utilities
- Calculation helpers (non-domain specific)

---

## MCP Design Rules

### Tools

- Represent executable actions
- Must delegate execution to domain services
- Must remain stateless
- Must not implement business logic

---

### Resources

- Represent read-only or contextual data
- Must not mutate state
- Must expose domain data safely
- Must remain framework-agnostic in design

---

### Prompts

- Represent reusable LLM context definitions
- Must be deterministic when possible
- Must not contain business logic
- Must compose domain and resource data

---

## Persistence Rules

- Use Spring Data JPA
- Prefer JPQL over native queries
- Avoid N+1 query problems
- Use fetch joins when necessary
- Keep transactions explicit
- Use PostgreSQL as the primary database
- Use Flyway for schema evolution

---

## Testing (Explicit Non-Goal)

- A bootstrap automated test exists at `src/test/java/com/demo/investments_wallet/InvestmentsWalletApplicationTests.java`
- Maven test starter dependencies are configured in `pom.xml`
- Broad feature-level test coverage is not implemented yet

---

## Deployment (Explicit Non-Goal)

- No Docker usage
- No CI/CD pipelines
- No Kubernetes or cloud deployment configuration
- Application is development-focused only

---

## Build System

- Maven is the build system
- Use Maven Wrapper commands from project root (Windows `cmd`):
  - `mvnw.cmd spring-boot:run`
  - `mvnw.cmd test`
  - `mvnw.cmd clean package`
- Dependency management via BOM / dependencyManagement
- Spring Boot auto-configuration is used with explicit overrides when needed

---

## Non-Goals

This project explicitly avoids:

- Overengineering
- Hidden framework behavior
- Distributed system complexity
- Unnecessary abstraction layers
- Premature optimization
- Automatic infrastructure concerns (CI/CD, Docker, etc.)

---

## Final Note

This repository prioritizes:

- Clear boundaries
- Explicit architecture
- Predictable structure
- MCP as a thin integration layer
- Domain as the true core of the system