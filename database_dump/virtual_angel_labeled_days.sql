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
-- Table structure for table `labeled_days`
--

DROP TABLE IF EXISTS `labeled_days`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `labeled_days` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date NOT NULL,
  `label` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labeled_days`
--

LOCK TABLES `labeled_days` WRITE;
/*!40000 ALTER TABLE `labeled_days` DISABLE KEYS */;
INSERT INTO `labeled_days` VALUES (1,'2019-11-06','anomalous'),(2,'2019-11-07','normal'),(3,'2019-11-08','normal'),(4,'2019-11-09','normal'),(5,'2019-11-10','normal'),(6,'2019-11-11','normal'),(7,'2019-11-12','anomalous'),(8,'2019-11-13','normal'),(9,'2019-11-14','normal'),(10,'2019-11-15','normal'),(11,'2019-11-16','normal'),(12,'2019-11-17','anomalous'),(13,'2019-11-18','normal'),(14,'2019-11-19','normal'),(15,'2019-11-20','normal'),(16,'2019-11-21','normal'),(17,'2019-11-22','normal'),(18,'2019-11-23','normal'),(19,'2019-11-24','anomalous'),(20,'2019-11-25','normal'),(21,'2019-11-26','normal'),(22,'2019-11-27','normal'),(23,'2019-11-28','normal'),(24,'2019-11-29','normal'),(25,'2019-11-30','normal'),(26,'2019-12-01','anomalous'),(27,'2019-12-02','normal'),(28,'2019-12-03','normal'),(29,'2019-12-04','normal'),(30,'2019-12-05','normal'),(31,'2019-12-06','normal'),(32,'2019-12-07','normal'),(33,'2019-12-08','normal'),(34,'2019-12-09','anomalous'),(35,'2019-12-10','normal'),(36,'2019-12-11','normal');
/*!40000 ALTER TABLE `labeled_days` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-05 21:44:59
