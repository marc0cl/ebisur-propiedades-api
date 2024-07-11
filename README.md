# Ebisur API

Este proyecto es una API RESTful segura construida con Spring Boot 3 y Kotlin, utilizando las mejores prácticas de desarrollo y seguridad.

## Características Principales

- Autenticación y autorización basada en JWT
- Manejo de roles de usuario
- Operaciones CRUD para usuarios
- Validación de entrada robusta
- Protección contra vulnerabilidades comunes de seguridad web

## Tecnologías y Frameworks

- Kotlin 1.9.x
- Spring Boot 3.2.x
- Spring Security
- JSON Web Tokens (JWT)
- JPA / Hibernate
- Gradle
- H2 Database (desarrollo) / MySQL (producción)

## Estructura del Proyecto

```
src
├── main
│   ├── kotlin
│   │   └── org
│   │       └── ebisur
│   │           ├── Application.kt
│   │           ├── config
│   │           │   ├── SecurityConfig.kt
│   │           │   └── AuthConfig.kt
│   │           ├── controller
│   │           │   ├── AuthController.kt
│   │           │   └── UserController.kt
│   │           ├── dto
│   │           │   └── UserDto.kt
│   │           ├── model
│   │           │   └── User.kt
│   │           ├── repository
│   │           │   └── UserRepository.kt
│   │           ├── security
│   │           │   └── JwtAuthenticationFilter.kt
│   │           └── service
│   │               ├── JwtService.kt
│   │               └── UserService.kt
│   └── resources
│       ├── application.properties
│       ├── application-dev.properties
│       └── application-prod.properties
└── test
    └── kotlin
        └── org
            └── ebisur
                └── ApplicationTests.kt

build.gradle.kts
```

## Buenas Prácticas y Patrones de Diseño Implementados

1. **Arquitectura en Capas**: Separación clara de responsabilidades entre controladores, servicios y repositorios.

2. **DTO Pattern**: Uso de Data Transfer Objects para la comunicación entre capas, evitando la exposición directa de entidades de dominio.

3. **Inyección de Dependencias**: Utilización de Spring para la inyección de dependencias, reduciendo el acoplamiento.

4. **Principio de Responsabilidad Única (SRP)**: Cada clase tiene una única responsabilidad bien definida.

5. **Manejo de Excepciones Centralizado**: Uso de `@ControllerAdvice` para manejar excepciones de manera global.

6. **Validación de Entrada**: Implementación de validaciones robustas usando anotaciones de validación de Jakarta.

7. **Seguridad en Capas**: Implementación de seguridad a nivel de método y a nivel de URL.

8. **Configuración Externalizada**: Uso de archivos de propiedades específicos para diferentes entornos.

9. **Encriptación de Contraseñas**: Uso de BCrypt para el hash seguro de contraseñas.

10. **Autenticación Stateless**: Implementación de autenticación basada en JWT para mantener la aplicación stateless.

## Anotaciones Clave Utilizadas

- `@SpringBootApplication`: Configura la aplicación Spring Boot.
- `@Configuration`: Define clases de configuración de Spring.
- `@EnableWebSecurity`: Habilita la seguridad web de Spring.
- `@EnableMethodSecurity`: Permite la seguridad a nivel de método.
- `@Service`: Marca clases como componentes de servicio.
- `@Repository`: Indica que una clase es un repositorio de datos.
- `@RestController`: Define controladores REST.
- `@RequestMapping`: Mapea rutas HTTP a métodos de controlador.
- `@Transactional`: Gestiona transacciones de base de datos.
- `@Entity`: Marca clases como entidades JPA.
- `@Id`, `@GeneratedValue`: Configuran claves primarias en entidades.
- `@Column`: Personaliza el mapeo de columnas de base de datos.
- `@Value`: Inyecta valores de propiedades en campos.

## Consideraciones de Seguridad

- Implementación de CORS configurado para entornos específicos.
- Protección contra CSRF deshabilitada para APIs stateless (considerar habilitar para aplicaciones con estado).
- Uso de HTTPS obligatorio en producción.
- Implementación de rate limiting para prevenir ataques de fuerza bruta.
- Validación y sanitización de todas las entradas de usuario.
- Uso de cabeceras de seguridad HTTP.
- Rotación regular de secretos JWT.

## Configuración y Ejecución

1. Clonar el repositorio.
2. Configurar las propiedades de la base de datos en `application-dev.properties` y `application-prod.properties`.
3. Para desarrollo local:
   ```
   ./gradlew bootRun --args='--spring.profiles.active=dev'
   ```
4. Para producción:
   ```
   ./gradlew bootRun --args='--spring.profiles.active=prod'
   ```

## Pruebas

Ejecutar pruebas unitarias e integración:
```
./gradlew test
```

## Seguridad y Mejores Prácticas

- Mantener todas las dependencias actualizadas regularmente.
- Realizar auditorías de seguridad periódicas.
- Implementar logging extensivo para todas las operaciones críticas.
- Utilizar variables de entorno para secrets en producción.
- Realizar backups regulares de la base de datos.

## Contribución

Las contribuciones son bienvenidas. Por favor, abra un issue para discutir cambios mayores antes de crear un pull request.
