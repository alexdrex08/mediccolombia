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
INSERT INTO `barrio_direccion` VALUES (1,'El Prado (Barranquilla)'),(2,'Alto Prado (Barranquilla)'),(3,'Riomar (Barranquilla)'),(4,'Ciudad Jardín (Barranquilla)'),(5,'Boston (Barranquilla)'),(6,'Recreo (Barranquilla)'),(7,'Barrio Abajo (Barranquilla)'),(8,'El Rosario (Barranquilla)'),(9,'Centro (Barranquilla)'),(10,'San Roque (Barranquilla)'),(11,'Chiquinquirá (Barranquilla)'),(12,'San Felipe (Barranquilla)'),(13,'Olaya (Barranquilla)'),(14,'La Victoria (Barranquilla)'),(15,'San José (Barranquilla)'),(16,'Cevillar (Barranquilla)'),(17,'La Unión (Barranquilla)'),(18,'Las Nieves (Barranquilla)'),(19,'Simón Bolívar (Barranquilla)'),(20,'Miramar (Barranquilla)'),(21,'Tabor (Barranquilla)'),(22,'Villa Santos (Barranquilla)'),(23,'Las Flores (Barranquilla)'),(24,'Siape (Barranquilla)'),(25,'La Playa (Barranquilla)'),(26,'El Centenario (Soledad)'),(27,'Hipódromo (Soledad)'),(28,'Costa Hermosa (Soledad)'),(29,'Los Cusules (Soledad)'),(30,'Villa Katanga (Soledad)'),(31,'Ciudadela Metropolitana (Soledad)'),(32,'Los Almendros (Soledad)'),(33,'Las Trinitarias (Soledad)'),(34,'Soledad 2000 (Soledad)'),(35,'Manuela Beltrán (Soledad)'),(36,'Las Gaviotas (Soledad)'),(37,'Villa Muvdi (Soledad)'),(38,'La Central (Soledad)'),(39,'El Parque (Soledad)'),(40,'Juan Domínguez Romero (Soledad)');
/*!40000 ALTER TABLE `barrio_direccion` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:08:58
