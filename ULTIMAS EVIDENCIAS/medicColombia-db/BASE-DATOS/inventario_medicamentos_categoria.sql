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
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id_categoria` bigint NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `nombre_cat` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (2,'Sustancias para combatir infecciones bacterianas.','Antibióticos'),(3,'Reducen la inflamación y el dolor asociado.','Antiinflamatorios'),(4,'Utilizados para reducir la fiebre.','Antipiréticos'),(5,'Tratamiento para reacciones alérgicas.','Antihistamínicos'),(6,'Neutralizan la acidez estomacal.','Antiácidos'),(7,'Controlan y detienen la diarrea.','Antidiarréicos'),(8,'Facilitan la evacuación intestinal.','Laxantes'),(9,'Medicamentos para calmar la tos seca.','Antitusivos'),(10,'Ayudan a eliminar el exceso de moco en vías respiratorias.','Mucolíticos'),(11,'Facilitan la expulsión de secreciones bronquiales.','Expectorantes'),(12,'Sustancias para prevenir infecciones en heridas superficiales.','Antisépticos'),(13,'Tratamiento contra infecciones causadas por hongos.','Antifúngicos'),(14,'Medicamentos para combatir infecciones por virus.','Antivirales'),(15,'Reducen la ansiedad y estados de nerviosismo.','Ansiolíticos'),(16,'Tratamiento para trastornos de la depresión.','Antidepresivos'),(17,'Controlan la presión arterial alta.','Antihipertensivos'),(18,'Regulan los niveles de azúcar en la sangre.','Antidiabéticos'),(19,'Previenen la formación de coágulos sanguíneos.','Anticoagulantes'),(20,'Abren las vías respiratorias en pacientes con asma o EPOC.','Broncodilatadores'),(21,'Potentes antiinflamatorios hormonales.','Corticosteroides'),(22,'Ayudan a eliminar el exceso de líquido del cuerpo.','Diuréticos'),(23,'Complementos para cubrir deficiencias nutricionales.','Vitaminas y Suplementos'),(24,'Suplementos como calcio, hierro y magnesio.','Minerales'),(25,'Cremas y ungüentos para afecciones de la piel.','Dermatológicos'),(26,'Gotas y pomadas para el cuidado de los ojos.','Oftalmológicos'),(27,'Gotas para el tratamiento de afecciones del oído.','Óticos'),(28,'Medicamentos específicos para la salud femenina.','Ginecológicos'),(29,'Tratamientos para el sistema urinario.','Urológicos'),(30,'Relajan los espasmos musculares internos.','Antiespasmódicos'),(31,'Protegen la mucosa del estómago.','Protectores Gástricos'),(32,'Reducen los niveles de colesterol y triglicéridos.','Hipolipemiantes'),(33,'Tratan contracturas y dolores musculares intensos.','Relajantes Musculares'),(34,'Bloquean el dolor en una zona específica.','Anestésicos Locales'),(35,'Previenen o detienen las náuseas y el vómito.','Antieméticos'),(36,'Tratamientos para desequilibrios del sistema endocrino.','Hormonales'),(37,'Reducen la actividad del sistema inmunitario.','Inmunosupresores'),(38,'Medicamentos que inducen el sueño o calma profunda.','Sedantes'),(39,'Medicamentos generales para la salud del corazón.','Cardiovasculares'),(40,'Métodos hormonales para prevenir el embarazo.','Anticonceptivos'),(41,'Medicamentos con dosis y fórmulas para niños.','Pediatricos'),(42,'Medicamentos especializados para adultos mayores.','Geriatricos'),(43,'Productos de medicina alternativa.','Homeopáticos'),(44,'Medicamentos a base de extractos de plantas.','Fitoterapéuticos'),(45,'Combinaciones de varias vitaminas en un solo producto.','Multivitamínicos'),(46,'Soluciones para recuperar electrolitos.','Sueros Rehidratantes'),(47,'Eliminan parásitos internos o externos.','Antiparasitarios'),(48,'Tratamientos específicos para candidiasis y otros.','Antimicóticos Vaginales'),(49,'Estimulantes del sistema nervioso central.','Analépticos'),(50,'Medicamentos que actúan sobre el estado mental (Controlados).','Psicotrópicos'),(51,'Medicamentos para el alivio del dolor leve a moderado.','Analgésicos'),(52,'Materiales de curación y elementos médicos.','Insumos médicos');
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-23 14:09:03
