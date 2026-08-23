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
INSERT INTO `telefono` VALUES (1,'Llamar despues de las 12PM','3001234567',1,NULL,1),(2,'+57','3043887661',3,NULL,1),(3,'+57','3043887661',NULL,2,1);
/*!40000 ALTER TABLE `telefono` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:08:59
