# Sistema-de-salud

# Sistema de Gestión Clínica - Arquitectura de Microservicios

## Descripción General

Proyecto académico desarrollado utilizando arquitectura de microservicios con Spring Boot.

El sistema permite gestionar:

* Pacientes
* Médicos
* Medicamentos
* Recetas
* Citas médicas
* Exámenes
* Notificaciones
* Facturación
* Atención médica
* Autenticación de usuarios

La comunicación entre microservicios se realiza mediante Eureka Server y OpenFeign.

---

# Tecnologías Utilizadas

## Backend

* Java 21
* Spring Boot 3
* Spring Data JPA
* Spring Web
* Spring Validation
* OpenFeign
* Eureka Discovery Server
* Lombok
* Maven

## Base de Datos

* MySQL
* XAMPP

## Herramientas

* Postman
* Draw.io
* GitHub
* IntelliJ IDEA

---

# Arquitectura del Proyecto

## Microservicios

| Microservicio      | Función Principal                   | Puerto |
| ------------------ | ----------------------------------- | ------ |
| Eureka Server      | Descubrimiento de servicios         | 8761   |
| API Gateway        | Enrutamiento y acceso centralizado  | 8080   |
| ms-paciente        | Gestión de pacientes                | 8085   |
| ms-farmacia        | Gestión de medicamentos y stock     | 8086   |
| ms-medico          | Gestión de médicos                  | 8087   |
| ms-receta          | Gestión de recetas médicas          | 8088   |
| ms-citas           | Gestión de citas médicas            | 8089   |
| ms-notificaciones  | Gestión de notificaciones           | 8090   |
| ms-examenes        | Gestión de exámenes clínicos        | 8081   |
| ms-facturacion     | Gestión de facturación              | 8082   |
| ms-atencion-medica | Gestión de atenciones médicas       | 8084   |
| ms-autenticacion   | Gestión de autenticación y usuarios | 8083   |

---

# Orden de Ejecución

Levantar los servicios en el siguiente orden:

```bash
1. Eureka Server
2. API Gateway
3. ms-paciente
4. ms-farmacia
5. ms-medico
6. ms-autenticacion
7. ms-receta
8. ms-citas
9. ms-examenes
10. ms-notificaciones
11. ms-facturacion
12. ms-atencion-medica
```

---

# Eureka Server

URL:

```text
http://localhost:8761
```

Todos los servicios deben aparecer como:

```text
UP
```

---

# Funcionalidades Implementadas

## Pacientes

* Crear pacientes
* Editar pacientes
* Eliminar pacientes
* Obtener pacientes
* Validaciones de datos

## Médicos

* Registrar médicos
* Editar médicos
* Eliminar médicos
* Gestión de especialidades

## Farmacia

* Registrar medicamentos
* Actualizar stock
* Editar medicamentos
* Eliminar medicamentos
* Gestión de inventario

## Recetas

* Crear recetas
* Asociar medicamentos
* Obtener recetas por paciente
* Obtener recetas por médico
* Validaciones de medicamentos
* Comunicación entre microservicios

## Citas

* Crear citas
* Actualizar estado
* Eliminar citas
* Gestión de horarios

## Exámenes

* Registrar exámenes
* Actualizar estados
* Registrar resultados
* Consultar exámenes por paciente

## Notificaciones

* CRUD de notificaciones
* Listados dinámicos
* Integración con citas
* Integración con recetas
* Integración con exámenes

## Facturación

* Generación de facturas
* Consulta por paciente
* Gestión de pagos

## Atención Médica

* Registro de atenciones
* Diagnósticos médicos
* Observaciones clínicas

## Autenticación

* Registro de usuarios
* Inicio de sesión
* Gestión básica de usuarios

---

# Comunicación Entre Microservicios

## OpenFeign

### ms-receta consume:

* ms-paciente
* ms-medico
* ms-farmacia

### ms-notificaciones consume:

* ms-citas
* ms-receta
* ms-examenes

### ms-atencion-medica consume:

* ms-paciente
* ms-medico

---

# Endpoints Principales

## ms-paciente

Base URL:

```text
http://localhost:8085/api/pacientes
```

| Método | Endpoint            |
| ------ | ------------------- |
| GET    | /api/pacientes      |
| GET    | /api/pacientes/{id} |
| POST   | /api/pacientes      |
| PUT    | /api/pacientes/{id} |
| DELETE | /api/pacientes/{id} |

---

## ms-medico

Base URL:

```text
http://localhost:8087/api/medicos
```

| Método | Endpoint          |
| ------ | ----------------- |
| GET    | /api/medicos      |
| GET    | /api/medicos/{id} |
| POST   | /api/medicos      |
| PUT    | /api/medicos/{id} |
| DELETE | /api/medicos/{id} |

---

## ms-farmacia

Base URL:

