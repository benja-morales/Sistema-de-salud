# Consultorio - Sistema de Microservicios Médicos

Sistema multi-módulo corporativo para la gestión integral de un centro de salud y consultorio clínico. La plataforma distribuye las responsabilidades críticas de atención, emisión de recetas, facturación y logística de farmacia mediante microservicios independientes desarrollados con Spring Boot.

Este proyecto está diseñado como una Arquitectura de Microservicios Orquestada utilizando Eureka Server, API Gateway, Feign Client, MySQL, Swagger/OpenAPI y estructura Maven padre-hijos.

---

## 📥 0. Enlaces de Descarga y Entregables (Evaluación Parcial 3)
> ⚠️ **Nota para el Evaluador**: Los entregables han sido empaquetados de forma independiente para facilitar su revisión y mitigar conflictos de entornos locales.

* 📦 **Descargar Versión Nativa — Paquete .jar + .bat**: Incluye ejecutables compilados y scripts de automatización secuencial.
* 🐳 **Descargar Versión Docker — Entorno Contenedores**: Incluye `docker-compose.yml`, variables `.env` y scripts de respaldo.
* 🎬 **Ver Video de Defensa de la Arquitectura**: Duración aproximada: 15 minutos. El repositorio incluye el archivo de soporte `subtitulos-video.txt` en la raíz para garantizar la accesibilidad.

---

## 1. Objetivo del Proyecto
El sistema permite gestionar el flujo completo de atención de una clínica u hospital:

* Administrar pacientes titulares y sus fichas médicas.
* Controlar el alta de médicos y asignación de personal facultativo.
* Planificar, reservar y agendar citas médicas integradas.
* Registrar atenciones médicas (diagnósticos y evolución del paciente).
* Emitir recetas médicas vinculadas a la atención.
* Sincronizar el inventario físico de fármacos en farmacia.
* Solicitar órdenes y almacenar resultados de exámenes de laboratorio.
* Gestionar la facturación total y el detalle de cuentas por cobrar.
* Enviar alertas y notificaciones automáticas por SMS y Correo Electrónico.

---

## 2. Arquitectura General

```text
Cliente externo / Postman / Navegador
       │
       ▼
api-gateway :8080
       │
       ├──► ms-paciente        :8085  -> mspaciente
       ├──► ms-medico          :8087  -> msmedico
       ├──► ms_citas           :8089  -> mscitas
       ├──► ms-receta          :8088  -> msreceta
       ├──► msAtencionMedica   :8084  -> msatencionmedica
       ├──► ms-farmacia        :8086  -> msfarmacia
       ├──► ms-examen          :8081  -> consultorio_examenes_db
       ├──► msfacturacion      :8082  -> facturas
       ├──► ms-notificaciones  :8090  -> msnotificaciones
       └──► msAutentificacion  :8083  -> db_consultorio
       │
eureka-server :8761

```

---

## 3. Microservicios del Sistema y Matriz de Responsabilidades

| Componente | Descripción | Puerto | Base de Datos Asociada (SQL) |
| --- | --- | --- | --- |
| **init.sql** | Base de datos unificada del clúster | 3307 externo / 3306 interno | *Mapea todas las bases de datos de abajo* |
| **eureka-server** | Descubrimiento y registro de servicios | 8761 | *Ninguna / Registro en memoria* |
| **api-gateway** | Enrutamiento perimetral unificado | 8080 | *Ninguna / Enrutador* |
| **ms-paciente** | Gestión de fichas clínicas y pacientes | 8085 | `mspaciente` |
| **ms-medico** | Administración de personal de salud y especialidad | 8087 | `msmedico` |
| **ms_citas** | Gestión de reservas y agendas médicas | 8089 | `mscitas` |
| **ms-receta** | Generación de prescripciones y dosis | 8088 | `msreceta` |
| **msAtencionMedica** | Registro de atenciones y diagnósticos | 8084 | `msatencionmedica` |
| **ms-farmacia** | Inventario, precios y stock de medicamentos | 8086 | `msfarmacia` |
| **ms-examen** | Solicitudes y órdenes de laboratorio | 8081 | `consultorio_examenes_db` |
| **msfacturacion** | Emisión de cuentas, IVA y liquidaciones | 8082 | `facturas` |
| **ms-notificaciones** | Motor de envío de alertas SMS/Email | 8090 | `msnotificaciones` |
| **msAutentificacion** | Control de accesos, JWT y roles | 8083 | `db_consultorio` |

