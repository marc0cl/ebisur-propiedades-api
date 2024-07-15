# Ebisur API

## Overview

Ebisur API is a robust, secure RESTful service built with Spring Boot 3 and Kotlin. It implements industry-standard security practices and follows modern development patterns to provide a scalable and maintainable backend solution.

## Key Features

- JWT-based authentication and authorization
- Role-based access control
- User management with CRUD operations
- Comprehensive input validation
- Protection against common web vulnerabilities
- Environment-specific configurations

## Tech Stack

- Kotlin 1.9.x
- Spring Boot 3.2.x
- Spring Security
- JSON Web Tokens (JWT)
- JPA / Hibernate
- Gradle
- H2 Database (Development) / MySQL (Production)

## Project Structure

```
src
├── main
│   ├── kotlin/org/ebisur
│   │   ├── config
│   │   ├── controller
│   │   ├── dto
│   │   ├── model
│   │   ├── repository
│   │   ├── security
│   │   └── service
│   └── resources
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test
    └── kotlin/org/ebisur
```

## Design Patterns and Best Practices

1. **Layered Architecture**: Clear separation of concerns between controllers, services, and repositories.
2. **DTO Pattern**: Utilization of Data Transfer Objects for inter-layer communication.
3. **Dependency Injection**: Leveraging Spring's IoC container for loose coupling.
4. **Single Responsibility Principle**: Each class has a well-defined, single responsibility.
5. **Global Exception Handling**: Centralized error management using `@ControllerAdvice`.
6. **Input Validation**: Robust input validation using Jakarta validation annotations.
7. **Multi-layered Security**: Method-level and URL-based security implementations.
8. **Externalized Configuration**: Environment-specific property files for flexible deployment.
9. **Secure Password Storage**: BCrypt hashing for password security.
10. **Stateless Authentication**: JWT-based authentication for scalability.

## Security Considerations

- CORS configuration tailored for specific environments
- CSRF protection strategy for stateless APIs
- Mandatory HTTPS in production
- Rate limiting to prevent brute-force attacks
- Comprehensive input validation and sanitization
- Implementation of security HTTP headers
- Regular rotation of JWT secrets

## Getting Started

1. Clone the repository:
   ```
   git clone https://github.com/marc0cl/ebisur-propiedades-api
   ```

2. Configure database properties in `application-dev.properties` and `application-prod.properties`.

3. Run the application:
   - Development:
     ```
     ./gradlew bootRun --args='--spring.profiles.active=dev'
     ```
   - Production:
     ```
     ./gradlew bootRun --args='--spring.profiles.active=prod'
     ```

## Testing

Execute the test suite:
```
./gradlew test
```

## Security Best Practices

- Regular dependency updates
- Periodic security audits
- Comprehensive logging for critical operations
- Use of environment variables for production secrets
- Regular database backups

## Contributing

We welcome contributions! Please open an issue to discuss major changes before submitting a pull request.
