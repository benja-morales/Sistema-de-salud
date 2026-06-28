# Consultorio - Endpoints Principales

Este documento resume los principales endpoints del sistema **Consultorio**, desarrollado mediante una arquitectura de microservicios utilizando **Spring Boot**, **Spring Cloud**, **Eureka Server**, **API Gateway**, **OpenFeign**, **JWT**, **MySQL** y **Swagger/OpenAPI**.

---

# Arquitectura General

El sistema está compuesto por múltiples microservicios independientes, registrados en Eureka Server y consumidos mediante un API Gateway centralizado.

---

# Puertos del Sistema

| Servicio           | Puerto | Descripción                                     |
| ------------------ | -----: | ----------------------------------------------- |
| API Gateway        |   8080 | Punto único de entrada a las APIs               |
| ms-examen          |   8081 | Gestión de solicitudes y resultados de exámenes |
| ms-factura         |   8082 | Gestión de facturación y cuentas médicas        |
| ms-autenticacion   |   8083 | Gestión de usuarios y autenticación JWT         |
| ms-atencion-medica |   8084 | Registro de atenciones médicas                  |
| ms-paciente        |   8085 | Gestión de pacientes                            |
| ms-farmacia        |   8086 | Gestión de medicamentos                         |
| ms-medico          |   8087 | Gestión de médicos                              |
| ms-receta          |   8088 | Gestión de recetas médicas                      |
| ms-cita            |   8089 | Gestión de citas médicas                        |
| ms-notificaciones  |   8090 | Gestión de notificaciones                       |
| Eureka Server      |   8761 | Servidor de descubrimiento de servicios         |

---

# 1. Acceso por API Gateway

El API Gateway permite consumir todos los microservicios desde un único punto de acceso.

```text
http://localhost:8080
```

| Recurso            | URL por Gateway                          |
| ------------------ | ---------------------------------------- |
| Pacientes          | http://localhost:8080/api/pacientes      |
| Médicos            | http://localhost:8080/api/medicos        |
| Citas              | http://localhost:8080/api/citas          |
| Atenciones Médicas | http://localhost:8080/api/atenciones     |
| Recetas            | http://localhost:8080/api/recetas        |
| Farmacia           | http://localhost:8080/api/medicamentos   |
| Exámenes           | http://localhost:8080/api/examenes       |
| Facturas           | http://localhost:8080/api/facturas       |
| Notificaciones     | http://localhost:8080/api/notificaciones |
| Autenticación      | http://localhost:8080/auth               |

---

# 2. Swagger por Microservicio

La documentación interactiva de cada API se encuentra disponible mediante Swagger UI.

| Microservicio      | URL Swagger                                 |
| ------------------ | ------------------------------------------- |
| ms-paciente        | http://localhost:8085/swagger-ui/index.html |
| ms-medico          | http://localhost:8087/swagger-ui/index.html |
| ms-cita            | http://localhost:8089/swagger-ui/index.html |
| ms-receta          | http://localhost:8088/swagger-ui/index.html |
| ms-atencion-medica | http://localhost:8084/swagger-ui/index.html |
| ms-farmacia        | http://localhost:8086/swagger-ui/index.html |
| ms-examen          | http://localhost:8081/swagger-ui/index.html |
| ms-factura         | http://localhost:8082/swagger-ui/index.html |
| ms-notificaciones  | http://localhost:8090/swagger-ui/index.html |
| ms-autenticacion   | http://localhost:8083/swagger-ui/index.html |

---

# 3. Microservicio Paciente

## Puerto Directo

```text
http://localhost:8085
```

## Endpoints

| Método | Endpoint            | Descripción                 |
| ------ | ------------------- | --------------------------- |
| GET    | /api/pacientes      | Obtener todos los pacientes |
| GET    | /api/pacientes/{id} | Obtener paciente por ID     |
| POST   | /api/pacientes      | Registrar paciente          |
| PUT    | /api/pacientes/{id} | Actualizar paciente         |
| DELETE | /api/pacientes/{id} | Eliminar paciente           |