---

## 4. Tecnologías Utilizadas

* Java 17
* Spring Boot & Spring Cloud
* Eureka Server / Client & Spring Cloud Gateway
* OpenFeign
* Spring Web & Spring Data JPA
* MySQL / MariaDB (Puerto 3307 externo / 3306 interno en Docker)
* JUnit 5 & Mockito
* Lombok & Bean Validation
* Swagger / OpenAPI (Springdoc)
* Maven & VSCode

---

## 5. Estructura del Proyecto

```text
consultorio/
├── pom.xml
├── README.md
├── subtitulos-video.txt
├── docs/
│   ├── init.sql
│   ├── endpoints.md
│   └── orden-ejecucion.md
├── eureka-server/
├── api-gateway/
├── ms-paciente/
├── ms-medico/
├── ms_citas/
├── ms-receta/
├── msAtencionMedica/
├── ms-farmacia/
├── ms-examen/
├── msfacturacion/
├── ms-notificaciones/
└── msAutentificacion/

```

---

## 6. Calidad de Software y Suite de Pruebas Unitarias

El sistema implementa una suite rigurosa de pruebas lógicas y de componentes utilizando **JUnit 5** y **Mockito** para mitigar errores en la persistencia del historial clínico.

Para compilar el clúster clínico completo y ejecutar de forma limpia todos los tests del proyecto, use:

```bash
mvn clean install

```

Si requiere realizar un empaquetado rápido omitiendo la suite de pruebas, puede usar la bandera correspondiente:

```bash
mvn clean install -DskipTests

```

---

## 7. Documentación de Endpoints con Swagger / OpenAPI

Cada microservicio incorpora en sus propiedades de desarrollo las siguientes directivas para garantizar el mapeo e indexación interactiva de la documentación técnica mediante Swagger:

```properties
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/doc/swagger-ui.html

```

### Acceso Directo a Documentación Swagger

Las rutas de Swagger UI de los microservicios se consultan de manera independiente a través de sus puertos asignados de forma coherente con el clúster:

| Microservicio | Ruta Swagger UI |
| --- | --- |
| Pacientes | http://localhost:8085/doc/swagger-ui.html |
| Médicos | http://localhost:8087/doc/swagger-ui.html |
| Citas | http://localhost:8089/doc/swagger-ui.html |
| Recetas | http://localhost:8088/doc/swagger-ui.html |
| Atención Médica | http://localhost:8084/doc/swagger-ui.html |
| Farmacia | http://localhost:8086/doc/swagger-ui.html |
| Exámenes | http://localhost:8081/doc/swagger-ui.html |
| Facturación | http://localhost:8082/doc/swagger-ui.html |
| Notificaciones | http://localhost:8090/doc/swagger-ui.html |
| Autenticación | http://localhost:8083/doc/swagger-ui.html |

---

## 8. Puesta en Marcha Versión Nativa (Orden de Arranque)

Para asegurar el correcto funcionamiento del ecosistema clínico y evitar excepciones transaccionales en los clientes declarativos **OpenFeign**, se debe respetar el siguiente orden jerárquico estricto:

### ⏳ Secuencia Obligatoria:

1. **Servidor de Base de Datos (MySQL / MariaDB)** -> Debe estar activo localmente y con el script `docs/init.sql` cargado.
2. **Eureka Server (`eureka-server.jar`)** -> Registrar en el puerto `8761`.
3. **Microservicios de Negocio** -> Mapear los 10 módulos hacia el Discovery Server.
4. **API Gateway (`api-gateway.jar`)** -> Inicializar al final en el puerto `8080`.

### 🚀 Automatización con Script .bat

El paquete de despliegue nativo incluye el script maestro `iniciar-sistema-nativo.bat` para automatizar este flujo abriendo terminales independientes con los retardos correspondientes:

```cmd
iniciar-sistema-nativo.bat

```

---

## 9. Bases de Datos

El proyecto utiliza un enfoque de **Base de Datos por Microservicio** para garantizar el desacoplamiento transaccional.

