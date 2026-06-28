/* =========================================================
   DROP BD
========================================================= */
DROP DATABASE IF EXISTS facturas;
DROP DATABASE IF EXISTS db_consultorio;
DROP DATABASE IF EXISTS msatencionmedica;
DROP DATABASE IF EXISTS msreceta;
DROP DATABASE IF EXISTS msnotificaciones;
DROP DATABASE IF EXISTS msfarmacia;
DROP DATABASE IF EXISTS consultorio_examenes_db;
DROP DATABASE IF EXISTS mscitas;
DROP DATABASE IF EXISTS msmedico;
DROP DATABASE IF EXISTS mspaciente;



/* =========================================================
   BASE DE DATOS: MSPACIENTE
========================================================= */
CREATE DATABASE IF NOT EXISTS mspaciente;
USE mspaciente;

CREATE TABLE IF NOT EXISTS pacientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rut VARCHAR(12) UNIQUE NOT NULL,
    dv VARCHAR(1) NOT NULL,
    nombre VARCHAR(100),
    apellidos VARCHAR(100),
    email VARCHAR(100),
    telefono VARCHAR(15),
    activo BOOLEAN
);

INSERT INTO pacientes (rut,dv,nombre,apellidos,email,telefono,activo) VALUES
('11111111','1','Juan Carlos','Perez Gomez','juan@mail.com','+56911111111',1),
('22222222','2','Maria Jose','Lopez Diaz','maria@mail.com','+56922222222',1),
('33333333','3','Pedro','Soto Rojas','pedro@mail.com','+56933333333',1),
('44444444','4','Ana','Torres Silva','ana@mail.com','+56944444444',1),
('55555555','5','Luis','Ramirez Soto','luis@mail.com','+56955555555',1),
('66666666','6','Carla','Mora Vega','carla@mail.com','+56966666666',1),
('77777777','7','Diego','Fuentes Rios','diego@mail.com','+56977777777',1),
('88888888','8','Fernanda','Castro Mena','fer@mail.com','+56988888888',1),
('99999999','9','Jorge','Navarro Diaz','jorge@mail.com','+56999999999',1),
('10101010','0','Valentina','Reyes Perez','vale@mail.com','+56910101010',1);

/* =========================================================
   BASE DE DATOS: MSMEDICO
========================================================= */
CREATE DATABASE IF NOT EXISTS msmedico;
USE msmedico;

CREATE TABLE IF NOT EXISTS medico (
    id_med BIGINT AUTO_INCREMENT PRIMARY KEY,
    pnom_med VARCHAR(20),
    snom_med VARCHAR(20),
    ape_pat_med VARCHAR(20),
    ape_mat_med VARCHAR(20),
    rut_med INT UNIQUE,
    dv_med VARCHAR(1),
    especialidad VARCHAR(30),
    email VARCHAR(100),
    telefono VARCHAR(15),
    fecha_contratacion DATE,
    estado BOOLEAN
);

INSERT INTO medico VALUES
(1,'Juan','Carlos','Perez','Gomez',1111,'1','Cardiologia','juan@med.com','+5691','2025-01-01',1),
(2,'Maria',NULL,'Lopez','Diaz',2222,'2','Pediatria','maria@med.com','+5692','2025-01-02',1),
(3,'Pedro',NULL,'Soto','Rojas',3333,'3','Traumatologia','pedro@med.com','+5693','2025-01-03',1),
(4,'Ana',NULL,'Torres','Silva',4444,'4','Dermatologia','ana@med.com','+5694','2025-01-04',1),
(5,'Luis',NULL,'Ramirez','Soto',5555,'5','Neurologia','luis@med.com','+5695','2025-01-05',1),
(6,'Carla',NULL,'Mora','Vega',6666,'6','Ginecologia','carla@med.com','+5696','2025-01-06',1),
(7,'Diego',NULL,'Fuentes','Rios',7777,'7','Urologia','diego@med.com','+5697','2025-01-07',1),
(8,'Fernanda',NULL,'Castro','Mena',8888,'8','Oncologia','fer@med.com','+5698','2025-01-08',1),
(9,'Jorge',NULL,'Navarro','Diaz',9999,'9','Medicina General','jorge@med.com','+5699','2025-01-09',1),
(10,'Valentina',NULL,'Reyes','Perez',1010,'0','Otorrino','vale@med.com','+56910','2025-01-10',1);

