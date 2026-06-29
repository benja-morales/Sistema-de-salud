\# README-DESPLIEGUE-DOCKER.md

\# Puesta en marcha con Docker — Consultorio

# \#\# 0\. Enlaces de Descarga y Entregables (Evaluación Parcial 3\)

\#\#\# Nota para el Evaluador: 

- Los entregables han sido empaquetados de forma independiente para facilitar su revisión y mitigar conflictos de entornos locales.  
- [**Descargar Versión Nativa — Paquete .jar \+ .bat**](https://drive.google.com/file/d/1OhsQpED6UEvMB825QFUbAMFmcGCO70we/view?usp=sharing) *(Incluye ejecutables compilados y scripts de automatización secuencial)*  
  [**Descargar Versión Docker — Entorno Contenedores**](https://drive.google.com/file/d/1_Qn0p4u1zWX1YmOjBxZIFS5NTDHi1t7Q/view?usp=sharing) *(Incluye docker-compose.yml, variables .env y scripts de respaldo)*  
- [**Ver Video de Defensa de la Arquitectura**](https://drive.google.com/file/d/1B6cK0Hy_VqpqPqKh97qDvkazvzXv3UeI/view?usp=sharing) *(Duración aproximada: 15 minutos. El repositorio incluye el archivo de soporte `subtitulos-video.txt` en la raíz para garantizar la accesibilidad)*

\---

\#\# 1\. Descripción general

Este documento explica cómo ejecutar el ecosistema de microservicios \*\*Consultorio\*\* utilizando \*\*Docker\*\* y \*\*Docker Compose\*\*.

La puesta en marcha con Docker permite levantar todos los componentes necesarios del sistema en contenedores independientes, evitando instalar manualmente cada servicio o configurar cada entorno por separado.

El sistema considera los siguientes componentes:

- MySQL (Contenedor de Bases de Datos Clúster)  
- Eureka Server  
- API Gateway  
- Microservicio de Autenticación (ms-autenticacion)  
- Microservicio de Pacientes (ms-paciente)  
- Microservicio de Médicos (ms-medico)  
- Microservicio de Citas (ms-cita)  
- Microservicio de Atención Médica (ms-atencion-medica)  
- Microservicio de Exámenes (ms-examen)  
- Microservicio de Recetas (ms-receta)  
- Microservicio de Farmacia (ms-farmacia)  
- Microservicio de Facturación (ms-factura)  
- Microservicio de Notificaciones (ms-notificaciones)

## 2\. Objetivo del despliegue

El objetivo de esta puesta en marcha es ejecutar Consultorio como un sistema distribuido compuesto por varios microservicios encapsulados. Docker permite que cada componente se ejecute en su propio contenedor, pero todos puedan comunicarse entre sí mediante una red interna común. Con Docker Compose es posible levantar todo el ecosistema médico mediante un solo comando.

## 3\. Importante: el ZIP no es una imagen Docker completa

El archivo ZIP entregado no corresponde a una imagen Docker completa. El ZIP corresponde a un paquete de despliegue del sistema Consultorio. Este paquete incluye:

Archivos .jar de los microservicios y servidores Spring Cloud.

Archivo docker-compose.yml.

Archivo .env.

Archivo init.sql.

Scripts .bat para levantar, detener, revisar logs, respaldar y restaurar en Windows.

README con instrucciones de puesta en marcha.

Docker utiliza estos archivos para crear y ejecutar los contenedores necesarios. El archivo principal de configuración es:

docker-compose.yml

Este archivo define cómo se levanta el sistema completo. En el docker-compose.yml se configura:

Qué servicios se ejecutan.

Qué imágenes Docker se utilizan.

Qué archivos .jar se ejecutan.

Qué puertos se exponen hacia el host.

Qué variables de entorno se usan.

Qué red interna conecta los servicios (Bridge).

Qué volumen se utiliza para la persistencia de MySQL.

Qué servicios dependen de otros (depends\_on).

Por lo tanto:

El ZIP no reemplaza a Docker.

El ZIP entrega los archivos necesarios para que Docker pueda levantar el ecosistema de microservicios.

## 4\. Requisito obligatorio: Docker Desktop debe estar abierto

Antes de ejecutar el sistema, es obligatorio abrir Docker Desktop. Si Docker Desktop no está iniciado, los comandos no funcionarán. Antes de levantar el sistema, verificar:

docker \--version

docker compose version

docker info

Si aparece un error similar a:

Cannot connect to the Docker daemon

o:

open //./pipe/DockerDesktopLinuxEngine: The system cannot find the file specified

significa que Docker Desktop no está abierto o que el motor de Docker todavía no ha iniciado. Solución:

1\. Abrir Docker Desktop.

2\. Esperar a que Docker quede en estado "running".

3\. Volver a ejecutar el comando.

Luego recién ejecutar:

docker compose up \-d

o el script automatizado:

arrancar-docker.bat

## 5\. Requisitos previos

Antes de ejecutar el sistema, se debe contar con:

Docker Desktop instalado.

Docker Desktop abierto y funcionando.

Carpeta de despliegue con los archivos .jar compilados en la carpeta /apps.

Archivo docker-compose.yml.

Archivo .env configurado con credenciales clínicas.

Carpeta docs con el archivo init.sql.

Carpeta backups para los dumps estructurados de base de datos.

## 6\. Verificación de Docker

Abrir CMD, PowerShell o terminal integrada de VSCode y ejecutar:

docker \--version

Resultado esperado (o superior):

Docker version 29.1.3, build f52814d

Verificar Docker Compose:

docker compose version

Resultado esperado (o superior):

Docker Compose version v5.0.0-desktop.1

Si ambos comandos responden correctamente, Docker está listo.

## 7\. Estructura de carpetas esperada

La carpeta de despliegue debe tener la siguiente estructura exacta:

consultoriomed-docker/

├── apps/

│   ├── api-gateway.jar

│   ├── eureka-server.jar

│   ├── ms_citas.jar

│   ├── msAtencionMedica.jar

│   ├── msAutentificacion.jar

│   ├── ms-examen.jar

│   ├── msfacturacion.jar

│   ├── ms-farmacia.jar

│   ├── ms-medico.jar

│   ├── ms-notificaciones.jar

│   ├── ms-paciente.jar

│   └── ms-receta.jar

├── docs/

│   └── init.sql

├── backups/

├── .env

├── docker-compose.yml

├── arrancar-docker.bat

├── detener-docker.bat

├── ver-logs.bat

├── backup-db.bat

├── restaurar-db.bat

└── README-DESPLIEGUE-DOCKER CONSULTORIO.md

## 8\. Archivos .jar requeridos

La carpeta apps debe contener los siguientes artefactos compilados:

api-gateway.jar  
eureka-server.jar  
ms_citas.jar  
msAtencionMedica.jar  
msAutentificacion.jar  
ms-examen.jar  
msfacturacion.jar  
ms-farmacia.jar  
ms-medico.jar  
ms-notificaciones.jar  
ms-paciente.jar  
ms-receta.jar

Los nombres deben coincidir exactamente con los definidos en las directivas del archivo docker-compose.yml. Si el archivo .jar difiere, se debe renombrar o ajustar la propiedad del contexto en el compose.

## 

## 9\. Archivo .env

El archivo .env contiene variables de entorno críticas reutilizadas por Docker Compose. Contenido esperado:

MYSQL\_ROOT\_PASSWORD=root  
MYSQL\_PORT=3307  
EUREKA\_PORT=8761  
GATEWAY\_PORT=8080

Estas variables permiten centralizar los nombres de esquemas relacionales, usuarios y contraseñas.

## 10\. Archivo docs/init.sql

El archivo init.sql permite crear e inicializar de forma asíncrona todas las bases de datos necesarias cuando se despliega MySQL por primera vez. Contenido esperado:

CREATE DATABASE IF NOT EXISTS mspaciente;  
USE mspaciente;

CREATE TABLE IF NOT EXISTS pacientes (  
    id BIGINT AUTO\_INCREMENT PRIMARY KEY,  
    rut VARCHAR(12) UNIQUE NOT NULL,  
    dv VARCHAR(1) NOT NULL,  
    nombre VARCHAR(100),  
    apellidos VARCHAR(100),  
    email VARCHAR(100),  
    telefono VARCHAR(15),  
    activo BOOLEAN  
);

CREATE DATABASE IF NOT EXISTS msmedico;  
USE msmedico;

CREATE TABLE IF NOT EXISTS Medico (  
    id\_med BIGINT AUTO\_INCREMENT PRIMARY KEY,  
    pnom\_med VARCHAR(20),  
    snom\_med VARCHAR(20),  
    ape\_pat\_med VARCHAR(20),  
    ape\_mat\_med VARCHAR(20),  
    rut\_med INT UNIQUE,  
    dv\_med VARCHAR(1),  
    especialidad VARCHAR(30),  
    email VARCHAR(100),  
    telefono VARCHAR(15),  
    fecha\_contratacion DATE,  
    estado BOOLEAN  
);

Importante:

Este archivo se ejecuta automáticamente solo la primera vez que se crea el volumen de MySQL.

Si el volumen ya existe, MySQL omitirá la inicialización de este archivo de script.

## 11\. Componentes del sistema

| Componente | Descripción | Puerto |
| :---- | :---- | :---- |
| `init.sql` | Base de datos unificada del clúster | 3307 externo / 3306 interno |
| `eureka-server` | Descubrimiento y registro de servicios | 8761 |
| `api-gateway` | Enrutamiento perimetral unificado | 8080 |
| `ms-paciente` | Gestión de fichas clínicas y pacientes | 8085 |
| `ms-medico` | Administración de personal de salud y especialidad | 8087 |
| `ms-cita` | Gestión de reservas y agendas médicas | 8089 |
| `ms-receta` | Generación de prescripciones y dosis | 8088 |
| `ms-atencion-medica` | Registro de atenciones y diagnósticos | 8084 |
| `ms-farmacia` | Inventario, precios y stock de medicamentos | 8086 |
| `ms-examen` | Solicitudes y órdenes de laboratorio | 8081 |
| `ms-factura` | Emisión de cuentas, IVA y liquidaciones | 8082 |
| `ms-notificaciones` | Motor de envío de alertas SMS/Email | 8090 |
| `ms-autenticacion` | Control de accesos, JWT y roles | 8083 |

## 12\. Orden lógico de arranque

Aunque Docker Compose gestiona el arranque de forma global, el orden lógico de estabilización del ecosistema clínico es:

1\. eureka-server

2\. init.sql

3\. Microservicios de Negocio (ms-autenticacion, ms-paciente, ms-medico, etc.)

4\. api-gateway

\#\# En esta configuración:

La Base de Datos debe estar arriba y saludable (healthy) antes de que los servicios JPA intenten conectarse.

Eureka Server debe mapear los puertos antes de que los clientes OpenFeign se registren.

API Gateway se estabiliza al final para enrutar tráfico hacia los microservicios ya indexados.

## 13\. Levantar el sistema completo

Ubicarse en la carpeta raíz del despliegue Docker:

consultorio-docker

Ejecutar el comando de inicialización en la consola de comandos:

docker compose up \-d

La opción `-d` activa el *detached mode*, permitiendo que el clúster se ejecute en background y permitiendo cerrar la terminal sin interrumpir el funcionamiento del consultorio.

## 14\. Revisar el estado de los contenedores

Ejecutar en la terminal:

docker compose ps

Resultado esperado:

consultoriomed-mysql       running / healthy

eureka-server              running

ms-autenticacion           running

ms-paciente                running

ms-medico                  running

ms-cita                    running

ms-atencion-medica         running

ms-examen                  running

ms-receta                  running

ms-farmacia                running

ms-factura                 running

ms-notificaciones          running

api-gateway                running

## 15\. Levantar servicios de forma progresiva

Si requieres realizar depuración o levantar módulos bajo demanda para optimizar memoria RAM, ejecuta:

docker compose up \-d consultoriomed-mysql

docker compose up \-d eureka-server

docker compose up \-d msAutenticacion

docker compose up \-d ms-paciente

docker compose up \-d ms-medico

docker compose up \-d ms-citas

docker compose up \-d msAtencionMedica

docker compose up \-d ms-examen

docker compose up \-d ms-receta

docker compose up \-d ms-farmacia

docker compose up \-d msfacturacion

docker compose up \-d ms-notificaciones

docker compose up \-d api-gateway

## 16\. Revisar logs

Para monitorear la trazabilidad completa del ecosistema clínico:

docker compose logs \-f

Para aislar los logs de un microservicio crítico (ejemplo, atención médica o recetas):

docker compose logs \-f msAtencionMedica

docker compose logs \-f msFacturacion

Para cancelar el visor de traza presiona `Ctrl + C`. Los contenedores seguirán operando con normalidad.

## 17\. Accesos principales

Mientras los contenedores estén activos, puedes auditar las capas del sistema mediante las siguientes direcciones:

Dashboard de Eureka:

http://localhost:8761

API Gateway (Proxy Perimetral):

http://localhost:8080

## 18\. Disponibilidad del sistema para usuarios

Para mantener la operatividad de las fichas del consultorio, los servicios Docker deben persistir activos. Si se ejecuta:

docker compose down

El ecosistema clínico se apagará por completo y las conexiones externas serán rechazadas. Frase clave:

Docker debe permanecer corriendo para que el sistema esté disponible para los usuarios.

## 19\. Importante sobre ejecución local

En esta guía, el desarrollo perimetral mapea sobre `localhost`. Las peticiones fuera del computador anfitrión fallarán si se invoca la palabra reservada `localhost` en lugar de la dirección física del nodo.

## 20\. Acceso desde otros equipos de la red

Para que personal médico u administrativo consuma la API desde otra estación de trabajo en la misma subred, deben invocar la IP privada del servidor Docker:

API Gateway Clínico:

\[http://192.168.1.50:8080\](http://192.168.1.50:8080)

Eureka Server:

\[http://192.168.1.50:8761\](http://192.168.1.50:8761)

⚠️ **Importante**: Asegúrate de que el Firewall de Windows mantenga habilitadas las reglas de entrada para los puertos expuestos (8080, 8761, 8081 al 8090).

## 21\. Uso de dominio

Si el ecosistema es migrado a producción bajo una infraestructura DNS, las URL locales cambian de estructura:

Gateway Público:

\[https://api.consultorio.cl\](https://api.consultorio.cl)

Fichas Médicas Directas:

\[https://pacientes.consultorio.cl\](https://pacientes.consultorio.cl)

## 22\. Configuraciones que deben revisarse al usar dominio

Al implementar un dominio corporativo, audite los siguientes puntos de arquitectura:

1\. Endpoints de comunicación cruzada síncrona en Clientes Feign.

2\. Filtros de Orígenes Cruzados (CORS) en el api-gateway.

3\. Políticas de seguridad y decodificación JWT de tokens en msAutenticacion.

4\. Certificados SSL/TLS (HTTPS) montados sobre el Gateway o Proxy Inverso (Nginx/Traefik).

5\. Configuración de DNS apuntando a la IP pública del host Docker.

## 23\. Ejemplo de cambio de URL en clientes

Si una aplicación o cliente SPA consume el Gateway local, su variable estructural:

GATEWAY\_URL=http://localhost:8080

Deberá mutar al dominio productivo:

GATEWAY\_URL=\[https://api.consultoriomed.cl\](https://api.consultoriomed.cl)

## 24\. Docker en un servidor

Para entornos institucionales reales, se recomienda desacoplar Docker del entorno del estudiante y desplegarlo sobre un servidor CentOS/Ubuntu Server o Windows Server dedicado:

1\. Copiar la estructura de empaquetado (/apps, /docs, compose, .env).

2\. Ejecutar el demonio de Docker (systemctl start docker).

3\. Levantar con docker compose up \-d de forma desatendida.

4\. Programar tareas cron para la ejecución de scripts de respaldo automático.

## 25\. Persistencia de datos de MySQL

Para salvaguardar la información sensible e histórica de los pacientes, el servicio de base de datos define un volumen administrado:

volumes:

  consultorio\_clinical\_data:

Mapeado internamente en el contenedor MySQL hacia:

volumes:

  \- consultorio\_clinical\_data:/var/lib/mysql

## 26\. ¿Se pierden los datos si se reinicia el computador?

No. Si los contenedores mapean a un volumen de Docker, los registros de atenciones, recetas e historial clínico persistirán en el disco duro. Tras el reinicio del host, basta con volver a iniciar Docker Desktop y ejecutar `docker compose up -d`.

## 27\. Cuándo sí se pueden perder los datos

Los datos clínicos se eliminarán permanentemente en los siguientes escenarios:

Se ejecuta de forma explícita el comando destructivo: docker compose down \-v

Se elimina el volumen físico desde la interfaz gráfica de Docker Desktop.

Se borra la instalación limpia de la carpeta de almacenamiento de Docker.

🛑 **Advertencia**: No uses `docker compose down -v` bajo ningún motivo en ambientes evaluativos o productivos si deseas mantener los registros ingresados en las pruebas.

## 28\. Respaldo de base de datos

Para mitigar riesgos de corrupción de datos, genera volcados relacionales periódicos imponiendo el script:

backup-db.bat

Los dumps resultantes se versionarán con fecha y hora dentro del directorio:

backups/

Ejemplo de salida: `backup_consultorio_2026-06-23_15-30-00.sql`.

## 29\. Archivo backup-db.bat

Estructura operativa para respaldar de forma masiva los esquemas lógicos de los microservicios en Windows:

@echo off

title Respaldo de Base de Datos \- Consultorio

cls

echo \==========================================================

echo       RESPALDO CLÚSTER DE BASES DE DATOS \- DOCKER

echo \==========================================================

echo.

echo Verificando contenedores activos...

docker compose ps

echo.

if not exist backups mkdir backups

set FECHA=%date:\~6,4%-%date:\~3,2%-%date:\~0,2%

set HORA=%time:\~0,2%-%time:\~3,2%-%time:\~6,2%

set HORA=%HORA: \=0%

set BACKUP\_FILE=backups\\backup\_consultoriomed\_%FECHA%\_%HORA%.sql

echo Destino del Respaldo: %BACKUP\_FILE%

echo.

echo Procesando volcado relacional vía mysqldump...

docker exec consultoriomed-mysql mysqldump \-u root \-proot \--all-databases \> %BACKUP\_FILE%

echo.

echo \==========================================================

echo RESPALDO GENERADO CON ÉXITO

echo \==========================================================

pause

## 30\. Restaurar base de datos

Para levantar una instantánea previa de las tablas del consultorio, utiliza el comando:

restaurar-db.bat

## 31\. Archivo restaurar-db.bat

Script interactivo de recuperación ante desastres para entornos de bases de datos clínicas:

@echo off

title Restauración de Datos \- ConsultorioMed

cls

echo \==========================================================

echo       RESTAURACIÓN CLÚSTER DE BASES DE DATOS \- DOCKER

echo \==========================================================

echo.

echo Respaldos detectados en el directorio backups:

echo.

dir backups\\\*.sql

echo.

set /p BACKUP\_FILE=Ingrese el nombre exacto del archivo .sql a restaurar: 

if not exist backups\\%BACKUP\_FILE% (

    echo ERROR: El archivo especificado no existe en la ruta.

    pause

    exit

)

echo.

echo Inyectando datos estructurados en el contenedor MySQL...

docker exec \-i consultoriomed-mysql mysql \-u root \-proot \< backups\\%BACKUP\_FILE%

echo.

echo \==========================================================

echo CONTEXTO RESTAURADO CORRECTAMENTE

echo \==========================================================

pause

## 32\. Recomendación de respaldo automático

Se sugiere automatizar las salvas de datos mediante el Programador de Tareas de Windows vinculando el archivo `backup-db.bat` para que se ejecute diariamente, sincronizando la carpeta `backups/` con nubes seguras.

## 33\. Verificación en Eureka

Al ingresar a [http://localhost:8761](http://localhost:8761), la sección "Instances currently registered with Eureka" debe reflejar de forma dinámica los siguientes identificadores de aplicación:

API-GATEWAY

MS-AUTENTICACION

MS-PACIENTE

MS-MEDICO

MS-CITA

MS-ATENCION-MEDICA

MS-EXAMEN

MS-RECETA

MS-FARMACIA

MS-FACTURA

MS-NOTIFICACIONES

## 34\. Pruebas de endpoints directos

Puedes omitir temporalmente el Gateway consumiendo los microservicios directamente mediante sus puertos dedicados expuestos:

http://localhost:8085/api/pacientes

http://localhost:8087/api/medicos

http://localhost:8089/api/citas

http://localhost:8084/api/atenciones

http://localhost:8081/api/examenes

http://localhost:8082/api/facturas

## 35\. Pruebas mediante API Gateway

El flujo de producción centraliza las llamadas a través del puerto único 8080:

http://localhost:8080/api/pacientes

http://localhost:8080/api/medicos

http://localhost:8080/api/citas

## 36\. Swagger / OpenAPI en Contenedores

La documentación interactiva e indexada de las APIs bajo Docker se mapea de acuerdo al prefijo unificado `/doc/swagger-ui.html`:

Paciente		http://localhost:8085/doc/swagger-ui.html  
Médico			http://localhost:8087/doc/swagger-ui.html  
Citas			http://localhost:8089/doc/swagger-ui.html  
Receta			http://localhost:8088/doc/swagger-ui.html  
Atención Médica	http://localhost:8084/doc/swagger-ui.html  
Farmacia		http://localhost:8086/doc/swagger-ui.html  
Exámen		http://localhost:8081/doc/swagger-ui.html  
Facturación		http://localhost:8082/doc/swagger-ui.html  
Notificaciones		http://localhost:8090/doc/swagger-ui.html  
Autenticación		http://localhost:8083/doc/swagger-ui.html

## 37\. Scripts disponibles

### 37.1 arrancar-docker.bat

Ejecuta `docker compose up -d` de manera automatizada para entornos Windows.

### 37.2 detener-docker.bat

Aplica un apagado controlado de la arquitectura mediante el comando `docker compose down`.

### 37.3 ver-logs.bat

Abre una consola interactiva de seguimiento con `docker compose logs -f`.

### 37.4 backup-db.bat

Extrae una copia de seguridad física de todas las bases de datos de la aplicación.

### 37.5 restaurar-db.bat

Ejecuta una inyección SQL para recuperar estados previos de la información del sistema.

## 38\. Detener el sistema

Para apagar los contenedores resguardando los volúmenes intactos:

docker compose down

## 39\. Detener y eliminar datos persistentes

Si requieres limpiar el clúster clínico por completo y ejecutar una base de datos en blanco:

docker compose down \-v

## 40\. Reiniciar el sistema

docker compose down

docker compose up \-d

## 41\. Errores comunes

### 41.1 Docker Daemon Inactivo

* **Mensaje**: `Cannot connect to the Docker daemon`  
* **Causa**: Docker Desktop se encuentra cerrado o crasheado.  
* **Solución**: Inicie Docker Desktop y verifique el indicador verde de estado en la barra de tareas.

### 41.2 Puerto de Microservicio Ocupado

* **Mensaje**: `port is already allocated`  
* **Causa**: Tienes una instancia local de Tomcat u otro servicio ocupando puertos del rango.  
* **Solución**: Libere el puerto o reasigne el puerto externo izquierdo en el archivo `docker-compose.yml`.

### 41.3 Link de Conexión de Datos Fallido

* **Mensaje**: `Communications link failure / Access denied for user`  
* **Causa**: El microservicio intenta resolver a través de `localhost` en vez de usar el DNS de red interna de Docker.  
* **Solución**: Asegúrese de que la cadena de conexión apunte a `jdbc:mysql://init.sql:3306/` y que las credenciales del `.env` coincidan con el archivo de propiedades.

## 42\. Evidencia recomendada para revisión

Para validar la correcta implementación del taller clínico distribuido, capture:

* Salida de comandos de versiones (`docker --version` y `docker compose version`).  
* Contenedores estables en consola (`docker compose ps`).  
* Panel de control de Eureka mostrando las 11 instancias activas.  
* Respuesta HTTP exitosa consumiendo endpoints mediante el API Gateway (Puerto 8080).  
* Archivos de respaldo `.sql` generados con éxito en la carpeta `/backups`.

## 43\. Comandos principales

| Acción | Comando |
| :---- | :---- |
| Levantar clúster en segundo plano | `docker compose up -d` |
| Auditar estado de procesos clínicos | `docker compose ps` |
| Inspección interactiva de logs | `docker compose logs -f` |
| Apagado de contenedores preservando datos | `docker compose down` |
| Reseteo estructural completo (Borra datos) | `docker compose down -v` |

## 44\. Estado final esperado

Al completar el flujo, se garantiza:

* Contenedor de base de datos MySQL estable y en estado *healthy*.  
* Panel central de Eureka accesible y mapeando los nombres lógicos de los servicios.  
* Clientes OpenFeign enlazándose síncronamente sin errores de resolución.  
* Persistencia garantizada en volúmenes Docker ante interrupciones de energía del equipo host.

## 45\. Resumen importante

* El empaquetado ZIP suministrado no contiene imágenes Docker pesadas; provee las configuraciones orquestadas y los artefactos ejecutables (`.jar`).  
* Las llamadas internas entre servicios deben apuntar a los alias del contenedor (`eureka-server`, `init.sql`) en lugar de usar referencias a direcciones IP de la máquina local.  
* Modificar volúmenes o ejecutar modificadores de bandera `-v` sin respaldos previos provocará la pérdida irreversible del historial clínico de los pacientes ficticios ingresados en las pruebas.

## 46\. Conclusión

El despliegue con Docker Compose dota al sistema Consultorio de una infraestructura portable y escalable. Al independizar cada servicio en su propio contenedor Linux, se mitiga el conflicto de dependencias del sistema operativo anfitrión, permitiendo simular con precisión un entorno de nube microserviciado real directamente en una estación de trabajo académica.

