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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `user_type_id` int(11) NOT NULL,
  `birthdate` date NOT NULL,
  `gender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `user_type_fk_idx` (`user_type_id`),
  KEY `gender_fk_idx` (`gender_id`),
  CONSTRAINT `gender_fk` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_type_fk` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('023ea855-8f45-48e8-a1ed-77d0ef6ad3a5','new_caregiver','#new_caregiver#','Dan','Negru',3,'1991-08-05',1),('4d0acc3c-919f-41bb-ac0d-a19b9ed7560f','ion20','i_love_cats','Ion','Ionescu',1,'1992-10-17',1),('89cfd764-5510-4522-82a0-83bf1d1cc387','caregiver3','caregiver3','Joshua','Weissman',3,'1990-10-21',1),('8b98c18f-ddfc-44f1-9476-7a328db7593b','test_t','ileana_love','Fat','Frumos',2,'1990-10-10',1),('b4346cbc-ffee-4c07-bde7-b52062cd0131','new_patient','#new_patient#','Rares','Pavel',2,'1999-01-29',1),('cc8e5854-8d43-4132-8ff6-8aea96d1b687','admin','#admin#','Admin','Admin',4,'2005-10-21',1),('ccc06c0b-39e5-4419-a5b5-e9b0b05b2540','doctor_complex','#Complex_password123','doctor_complex','doctor_complex',1,'1990-10-10',1),('ce8111e6-0c43-4a3f-b9e5-69c8da149bd2','new_admin','#new_admin#','Admin','New',4,'1990-06-05',2),('d2632ee7-68a1-4bd9-81be-841894b063fe','new_doctor','#new_doctor#','Doctor','New',1,'1990-11-04',2),('d27fb1a8-d06d-4721-85c8-6eb4618adda0','red_ross','i_love_rachel','Ionica','al lui Marie',2,'1970-10-21',1),('d64ed515-21b8-4233-889a-4a456c66a6c8','test_p','#test_p#','test_p','test_p',2,'1990-10-10',1),('e6c8a27e-3500-4ac2-b541-b8e032a7302d','new_patient2','#new_patient2#','Diana','Muresan',2,'1990-10-10',2),('f0d5943c-5c47-485a-98b4-860fc56e6e16','alexa','#alexa#','a','a',3,'1990-10-15',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
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
