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
-- Table structure for table `alerta_inv`
--

DROP TABLE IF EXISTS `alerta_inv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alerta_inv` (
  `id_alerta` bigint NOT NULL AUTO_INCREMENT,
  `descripcion_alerta` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `fecha_creacion` datetime(6) DEFAULT NULL,
  `tipo_alerta` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `id_producto` bigint DEFAULT NULL,
  `is_resuelta` tinyint(1) DEFAULT '0',
  `fecha_resolucion` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id_alerta`),
  KEY `FK9gawm12joc57kfp6bcfavvd3l` (`id_producto`),
  CONSTRAINT `FK9gawm12joc57kfp6bcfavvd3l` FOREIGN KEY (`id_producto`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alerta_inv`
--

LOCK TABLES `alerta_inv` WRITE;
/*!40000 ALTER TABLE `alerta_inv` DISABLE KEYS */;
INSERT INTO `alerta_inv` VALUES (4,'El producto Amoxicilina 500mg Cap x10 , venció en la fecha: 2026-06-30T00:00. Stock actual: 100','2026-07-02 00:00:00.207617','PRODUCTO_VENCIDO',77,1,'2026-07-06 02:32:59'),(5,'El producto Amoxicilina 250mg/5ml Susp , venció en la fecha: 2026-05-31T00:00. Stock actual: 50','2026-07-02 00:00:00.236327','PRODUCTO_VENCIDO',78,1,'2026-07-06 02:33:04'),(6,'El producto: Tetracilina 500mg Cap x10 , vence el: 2026-07-06T19:19:29. Stock actual: 30','2026-07-02 00:00:00.259497','PROXIMO_A_VENCER',117,1,'2026-07-06 04:12:44'),(7,'El producto: Eritromicina 500mg Tab x10 , vence el: 2026-07-04T19:19:29. Stock actual: 20','2026-07-02 00:00:00.277627','PROXIMO_A_VENCER',118,1,'2026-07-06 02:53:37'),(8,'El producto: perromol , vence el: 2026-07-02T22:03. Stock actual: 12','2026-07-02 00:00:00.300800','PROXIMO_A_VENCER',120,1,'2026-07-06 03:12:33'),(9,'El producto Eritromicina 500mg Tab x10 , venció en la fecha: 2026-07-04T19:19:29. Stock actual: 20','2026-07-05 00:00:00.261962','PRODUCTO_VENCIDO',118,1,'2026-07-06 02:30:47'),(10,'El producto perromol , venció en la fecha: 2026-07-02T22:03. Stock actual: 12','2026-07-05 00:00:00.282065','PRODUCTO_VENCIDO',120,1,'2026-07-06 01:44:08'),(11,'El productoAmoxicilina 500mg Cap x10 , tiene stock bajo. Stock actual:0, stock minimo: 200','2026-07-05 22:07:42.776973','STOCK_BAJO',77,0,NULL),(12,'El productoAmoxicilina 250mg/5ml Susp , tiene stock bajo. Stock actual:0, stock minimo: 100','2026-07-05 22:07:42.868992','STOCK_BAJO',78,0,NULL),(13,'El productoEritromicina 500mg Tab x10 , tiene stock bajo. Stock actual:0, stock minimo: 100','2026-07-05 22:07:42.903234','STOCK_BAJO',118,1,'2026-07-06 04:14:59'),(14,'El productoperromol , tiene stock bajo. Stock actual:0, stock minimo: 100','2026-07-05 22:07:42.920762','STOCK_BAJO',120,1,'2026-07-06 03:31:47'),(15,'El productoperromol , tiene stock bajo. Stock actual:0, stock minimo: 100','2026-07-05 22:07:42.935780','STOCK_BAJO',122,1,'2026-07-06 03:47:54'),(16,'El producto: perromol , vence el: 2026-07-07T11:47. Stock actual: 200','2026-07-05 23:12:17.287741','PROXIMO_A_VENCER',122,1,'2026-07-06 04:12:32'),(17,'El productoTetracilina 500mg Cap x10 , tiene stock bajo. Stock actual:0, stock minimo: 100','2026-07-09 00:00:00.359493','STOCK_BAJO',117,0,NULL),(18,'El productoIbuprofeno 600mg Tab x10 , tiene stock bajo. Stock actual:0, stock minimo: 350','2026-08-15 00:00:00.817625','STOCK_BAJO',73,0,NULL),(19,'El productoRanitidina 150mg Tab x10 , tiene stock bajo. Stock actual:0, stock minimo: 240','2026-08-15 00:00:00.947589','STOCK_BAJO',90,0,NULL);
/*!40000 ALTER TABLE `alerta_inv` ENABLE KEYS */;
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
