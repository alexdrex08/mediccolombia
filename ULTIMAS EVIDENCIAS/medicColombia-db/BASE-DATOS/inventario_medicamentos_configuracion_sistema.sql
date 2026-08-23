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
INSERT INTO `configuracion_sistema` VALUES (1,'nombre_empresa','MedicColombia','Nombre comercial mostrado en la interfaz y documentos','SISTEMA'),(2,'nombre_sistema','Asuras Col','Nombre técnico del sistema','SISTEMA'),(3,'slogan_empresa','Control y confianza en cada registro','Eslogan mostrado en footer, facturas y reportes','SISTEMA'),(4,'version_sistema','1.0.1','Versión actual del sistema','SISTEMA'),(5,'dias_alerta_vencimiento','7','Días de anticipación para generar alerta de vencimiento próximo','INVENTARIO'),(6,'dias_proximo_vencer','30','Días para considerar un producto como próximo a vencer en reportes','INVENTARIO'),(7,'stock_minimo_default','10','Stock mínimo sugerido al registrar un nuevo producto','INVENTARIO'),(8,'stock_maximo_default','100','Stock máximo sugerido al registrar un nuevo producto','INVENTARIO'),(9,'porcentaje_estimacion_pedidos','20','Porcentaje de incremento aplicado al histórico para estimar pedidos en proyecciones','INVENTARIO'),(10,'moneda','COP','Código de moneda usado en facturas y reportes','FACTURACION'),(11,'iva_porcentaje','0','Porcentaje de IVA aplicado en ventas (0 = exento)','FACTURACION'),(12,'items_por_pagina','2','Cantidad de filas por página en todas las tablas','INTERFAZ'),(13,'filas_dashboard','5','Cantidad de movimientos mostrados en el dashboard','INTERFAZ'),(14,'sesion_timeout_minutos','30','Tiempo de inactividad antes de cerrar sesión (referencia visual)','SEGURIDAD'),(15,'intentos_login_max','3','Máximo de intentos fallidos de inicio de sesión (referencia visual)','SEGURIDAD');
/*!40000 ALTER TABLE `configuracion_sistema` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:08:57
