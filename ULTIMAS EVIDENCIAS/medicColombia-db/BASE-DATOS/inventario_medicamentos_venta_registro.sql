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
-- Table structure for table `venta_registro`
--

DROP TABLE IF EXISTS `venta_registro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta_registro` (
  `id_venta` bigint NOT NULL AUTO_INCREMENT,
  `fecha_venta` datetime(6) DEFAULT NULL,
  `total_venta` decimal(38,2) DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  `medio_pago` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_venta`),
  KEY `FKq3d4xwe32kguxl0n1q2cgtwty` (`cliente_id`),
  KEY `FKn0q3d6luva7ri2x2xtf7yq79o` (`usuario_id`),
  CONSTRAINT `FKn0q3d6luva7ri2x2xtf7yq79o` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKq3d4xwe32kguxl0n1q2cgtwty` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta_registro`
--

LOCK TABLES `venta_registro` WRITE;
/*!40000 ALTER TABLE `venta_registro` DISABLE KEYS */;
INSERT INTO `venta_registro` VALUES (9,'2026-07-01 19:56:32.815551',6000.00,3,17,'EFECTIVO'),(10,'2026-07-02 21:41:16.882237',26000.00,4,16,'EFECTIVO'),(11,'2026-07-02 22:52:56.622158',12000.00,4,16,'EFECTIVO'),(12,'2026-07-04 23:40:01.544426',1200.00,4,16,'EFECTIVO'),(15,'2026-07-05 09:20:04.337612',2000.00,4,16,'TARJETA'),(16,'2026-07-09 02:06:37.457675',5000.00,3,16,'TARJETA'),(17,'2026-07-10 22:59:33.490509',40000.00,4,16,'EFECTIVO'),(18,'2026-07-10 23:04:44.142861',10000.00,4,16,'TARJETA'),(19,'2026-07-17 00:01:32.634899',5000.00,4,16,'EFECTIVO'),(22,'2026-08-23 13:32:31.928992',44501.50,1,16,'EFECTIVO');
/*!40000 ALTER TABLE `venta_registro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:09:00
