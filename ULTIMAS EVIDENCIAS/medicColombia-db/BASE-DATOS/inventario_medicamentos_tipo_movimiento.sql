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
-- Table structure for table `tipo_movimiento`
--

DROP TABLE IF EXISTS `tipo_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_movimiento` (
  `id_tipo_movimiento` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_movimiento` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `signo` int DEFAULT NULL,
  PRIMARY KEY (`id_tipo_movimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_movimiento`
--

LOCK TABLES `tipo_movimiento` WRITE;
/*!40000 ALTER TABLE `tipo_movimiento` DISABLE KEYS */;
INSERT INTO `tipo_movimiento` VALUES (1,'Entrada de mercancía por factura de proveedor','Compra',1),(2,'Retorno de producto por parte del comprador','Devolución de Cliente',1),(3,'Ajuste manual al encontrar más stock físico del esperado','Nivelación Positiva',1),(4,'Mercancía que llega desde otra bodega o sucursal','Traslado Recibido',1),(5,'Reingreso de producto por cancelación de factura','Anulación de Venta',1),(6,'Salida de producto por transacción comercial','Venta',-1),(7,'Baja de producto por fecha de caducidad superada','Retiro por Vencimiento',-1),(8,'Retiro de stock por averías, ruptura o mal estado','Mercancía Dañada',-1),(9,'Salida de mercancía hacia otra ubicación o tienda','Traslado Enviado',-1),(10,'Ajuste manual por pérdida, robo o error de conteo','Nivelación Negativa',-1),(11,'Uso de insumos para la operación de la clínica/local','Consumo Interno',-1),(12,'Mercancía devuelta al proveedor de origen','Devolución al proveedor',-1);
/*!40000 ALTER TABLE `tipo_movimiento` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:08:56