```text
http://localhost:8086/api/medicamentos
```

| Método | Endpoint               |
| ------ | ---------------------- |
| GET    | /api/medicamentos      |
| GET    | /api/medicamentos/{id} |
| POST   | /api/medicamentos      |
| PUT    | /api/medicamentos/{id} |
| DELETE | /api/medicamentos/{id} |

---

## ms-receta

Base URL:

```text
http://localhost:8088/api/recetas
```

| Método | Endpoint                           |
| ------ | ---------------------------------- |
| GET    | /api/recetas                       |
| GET    | /api/recetas/{id}                  |
| GET    | /api/recetas/paciente/{idPaciente} |
| GET    | /api/recetas/medicos/{idMedico}    |
| POST   | /api/recetas                       |
| DELETE | /api/recetas/{id}                  |

---

## ms-citas

Base URL:

```text
http://localhost:8089/api/citas
```

| Método | Endpoint        |
| ------ | --------------- |
| GET    | /api/citas      |
| GET    | /api/citas/{id} |
| POST   | /api/citas      |
| PUT    | /api/citas/{id} |
| DELETE | /api/citas/{id} |

---

## ms-examenes

Base URL:

```text
http://localhost:8081/api/examenes
```

| Método | Endpoint                            |
| ------ | ----------------------------------- |
| GET    | /api/examenes                       |
| GET    | /api/examenes/{id}                  |
| GET    | /api/examenes/paciente/{idPaciente} |
| POST   | /api/examenes                       |
| PUT    | /api/examenes/{id}/estado           |
| DELETE | /api/examenes/{id}                  |

---

## ms-notificaciones

Base URL:

```text
http://localhost:8090/api/notificaciones
```

### CRUD

| Método | Endpoint                      |
| ------ | ----------------------------- |
| GET    | /api/notificaciones           |
| GET    | /api/notificaciones/{id}      |
| POST   | /api/notificaciones           |
| PUT    | /api/notificaciones/{id}/leer |
| DELETE | /api/notificaciones/{id}      |

### Integración dinámica

| Método | Endpoint                     |
| ------ | ---------------------------- |
| GET    | /api/notificaciones/citas    |
| GET    | /api/notificaciones/recetas  |
| GET    | /api/notificaciones/examenes |

---

## ms-facturacion

Base URL:

```text
http://localhost:8082/api/facturas
```

| Método | Endpoint                            |
| ------ | ----------------------------------- |
| GET    | /api/facturas/{id}                  |
| GET    | /api/facturas/paciente/{idPaciente} |
| GET    | /api/facturas/fecha/{fecha}         |
| POST   | /api/facturas                       |
| PUT    | /api/facturas/{id}                  |
| DELETE | /api/facturas/{id}                  |

---

## ms-atencion-medica

Base URL:

```text
http://localhost:8084/api/atenciones
```

| Método | Endpoint             |
| ------ | -------------------- |
| GET    | /api/atenciones      |
| GET    | /api/atenciones/{id} |
| POST   | /api/atenciones      |
| PUT    | /api/atenciones/{id} |
| DELETE | /api/atenciones/{id} |

---

## ms-autenticacion

Base URL:

```text
http://localhost:8083/api/auth
```

| Método | Endpoint                |
| ------ | ----------------------- |
| POST   | /api/auth/registro      |
| POST   | /api/auth/login         |
| GET    | /api/auth/usuarios      |
| DELETE | /api/auth/eliminar/{id} |

---

# Validaciones Implementadas

Se utilizaron validaciones Jakarta:

* `@NotBlank`
* `@NotNull`
* `@Email`

Además:

* manejo global de excepciones
* respuestas HTTP personalizadas
* validaciones automáticas

---

# Estructura General de Microservicios

Cada microservicio sigue arquitectura por capas:

```text
controller
service
repository
model
client
config
exception
```

---

# Estado Actual del Proyecto

## Funcionalidades verificadas

* Eureka operativo
* OpenFeign operativo
* CRUDs funcionales
* Persistencia MySQL
* Comunicación entre microservicios
* Validaciones
* Manejo de errores
* APIs REST
* Notificaciones dinámicas

---

# Video de Demostración

El proyecto incluye:

* demostración de arquitectura
* Eureka Server
* CRUD funcional
* validaciones
* integración Feign
* comunicación entre microservicios

---

# Integrantes

| Nombre       | Participación               |
| ------------ | --------------------------- |
| Integrante 1 | Backend / Microservicios    |
| Integrante 2 | Base de datos / Integración |
| Integrante 3 | Testing / Documentación     |

---

# Conclusión

El proyecto implementa una arquitectura de microservicios funcional utilizando Spring Boot.

La solución permite desacoplar responsabilidades, facilitar escalabilidad y mejorar la comunicación entre servicios mediante Eureka y OpenFeign.
