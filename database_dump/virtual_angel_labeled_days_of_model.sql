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
-- Table structure for table `labeled_days_of_model`
--

DROP TABLE IF EXISTS `labeled_days_of_model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `labeled_days_of_model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `day` date NOT NULL,
  `label` varchar(45) NOT NULL,
  `ml_variable_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `ldom_ml_variable_fk_idx` (`ml_variable_id`),
  CONSTRAINT `ldom_ml_variable_fk` FOREIGN KEY (`ml_variable_id`) REFERENCES `ml_variables` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=505 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `labeled_days_of_model`
--

LOCK TABLES `labeled_days_of_model` WRITE;
/*!40000 ALTER TABLE `labeled_days_of_model` DISABLE KEYS */;
INSERT INTO `labeled_days_of_model` VALUES (289,'2019-11-06','anomalous',28),(290,'2019-11-07','normal',28),(291,'2019-11-08','normal',28),(292,'2019-11-09','normal',28),(293,'2019-11-10','normal',28),(294,'2019-11-11','normal',28),(295,'2019-11-12','anomalous',28),(296,'2019-11-13','normal',28),(297,'2019-11-14','normal',28),(298,'2019-11-15','normal',28),(299,'2019-11-16','normal',28),(300,'2019-11-17','anomalous',28),(301,'2019-11-18','normal',28),(302,'2019-11-19','normal',28),(303,'2019-11-20','normal',28),(304,'2019-11-21','normal',28),(305,'2019-11-22','normal',28),(306,'2019-11-23','normal',28),(307,'2019-11-24','anomalous',28),(308,'2019-11-25','normal',28),(309,'2019-11-26','normal',28),(310,'2019-11-27','normal',28),(311,'2019-11-28','normal',28),(312,'2019-11-29','normal',28),(313,'2019-11-30','normal',28),(314,'2019-12-01','anomalous',28),(315,'2019-12-02','normal',28),(316,'2019-12-03','normal',28),(317,'2019-12-04','normal',28),(318,'2019-12-05','anomalous',28),(319,'2019-12-06','normal',28),(320,'2019-12-07','normal',28),(321,'2019-12-08','normal',28),(322,'2019-12-09','anomalous',28),(323,'2019-12-10','normal',28),(324,'2019-12-11','normal',28),(325,'2019-11-06','anomalous',29),(326,'2019-11-07','normal',29),(327,'2019-11-08','normal',29),(328,'2019-11-09','normal',29),(329,'2019-11-10','normal',29),(330,'2019-11-11','normal',29),(331,'2019-11-12','anomalous',29),(332,'2019-11-13','normal',29),(333,'2019-11-14','normal',29),(334,'2019-11-15','normal',29),(335,'2019-11-16','normal',29),(336,'2019-11-17','anomalous',29),(337,'2019-11-18','normal',29),(338,'2019-11-19','normal',29),(339,'2019-11-20','normal',29),(340,'2019-11-21','normal',29),(341,'2019-11-22','normal',29),(342,'2019-11-23','normal',29),(343,'2019-11-24','anomalous',29),(344,'2019-11-25','normal',29),(345,'2019-11-26','normal',29),(346,'2019-11-27','normal',29),(347,'2019-11-28','normal',29),(348,'2019-11-29','normal',29),(349,'2019-11-30','normal',29),(350,'2019-12-01','anomalous',29),(351,'2019-12-02','normal',29),(352,'2019-12-03','normal',29),(353,'2019-12-04','normal',29),(354,'2019-12-05','normal',29),(355,'2019-12-06','normal',29),(356,'2019-12-07','normal',29),(357,'2019-12-08','normal',29),(358,'2019-12-09','anomalous',29),(359,'2019-12-10','normal',29),(360,'2019-12-11','normal',29),(361,'2019-11-06','anomalous',30),(362,'2019-11-07','normal',30),(363,'2019-11-08','normal',30),(364,'2019-11-09','normal',30),(365,'2019-11-10','normal',30),(366,'2019-11-11','normal',30),(367,'2019-11-12','anomalous',30),(368,'2019-11-13','normal',30),(369,'2019-11-14','normal',30),(370,'2019-11-15','normal',30),(371,'2019-11-16','normal',30),(372,'2019-11-17','anomalous',30),(373,'2019-11-18','normal',30),(374,'2019-11-19','normal',30),(375,'2019-11-20','normal',30),(376,'2019-11-21','normal',30),(377,'2019-11-22','normal',30),(378,'2019-11-23','normal',30),(379,'2019-11-24','anomalous',30),(380,'2019-11-25','normal',30),(381,'2019-11-26','normal',30),(382,'2019-11-27','normal',30),(383,'2019-11-28','normal',30),(384,'2019-11-29','normal',30),(385,'2019-11-30','normal',30),(386,'2019-12-01','anomalous',30),(387,'2019-12-02','normal',30),(388,'2019-12-03','normal',30),(389,'2019-12-04','normal',30),(390,'2019-12-05','normal',30),(391,'2019-12-06','normal',30),(392,'2019-12-07','normal',30),(393,'2019-12-08','normal',30),(394,'2019-12-09','anomalous',30),(395,'2019-12-10','normal',30),(396,'2019-12-11','normal',30),(397,'2019-11-06','anomalous',31),(398,'2019-11-07','normal',31),(399,'2019-11-08','normal',31),(400,'2019-11-09','normal',31),(401,'2019-11-10','normal',31),(402,'2019-11-11','normal',31),(403,'2019-11-12','anomalous',31),(404,'2019-11-13','normal',31),(405,'2019-11-14','normal',31),(406,'2019-11-15','normal',31),(407,'2019-11-16','normal',31),(408,'2019-11-17','anomalous',31),(409,'2019-11-18','normal',31),(410,'2019-11-19','normal',31),(411,'2019-11-20','normal',31),(412,'2019-11-21','normal',31),(413,'2019-11-22','normal',31),(414,'2019-11-23','normal',31),(415,'2019-11-24','anomalous',31),(416,'2019-11-25','normal',31),(417,'2019-11-26','normal',31),(418,'2019-11-27','normal',31),(419,'2019-11-28','normal',31),(420,'2019-11-29','normal',31),(421,'2019-11-30','normal',31),(422,'2019-12-01','anomalous',31),(423,'2019-12-02','normal',31),(424,'2019-12-03','normal',31),(425,'2019-12-04','normal',31),(426,'2019-12-05','normal',31),(427,'2019-12-06','normal',31),(428,'2019-12-07','normal',31),(429,'2019-12-08','normal',31),(430,'2019-12-09','anomalous',31),(431,'2019-12-10','normal',31),(432,'2019-12-11','normal',31),(433,'2019-11-06','anomalous',32),(434,'2019-11-07','normal',32),(435,'2019-11-08','normal',32),(436,'2019-11-09','normal',32),(437,'2019-11-10','normal',32),(438,'2019-11-11','normal',32),(439,'2019-11-12','anomalous',32),(440,'2019-11-13','normal',32),(441,'2019-11-14','normal',32),(442,'2019-11-15','normal',32),(443,'2019-11-16','normal',32),(444,'2019-11-17','anomalous',32),(445,'2019-11-18','normal',32),(446,'2019-11-19','normal',32),(447,'2019-11-20','normal',32),(448,'2019-11-21','normal',32),(449,'2019-11-22','normal',32),(450,'2019-11-23','normal',32),(451,'2019-11-24','anomalous',32),(452,'2019-11-25','normal',32),(453,'2019-11-26','normal',32),(454,'2019-11-27','normal',32),(455,'2019-11-28','normal',32),(456,'2019-11-29','normal',32),(457,'2019-11-30','normal',32),(458,'2019-12-01','anomalous',32),(459,'2019-12-02','normal',32),(460,'2019-12-03','normal',32),(461,'2019-12-04','normal',32),(462,'2019-12-05','normal',32),(463,'2019-12-06','normal',32),(464,'2019-12-07','normal',32),(465,'2019-12-08','normal',32),(466,'2019-12-09','anomalous',32),(467,'2019-12-10','normal',32),(468,'2019-12-11','normal',32),(469,'2019-11-06','anomalous',33),(470,'2019-11-07','normal',33),(471,'2019-11-08','normal',33),(472,'2019-11-09','normal',33),(473,'2019-11-10','normal',33),(474,'2019-11-11','normal',33),(475,'2019-11-12','anomalous',33),(476,'2019-11-13','normal',33),(477,'2019-11-14','normal',33),(478,'2019-11-15','normal',33),(479,'2019-11-16','normal',33),(480,'2019-11-17','anomalous',33),(481,'2019-11-18','normal',33),(482,'2019-11-19','normal',33),(483,'2019-11-20','normal',33),(484,'2019-11-21','normal',33),(485,'2019-11-22','normal',33),(486,'2019-11-23','normal',33),(487,'2019-11-24','anomalous',33),(488,'2019-11-25','normal',33),(489,'2019-11-26','normal',33),(490,'2019-11-27','normal',33),(491,'2019-11-28','normal',33),(492,'2019-11-29','normal',33),(493,'2019-11-30','normal',33),(494,'2019-12-01','anomalous',33),(495,'2019-12-02','normal',33),(496,'2019-12-03','normal',33),(497,'2019-12-04','normal',33),(498,'2019-12-05','normal',33),(499,'2019-12-06','normal',33),(500,'2019-12-07','normal',33),(501,'2019-12-08','normal',33),(502,'2019-12-09','anomalous',33),(503,'2019-12-10','normal',33),(504,'2019-12-11','normal',33);
/*!40000 ALTER TABLE `labeled_days_of_model` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-05 21:44:58
