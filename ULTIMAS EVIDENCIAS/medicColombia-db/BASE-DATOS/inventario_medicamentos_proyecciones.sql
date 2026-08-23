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
-- Table structure for table `proyecciones`
--

DROP TABLE IF EXISTS `proyecciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proyecciones` (
  `id_proyecciones` bigint NOT NULL AUTO_INCREMENT,
  `fecha_fin` datetime(6) DEFAULT NULL,
  `fecha_generacion` datetime(6) DEFAULT NULL,
  `fecha_inicio` datetime(6) DEFAULT NULL,
  `pedidos_estimados` int DEFAULT NULL,
  `referencia_tipo` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `resultado_proyeccion` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `unidad_medida` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `categoria_id` bigint DEFAULT NULL,
  `metodo_proyeccion_id` bigint DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  `tipo_proyeccion_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id_proyecciones`),
  KEY `FK797huv4e980522a03cyhyq6fa` (`categoria_id`),
  KEY `FKkj9js3pyn8n97gyhscd2f2jwq` (`metodo_proyeccion_id`),
  KEY `FKmw4g0jhq595oib8nxxr80io5y` (`producto_id`),
  KEY `FKq8ige04jmwbj9h189rgvum7et` (`tipo_proyeccion_id`),
  CONSTRAINT `FK797huv4e980522a03cyhyq6fa` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id_categoria`),
  CONSTRAINT `FKkj9js3pyn8n97gyhscd2f2jwq` FOREIGN KEY (`metodo_proyeccion_id`) REFERENCES `metodo_proyeccion` (`id_metodo_proyeccion`),
  CONSTRAINT `FKmw4g0jhq595oib8nxxr80io5y` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id_producto`),
  CONSTRAINT `FKq8ige04jmwbj9h189rgvum7et` FOREIGN KEY (`tipo_proyeccion_id`) REFERENCES `tipo_proyeccion` (`id_tipo_proyeccion`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecciones`
--

LOCK TABLES `proyecciones` WRITE;
/*!40000 ALTER TABLE `proyecciones` DISABLE KEYS */;
INSERT INTO `proyecciones` VALUES (1,'2026-07-12 23:59:00.000000','2026-07-12 21:40:07.631758','2026-06-12 00:00:00.000000',132,'PRODUCTO_MAS_VENDIDO','25 unidades vendidas en el período','unidades',NULL,2,67,1),(2,'2026-07-12 23:59:00.000000','2026-07-12 21:40:07.780999','2026-06-12 00:00:00.000000',120,'PRODUCTO_MAS_VENDIDO','12 unidades vendidas en el período','unidades',NULL,2,77,1),(3,'2026-07-12 23:59:00.000000','2026-07-12 21:40:07.806460','2026-06-12 00:00:00.000000',0,'PRODUCTO_MAS_VENDIDO','5 unidades vendidas en el período','unidades',NULL,2,68,1),(4,'2026-07-12 23:59:00.000000','2026-07-12 21:40:07.828039','2026-06-12 00:00:00.000000',0,'PRODUCTO_MAS_VENDIDO','4 unidades vendidas en el período','unidades',NULL,2,97,1),(5,'2026-07-12 23:59:00.000000','2026-07-12 21:40:07.847045','2026-06-12 00:00:00.000000',0,'PRODUCTO_MAS_VENDIDO','3 unidades vendidas en el período','unidades',NULL,2,75,1),(6,'2026-07-12 23:59:00.000000','2026-07-12 21:41:54.329128','2026-04-13 00:00:00.000000',0,'RETIRO_VENCIMIENTO','112 unidades retiradas por vencimiento','unidades',NULL,3,120,3),(7,'2026-07-12 23:59:00.000000','2026-07-12 21:41:54.337939','2026-04-13 00:00:00.000000',0,'RETIRO_VENCIMIENTO','201 unidades retiradas por vencimiento','unidades',NULL,3,122,3),(8,'2026-07-12 23:59:00.000000','2026-07-12 21:41:54.344352','2026-04-13 00:00:00.000000',0,'RETIRO_VENCIMIENTO','50 unidades retiradas por vencimiento','unidades',NULL,3,118,3),(9,'2026-07-12 23:59:00.000000','2026-07-12 21:41:54.350765','2026-04-13 00:00:00.000000',0,'RETIRO_VENCIMIENTO','35 unidades retiradas por vencimiento','unidades',NULL,3,117,3);
/*!40000 ALTER TABLE `proyecciones` ENABLE KEYS */;
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
