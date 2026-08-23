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
-- Table structure for table `detalle_filtro`
--

DROP TABLE IF EXISTS `detalle_filtro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_filtro` (
  `id_detalle_filtro` bigint NOT NULL AUTO_INCREMENT,
  `campo_filtro` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tipo_dato` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `valor_filtro` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `filtro_busqueda_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_detalle_filtro`),
  KEY `FK5jh79sbqxlvyygjsq3i5m0pyi` (`filtro_busqueda_id`),
  CONSTRAINT `FK5jh79sbqxlvyygjsq3i5m0pyi` FOREIGN KEY (`filtro_busqueda_id`) REFERENCES `filtro_busqueda` (`id_filtro_busqueda`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_filtro`
--

LOCK TABLES `detalle_filtro` WRITE;
/*!40000 ALTER TABLE `detalle_filtro` DISABLE KEYS */;
INSERT INTO `detalle_filtro` VALUES (1,'Proveedor','cantidad','fecha superior a 1 semestre',1),(2,'fechaExpiracion','fecha','2026-07-31',2),(3,'fechaInicio','DATE','2026-06-01T01:43',3),(4,'FechaFin','DATE','2026-06-30T01:43',3),(5,'fechaInicio','DATE','2026-07-10T23:03',4),(6,'FechaFin','DATE','2026-07-10T23:03',4),(7,'fechaInicio','DATE','2026-07-10T00:06',5),(8,'FechaFin','DATE','2026-07-10T23:59',5);
/*!40000 ALTER TABLE `detalle_filtro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:08:55
