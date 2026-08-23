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
-- Table structure for table `pedido_compra`
--

DROP TABLE IF EXISTS `pedido_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_compra` (
  `id_pedido` bigint NOT NULL AUTO_INCREMENT,
  `fecha_pedido` datetime(6) DEFAULT NULL,
  `observacion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `total_pedido` decimal(38,2) DEFAULT NULL,
  `estado_pedido_id` bigint DEFAULT NULL,
  `proveedor_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `FKbt6vvu1a7q4aixbsiqcrn3pe3` (`estado_pedido_id`),
  KEY `FKbjix9einfbioq2rftbeufjw1u` (`proveedor_id`),
  CONSTRAINT `FKbjix9einfbioq2rftbeufjw1u` FOREIGN KEY (`proveedor_id`) REFERENCES `proveedor` (`id_proveedor`),
  CONSTRAINT `FKbt6vvu1a7q4aixbsiqcrn3pe3` FOREIGN KEY (`estado_pedido_id`) REFERENCES `estado_pedido` (`id_estado_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_compra`
--

LOCK TABLES `pedido_compra` WRITE;
/*!40000 ALTER TABLE `pedido_compra` DISABLE KEYS */;
INSERT INTO `pedido_compra` VALUES (1,'2026-05-20 22:44:39.586049','El pedido se ha devuelto a proveedor',985000.00,9,2),(2,'2026-05-20 22:52:48.078781','El pedido se ha devuelto a proveedor',47000.00,9,2),(3,'2026-05-20 23:04:15.193481','El pedido se ha devuelto a proveedor',9400.00,9,2),(4,'2026-05-20 23:04:23.180068','El pedido se ha devuelto a proveedor',9400.00,9,2),(5,'2026-05-20 23:04:30.190069','El pedido se ha devuelto a proveedor',9400.00,9,2),(6,'2026-05-20 23:39:33.281723','El pedido fue recibido con exito',72900.00,9,3),(7,'2026-07-01 23:29:46.869095','Mercancía verificada y comprobada por el almacén.',22530.00,6,4),(8,'2026-07-02 21:42:47.414715','se recibio exitosamente la mercsancia',44000.00,6,3),(9,'2026-07-02 22:55:50.948870','',219000.00,7,3),(10,'2026-07-05 22:13:34.398426','',50000.00,6,2),(11,'2026-07-05 22:33:58.900826','',4000.00,6,5),(12,'2026-07-05 23:14:13.961810','RECIBIDO.',66000.00,6,4),(13,'2026-07-05 23:17:34.422558',NULL,152000.00,1,3),(14,'2026-07-10 23:00:55.343190','',2200.00,6,3);
/*!40000 ALTER TABLE `pedido_compra` ENABLE KEYS */;
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
