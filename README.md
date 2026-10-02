# Banking Microservices

Prueba técnica de arquitectura de microservicios desarrollada con **Java y Spring Boot**, orientada a la gestión de clientes, cuentas bancarias, movimientos y generación de estados de cuenta.

## Arquitectura

La solución está separada en dos microservicios:

```text
                    Banking Microservices
                           │
             ┌─────────────┴─────────────┐
             │                           │
             ▼                           ▼
     customer-service             account-service
     ────────────────             ──────────────
     Persona                       Cuenta
     Cliente                       Movimiento
                                   Reportes
```
---
### Customer Service

Responsable de la gestión de:

* Persona
* Cliente

Endpoint principal:

```text
/api/clientes
```
---
### Account Service

Responsable de la gestión de:

* Cuenta
* Movimiento
* Estado de cuenta

Endpoints principales:

```text
/api/cuentas
/api/movimientos
/api/reportes
```
---
## Tecnologías

* Java 21
* Spring Boot 4.1.1
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven
* JUnit
* Mockito
* Testcontainers
* Postman
* Docker Desktop
---
## Estructura del proyecto

```text
banking-microservices/
│
├── customer-service/
│   ├── src/
│   ├── pom.xml
│   └── ...
│
├── account-service/
│   ├── src/
│   ├── pom.xml
│   └── ...
│
├── database/
│   └── BaseDatos.sql
│
├── postman/
│   └── banking-api.postman_collection.json
│
└── README.md
```
---
## Base de datos

La solución utiliza dos bases de datos PostgreSQL independientes:

```text
banking_customer
├── persona
└── cliente

banking_account
├── cuenta
└── movimiento
```

El script de estructura se encuentra en:

```text
database/BaseDatos.sql
```
---
## Configuración

Cada microservicio utiliza variables de entorno para configurar la conexión a PostgreSQL.

### Customer Service

```text
DB_URL=jdbc:postgresql://localhost:5432/banking_customer
DB_USERNAME=postgres
DB_PASSWORD=********
```

### Account Service

```text
DB_URL=jdbc:postgresql://localhost:5432/banking_account
DB_USERNAME=postgres
DB_PASSWORD=********
```

Las credenciales reales no forman parte del repositorio.
---
## Ejecución

### Customer Service

Desde la carpeta `customer-service`:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

### Account Service