| Microservicio | Base de datos (SQL) | Tablas Principales / Relaciones |
| --- | --- | --- |
| msAutentificacion | `db_consultorio` | `usuarios`, `roles`, `usuario_roles` |
| ms-paciente | `mspaciente` | `pacientes` |
| ms-medico | `msmedico` | `medico` |
| ms_citas | `mscitas` | `citas` |
| msAtencionMedica | `msatencionmedica` | `atencion_medica` |
| ms-examen | `consultorio_examenes_db` | `examenes` |
| ms-receta | `msreceta` | `recetas`, `medicamentos_receta` |
| ms-farmacia | `msfarmacia` | `medicamentos` |
| msfacturacion | `facturas` | `facturas`, `factura_detalles` |
| ms-notificaciones | `msnotificaciones` | `notificaciones` |

> 📁 El script completo de creación de las estructuras de esquemas, tablas e inserciones de prueba de 10 registros por entidad se localiza en `docs/init.sql`.

---

## 10. Comunicación mediante OpenFeign

El sistema implementa clientes declarativos para interactuar de forma síncrona entre los diferentes límites de contexto médico:

* **ms_citas ──► ms-paciente**: Valida que el `id_paciente` exista y se encuentre activo antes de agendar.
* **ms_citas ──► ms-medico**: Valida que el `id_medico` esté registrado y posee el flag `estado` en `true` (activo).
* **ms-receta ──► msAtencionMedica**: Enlaza los medicamentos e indicaciones recetadas al folio clínico de la atención.
* **ms-farmacia ──► ms-receta**: Rebaja el stock físico en la tabla `medicamentos` cruzando los identificadores de la prescripción.
* **msfacturacion ──► msAtencionMedica**: Consolida los cargos clínicos generados para calcular el costo de la cuenta del paciente.

---

## 11. Flujo Funcional Principal (API Gateway)

El Gateway unifica el tráfico perimetral a través del puerto único `8080`. Los cuerpos JSON estructuran sus propiedades basándose exactamente en el esquema y tipos de datos del modelo SQL:

### Paso 1: Registrar Paciente

> **Nota de Integridad:** Se mapea dividiendo el RUT de su Dígito Verificador (`rut`, `dv`), y se configuran las propiedades de texto e indicación de estado activo acorde a la tabla `pacientes`.

```http
POST http://localhost:8080/api/v1/pacientes
Content-Type: application/json

{
  "rut": "11111111",
  "dv": "1",
  "nombre": "Juan Carlos",
  "apellidos": "Perez Gomez",
  "email": "juan@mail.com",
  "telefono": "+56911111111",
  "activo": true
}

```

### Paso 2: Registrar Médico

> **Nota de Integridad:** El campo `rut_med` se procesa como un valor entero (sin guion ni puntos) y se definen los campos individuales de nombres y apellidos según las columnas de la tabla `medico`.

```http
POST http://localhost:8080/api/v1/medicos
Content-Type: application/json

{
  "pnom_med": "Juan",
  "snom_med": "Carlos",
  "ape_pat_med": "Perez",
  "ape_mat_med": "Gomez",
  "rut_med": 1111,
  "dv_med": "1",
  "especialidad": "Cardiologia",
  "email": "juan@med.com",
  "telefono": "+5691",
  "fecha_contratacion": "2025-01-01",
  "estado": true
}

```

### Paso 3: Agendar Cita Médica

> **Nota de Integridad:** Las relaciones de llaves foráneas (`id_paciente`, `id_medico`) corresponden a registros válidos existentes y los bloques de tiempo respetan el formato de fecha y hora `fech_ini` y `fech_term` mapeado por JPA.

```http
POST http://localhost:8080/api/v1/citas
Content-Type: application/json

{
  "id_paciente": 1,
  "id_medico": 1,
  "fech_ini": "2026-06-01T10:00:00",
  "fech_term": "2026-06-01T10:30:00",
  "estado": "PROGRAMADA"
}

```

---

## 12. Manejo de Errores y Logs

Cada microservicio incorpora un manejador centralizado de excepciones genéricas y personalizadas mediante `@RestControllerAdvice` para capturar errores de base de datos o lógica de negocio, mapeándolos a un formato JSON estándar.

Implementación estricta de trazas operacionales e hilos mediante la anotación de Lombok `@Slf4j`.

```java
@Slf4j

```

```

```