/* =========================================================
   BASE DE DATOS: MSCITAS
========================================================= */
CREATE DATABASE IF NOT EXISTS mscitas;
USE mscitas;

CREATE TABLE IF NOT EXISTS citas (
    id_cita BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT,
    id_medico BIGINT,
    fech_ini DATETIME,
    fech_term DATETIME,
    estado VARCHAR(20)
);

INSERT INTO citas (id_paciente,id_medico,fech_ini,fech_term,estado) VALUES
(1,1,'2026-06-01 10:00:00','2026-06-01 10:30:00','PROGRAMADA'),
(2,2,'2026-06-01 11:00:00','2026-06-01 11:30:00','PROGRAMADA'),
(3,3,'2026-06-02 09:00:00','2026-06-02 09:30:00','COMPLETADA'),
(4,4,'2026-06-02 10:00:00','2026-06-02 10:30:00','PROGRAMADA'),
(5,5,'2026-06-03 12:00:00','2026-06-03 12:30:00','PROGRAMADA'),
(6,6,'2026-06-03 13:00:00','2026-06-03 13:30:00','CANCELADA'),
(7,7,'2026-06-04 08:00:00','2026-06-04 08:30:00','PROGRAMADA'),
(8,8,'2026-06-04 09:00:00','2026-06-04 09:30:00','PROGRAMADA'),
(9,9,'2026-06-05 14:00:00','2026-06-05 14:30:00','PROGRAMADA'),
(10,10,'2026-06-05 15:00:00','2026-06-05 15:30:00','PROGRAMADA');

/* =========================================================
   BASE DE DATOS: EXAMENES
========================================================= */
CREATE DATABASE IF NOT EXISTS consultorio_examenes_db;
USE consultorio_examenes_db;

CREATE TABLE IF NOT EXISTS examenes (
    id_examen BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_examen VARCHAR(100),
    id_paciente BIGINT,
    fecha_solicitud DATETIME,
    fecha_resultado DATE,
    resultado_texto TEXT,
    archivo_url TEXT,
    estado VARCHAR(20)
);

INSERT INTO examenes (tipo_examen,id_paciente,fecha_solicitud,fecha_resultado,resultado_texto,archivo_url,estado) VALUES
('Hemograma',1,NOW(),NULL,NULL,NULL,'PENDIENTE'),
('Orina',2,NOW(),NULL,NULL,NULL,'PENDIENTE'),
('Rayos X',3,NOW(),NULL,NULL,NULL,'EN_PROCESO'),
('ECG',4,NOW(),NULL,NULL,NULL,'COMPLETADO'),
('Glucosa',5,NOW(),NULL,NULL,NULL,'PENDIENTE'),
('Colesterol',6,NOW(),NULL,NULL,NULL,'COMPLETADO'),
('Sangre',7,NOW(),NULL,NULL,NULL,'PENDIENTE'),
('PCR',8,NOW(),NULL,NULL,NULL,'COMPLETADO'),
('Resonancia',9,NOW(),NULL,NULL,NULL,'EN_PROCESO'),
('Tomografia',10,NOW(),NULL,NULL,NULL,'PENDIENTE');

/* =========================================================
   BASE DE DATOS: FARMACIA
========================================================= */
CREATE DATABASE IF NOT EXISTS msfarmacia;
USE msfarmacia;

CREATE TABLE IF NOT EXISTS medicamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion VARCHAR(255),
    stock INT,
    precio DOUBLE,
    laboratorio VARCHAR(100)
);

