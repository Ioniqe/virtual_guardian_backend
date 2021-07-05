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
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `patient` (
  `user_id` varchar(45) NOT NULL,
  `caregiver_user_id` varchar(45) DEFAULT NULL,
  `doctor_user_id` varchar(45) NOT NULL,
  `address` varchar(150) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `p_doctor_id_idx` (`doctor_user_id`),
  KEY `p_caregiver_id_idx` (`caregiver_user_id`),
  CONSTRAINT `p_caregiver_id` FOREIGN KEY (`caregiver_user_id`) REFERENCES `caregiver` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `p_doctor_id` FOREIGN KEY (`doctor_user_id`) REFERENCES `doctor` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `p_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES ('8b98c18f-ddfc-44f1-9476-7a328db7593b','f0d5943c-5c47-485a-98b4-860fc56e6e16','4d0acc3c-919f-41bb-ac0d-a19b9ed7560f','str. Zmeilor'),('b4346cbc-ffee-4c07-bde7-b52062cd0131','023ea855-8f45-48e8-a1ed-77d0ef6ad3a5','d2632ee7-68a1-4bd9-81be-841894b063fe','str. Zapezii nr. 23'),('d27fb1a8-d06d-4721-85c8-6eb4618adda0','89cfd764-5510-4522-82a0-83bf1d1cc387','4d0acc3c-919f-41bb-ac0d-a19b9ed7560f','str. Central perk, nr.15'),('e6c8a27e-3500-4ac2-b541-b8e032a7302d','023ea855-8f45-48e8-a1ed-77d0ef6ad3a5','d2632ee7-68a1-4bd9-81be-841894b063fe','str. Maranului nr. 45');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-05 21:45:02
