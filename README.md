# Hogar360 - Microservicio de Visitas

Microservicio encargado de gestionar la disponibilidad y reserva de visitas a inmuebles dentro de la plataforma **Hogar360**. Permite a vendedores o agentes crear ventanas de disponibilidad y a compradores reservar citas para visitar propiedades.

## Descripcion

Este microservicio hace parte de una arquitectura de microservicios y se comunica con el [Microservicio de Casas](https://github.com) (`houses-ms`) a traves de **OpenFeign** para validar la propiedad de los inmuebles y obtener informacion de ubicacion.

**Funcionalidades principales:**

- Crear disponibilidad de visitas (solo vendedores)
- Buscar y filtrar visitas por fecha, ubicacion y paginacion
- Consultar visitas disponibles por inmueble
- Reservar una visita como comprador (maximo 2 reservas por visita)

## Stack Tecnologico

| Componente         | Tecnologia                |
| ------------------ | ------------------------- |
| Lenguaje           | Java 17                   |
| Framework          | Spring Boot 3.2.5         |
| Arquitectura       | Hexagonal (Puertos y Adaptadores) |
| Base de datos      | MySQL 8                   |
| ORM                | Spring Data JPA / Hibernate |
| Seguridad          | Spring Security + JWT     |
| Comunicacion       | Spring Cloud OpenFeign    |
| Mapeo de Objetos   | MapStruct 1.6.3           |
| Construccion       | Gradle 8.13               |
| Documentacion API  | SpringDoc OpenAPI 2.5.0   |
| Pruebas            | JUnit 5 + Mockito 5.11.0  |
| Cobertura          | JaCoCo 0.8.8              |

## Arquitectura

El proyecto sigue el patron de **Arquitectura Hexagonal** con las siguientes capas:

```
src/main/java/com/hogar360/visits/visits/
├── domain/                          ← Logica de negocio pura (sin dependencias de framework)
│   ├── model/                       ← Modelos de dominio (VisitModel, VisitReservationModel)
│   ├── criteria/                    ← Objetos de valor para busqueda (VisitCriteria)
│   ├── usecases/                    ← Casos de uso y reglas de negocio
│   ├── ports/
│   │   ├── in/                      ← Puertos de entrada (VisitServicePort, VisitReservationServicePort, RoleValidatorPort)
│   │   └── out/                     ← Puertos de salida (persistence, HouseServicePort)
│   ├── exceptions/                  ← Excepciones de dominio
│   └── utils/                       ← Constantes y utilidades de dominio
│
├── application/                     ← Orquestacion y capa de servicios
│   ├── services/                    ← Interfaces de servicio
│   │   └── impl/                    ← Implementaciones de servicios
│   ├── dto/
│   │   ├── request/                 ← DTOs de entrada (records)
│   │   └── response/                ← DTOs de salida (records)
│   └── mappers/                     ← Mappers MapStruct (DTO <-> Dominio)
│
└── infrastructure/                  ← Implementaciones framework-especificas
    ├── endpoints/rest/              ← Controladores REST
    ├── entities/                    ← Entidades JPA
    ├── repositories/                ← Repositorios Spring Data
    ├── specifications/              ← JPA Specifications para consultas dinamicas
    ├── adapters/
    │   ├── persistence/             ← Adaptadores de persistencia (Puerto -> Repositorio)
    │   └── feign/                   ← Adaptador Feign (Puerto -> Servicio externo)
    ├── feigns/                      ← Clientes Feign y DTOs de respuesta
    ├── mappers/                     ← Mappers MapStruct (Entidad <-> Dominio)
    ├── security/                    ← Filtros JWT, configuracion de seguridad
    └── exceptionhandlers/           ← Manejo global de excepciones
```

## Prerequisitos

- Java 17 o superior
- Gradle 8.13 (incluido via wrapper)
- MySQL 8 corriendo en `localhost:3306`
- Base de datos `hogar360_visits` creada
- Microservicio `houses-ms` corriendo en el puerto `8090`

## Inicio Rapido

1. **Clonar el repositorio:**

```bash
git clone https://github.com/tu-usuario/hogar360-visits-ms.git
cd hogar360-visits-ms
```

2. **Configurar las variables de entorno** (ver seccion siguiente).

3. **Crear la base de datos en MySQL:**

```sql
CREATE DATABASE hogar360_visits;
```

4. **Ejecutar la aplicacion:**

```bash
# Windows
gradlew.bat bootRun

# Linux / macOS
./gradlew bootRun
```

La aplicacion estara disponible en `http://localhost:8092`.

## Variables de Entorno

| Variable                | Descripcion                                  | Ejemplo                        |
| ----------------------- | -------------------------------------------- | ------------------------------ |
| `DB_USER`               | Usuario de MySQL                             | `root`                         |
| `DB_PASSWORD`           | Contrasena de MySQL                          | `password123`                  |
| `JWT_SECRET_KEY`        | Clave HMAC para firmar y verificar tokens JWT| `mySecretKey1234567890...`     |
| `JWT_EXPIRATION_MILLIS` | Tiempo de expiracion del token en milisegundos| `3600000` (1 hora)            |

## Endpoints API

| Metodo | Ruta                                       | Autenticacion   | Descripcion                                      |
| ------ | ------------------------------------------ | --------------- | ------------------------------------------------ |
| `POST`   | `/api/v1/visits/`                         | JWT (VENDEDOR)  | Crear disponibilidad de visita                   |
| `GET`    | `/api/v1/visits/search`                   | Publico         | Buscar visitas con filtros y paginacion          |
| `GET`    | `/api/v1/visits/available/{houseId}`      | Publico         | Listar visitas disponibles para un inmueble      |
| `POST`   | `/api/v1/visits/reservations/`            | Publico         | Reservar una visita                              |

### Ejemplo: Crear disponibilidad de visita

```http
POST /api/v1/visits/
Content-Type: application/json
Authorization: Bearer <token>

{
  "houseId": 123,
  "startDateTime": "2025-05-01T10:00:00",
  "endDateTime": "2025-05-01T11:00:00"
}
```

### Ejemplo: Buscar visitas

```http
GET /api/v1/visits/search?cityId=1&sector=Norte&page=0&size=10&sortBy=startDateTime&sortDirection=asc
```

## Estructura del Proyecto

```
hogar360-visits-ms/
├── build.gradle                          # Configuracion de dependencias y plugins
├── settings.gradle                       # Nombre del proyecto
├── gradlew / gradlew.bat                 # Gradle wrapper
├── src/
│   ├── main/
│   │   ├── java/com/hogar360/visits/
│   │   │   ├── VisitsApplication.java
│   │   │   ├── commons/configurations/   # Configuracion de beans y constantes
│   │   │   └── visits/
│   │   │       ├── domain/               # Modelos, casos de uso, puertos, excepciones
│   │   │       ├── application/          # Servicios, DTOs, mappers
│   │   │       └── infrastructure/       # Controladores, entidades, repositorios, adaptadores
│   │   └── resources/
│   │       └── application.yml           # Configuracion de la aplicacion
│   └── test/
│       └── java/com/hogar360/visits/
│           └── visits/domain/usecases/   # Pruebas unitarias de casos de uso
```

## Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
gradlew.bat test

# Ejecutar pruebas con reporte de cobertura
gradlew.bat test jacocoTestReport
```

Los reportes de cobertura se generan en:
- HTML: `build/reports/jacoco/test/html/index.html`
- XML: `build/reports/jacoco/test/jacocoTestReport.xml`

## Documentacion API

Una vez ejecutada la aplicacion, la documentacion interactiva esta disponible en:

- **Swagger UI:** `http://localhost:8092/swagger-ui/index.html`
- **OpenAPI Docs:** `http://localhost:8092/api-docs`

## Reglas de Negocio

- Solo usuarios con rol **VENDEDOR** pueden crear disponibilidad de visitas.
- El vendedor debe ser el propietario verificado del inmueble.
- La fecha de inicio debe ser futura y la fecha de fin posterior a la de inicio.
- La visita debe programarse dentro de las proximas 3 semanas.
- No se permiten horarios solapados para el mismo vendedor.
- Cada visita admite un maximo de **2 reservas**.
- Un comprador no puede reservar la misma visita dos veces.

## Licencia

Este proyecto es privado. Todos los derechos reservados.
