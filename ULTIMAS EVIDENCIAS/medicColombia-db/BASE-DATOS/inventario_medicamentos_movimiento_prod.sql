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
-- Table structure for table `movimiento_prod`
--

DROP TABLE IF EXISTS `movimiento_prod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movimiento_prod` (
  `id_movimiento_inv` bigint NOT NULL AUTO_INCREMENT,
  `cantidad_desplazada` int DEFAULT NULL,
  `fecha_movimiento` datetime(6) DEFAULT NULL,
  `motivo_repor` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `picker_checker` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `tipo_movimiento_id` bigint DEFAULT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_movimiento_inv`),
  KEY `FKe4x52frf9bfkwv1r3b5c4ssvj` (`producto_id`),
  KEY `FKddpku4stfidlen6u8dah4x8u1` (`tipo_movimiento_id`),
  KEY `FKcdj4kctpo1hgljymu5hsbv9wk` (`usuario_id`),
  CONSTRAINT `FKcdj4kctpo1hgljymu5hsbv9wk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id_usuario`),
  CONSTRAINT `FKddpku4stfidlen6u8dah4x8u1` FOREIGN KEY (`tipo_movimiento_id`) REFERENCES `tipo_movimiento` (`id_tipo_movimiento`),
  CONSTRAINT `FKe4x52frf9bfkwv1r3b5c4ssvj` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimiento_prod`
--

LOCK TABLES `movimiento_prod` WRITE;
/*!40000 ALTER TABLE `movimiento_prod` DISABLE KEYS */;
INSERT INTO `movimiento_prod` VALUES (61,3,'2026-07-01 19:54:59.186326','Salida por venta #8','VENTA-8',67,6,16),(62,1,'2026-07-01 19:56:32.847386','Salida por venta #9','VENTA-9',75,6,17),(63,5,'2026-07-02 00:01:27.571834','Entrada por pedido #7','PRES7P67-70-82-117-A1-C5-5-6-5',67,1,16),(64,5,'2026-07-02 00:01:27.582939','Entrada por pedido #7','PRES7P67-70-82-117-A1-C5-5-6-5',70,1,16),(65,6,'2026-07-02 00:01:27.590335','Entrada por pedido #7','PRES7P67-70-82-117-A1-C5-5-6-5',82,1,16),(66,5,'2026-07-02 00:01:27.595094','Entrada por pedido #7','PRES7P67-70-82-117-A1-C5-5-6-5',117,1,16),(67,4,'2026-07-02 21:41:16.915421','Salida por venta #10','VENTA-10',97,6,16),(68,1,'2026-07-02 21:41:16.928970','Salida por venta #10','VENTA-10',67,6,16),(69,100,'2026-07-02 21:43:55.705360','Entrada por pedido #8','PRES8P67-A1-C100',67,1,16),(70,4,'2026-07-02 22:52:56.643553','Salida por venta #11','VENTA-11',67,6,16),(71,1,'2026-07-02 22:52:56.653478','Salida por venta #11','VENTA-11',75,6,16),(72,300,'2026-07-02 22:57:02.235022','Entrada por pedido #9','PRES9P72-A1-C300',72,1,16),(73,12,'2026-07-04 23:40:01.571281','Salida por venta #12','VENTA-12',77,6,16),(74,1,'2026-07-05 09:20:04.569039','Salida por venta #15','VENTA-15',68,6,16),(75,12,'2026-07-05 21:52:09.465942','Retiro automático por vencimiento',NULL,120,7,1),(76,1,'2026-07-05 21:52:09.474354','Retiro automático por vencimiento',NULL,122,7,1),(77,100,'2026-07-05 22:13:40.648397','Entrada por pedido #10','PRES10P120-A1-C100',120,1,16),(78,200,'2026-07-05 22:34:03.520888','Entrada por pedido #11','PRES11P122-A1-C200',122,1,16),(79,100,'2026-07-05 23:12:17.391550','Retiro automático por vencimiento',NULL,120,7,1),(80,50,'2026-07-05 23:14:25.603305','Entrada por pedido #12','PRES12P118-A1-C50',118,1,16),(81,50,'2026-07-06 00:00:00.072896','Retiro automático por vencimiento',NULL,118,7,1),(82,35,'2026-07-09 00:00:00.097630','Retiro automático por vencimiento',NULL,117,7,1),(83,200,'2026-07-09 00:00:00.127278','Retiro automático por vencimiento',NULL,122,7,1),(84,1,'2026-07-09 02:06:37.496744','Salida por venta #16','VENTA-16',75,6,16),(85,4,'2026-07-10 22:59:33.626394','Salida por venta #17','VENTA-17',68,6,16),(86,5,'2026-07-10 23:01:02.417327','Entrada por pedido #14','PRES14P67-A1-C5',67,1,16),(87,20,'2026-07-10 23:04:44.169580','Salida por venta #18','VENTA-18',67,6,16),(88,1,'2026-07-17 00:01:32.711667','Salida por venta #19','VENTA-19',75,6,16),(89,180,'2026-08-15 00:00:00.225344','Retiro automático por vencimiento',NULL,73,7,1),(90,120,'2026-08-15 00:00:00.416344','Retiro automático por vencimiento',NULL,90,7,1),(93,3,'2026-08-23 13:32:31.981025','Salida por venta #22','VENTA-22',67,6,16),(94,1,'2026-08-23 13:32:32.002790','Salida por venta #22','VENTA-22',68,6,16);
/*!40000 ALTER TABLE `movimiento_prod` ENABLE KEYS */;
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