Desde la carpeta `account-service`:

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```
---
## API REST

### Clientes

| Método | Endpoint                    | Descripción        |
| ------ | --------------------------- | ------------------ |
| GET    | `/api/clientes`             | Listar clientes    |
| GET    | `/api/clientes/{clienteId}` | Obtener cliente    |
| POST   | `/api/clientes`             | Crear cliente      |
| PUT    | `/api/clientes/{clienteId}` | Actualizar cliente |
| DELETE | `/api/clientes/{clienteId}` | Eliminar cliente   |

### Cuentas

| Método | Endpoint                      | Descripción       |
| ------ | ----------------------------- | ----------------- |
| GET    | `/api/cuentas`                | Listar cuentas    |
| GET    | `/api/cuentas/{numeroCuenta}` | Obtener cuenta    |
| POST   | `/api/cuentas`                | Crear cuenta      |
| PUT    | `/api/cuentas/{numeroCuenta}` | Actualizar cuenta |
| DELETE | `/api/cuentas/{numeroCuenta}` | Eliminar cuenta   |

### Movimientos

| Método | Endpoint           | Descripción          |
| ------ | ------------------ | -------------------- |
| POST   | `/api/movimientos` | Registrar movimiento |

Los movimientos permiten realizar depósitos y retiros, actualizando el saldo de la cuenta y registrando la transacción.

Cuando un retiro supera el saldo disponible, la API responde con:

```text
Saldo no disponible
```

---
### Reportes

```text
GET /api/reportes?clienteId={clienteId}&fechaInicio={fechaInicio}&fechaFin={fechaFin}
```

Ejemplo:

```text
GET /api/reportes?clienteId=1&fechaInicio=2026-10-01&fechaFin=2026-10-02
```

El reporte devuelve información en formato JSON incluyendo:

* Cliente
* Cuentas asociadas
* Saldo actual
* Estado de la cuenta
* Movimientos dentro del rango solicitado

El servicio valida que la fecha inicial no sea posterior a la fecha final y que el cliente tenga cuentas registradas.

---
## Manejo de errores

La aplicación utiliza un manejo global de excepciones mediante `@RestControllerAdvice`.

Entre los casos manejados se encuentran:

* Recursos no encontrados
* Registros duplicados
* Errores de validación
* Saldo insuficiente
* Tipo de movimiento inválido
* Rango de fechas inválido
* Clientes sin cuentas

Las respuestas de error contienen información como:

```json
{
  "timestamp": "2026-10-01T20:00:00",
  "status": 400,
  "message": "Saldo no disponible"
}
```

---
## Persistencia y transacciones

La persistencia se implementa utilizando **Spring Data JPA** y el patrón **Repository**.

El registro de movimientos utiliza una transacción para garantizar que:

1. Se consulte la cuenta.
2. Se valide el movimiento.
3. Se calcule el nuevo saldo.
4. Se actualice el saldo.
5. Se registre el movimiento.

Estas operaciones se ejecutan dentro de una misma transacción.

---
## Validaciones

Los DTOs utilizan Bean Validation para validar los datos recibidos por la API.

Entre las validaciones implementadas:

* Campos obligatorios.
* Valores positivos.
* Edad no negativa.
* Saldo no negativo.
* Identificadores válidos.
* Valor del movimiento mayor a cero.

---
## Pruebas

El proyecto contiene diferentes niveles de pruebas.

---
### Pruebas unitarias

Se incluye una prueba unitaria para la entidad de dominio `Cliente`.

También se incluyen pruebas unitarias para endpoints del `ClienteController`.

---
### Prueba de integración

Se incluye una prueba de integración utilizando **Testcontainers** y PostgreSQL para verificar la persistencia real de un cliente.

Para ejecutar las pruebas:

```bash
./mvnw test
```

En Windows:

```bash
mvnw.cmd test
```

---
## Postman

La colección utilizada para validar los endpoints se encuentra en:

```text
postman/banking-api.postman_collection.json
```

Puede importarse directamente en Postman.

---
## Consideraciones de arquitectura

La separación en microservicios permite mantener responsabilidades independientes:

```text
customer-service
        │
        │ Cliente / Persona
        │
        └────────────────────

account-service
        │
        │ Cuenta / Movimiento / Reporte
        │
        └────────────────────
```

Esta separación facilita una futura evolución independiente de cada servicio y permite incorporar mecanismos de comunicación asíncrona, escalabilidad y resiliencia.

---
## Estado de la solución

Implementado:

* CRUD de clientes.
* CRUD de cuentas.
* Registro de movimientos.
* Actualización de saldos.
* Validación de saldo insuficiente.
* Registro de transacciones.
* Reporte de estado de cuenta.
* Manejo global de excepciones.
* Validaciones de entrada.
* Pruebas unitarias.
* Prueba de integración.
* Persistencia con PostgreSQL.
* Separación en dos microservicios.

Como parte de la evolución de la solución se contempla la incorporación de comunicación asíncrona entre los microservicios y el despliegue completo de la solución mediante contenedores.

---
## Autor
Si este proyecto te ayudó, ¡no olvides darle una ⭐!

Para cualquier consulta o sugerencia, puedes contactarnos a través de:

- **WhatsApp**: (https://wa.me/+593984039120)
- **LinkedIn**: (https://www.linkedin.com/in/carlos-salcan/)
- **Email**: [dancode.lab01@gmail.com](mailto:dancode.lab01@gmail.com)

¡Gracias por visitar "Banking-Microservices"!

