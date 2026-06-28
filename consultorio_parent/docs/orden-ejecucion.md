# Consultorio - Orden de ejecución

## Requisitos previos

Antes de levantar el sistema se debe tener iniciado:

* XAMPP
* MySQL
* JDK 21 o superior
* Maven 3.9 o superior (nativo)
* Eureka Server
* Microservicios de negocio
* API Gateway

## Bases de datos

El proyecto utiliza una base de datos independiente por cada microservicio:

| Microservicio      | Base de datos      |
| ------------------ | ------------------ |
| ms-paciente        | bd_paciente        |
| ms-medico          | bd_medico          |
| ms-cita            | bd_cita            |
| ms-atencion-medica | bd_atencion_medica |
| ms-receta          | bd_receta          |
| ms-farmacia        | bd_farmacia        |
| ms-examen          | bd_examen          |
| ms-factura         | bd_factura         |
| ms-notificaciones  | bd_notificaciones  |
| ms-autenticacion   | bd_autenticacion   |

El script de creación se encuentra en:

```text
docs/init.sql
```

## Orden recomendado de ejecución

Para evitar errores de dependencias entre microservicios, se recomienda iniciar los servicios en el siguiente orden:

### 1. Eureka Server

Levantar primero el servidor de descubrimiento:

```text
http://localhost:8761
```

### 2. API Gateway

Levantar el API Gateway para centralizar todas las peticiones:

```text
http://localhost:8080
```

### 3. Microservicios independientes

Estos servicios no dependen directamente de otros microservicios para iniciar:

1. ms-autenticacion
2. ms-paciente
3. ms-medico
4. ms-farmacia
5. ms-examen
6. ms-notificaciones

### 4. Microservicios dependientes

Una vez iniciados los servicios anteriores, levantar:

1. ms-cita
2. ms-atencion-medica
3. ms-receta
4. ms-factura

## Verificación del sistema

Una vez iniciados todos los servicios, verificar:

### Eureka

```text
http://localhost:8761
```

Todos los microservicios deben aparecer con estado:

```text
UP
```

### API Gateway

```text
http://localhost:8080
```

### Swagger

Verificar que la documentación esté disponible en cada microservicio.

Ejemplo:

```text
http://localhost:8085/swagger-ui/index.html
```

## Flujo recomendado de pruebas

1. Registrar usuarios mediante ms-autenticacion.
2. Registrar pacientes.
3. Registrar médicos.
4. Registrar medicamentos.
5. Agendar citas médicas.
6. Registrar atenciones médicas.
7. Emitir recetas médicas.
8. Registrar exámenes.
9. Generar facturas.
10. Enviar notificaciones.

## Detención del sistema

Para detener correctamente la aplicación, cerrar los servicios en el siguiente orden:

1. API Gateway.
2. Microservicios dependientes.
3. Microservicios independientes.
4. Eureka Server.
