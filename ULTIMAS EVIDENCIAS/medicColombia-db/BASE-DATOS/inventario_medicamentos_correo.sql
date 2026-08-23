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
INSERT INTO `correo` VALUES (1,'ventas@pharmacolombia.co',NULL,2,1),(2,'stiven.robles@gmail.com',1,NULL,2),(3,'byronluma96@gmail.com',3,NULL,2),(4,'ejemplo@gmail.com',1,NULL,1),(5,'ejemplo@gmail.com',NULL,2,3),(6,'ayuda.eduardo12@hotmail.com',NULL,2,2);
/*!40000 ALTER TABLE `correo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:09:01