INSERT INTO medicamentos VALUES
(1,'Paracetamol','Dolor',100,1500,'LabA'),
(2,'Ibuprofeno','Inflamacion',200,2000,'LabB'),
(3,'Amoxicilina','Antibiotico',150,3000,'LabC'),
(4,'Loratadina','Alergia',120,2500,'LabD'),
(5,'Omeprazol','Gastrico',80,1800,'LabE'),
(6,'Metformina','Diabetes',90,2200,'LabF'),
(7,'Aspirina','Dolor',300,1200,'LabG'),
(8,'Diclofenaco','Muscular',110,2100,'LabH'),
(9,'Salbutamol','Asma',70,4000,'LabI'),
(10,'Azitromicina','Antibiotico',60,5000,'LabJ');

/* =========================================================
   BASE DE DATOS: NOTIFICACIONES
========================================================= */
CREATE DATABASE IF NOT EXISTS msnotificaciones;
USE msnotificaciones;

CREATE TABLE IF NOT EXISTS notificaciones (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    modulo VARCHAR(50),
    mensaje VARCHAR(255),
    fecha DATETIME,
    leida BOOLEAN
);

INSERT INTO notificaciones (modulo,mensaje,fecha,leida) VALUES
('CITAS','Cita creada',NOW(),0),
('EXAMENES','Examen listo',NOW(),0),
('FARMACIA','Stock bajo',NOW(),0),
('RECETAS','Receta emitida',NOW(),0),
('FACTURAS','Factura creada',NOW(),0),
('CITAS','Recordatorio',NOW(),0),
('EXAMENES','Resultado listo',NOW(),0),
('PACIENTES','Nuevo paciente',NOW(),0),
('MEDICOS','Medico creado',NOW(),0),
('SISTEMA','Backup OK',NOW(),0);

/* =========================================================
   BASE DE DATOS: RECETAS
========================================================= */
CREATE DATABASE IF NOT EXISTS msreceta;
USE msreceta;

CREATE TABLE IF NOT EXISTS recetas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT,
    id_medico BIGINT,
    fecha DATE
);

CREATE TABLE IF NOT EXISTS medicamentos_receta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_medicamento BIGINT,
    nombre_medicamento VARCHAR(100),
    cantidad INT,
    indicaciones VARCHAR(300),
    receta_id BIGINT
);

/* (10 recetas + 10 medicamentos) */
INSERT INTO recetas VALUES
(1,1,1,'2026-06-01'),
(2,2,2,'2026-06-02'),
(3,3,3,'2026-06-03'),
(4,4,4,'2026-06-04'),
(5,5,5,'2026-06-05'),
(6,6,6,'2026-06-06'),
(7,7,7,'2026-06-07'),
(8,8,8,'2026-06-08'),
(9,9,9,'2026-06-09'),
(10,10,10,'2026-06-10');

INSERT INTO medicamentos_receta (id_medicamento,nombre_medicamento,cantidad,indicaciones,receta_id) VALUES
(1,'Paracetamol',2,'Cada 8h',1),
(2,'Ibuprofeno',1,'Cada 12h',2),
(3,'Amoxicilina',3,'Cada 8h',3),
(4,'Loratadina',1,'Diario',4),
(5,'Omeprazol',1,'Antes comida',5),
(6,'Metformina',2,'Diario',6),
(7,'Aspirina',1,'Dolor',7),
(8,'Diclofenaco',2,'Cada 8h',8),
(9,'Salbutamol',1,'Inhalar',9),
(10,'Azitromicina',1,'Cada 24h',10);

/* =========================================================
   BASE DE DATOS: ATENCION MEDICA
========================================================= */
CREATE DATABASE IF NOT EXISTS msatencionmedica;
USE msatencionmedica;

CREATE TABLE IF NOT EXISTS atencion_medica (
    id_atencion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_cita BIGINT,
    diagnostico VARCHAR(100),
    observaciones TEXT,
    fecha_aten DATETIME
);

