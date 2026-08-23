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
INSERT INTO `detalle_proveedor_producto` VALUES (440.00,67,3),(420.00,67,4),(710.00,68,3),(690.00,68,4),(3100.00,69,7),(2950.00,69,9),(610.00,70,3),(590.00,70,4),(1580.00,71,10),(1620.00,71,11),(730.00,72,3),(760.00,72,4),(980.00,73,3),(950.00,73,4),(690.00,74,3),(720.00,74,6),(810.00,75,4),(830.00,75,6),(1180.00,76,6),(1160.00,76,10),(1520.00,77,3),(1550.00,77,4),(6200.00,78,4),(2680.00,79,10),(2740.00,79,11),(2180.00,80,4),(2140.00,80,10),(1360.00,81,4),(1980.00,82,3),(1930.00,82,4),(590.00,83,3),(620.00,83,4),(2850.00,84,7),(720.00,85,3),(710.00,85,4),(1650.00,86,10),(920.00,87,3),(880.00,87,4),(720.00,88,3),(690.00,88,4),(1010.00,89,3),(980.00,89,4),(520.00,90,3),(2400.00,91,7),(1980.00,92,4),(910.00,93,3),(880.00,93,4),(640.00,94,4),(770.00,95,6),(980.00,96,6),(700.00,97,2),(430.00,97,7),(820.00,98,7),(790.00,98,8),(650.00,99,8),(930.00,100,8),(410.00,101,8),(780.00,102,8),(750.00,103,3),(720.00,103,4),(960.00,104,4),(540.00,105,4),(1450.00,106,10),(2600.00,107,10),(4200.00,108,10),(3850.00,109,10),(2800.00,110,12),(3300.00,111,9),(3400.00,111,12),(1900.00,112,9),(1850.00,112,12),(620.00,113,12),(1450.00,114,12),(1850.00,115,12),(5100.00,116,9),(5200.00,116,12),(1180.00,117,4),(1320.00,118,4),(500.00,120,2),(20.00,122,5);
/*!40000 ALTER TABLE `detalle_proveedor_producto` ENABLE KEYS */;
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