## Ejemplo Crear Paciente

```http
POST /api/pacientes
Content-Type: application/json
```

```json
{
    "rut": "12345678",
    "dv": "9",
    "nombre": "Juan",
    "apellido": "Pérez",
    "email": "juan@consultorio.cl",
    "telefono": "987654321",
    "activo": true
}
```

---

# 4. Microservicio Médico

## Puerto Directo

```text
http://localhost:8087
```

## Endpoints

| Método | Endpoint          | Descripción               |
| ------ | ----------------- | ------------------------- |
| GET    | /api/medicos      | Obtener todos los médicos |
| GET    | /api/medicos/{id} | Obtener médico por ID     |
| POST   | /api/medicos      | Registrar médico          |
| PUT    | /api/medicos/{id} | Actualizar médico         |
| DELETE | /api/medicos/{id} | Eliminar médico           |

## Ejemplo Crear Médico

```json
{
    "nombre": "María",
    "apellido": "González",
    "especialidad": "Cardiología",
    "correo": "maria@consultorio.cl",
    "telefono": "912345678"
}
```

---

# 5. Microservicio Cita

## Puerto Directo

```text
http://localhost:8089
```

## Endpoints

| Método | Endpoint        | Descripción             |
| ------ | --------------- | ----------------------- |
| GET    | /api/citas      | Obtener todas las citas |
| GET    | /api/citas/{id} | Obtener cita por ID     |
| POST   | /api/citas      | Registrar cita          |
| PUT    | /api/citas/{id} | Actualizar cita         |
| DELETE | /api/citas/{id} | Cancelar cita           |

## Ejemplo Crear Cita

```json
{
    "idPaciente": 1,
    "idMedico": 1,
    "fecha": "2026-07-01",
    "hora": "10:30",
    "estado": "PROGRAMADA"
}
```

---

# 6. Microservicio Atención Médica

## Puerto Directo

```text
http://localhost:8084
```

## Endpoints

| Método | Endpoint             | Descripción             |
| ------ | -------------------- | ----------------------- |
| GET    | /api/atenciones      | Obtener atenciones      |
| GET    | /api/atenciones/{id} | Obtener atención por ID |
| POST   | /api/atenciones      | Registrar atención      |
| PUT    | /api/atenciones/{id} | Actualizar atención     |
| DELETE | /api/atenciones/{id} | Eliminar atención       |

## Ejemplo Registrar Atención

```json
{
    "idPaciente": 1,
    "idMedico": 1,
    "diagnostico": "Hipertensión",
    "tratamiento": "Control mensual y medicamentos"
}
```

---

# 7. Microservicio Receta

## Puerto Directo

```text
http://localhost:8088
```

## Endpoints

| Método | Endpoint          | Descripción           |
| ------ | ----------------- | --------------------- |
| GET    | /api/recetas      | Obtener recetas       |
| GET    | /api/recetas/{id} | Obtener receta por ID |
| POST   | /api/recetas      | Crear receta          |
| PUT    | /api/recetas/{id} | Actualizar receta     |
| DELETE | /api/recetas/{id} | Eliminar receta       |

## Ejemplo Crear Receta

```json
{
    "idAtencion": 1,
    "medicamento": "Paracetamol",
    "dosis": "500 mg cada 8 horas"
}
```

---

# 8. Microservicio Farmacia

## Puerto Directo

```text
http://localhost:8086
```

## Endpoints

| Método | Endpoint               | Descripción                |
| ------ | ---------------------- | -------------------------- |
| GET    | /api/medicamentos      | Obtener medicamentos       |
| GET    | /api/medicamentos/{id} | Obtener medicamento por ID |
| POST   | /api/medicamentos      | Registrar medicamento      |
| PUT    | /api/medicamentos/{id} | Actualizar medicamento     |
| DELETE | /api/medicamentos/{id} | Eliminar medicamento       |

## Ejemplo Registrar Medicamento

```json
{
    "nombre": "Ibuprofeno",
    "stock": 100,
    "precio": 3500
}
```

---

# 9. Microservicio Examen

