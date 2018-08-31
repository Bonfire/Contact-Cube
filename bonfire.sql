-- MySQL dump 10.13  Distrib 8.0.12, for macos10.13 (x86_64)
--
-- Host: localhost    Database: bonfire
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `contacts` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Michael','Harris','3400 Boardwalk Drive','Winter Park','FL','32792','8503719988',NULL,1),(2,'Michael','Harris','3400 Boardwalk Drive','Winter Park','FL','32792','8503719988',NULL,2),(3,'Michael','Harris','3400 Boardwalk Drive','Winter Park','FL','32792','8503719988',NULL,3),(4,'Lucas','Gonzalez',NULL,NULL,'FL',NULL,NULL,NULL,2),(5,'Juan','Zambrano',NULL,NULL,'FL',NULL,NULL,NULL,2),(6,'Matthew','Balwant',NULL,NULL,'FL',NULL,NULL,NULL,2),(7,'Mikal','Young',NULL,NULL,'FL',NULL,NULL,NULL,2),(8,'Blake','Wyatt',NULL,NULL,'FL',NULL,NULL,NULL,2),(9,'John','Williams',NULL,NULL,'AL',NULL,NULL,NULL,1),(10,'Stacy','Davis',NULL,NULL,'GA',NULL,NULL,NULL,1),(11,'Robert','Miller','51 Green Bush Ave','Margate','FL','33063','9549725652',NULL,1),(12,'Mary','Wilson','1000 Brookhaven','Coral Springs','FL','33065','4078824743',NULL,1),(13,'Chris','Lynn','100 SR-50','Orlando','FL','32789','5827521646','clynn@gtcom.net',3),(14,'Will','Brun','354 Restful Drive','Orlando','FL','32787','9014837336','will@gmail.com',3);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dateCreated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `dateLastLoggedIn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `firstName` varchar(50) NOT NULL,
  `lastName` varchar(50) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` char(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2018-08-26 12:00:00','2018-08-31 12:31:10','Rick','Leinecker','rick','4914EB016456C4E6E305965C0501864601003161BA6BAF760E7031A36BB10B99'),(2,'2018-08-26 12:00:00','2018-08-31 12:50:45','Bonfire','User','user','536ADEE40DB561FF9C3933B6946B7AD38EDBD87A058423EFF0B6583BCCE6514F'),(3,'2018-08-31 01:48:05','2018-08-31 01:48:05','Demo','Account','demo','4B5076657E2B30EBEAB5E2305F1519F3D197B09ACF03686452720B9A0D574EC0');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-31  1:55:19
