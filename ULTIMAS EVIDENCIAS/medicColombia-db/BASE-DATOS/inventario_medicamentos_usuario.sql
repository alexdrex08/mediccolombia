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
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `correo_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nombre_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `rol_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `identificacion_usu` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `foto_perfil` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UKas5mdu7ejl9ytn6gvs9srfcwd` (`correo_usu`),
  UNIQUE KEY `identificacion_usu` (`identificacion_usu`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'1','1','SISTEMA','ADMIN','1',NULL),(16,'$2a$10$xzWWKrEiR8Xb3KsWZqhgMOJnkRLs5D.H9mwgrmdu58F2lxz7Qgw.y','admin@mediccolombia.com','ADMINISTRADOR GENERAL','ADMIN','1193643784','/uploads/perfil/perfil_16_1787500774376.jpg'),(17,'$2a$10$lou3YhhS3Je2thhAp9Aof.EmsVNkdy7uK4HcfTQHQ3XHj2q7FMaSm','sebasUru69@gmail.com','Sebastian Urueta','EMPLEADO','1048223645',NULL),(18,'$2a$10$MEZLA7PkaGhBegO5OJTp7.5pWNb7YZzZuEwsbzCFBRKBGh.jOcBdy','carlos@gmail.com','Carlos ','EMPLEADO','1222222','/uploads/perfil/perfil_18_1787500880757.png');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
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