## Puerto Directo

```text
http://localhost:8081
```

## Endpoints

| Método | Endpoint           | Descripción           |
| ------ | ------------------ | --------------------- |
| GET    | /api/examenes      | Obtener exámenes      |
| GET    | /api/examenes/{id} | Obtener examen por ID |
| POST   | /api/examenes      | Registrar examen      |
| PUT    | /api/examenes/{id} | Actualizar examen     |
| DELETE | /api/examenes/{id} | Eliminar examen       |

---

# 10. Microservicio Facturación

## Puerto Directo

```text
http://localhost:8082
```

## Endpoints

| Método | Endpoint           | Descripción            |
| ------ | ------------------ | ---------------------- |
| GET    | /api/facturas      | Obtener facturas       |
| GET    | /api/facturas/{id} | Obtener factura por ID |
| POST   | /api/facturas      | Crear factura          |
| PUT    | /api/facturas/{id} | Actualizar factura     |
| DELETE | /api/facturas/{id} | Eliminar factura       |

## Ejemplo Crear Factura

```json
{
    "idPaciente": 1,
    "monto": 25000,
    "estado": "PENDIENTE"
}
```

---

# 11. Microservicio Notificaciones

## Puerto Directo

```text
http://localhost:8090
```

## Endpoints

| Método | Endpoint                 | Descripción                 |
| ------ | ------------------------ | --------------------------- |
| GET    | /api/notificaciones      | Obtener notificaciones      |
| GET    | /api/notificaciones/{id} | Obtener notificación por ID |
| POST   | /api/notificaciones      | Enviar notificación         |
| DELETE | /api/notificaciones/{id} | Eliminar notificación       |

---

# 12. Microservicio Autenticación

## Puerto Directo

```text
http://localhost:8083
```

## Endpoints

| Método | Endpoint       | Descripción                  |
| ------ | -------------- | ---------------------------- |
| POST   | /auth/login    | Iniciar sesión               |
| POST   | /auth/register | Registrar usuario            |
| GET    | /usuarios      | Obtener usuarios registrados |

## Ejemplo Login

```json
{
    "username": "admin",
    "password": "admin123"
}
```

---

# 13. Flujo Completo de Prueba

Para probar completamente el sistema se recomienda seguir el siguiente flujo:

1. Registrar un paciente.
2. Registrar un médico.
3. Agendar una cita médica.
4. Registrar la atención médica.
5. Emitir una receta.
6. Registrar medicamentos en farmacia.
7. Solicitar un examen.
8. Generar una factura.
9. Enviar una notificación al paciente.

---

# 14. Comunicación entre Microservicios

| Servicio Origen   | Servicio Destino   | Motivo                            |
| ----------------- | ------------------ | --------------------------------- |
| ms-cita           | ms-paciente        | Validar existencia del paciente   |
| ms-cita           | ms-medico          | Validar disponibilidad del médico |
| ms-receta         | ms-atencion-medica | Obtener datos de la atención      |
| ms-factura        | ms-atencion-medica | Obtener información clínica       |
| ms-notificaciones | ms-paciente        | Obtener datos del paciente        |

---

# 15. Códigos de Respuesta Esperados

| Código HTTP | Descripción                          |
| ----------- | ------------------------------------ |
| 200         | Solicitud realizada correctamente    |
| 201         | Recurso creado exitosamente          |
| 400         | Solicitud inválida                   |
| 401         | Usuario no autenticado               |
| 403         | Acceso denegado                      |
| 404         | Recurso no encontrado                |
| 500         | Error interno del servidor           |
| 503         | Servicio temporalmente no disponible |

---

# 16. Tecnologías Utilizadas

* Java 17
* Spring Boot
* Spring Cloud
* Spring Security
* JWT
* OpenFeign
* Eureka Server
* API Gateway
* MySQL
* Maven
* Swagger/OpenAPI
* JUnit 5
* Mockito

---

# Autor

Proyecto desarrollado para la asignatura **Desarrollo Full Stack II - Arquitectura de Microservicios**.