INSERT INTO atencion_medica VALUES
(1,1,'Dolor cabeza','OK',NOW()),
(2,2,'Fiebre','Reposo',NOW()),
(3,3,'Fractura','Inmovilizar',NOW()),
(4,4,'Gripe','Medicamento',NOW()),
(5,5,'Dolor espalda','Terapia',NOW()),
(6,6,'Alergia','Antihistaminico',NOW()),
(7,7,'Asma','Control',NOW()),
(8,8,'HTA','Control',NOW()),
(9,9,'Diabetes','Dieta',NOW()),
(10,10,'Chequeo','Normal',NOW());

/* =========================================================
   BASE DE DATOS: AUTH
========================================================= */
CREATE DATABASE IF NOT EXISTS db_consultorio;
USE db_consultorio;

CREATE TABLE IF NOT EXISTS roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    email VARCHAR(100),
    password VARCHAR(255),
    activo BOOLEAN,
    fecha_creacion DATETIME
);

CREATE TABLE IF NOT EXISTS usuario_roles (
    usuario_id BIGINT,
    rol_id INT
);

INSERT INTO roles VALUES
(1,'ROLE_ADMIN'),
(2,'ROLE_MEDICO'),
(3,'ROLE_PACIENTE'),
(4,'ROLE_ADMIN'),
(5,'ROLE_MEDICO'),
(6,'ROLE_PACIENTE'),
(7,'ROLE_ADMIN'),
(8,'ROLE_MEDICO'),
(9,'ROLE_PACIENTE'),
(10,'ROLE_ADMIN');

INSERT INTO usuarios (username,email,password,activo,fecha_creacion) VALUES
('admin1','admin@mail.com','123',1,NOW()),
('med1','med1@mail.com','123',1,NOW()),
('pac1','pac1@mail.com','123',1,NOW()),
('u4','u4@mail.com','123',1,NOW()),
('u5','u5@mail.com','123',1,NOW()),
('u6','u6@mail.com','123',1,NOW()),
('u7','u7@mail.com','123',1,NOW()),
('u8','u8@mail.com','123',1,NOW()),
('u9','u9@mail.com','123',1,NOW()),
('u10','u10@mail.com','123',1,NOW());

INSERT INTO usuario_roles VALUES
(1,1),(2,2),(3,3),(4,1),(5,2),(6,3),(7,1),(8,2),(9,3),(10,1);

/* =========================================================
   BASE DE DATOS: FACTURAS
========================================================= */
CREATE DATABASE IF NOT EXISTS facturas;
USE facturas;

CREATE TABLE IF NOT EXISTS facturas (
    id_factura BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_paciente BIGINT,
    fecha_emision DATETIME,
    total DOUBLE,
    estado VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS factura_detalles (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    concepto VARCHAR(100),
    precio_unitario DOUBLE,
    cantidad INT,
    id_factura BIGINT
);

INSERT INTO facturas VALUES
(1,1,NOW(),1500,'PENDIENTE'),
(2,2,NOW(),2500,'PAGADA'),
(3,3,NOW(),3500,'PENDIENTE'),
(4,4,NOW(),4500,'PAGADA'),
(5,5,NOW(),5500,'ANULADA'),
(6,6,NOW(),6500,'PENDIENTE'),
(7,7,NOW(),7500,'PAGADA'),
(8,8,NOW(),8500,'PENDIENTE'),
(9,9,NOW(),9500,'PAGADA'),
(10,10,NOW(),10500,'PENDIENTE');

INSERT INTO factura_detalles VALUES
(1,'Consulta',1000,1,1),
(2,'Examen',500,1,1),
(3,'Consulta',2500,1,2),
(4,'Medicamento',500,2,3),
(5,'Consulta',4500,1,4),
(6,'Examen',1000,2,5),
(7,'Consulta',1500,1,6),
(8,'Medicamento',2000,1,7),
(9,'Consulta',3000,1,8),
(10,'Examen',4000,1,9);