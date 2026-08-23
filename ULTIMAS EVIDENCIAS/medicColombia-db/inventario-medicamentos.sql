CREATE DATABASE  IF NOT EXISTS `inventario_medicamentos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `inventario_medicamentos`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: inventario_medicamentos
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `barrio_direccion`
--

DROP TABLE IF EXISTS `barrio_direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `barrio_direccion` (
  `id_barrio` bigint NOT NULL AUTO_INCREMENT,
  `nombre_barrio` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_barrio`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `barrio_direccion`
--

LOCK TABLES `barrio_direccion` WRITE;
/*!40000 ALTER TABLE `barrio_direccion` DISABLE KEYS */;
INSERT INTO `barrio_direccion` (`id_barrio`, `nombre_barrio`) VALUES (1,'El Prado (Barranquilla)'),(2,'Alto Prado (Barranquilla)'),(3,'Riomar (Barranquilla)'),(4,'Ciudad Jardín (Barranquilla)'),(5,'Boston (Barranquilla)'),(6,'Recreo (Barranquilla)'),(7,'Barrio Abajo (Barranquilla)'),(8,'El Rosario (Barranquilla)'),(9,'Centro (Barranquilla)'),(10,'San Roque (Barranquilla)'),(11,'Chiquinquirá (Barranquilla)'),(12,'San Felipe (Barranquilla)'),(13,'Olaya (Barranquilla)'),(14,'La Victoria (Barranquilla)'),(15,'San José (Barranquilla)'),(16,'Cevillar (Barranquilla)'),(17,'La Unión (Barranquilla)'),(18,'Las Nieves (Barranquilla)'),(19,'Simón Bolívar (Barranquilla)'),(20,'Miramar (Barranquilla)'),(21,'Tabor (Barranquilla)'),(22,'Villa Santos (Barranquilla)'),(23,'Las Flores (Barranquilla)'),(24,'Siape (Barranquilla)'),(25,'La Playa (Barranquilla)'),(26,'El Centenario (Soledad)'),(27,'Hipódromo (Soledad)'),(28,'Costa Hermosa (Soledad)'),(29,'Los Cusules (Soledad)'),(30,'Villa Katanga (Soledad)'),(31,'Ciudadela Metropolitana (Soledad)'),(32,'Los Almendros (Soledad)'),(33,'Las Trinitarias (Soledad)'),(34,'Soledad 2000 (Soledad)'),(35,'Manuela Beltrán (Soledad)'),(36,'Las Gaviotas (Soledad)'),(37,'Villa Muvdi (Soledad)'),(38,'La Central (Soledad)'),(39,'El Parque (Soledad)'),(40,'Juan Domínguez Romero (Soledad)');
/*!40000 ALTER TABLE `barrio_direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_categoria` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_cat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` (`id_categoria`, `descripcion`, `nombre_cat`) VALUES (2,'Sustancias para combatir infecciones bacterianas.','Antibióticos'),(3,'Reducen la inflamación y el dolor asociado.','Antiinflamatorios'),(4,'Utilizados para reducir la fiebre.','Antipiréticos'),(5,'Tratamiento para reacciones alérgicas.','Antihistamínicos'),(6,'Neutralizan la acidez estomacal.','Antiácidos'),(7,'Controlan y detienen la diarrea.','Antidiarréicos'),(8,'Facilitan la evacuación intestinal.','Laxantes'),(9,'Medicamentos para calmar la tos seca.','Antitusivos'),(10,'Ayudan a eliminar el exceso de moco en vías respiratorias.','Mucolíticos'),(11,'Facilitan la expulsión de secreciones bronquiales.','Expectorantes'),(12,'Sustancias para prevenir infecciones en heridas superficiales.','Antisépticos'),(13,'Tratamiento contra infecciones causadas por hongos.','Antifúngicos'),(14,'Medicamentos para combatir infecciones por virus.','Antivirales'),(15,'Reducen la ansiedad y estados de nerviosismo.','Ansiolíticos'),(16,'Tratamiento para trastornos de la depresión.','Antidepresivos'),(17,'Controlan la presión arterial alta.','Antihipertensivos'),(18,'Regulan los niveles de azúcar en la sangre.','Antidiabéticos'),(19,'Previenen la formación de coágulos sanguíneos.','Anticoagulantes'),(20,'Abren las vías respiratorias en pacientes con asma o EPOC.','Broncodilatadores'),(21,'Potentes antiinflamatorios hormonales.','Corticosteroides'),(22,'Ayudan a eliminar el exceso de líquido del cuerpo.','Diuréticos'),(23,'Complementos para cubrir deficiencias nutricionales.','Vitaminas y Suplementos'),(24,'Suplementos como calcio, hierro y magnesio.','Minerales'),(25,'Cremas y ungüentos para afecciones de la piel.','Dermatológicos'),(26,'Gotas y pomadas para el cuidado de los ojos.','Oftalmológicos'),(27,'Gotas para el tratamiento de afecciones del oído.','Óticos'),(28,'Medicamentos específicos para la salud femenina.','Ginecológicos'),(29,'Tratamientos para el sistema urinario.','Urológicos'),(30,'Relajan los espasmos musculares internos.','Antiespasmódicos'),(31,'Protegen la mucosa del estómago.','Protectores Gástricos'),(32,'Reducen los niveles de colesterol y triglicéridos.','Hipolipemiantes'),(33,'Tratan contracturas y dolores musculares intensos.','Relajantes Musculares'),(34,'Bloquean el dolor en una zona específica.','Anestésicos Locales'),(35,'Previenen o detienen las náuseas y el vómito.','Antieméticos'),(36,'Tratamientos para desequilibrios del sistema endocrino.','Hormonales'),(37,'Reducen la actividad del sistema inmunitario.','Inmunosupresores'),(38,'Medicamentos que inducen el sueño o calma profunda.','Sedantes'),(39,'Medicamentos generales para la salud del corazón.','Cardiovasculares'),(40,'Métodos hormonales para prevenir el embarazo.','Anticonceptivos'),(41,'Medicamentos con dosis y fórmulas para niños.','Pediatricos'),(42,'Medicamentos especializados para adultos mayores.','Geriatricos'),(43,'Productos de medicina alternativa.','Homeopáticos'),(44,'Medicamentos a base de extractos de plantas.','Fitoterapéuticos'),(45,'Combinaciones de varias vitaminas en un solo producto.','Multivitamínicos'),(46,'Soluciones para recuperar electrolitos.','Sueros Rehidratantes'),(47,'Eliminan parásitos internos o externos.','Antiparasitarios'),(48,'Tratamientos específicos para candidiasis y otros.','Antimicóticos Vaginales'),(49,'Estimulantes del sistema nervioso central.','Analépticos'),(50,'Medicamentos que actúan sobre el estado mental (Controlados).','Psicotrópicos'),(51,'Medicamentos para el alivio del dolor leve a moderado.','Analgésicos'),(52,'Materiales de curación y elementos médicos.','Insumos médicos');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` bigint NOT NULL AUTO_INCREMENT,
  `identificacion_cliente` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre_cliente` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `UKhtnbqlgdilc7014pc62haikj2` (`identificacion_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`id_cliente`, `identificacion_cliente`, `nombre_cliente`) VALUES (1,'1140123456','Stiven Daniel Robles'),(3,'1048223513','Byron Lubo'),(4,'1193643784','Stiven Robles');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion_sistema`
--

DROP TABLE IF EXISTS `configuracion_sistema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion_sistema` (
  `id_configuracion` bigint NOT NULL AUTO_INCREMENT,
  `clave` varchar(60) COLLATE utf8mb4_general_ci NOT NULL,
  `valor` text COLLATE utf8mb4_general_ci NOT NULL,
  `descripcion` varchar(250) COLLATE utf8mb4_general_ci NOT NULL,
  `categoria` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_configuracion`),
  UNIQUE KEY `clave` (`clave`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion_sistema`
--

LOCK TABLES `configuracion_sistema` WRITE;
/*!40000 ALTER TABLE `configuracion_sistema` DISABLE KEYS */;
INSERT INTO `configuracion_sistema` (`id_configuracion`, `clave`, `valor`, `descripcion`, `categoria`) VALUES (1,'nombre_empresa','MedicColombia','Nombre comercial mostrado en la interfaz y documentos','SISTEMA'),(2,'nombre_sistema','Asuras Col','Nombre técnico del sistema','SISTEMA'),(3,'slogan_empresa','Control y confianza en cada registro','Eslogan mostrado en footer, facturas y reportes','SISTEMA'),(4,'version_sistema','1.0.1','Versión actual del sistema','SISTEMA'),(5,'dias_alerta_vencimiento','7','Días de anticipación para generar alerta de vencimiento próximo','INVENTARIO'),(6,'dias_proximo_vencer','30','Días para considerar un producto como próximo a vencer en reportes','INVENTARIO'),(7,'stock_minimo_default','10','Stock mínimo sugerido al registrar un nuevo producto','INVENTARIO'),(8,'stock_maximo_default','100','Stock máximo sugerido al registrar un nuevo producto','INVENTARIO'),(9,'porcentaje_estimacion_pedidos','20','Porcentaje de incremento aplicado al histórico para estimar pedidos en proyecciones','INVENTARIO'),(10,'moneda','COP','Código de moneda usado en facturas y reportes','FACTURACION'),(11,'iva_porcentaje','0','Porcentaje de IVA aplicado en ventas (0 = exento)','FACTURACION'),(12,'items_por_pagina','2','Cantidad de filas por página en todas las tablas','INTERFAZ'),(13,'filas_dashboard','5','Cantidad de movimientos mostrados en el dashboard','INTERFAZ'),(14,'sesion_timeout_minutos','30','Tiempo de inactividad antes de cerrar sesión (referencia visual)','SEGURIDAD'),(15,'intentos_login_max','3','Máximo de intentos fallidos de inicio de sesión (referencia visual)','SEGURIDAD');
/*!40000 ALTER TABLE `configuracion_sistema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuracion_usuario`
--

DROP TABLE IF EXISTS `configuracion_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `configuracion_usuario` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `usuario_id` bigint NOT NULL,
  `clave` varchar(60) COLLATE utf8mb4_general_ci NOT NULL,
  `valor` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_usuario_clave` (`usuario_id`,`clave`),
  CONSTRAINT `fk_conf_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuracion_usuario`
--

LOCK TABLES `configuracion_usuario` WRITE;
/*!40000 ALTER TABLE `configuracion_usuario` DISABLE KEYS */;
INSERT INTO `configuracion_usuario` (`id`, `usuario_id`, `clave`, `valor`) VALUES (1,1,'tema','oscuro'),(2,1,'idioma','es'),(3,1,'filas_por_pagina','10'),(4,1,'notificaciones','true'),(5,16,'notificaciones','false'),(6,16,'tema','oscuro'),(7,16,'idioma','es'),(8,16,'filas_por_pagina','10'),(9,18,'filas_por_pagina','10'),(10,18,'idioma','es'),(11,18,'tema','oscuro'),(12,18,'notificaciones','false');
/*!40000 ALTER TABLE `configuracion_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `correo`
--

DROP TABLE IF EXISTS `correo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `correo` (
  `id_correo` bigint NOT NULL AUTO_INCREMENT,
  `correo_electronico` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `proveedor_id` bigint DEFAULT NULL,
  `tipo_correo_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_correo`),
  KEY `FKmvmebo8gjep9se5sc1jfp6tds` (`cliente_id`),
  KEY `FKdmm1icxdt1joafrtjm6agsytb` (`proveedor_id`),
  KEY `FKdpjsl5268o9k38jucbshunbo3` (`tipo_correo_id`),
  CONSTRAINT `FKdmm1icxdt1joafrtjm6agsytb` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`),
  CONSTRAINT `FKdpjsl5268o9k38jucbshunbo3` FOREIGN KEY (`tipo_correo_id`) REFERENCES `tipo_correo` (`id_tipo_correo`),
  CONSTRAINT `FKmvmebo8gjep9se5sc1jfp6tds` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `correo`
--

LOCK TABLES `correo` WRITE;
/*!40000 ALTER TABLE `correo` DISABLE KEYS */;
INSERT INTO `correo` (`id_correo`, `correo_electronico`, `cliente_id`, `proveedor_id`, `tipo_correo_id`) VALUES (1,'ventas@pharmacolombia.co',NULL,2,1),(2,'stiven.robles@gmail.com',1,NULL,2),(3,'byronluma96@gmail.com',3,NULL,2),(4,'ejemplo@gmail.com',1,NULL,1),(5,'ejemplo@gmail.com',NULL,2,3),(6,'ayuda.eduardo12@hotmail.com',NULL,2,2);
/*!40000 ALTER TABLE `correo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_proveedor_producto`
--

DROP TABLE IF EXISTS `detalle_proveedor_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_proveedor_producto` (
  `precio_unitario` decimal(38,2) DEFAULT NULL,
  `producto_id` bigint NOT NULL,
  `proveedor_id` bigint NOT NULL,
  PRIMARY KEY (`producto_id`,`proveedor_id`),
  KEY `FKqnw6r5s211rw5gi73ap2doq4h` (`proveedor_id`),
  CONSTRAINT `FKnwfrhlko0vcfpil2fyp3ji9x4` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `FKqnw6r5s211rw5gi73ap2doq4h` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_proveedor_producto`
--

LOCK TABLES `detalle_proveedor_producto` WRITE;
/*!40000 ALTER TABLE `detalle_proveedor_producto` DISABLE KEYS */;
INSERT INTO `detalle_proveedor_producto` (`precio_unitario`, `producto_id`, `proveedor_id`) VALUES (440.00,67,3),(420.00,67,4),(710.00,68,3),(690.00,68,4),(3100.00,69,7),(2950.00,69,9),(610.00,70,3),(590.00,70,4),(1580.00,71,10),(1620.00,71,11),(730.00,72,3),(760.00,72,4),(980.00,73,3),(950.00,73,4),(690.00,74,3),(720.00,74,6),(810.00,75,4),(830.00,75,6),(1180.00,76,6),(1160.00,76,10),(1520.00,77,3),(1550.00,77,4),(6200.00,78,4),(2680.00,79,10),(2740.00,79,11),(2180.00,80,4),(2140.00,80,10),(1360.00,81,4),(1980.00,82,3),(1930.00,82,4),(590.00,83,3),(620.00,83,4),(2850.00,84,7),(720.00,85,3),(710.00,85,4),(1650.00,86,10),(920.00,87,3),(880.00,87,4),(720.00,88,3),(690.00,88,4),(1010.00,89,3),(980.00,89,4),(520.00,90,3),(2400.00,91,7),(1980.00,92,4),(910.00,93,3),(880.00,93,4),(640.00,94,4),(770.00,95,6),(980.00,96,6),(700.00,97,2),(430.00,97,7),(820.00,98,7),(790.00,98,8),(650.00,99,8),(930.00,100,8),(410.00,101,8),(780.00,102,8),(750.00,103,3),(720.00,103,4),(960.00,104,4),(540.00,105,4),(1450.00,106,10),(2600.00,107,10),(4200.00,108,10),(3850.00,109,10),(2800.00,110,12),(3300.00,111,9),(3400.00,111,12),(1900.00,112,9),(1850.00,112,12),(620.00,113,12),(1450.00,114,12),(1850.00,115,12),(5100.00,116,9),(5200.00,116,12),(1180.00,117,4),(1320.00,118,4),(500.00,120,2),(20.00,122,5);
/*!40000 ALTER TABLE `detalle_proveedor_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion`
--

DROP TABLE IF EXISTS `direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion` (
  `id_direccion` bigint NOT NULL AUTO_INCREMENT,
  `complemento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `direccion` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `barrio_id` bigint DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `proveedor_id` bigint DEFAULT NULL,
  `tipo_direccion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_direccion`),
  KEY `FKg0fj0k9eyyu6iobu9innt9jxb` (`barrio_id`),
  KEY `FK2t6fhjqxc6ln670rigl9crmmn` (`cliente_id`),
  KEY `FK74ure9i3hpp7lvk3xhctxxkrq` (`proveedor_id`),
  KEY `FK38stbcna0ik41pvi3y1lxhnwq` (`tipo_direccion_id`),
  CONSTRAINT `FK2t6fhjqxc6ln670rigl9crmmn` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `FK38stbcna0ik41pvi3y1lxhnwq` FOREIGN KEY (`tipo_direccion_id`) REFERENCES `tipo_direccion` (`id_tipo_direccion`),
  CONSTRAINT `FK74ure9i3hpp7lvk3xhctxxkrq` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`),
  CONSTRAINT `FKg0fj0k9eyyu6iobu9innt9jxb` FOREIGN KEY (`barrio_id`) REFERENCES `barrio_direccion` (`id_barrio`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion`
--

LOCK TABLES `direccion` WRITE;
/*!40000 ALTER TABLE `direccion` DISABLE KEYS */;
INSERT INTO `direccion` (`id_direccion`, `complemento`, `direccion`, `barrio_id`, `cliente_id`, `proveedor_id`, `tipo_direccion_id`) VALUES (1,'Frente al parque','Calle 72 # 43-20, Apto 402',2,1,NULL,1),(2,'Edificio Los Rosales','Calle 19A #47-14',11,NULL,2,1);
/*!40000 ALTER TABLE `direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_pedido`
--

DROP TABLE IF EXISTS `estado_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_pedido` (
  `id_estado_pedido` bigint NOT NULL AUTO_INCREMENT,
  `descripcion_estado` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_estado` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_estado_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_pedido`
--

LOCK TABLES `estado_pedido` WRITE;
/*!40000 ALTER TABLE `estado_pedido` DISABLE KEYS */;
INSERT INTO `estado_pedido` (`id_estado_pedido`, `descripcion_estado`, `nombre_estado`) VALUES (1,'El pedido se está armando y aún no ha sido enviado al proveedor.','Borrador'),(2,'El pedido requiere la firma o autorización de la gerencia o área financiera.','Pendiente de Aprobación'),(3,'La orden de compra ya fue emitida formalmente al proveedor.','Enviado / Solicitado'),(4,'El proveedor despachó la mercancía y viene en camino hacia la bodega.','En Tránsito'),(5,'Llegó una parte de los medicamentos, pero faltan ítems por entregar.','Recibido Parcial'),(6,'Toda la mercancía llegó correctamente y se ingresó al inventario.','Completado / Recibido'),(7,'El pedido fue anulado por la administración antes de ser despachado.','Cancelado'),(8,'El proveedor no pudo procesar el pedido (por falta de stock, precios desactualizados, etc.).','Rechazado'),(9,'La mercancía llegó a bodega pero fue devuelta en su totalidad por averías o no cumplir requisitos.','Devuelto');
/*!40000 ALTER TABLE `estado_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_usuario`
--

DROP TABLE IF EXISTS `estado_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_usuario` (
  `id_estado_usuario` bigint NOT NULL AUTO_INCREMENT,
  `fecha_fin` datetime(6) DEFAULT NULL,
  `fecha_inicio` datetime(6) DEFAULT NULL,
  `observacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tipo_estado_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_estado_usuario`),
  KEY `FKnj6ah2qvsmxdqxpu40487cjb1` (`tipo_estado_id`),
  KEY `FKanwoq8y8fbq3ccx6ylxrkpp6m` (`usuario_id`),
  CONSTRAINT `FKanwoq8y8fbq3ccx6ylxrkpp6m` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKnj6ah2qvsmxdqxpu40487cjb1` FOREIGN KEY (`tipo_estado_id`) REFERENCES `tipo_estado` (`id_tipo_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_usuario`
--

LOCK TABLES `estado_usuario` WRITE;
/*!40000 ALTER TABLE `estado_usuario` DISABLE KEYS */;
INSERT INTO `estado_usuario` (`id_estado_usuario`, `fecha_fin`, `fecha_inicio`, `observacion`, `tipo_estado_id`, `usuario_id`) VALUES (2,NULL,'2026-07-01 23:52:00.000000',NULL,1,16),(3,NULL,'2026-07-01 23:56:00.000000','pasante',1,17),(4,NULL,'2026-07-03 02:30:00.000000',NULL,1,18),(5,NULL,'2026-07-18 11:03:00.000000','',1,1),(6,NULL,'2026-07-22 08:03:00.000000','DESPIDO',2,17),(7,NULL,'2026-07-22 08:32:00.000000','VACACIONES',5,16),(8,NULL,'2026-08-23 16:00:00.000000',NULL,1,16);
/*!40000 ALTER TABLE `estado_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `metodo_proyeccion`
--

DROP TABLE IF EXISTS `metodo_proyeccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metodo_proyeccion` (
  `id_metodo_proyeccion` bigint NOT NULL AUTO_INCREMENT,
  `nombre_metodo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_metodo_proyeccion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `metodo_proyeccion`
--

LOCK TABLES `metodo_proyeccion` WRITE;
/*!40000 ALTER TABLE `metodo_proyeccion` DISABLE KEYS */;
INSERT INTO `metodo_proyeccion` (`id_metodo_proyeccion`, `nombre_metodo`) VALUES (1,'Promedio Histórico'),(2,'Suma Acumulada'),(3,'Conteo de Ocurrencias');
/*!40000 ALTER TABLE `metodo_proyeccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `id_producto` bigint NOT NULL AUTO_INCREMENT,
  `fecha_expiracion` datetime(6) NOT NULL,
  `fecha_creacion` datetime(6) NOT NULL,
  `fecha_modificacion` datetime(6) NOT NULL,
  `lote_producto` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre_prod` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `stock` int NOT NULL,
  `stock_maximo` int NOT NULL,
  `stock_minimo` int NOT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_producto`),
  KEY `FKodqr7965ok9rwquj1utiamt0m` (`categoria_id`),
  KEY `FK4f8g2yvj0uj7hqxlauy8p8k39` (`usuario_id`),
  CONSTRAINT `FK4f8g2yvj0uj7hqxlauy8p8k39` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKodqr7965ok9rwquj1utiamt0m` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`id_producto`, `fecha_expiracion`, `fecha_creacion`, `fecha_modificacion`, `lote_producto`, `nombre_prod`, `stock`, `stock_maximo`, `stock_minimo`, `categoria_id`, `usuario_id`, `activo`) VALUES (67,'2026-12-31 00:00:00.000000','2026-07-01 19:07:56.000000','2026-08-23 13:32:32.247013','L-2025-001','Acetaminofén 500mg Tab x10',379,500,50,51,16,1),(68,'2026-10-31 00:00:00.000000','2026-07-01 19:07:56.000000','2026-08-23 13:32:32.281626','L-2025-002','Acetaminofén 1g Tab x10',194,400,40,51,16,1),(69,'2026-08-31 00:00:00.000000','2026-07-01 19:07:56.000000','2026-07-01 19:07:56.000000','L-2025-003','Acetaminofén Jarabe 120mg/5ml',80,150,15,51,16,1),(70,'2026-11-30 00:00:00.000000','2026-07-01 19:07:56.000000','2026-07-02 00:01:27.622450','L-2025-004','Dipirona 500mg Tab x10',155,300,30,51,16,1),(71,'2027-01-31 00:00:00.000000','2026-07-01 19:07:56.000000','2026-07-01 19:07:56.000000','L-2025-005','Tramadol 50mg Cap x10',60,100,10,51,16,1),(72,'2026-09-30 00:00:00.000000','2026-07-01 19:08:49.000000','2026-07-02 22:57:02.246251','L-2025-006','Ibuprofeno 400mg Tab x10',550,500,50,3,16,1),(73,'2026-07-31 00:00:00.000000','2026-07-01 19:08:49.000000','2026-08-15 00:00:00.489342','L-2025-007','Ibuprofeno 600mg Tab x10',0,350,30,3,16,1),(74,'2026-11-30 00:00:00.000000','2026-07-01 19:08:49.000000','2026-07-01 19:08:49.000000','L-2025-008','Diclofenaco 50mg Tab x10',120,250,25,3,16,1),(75,'2027-02-28 00:00:00.000000','2026-07-01 19:08:49.000000','2026-07-17 00:01:32.764619','L-2025-009','Naproxeno 500mg Tab x10',86,200,20,3,16,1),(76,'2027-03-31 00:00:00.000000','2026-07-01 19:08:49.000000','2026-07-01 19:08:49.000000','L-2025-010','Meloxicam 15mg Tab x10',70,150,15,3,16,1),(77,'2026-07-07 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-05 23:15:41.318797','L-2025-011','Amoxicilina 500mg Cap x10',0,200,20,2,16,1),(78,'2026-05-31 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-05 21:33:03.650530','L-2025-012','Amoxicilina 250mg/5ml Susp',0,100,10,2,16,1),(79,'2027-01-31 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-01 19:09:18.000000','L-2025-013','Azitromicina 500mg Tab x3',80,150,15,2,16,1),(80,'2026-10-31 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-01 19:09:18.000000','L-2025-014','Ciprofloxacino 500mg Tab x10',60,120,10,2,16,1),(81,'2026-08-31 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-01 19:09:18.000000','L-2025-015','Metronidazol 500mg Tab x10',70,140,15,2,16,1),(82,'2026-12-31 00:00:00.000000','2026-07-01 19:09:18.000000','2026-07-02 00:01:27.622450','L-2025-016','Cefalexina 500mg Cap x10',61,110,10,2,16,1),(83,'2027-04-30 00:00:00.000000','2026-07-01 19:09:58.000000','2026-07-01 19:09:58.000000','L-2025-017','Loratadina 10mg Tab x10',200,400,40,5,16,1),(84,'2026-09-30 00:00:00.000000','2026-07-01 19:09:58.000000','2026-07-01 19:09:58.000000','L-2025-018','Loratadina Jarabe 5mg/5ml',60,120,12,5,16,1),(85,'2027-02-28 00:00:00.000000','2026-07-01 19:09:58.000000','2026-07-01 19:09:58.000000','L-2025-019','Cetirizina 10mg Tab x10',150,300,30,5,16,1),(86,'2026-11-30 00:00:00.000000','2026-07-01 19:09:58.000000','2026-07-01 19:09:58.000000','L-2025-020','Difenhidramina 50mg Cap x10',80,150,15,5,16,1),(87,'2027-05-31 00:00:00.000000','2026-07-01 19:09:58.000000','2026-07-01 19:09:58.000000','L-2025-021','Desloratadina 5mg Tab x10',90,180,20,5,16,1),(88,'2027-01-31 00:00:00.000000','2026-07-01 19:10:23.000000','2026-07-01 19:10:23.000000','L-2025-022','Omeprazol 20mg Cap x10',180,360,35,6,16,1),(89,'2027-03-31 00:00:00.000000','2026-07-01 19:10:23.000000','2026-07-01 19:10:23.000000','L-2025-023','Omeprazol 40mg Cap x10',100,200,20,6,16,1),(90,'2026-07-31 00:00:00.000000','2026-07-01 19:10:23.000000','2026-08-15 00:00:00.520319','L-2025-024','Ranitidina 150mg Tab x10',0,240,25,6,16,1),(91,'2026-10-31 00:00:00.000000','2026-07-01 19:10:23.000000','2026-07-01 19:10:23.000000','L-2025-025','Hidróxido de Aluminio Susp',70,140,15,6,16,1),(92,'2027-02-28 00:00:00.000000','2026-07-01 19:10:23.000000','2026-07-01 19:10:23.000000','L-2025-026','Sucralfato 1g Tab x10',50,100,10,6,16,1),(93,'2027-06-30 00:00:00.000000','2026-07-01 19:11:05.000000','2026-07-01 19:11:05.000000','L-2025-027','Losartán 50mg Tab x30',150,300,30,17,16,1),(94,'2027-04-30 00:00:00.000000','2026-07-01 19:11:05.000000','2026-07-01 19:11:05.000000','L-2025-028','Enalapril 10mg Tab x30',120,240,25,17,16,1),(95,'2027-05-31 00:00:00.000000','2026-07-01 19:11:05.000000','2026-07-01 19:11:05.000000','L-2025-029','Amlodipino 5mg Tab x30',100,200,20,17,16,1),(96,'2027-03-31 00:00:00.000000','2026-07-01 19:11:05.000000','2026-07-01 19:11:05.000000','L-2025-030','Metoprolol 50mg Tab x30',80,160,15,17,16,1),(97,'2027-08-31 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-02 21:41:16.977989','L-2025-031','Vitamina C 500mg Tab x10',296,600,50,23,16,1),(98,'2028-01-31 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-01 19:11:31.000000','L-2025-032','Vitamina D3 1000 UI Cap x30',150,300,30,23,16,1),(99,'2027-07-31 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-01 19:11:31.000000','L-2025-033','Complejo B Tab x30',200,400,40,23,16,1),(100,'2027-09-30 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-01 19:11:31.000000','L-2025-034','Calcio + Vitamina D Tab x30',100,200,20,23,16,1),(101,'2027-06-30 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-01 19:11:31.000000','L-2025-035','Ácido Fólico 1mg Tab x30',120,240,25,23,16,1),(102,'2027-05-31 00:00:00.000000','2026-07-01 19:11:31.000000','2026-07-01 19:11:31.000000','L-2025-036','Hierro + Ácido Fólico Tab x30',90,180,20,23,16,1),(103,'2027-04-30 00:00:00.000000','2026-07-01 19:11:59.000000','2026-07-01 19:11:59.000000','L-2025-037','Metformina 500mg Tab x30',120,240,25,18,16,1),(104,'2027-03-31 00:00:00.000000','2026-07-01 19:11:59.000000','2026-07-01 19:11:59.000000','L-2025-038','Metformina 850mg Tab x30',100,200,20,18,16,1),(105,'2027-02-28 00:00:00.000000','2026-07-01 19:11:59.000000','2026-07-01 19:11:59.000000','L-2025-039','Glibenclamida 5mg Tab x30',80,160,15,18,16,1),(106,'2027-01-31 00:00:00.000000','2026-07-01 19:12:51.000000','2026-07-01 19:12:51.000000','L-2025-040','Fluconazol 150mg Cap x1',60,120,10,13,16,1),(107,'2027-06-30 00:00:00.000000','2026-07-01 19:12:51.000000','2026-07-01 19:12:51.000000','L-2025-041','Clotrimazol Crema 1% x20g',80,160,15,13,16,1),(108,'2027-05-31 00:00:00.000000','2026-07-01 19:12:51.000000','2026-07-01 19:12:51.000000','L-2025-042','Ketoconazol Champú 2% x120ml',50,100,10,13,16,1),(109,'2027-04-30 00:00:00.000000','2026-07-01 19:12:51.000000','2026-07-01 19:12:51.000000','L-2025-043','Terbinafina 250mg Tab x14',40,80,8,13,16,1),(110,'2027-08-31 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-044','Yodo Povidona 10% x120ml',100,200,20,52,16,1),(111,'2028-01-31 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-045','Alcohol Antiséptico 70% x250ml',200,400,40,52,16,1),(112,'2027-06-30 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-046','Suero Fisiológico 0.9% x500ml',80,160,15,52,16,1),(113,'2028-06-30 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-047','Gasas Estériles x10 Sobres',150,300,30,52,16,1),(114,'2028-12-31 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-048','Vendas Elásticas 5cm x4.5m',100,200,20,52,16,1),(115,'2029-01-31 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-049','Jeringas 5ml c/Aguja x10',120,250,25,52,16,1),(116,'2027-12-31 00:00:00.000000','2026-07-01 19:15:02.000000','2026-07-01 19:15:02.000000','L-2025-050','Tapabocas Quirúrgico x50',80,160,15,52,16,1),(117,'2026-07-06 19:19:29.000000','2026-07-01 19:19:29.000000','2026-07-09 00:00:00.185779','L-2025-V01','Tetracilina 500mg Cap x10',0,100,10,2,16,1),(118,'2026-07-04 19:19:29.000000','2026-07-01 19:19:29.000000','2026-07-06 00:00:00.086001','L-2025-V02','Eritromicina 500mg Tab x10',0,100,10,2,16,1),(120,'2026-07-02 22:03:00.000000','2026-07-01 22:03:12.190979','2026-07-05 23:12:17.408631','L-1212-1212','perromol',0,100,10,10,16,0),(122,'2026-07-07 11:47:00.000000','2026-07-05 21:45:58.373533','2026-07-09 00:00:00.211018','L-1212-1212','perromol',0,100,10,13,16,0);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `id_proveedor` bigint NOT NULL AUTO_INCREMENT,
  `nit` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_prov` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` (`id_proveedor`, `nit`, `nombre_prov`) VALUES (2,'800.123.456-1','Droguerías Aliadas de la Costa S.A.S.'),(3,'890300279-1','Tecnoquímicas S.A.'),(4,'860005934-2','Laboratorios Genfar S.A.S.'),(5,'890900115-3','Droguerías Cruz Verde S.A.S.'),(6,'860002130-4','Bayer S.A.'),(7,'800254063-5','Copidrogas (Cooperativa Nacional de Droguistas)'),(8,'860010165-6','Laboratorios Procaps S.A.'),(9,'900123456-7','Distribuidora Médica del Caribe S.A.S.'),(10,'890201880-8','Abbott Laboratories de Colombia S.A.'),(11,'860000210-9','Pfizer S.A.S.'),(12,'901456789-0','Suministros Hospitalarios y Medicamentos del Interior'),(14,'12222232','Carmen Alicia');
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `telefono`
--

DROP TABLE IF EXISTS `telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `telefono` (
  `id_telefono` bigint NOT NULL AUTO_INCREMENT,
  `complemento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `numero` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `proveedor_id` bigint DEFAULT NULL,
  `tipo_telefono_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_telefono`),
  KEY `FK3cti5jlsdbqd6183co02gwbnv` (`cliente_id`),
  KEY `FK2ujupbhtd9g56f7s7rvn0tyso` (`proveedor_id`),
  KEY `FK40gcx1yiav5t3c1odf80h1tvp` (`tipo_telefono_id`),
  CONSTRAINT `FK2ujupbhtd9g56f7s7rvn0tyso` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`),
  CONSTRAINT `FK3cti5jlsdbqd6183co02gwbnv` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `FK40gcx1yiav5t3c1odf80h1tvp` FOREIGN KEY (`tipo_telefono_id`) REFERENCES `tipo_telefono` (`id_tipo_telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `telefono`
--

LOCK TABLES `telefono` WRITE;
/*!40000 ALTER TABLE `telefono` DISABLE KEYS */;
INSERT INTO `telefono` (`id_telefono`, `complemento`, `numero`, `cliente_id`, `proveedor_id`, `tipo_telefono_id`) VALUES (1,'Llamar despues de las 12PM','3001234567',1,NULL,1),(2,'+57','3043887661',3,NULL,1),(3,'+57','3043887661',NULL,2,1);
/*!40000 ALTER TABLE `telefono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_correo`
--

DROP TABLE IF EXISTS `tipo_correo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_correo` (
  `id_tipo_correo` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_correo`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_correo`
--

LOCK TABLES `tipo_correo` WRITE;
/*!40000 ALTER TABLE `tipo_correo` DISABLE KEYS */;
INSERT INTO `tipo_correo` (`id_tipo_correo`, `nombre_tipo`) VALUES (1,'Corporativo'),(2,'Personal'),(3,'Institucional');
/*!40000 ALTER TABLE `tipo_correo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_direccion`
--

DROP TABLE IF EXISTS `tipo_direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_direccion` (
  `id_tipo_direccion` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_direccion`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_direccion`
--

LOCK TABLES `tipo_direccion` WRITE;
/*!40000 ALTER TABLE `tipo_direccion` DISABLE KEYS */;
INSERT INTO `tipo_direccion` (`id_tipo_direccion`, `nombre_tipo`) VALUES (1,'Residencial'),(2,'Bodega Principal'),(3,'Consultorio');
/*!40000 ALTER TABLE `tipo_direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_estado`
--

DROP TABLE IF EXISTS `tipo_estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_estado` (
  `id_tipo_estado` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `descripcion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_estado`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_estado`
--

LOCK TABLES `tipo_estado` WRITE;
/*!40000 ALTER TABLE `tipo_estado` DISABLE KEYS */;
INSERT INTO `tipo_estado` (`id_tipo_estado`, `nombre_tipo`, `descripcion`) VALUES (1,'Activo','Usuario con acceso total al sistema y funciones habilitadas.'),(2,'Inactivo','Cuenta deshabilitada. El usuario no puede iniciar sesión.'),(3,'Bloqueado','Acceso restringido por seguridad tras múltiples intentos fallidos.'),(4,'Ausente','El usuario está logueado pero no disponible para asignación de tareas.'),(5,'Vacaciones','Estado temporal para usuarios en periodo de descanso legal.'),(6,'Pendiente','Usuario registrado que aún no ha confirmado su correo electrónico.');
/*!40000 ALTER TABLE `tipo_estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_movimiento`
--

DROP TABLE IF EXISTS `tipo_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_movimiento` (
  `id_tipo_movimiento` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_movimiento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `signo` int DEFAULT NULL,
  PRIMARY KEY (`id_tipo_movimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_movimiento`
--

LOCK TABLES `tipo_movimiento` WRITE;
/*!40000 ALTER TABLE `tipo_movimiento` DISABLE KEYS */;
INSERT INTO `tipo_movimiento` (`id_tipo_movimiento`, `descripcion`, `nombre_movimiento`, `signo`) VALUES (1,'Entrada de mercancía por factura de proveedor','Compra',1),(2,'Retorno de producto por parte del comprador','Devolución de Cliente',1),(3,'Ajuste manual al encontrar más stock físico del esperado','Nivelación Positiva',1),(4,'Mercancía que llega desde otra bodega o sucursal','Traslado Recibido',1),(5,'Reingreso de producto por cancelación de factura','Anulación de Venta',1),(6,'Salida de producto por transacción comercial','Venta',-1),(7,'Baja de producto por fecha de caducidad superada','Retiro por Vencimiento',-1),(8,'Retiro de stock por averías, ruptura o mal estado','Mercancía Dañada',-1),(9,'Salida de mercancía hacia otra ubicación o tienda','Traslado Enviado',-1),(10,'Ajuste manual por pérdida, robo o error de conteo','Nivelación Negativa',-1),(11,'Uso de insumos para la operación de la clínica/local','Consumo Interno',-1),(12,'Mercancía devuelta al proveedor de origen','Devolución al proveedor',-1);
/*!40000 ALTER TABLE `tipo_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_proyeccion`
--

DROP TABLE IF EXISTS `tipo_proyeccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_proyeccion` (
  `id_tipo_proyeccion` bigint NOT NULL AUTO_INCREMENT,
  `nombre_proyeccion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_proyeccion`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_proyeccion`
--

LOCK TABLES `tipo_proyeccion` WRITE;
/*!40000 ALTER TABLE `tipo_proyeccion` DISABLE KEYS */;
INSERT INTO `tipo_proyeccion` (`id_tipo_proyeccion`, `nombre_proyeccion`) VALUES (1,'Productos Más Vendidos'),(2,'Productos Menos Vendidos'),(3,'Retiros por Vencimiento'),(4,'Ventas por Categoría'),(5,'Proveedores Más Fiables'),(6,'Clientes Más Fieles'),(7,'Precios de Mercado');
/*!40000 ALTER TABLE `tipo_proyeccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_telefono`
--

DROP TABLE IF EXISTS `tipo_telefono`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_telefono` (
  `id_tipo_telefono` bigint NOT NULL AUTO_INCREMENT,
  `nombre_tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_tipo_telefono`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_telefono`
--

LOCK TABLES `tipo_telefono` WRITE;
/*!40000 ALTER TABLE `tipo_telefono` DISABLE KEYS */;
INSERT INTO `tipo_telefono` (`id_tipo_telefono`, `nombre_tipo`) VALUES (1,'Móvil Personal'),(2,'Fijo Oficina'),(3,'Emergencia');
/*!40000 ALTER TABLE `tipo_telefono` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `correo_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `rol_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `identificacion_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `foto_perfil` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UKas5mdu7ejl9ytn6gvs9srfcwd` (`correo_usu`),
  UNIQUE KEY `identificacion_usu` (`identificacion_usu`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id_usuario`, `contrasena`, `correo_usu`, `nombre_usu`, `rol_usu`, `identificacion_usu`, `foto_perfil`) VALUES (1,'1','1','SISTEMA','ADMIN','1',NULL),(16,'$2a$10$xzWWKrEiR8Xb3KsWZqhgMOJnkRLs5D.H9mwgrmdu58F2lxz7Qgw.y','admin@mediccolombia.com','ADMINISTRADOR GENERAL','ADMIN','1193643784','/uploads/perfil/perfil_16_1787500774376.jpg'),(17,'$2a$10$lou3YhhS3Je2thhAp9Aof.EmsVNkdy7uK4HcfTQHQ3XHj2q7FMaSm','sebasUru69@gmail.com','Sebastian Urueta','EMPLEADO','1048223645',NULL),(18,'$2a$10$MEZLA7PkaGhBegO5OJTp7.5pWNb7YZzZuEwsbzCFBRKBGh.jOcBdy','carlos@gmail.com','Carlos ','EMPLEADO','1222222','/uploads/perfil/perfil_18_1787500880757.png');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:16:28
