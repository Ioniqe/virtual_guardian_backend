-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: virtual_angel
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `emergencies`
--

DROP TABLE IF EXISTS `emergencies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `emergencies` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `patient_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `e_patient_fk_idx` (`patient_id`),
  CONSTRAINT `e_patient_fk` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emergencies`
--

LOCK TABLES `emergencies` WRITE;
/*!40000 ALTER TABLE `emergencies` DISABLE KEYS */;
INSERT INTO `emergencies` VALUES (1,'2021-05-21 00:00:00','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(2,'2021-05-29 00:00:00','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(3,'2021-05-31 10:30:44','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(4,'2021-06-01 07:51:09','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(5,'2021-06-01 07:51:57','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(6,'2021-06-01 07:52:11','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(7,'2021-06-01 09:10:23','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(8,'2021-06-01 09:14:34','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(9,'2021-06-01 09:46:14','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(10,'2021-06-03 11:54:21','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(11,'2021-06-03 11:54:44','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(12,'2021-06-03 11:56:08','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(13,'2021-06-14 08:49:57','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(14,'2021-07-02 08:21:16','d27fb1a8-d06d-4721-85c8-6eb4618adda0'),(67,'2021-07-05 17:28:23','b4346cbc-ffee-4c07-bde7-b52062cd0131'),(68,'2021-07-05 17:28:27','e6c8a27e-3500-4ac2-b541-b8e032a7302d'),(69,'2021-07-05 17:28:30','e6c8a27e-3500-4ac2-b541-b8e032a7302d'),(70,'2021-07-05 17:28:35','b4346cbc-ffee-4c07-bde7-b52062cd0131'),(71,'2021-07-05 17:28:43','e6c8a27e-3500-4ac2-b541-b8e032a7302d');
/*!40000 ALTER TABLE `emergencies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-05 21:45:01
