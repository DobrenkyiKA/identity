# 📖 Identity Microservice Description

The **Identity Microservice** is the centralized authentication and authorization component for the Interview Preparation & Knowledge System. It manages user accounts, roles, and issues secure tokens (JWT) for cross-service communication.

---

## 🎯 Business Perspective & Responsibilities

The microservice is responsible for **Identity and Access Management (IAM)**. It ensures that only authorized users can access the system's resources and provides different levels of access based on user roles.

### Key Responsibilities:
- **User Management**: Handling user registration and profile management.
- **Authentication**: Verifying user credentials (login) and issuing JSON Web Tokens (JWT).
- **Token Management**: Managing the lifecycle of access and refresh tokens.
- **Authorization**: Defining and managing user roles (e.g., USER, ADMIN) to control access to various features.
- **Security**: Ensuring secure password storage and implementing modern security standards (OAuth2/JWT).

---

## 🛠 Technology Stack

The microservice is built using a stable and secure Java-based stack:

- **Language**: [Java 25](https://www.oracle.com/java/)
- **Framework**: [Spring Boot 4.0.3](https://spring.io/projects/spring-boot)
- **Persistence**: 
    - [Spring Data JPA](https://spring.io/projects/spring-data-jpa) (Hibernate)
    - [PostgreSQL](https://www.postgresql.org/) (Database)
    - [Flyway](https://flywaydb.org/) (Database Migrations)
- **Security**: 
    - [Spring Security](https://spring.io/projects/spring-security)
    - [Bouncy Castle](https://www.bouncycastle.org/) (Cryptography)
    - OAuth2 Resource Server & JWT Support
- **API**: Spring Web (MVC) with OpenAPI (Swagger) documentation.
- **Build Tool**: Gradle (Groovy DSL)

---

## 🏗 Architecture

The microservice follows a **layered architecture** influenced by **Domain-Driven Design (DDD)** and **Hexagonal** principles, similar to other backend services in the project.

### Package Structure:
- `com.kdob.piq.user.domain`: Contains core domain models (`User`, `Role`), repository interfaces, and domain mappers.
- `com.kdob.piq.user.application`: Contains application services that orchestrate use cases (e.g., `AuthService`, `TokenService`, `UserService`).
- `com.kdob.piq.user.infrastructure`: Contains technical implementation details:
    - `persistence`: JPA entities, Spring Data repositories, and Flyway migration scripts.
    - `web`: REST controllers, request/response DTOs.
    - `config`: Spring Boot configurations for Security, JWT, JPA, and Password encoding.

### Key Design Patterns:
- **Repository Pattern**: Decoupling the domain from data access implementations.
- **DTO Pattern**: Using Data Transfer Objects for clean API contracts.
- **Service Layer**: Encapsulating business logic and transaction management.
- **Mapper Pattern**: Explicitly converting between domain objects, persistence entities, and API DTOs.
