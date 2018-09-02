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
  `state` varchar(24) DEFAULT NULL,
  `zip` varchar(10) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `email` varchar(254) DEFAULT NULL,
  `userID` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userID` (`userID`),
  CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'Michael','Harris','10257 Newton Avenue','Springfield','Louisiana','25815','9043801830','Michael@MHarris.us',2),(2,'Michael','Jones','4652 Elysian Street','Springfield','Tennessee','49059','9365962162','Michael@MJones.us',1),(3,'Michael','Sizemore','3669 Orange Lane','Fairview','Georgia','36753','6922365843','Michael@MSizemore.com',1),(4,'Michael','McKenzie','1052 Blue Place','Springfield','Ohio','27527','4073714241','Michael@MMcKenzie.com',2),(5,'Michael','Collins','2096 Newton Terrace','Springfield','Louisiana','35389','5909321934','Michael@MCollins.net',3),(6,'Michael','Miller','203 Blue Place','Greenville','Georgia','86740','9043991982','Michael@MMiller.com',3),(7,'Michael','Phillips','1062 Main Loop','Greenville','Texas','32885','7044394021','Michael@MPhillips.com',2),(8,'Michael','Wilson','183 Pegasus Court','Greenville','Georgia','49415','5906524105','Michael@MWilson.us',1),(9,'Michael','Davis','4596 Miasma Place','Franklin','Ohio','84836','9368527775','Michael@MDavis.com',1),(10,'Michael','Smith','108 Miasma Loop','Fairview','Louisiana','85116','9044999197','Michael@MSmith.us',3),(11,'Mary','Harris','119 Elysian Circle','Salem','Utah','34549','9044476031','Mary@MHarris.com',3),(12,'Mary','Jones','4592 43rd Road','Franklin','Louisiana','75101','5204232210','Mary@MJones.com',3),(13,'Mary','Sizemore','10101 Newton Loop','Salem','Tennessee','87636','9044157783','Mary@MSizemore.org',2),(14,'Mary','McKenzie','3657 Main Path','Franklin','Alaska','33721','5724727743','Mary@MMcKenzie.us',3),(15,'Mary','Collins','10169 Elysian Avenue','Springfield','Louisiana','76373','6923316408','Mary@MCollins.biz',3),(16,'Mary','Miller','5144 Pegasus Circle','Greenville','Georgia','78029','6925245771','Mary@MMiller.com',2),(17,'Mary','Phillips','1028 Miasma Terrace','Springfield','Tennessee','85476','9365962466','Mary@MPhillips.us',2),(18,'Mary','Wilson','5060 Pegasus Terrace','Salem','Montana','50459','7043674221','Mary@MWilson.biz',3),(19,'Mary','Davis','2100 Miasma Loop','Fairview','Georgia','84320','7743672166','Mary@MDavis.net',2),(20,'Mary','Smith','4624 Pegasus Avenue','Franklin','Texas','85800','7047047535','Mary@MSmith.org',3),(21,'Fred','Harris','10257 Pegasus Avenue','Greenville','Texas','86520','5207526244','Fred@FHarris.biz',1),(22,'Fred','Jones','5044 Miasma Terrace','Springfield','Alaska','74793','9044446921','Fred@FJones.net',1),(23,'Fred','Sizemore','5056 Main Place','Greenville','Alaska','75877','7043754053','Fred@FSizemore.org',2),(24,'Fred','McKenzie','2528 Duval Road','Franklin','Louisiana','48911','5725126861','Fred@FMcKenzie.us',2),(25,'Fred','Collins','5020 Duval Road','Salem','Louisiana','35329','7742864277','Fred@FCollins.net',3),(26,'Fred','Miller','4552 Duval Terrace','Springfield','Georgia','36317','5201864149','Fred@FMiller.us',2),(27,'Fred','Phillips','10165 43rd Road','Springfield','Utah','77349','9049046853','Fred@FPhillips.net',3),(28,'Fred','Wilson','5020 Elysian Street','Fairview','Ohio','84252','5724844121','Fred@FWilson.biz',3),(29,'Fred','Davis','4520 43rd Path','Fairview','Georgia','25099','5204593170','Fred@FDavis.biz',1),(30,'Fred','Smith','1018 43rd Road','Franklin','Tennessee','87084','5202207907','Fred@FSmith.biz',1),(31,'Thomas','Harris','4584 Pegasus Path','Springfield','Georgia','28295','4073552394','Thomas@THarris.net',1),(32,'Thomas','Jones','2152 Orange Place','Franklin','Utah','35281','9043753502','Thomas@TJones.com',2),(33,'Thomas','Sizemore','1150 Pegasus Street','Springfield','Florida','25491','7044035839','Thomas@TSizemore.org',1),(34,'Thomas','McKenzie','1102 Orange Loop','Franklin','Tennessee','49103','8503034289','Thomas@TMcKenzie.org',2),(35,'Thomas','Collins','247 University Loop','Fairview','California','51183','5905202286','Thomas@TCollins.net',2),(36,'Thomas','Miller','1136 Pegasus Terrace','Salem','Louisiana','48879','4071787603','Thomas@TMiller.biz',1),(37,'Thomas','Phillips','10149 Blue Terrace','Salem','Ohio','49523','8503124033','Thomas@TPhillips.us',1),(38,'Thomas','Wilson','5000 Main Place','Springfield','Utah','35857','6927486120','Thomas@TWilson.biz',3),(39,'Thomas','Davis','4588 Main Avenue','Salem','Georgia','48591','9364074437','Thomas@TDavis.biz',1),(40,'Thomas','Smith','1060 University Terrace','Springfield','California','27791','5203956059','Thomas@TSmith.biz',2),(41,'Martin','Harris','4512 Blue Street','Salem','Ohio','35021','6928043402','Martin@MHarris.com',1),(42,'Martin','Jones','148 Duval Lane','Fairview','Ohio','78189','5204756304','Martin@MJones.com',3),(43,'Martin','Sizemore','1064 Elysian Place','Franklin','Texas','28663','7042522170','Martin@MSizemore.org',1),(44,'Martin','McKenzie','203 Pegasus Street','Fairview','Alaska','36481','5724522150','Martin@MMcKenzie.us',2),(45,'Martin','Collins','5080 Miasma Court','Greenville','Texas','75933','5723753426','Martin@MCollins.com',2),(46,'Martin','Miller','1120 Blue Terrace','Fairview','Ohio','49107','5723636208','Martin@MMiller.com',3),(47,'Martin','Phillips','2016 Miasma Street','Fairview','Tennessee','49259','7746844369','Martin@MPhillips.us',3),(48,'Martin','Wilson','1088 Elysian Path','Greenville','California','75657','4072723238','Martin@MWilson.net',3),(49,'Martin','Davis','2084 Orange Place','Fairview','Florida','85612','6923153502','Martin@MDavis.net',2),(50,'Martin','Smith','2524 Blue Lane','Franklin','Utah','29111','5203117791','Martin@MSmith.net',1),(51,'Richard','Harris','204 Orange Loop','Salem','Ohio','76153','7048848965','Richard@RHarris.biz',2),(52,'Richard','Jones','1084 Main Place','Fairview','Alaska','33821','8504442154','Richard@RJones.net',3),(53,'Richard','Sizemore','256 Duval Court','Greenville','Georgia','28691','9363837001','Richard@RSizemore.biz',2),(54,'Richard','McKenzie','5152 Newton Street','Franklin','California','84932','7044084197','Richard@RMcKenzie.biz',3),(55,'Richard','Collins','267 Blue Road','Franklin','Tennessee','77577','8505764025','Richard@RCollins.us',2),(56,'Richard','Miller','2104 Pegasus Court','Franklin','Montana','76861','7044441794','Richard@RMiller.biz',1),(57,'Richard','Phillips','5072 Pegasus Terrace','Greenville','Montana','87408','5723156140','Richard@RPhillips.net',1),(58,'Richard','Wilson','3609 Blue Lane','Greenville','Georgia','33477','8504196432','Richard@RWilson.net',2),(59,'Richard','Davis','10205 University Road','Greenville','California','75553','5208685715','Richard@RDavis.biz',3),(60,'Richard','Smith','1148 Newton Lane','Franklin','California','50395','7746289097','Richard@RSmith.com',3),(61,'Marie','Harris','199 Main Court','Salem','Ohio','50827','7742756320','Marie@MHarris.biz',1),(62,'Marie','Jones','10229 Blue Terrace','Greenville','Montana','84628','6923554125','Marie@MJones.us',3),(63,'Marie','Sizemore','2124 Orange Court','Greenville','Utah','85104','5726284125','Marie@MSizemore.biz',2),(64,'Marie','McKenzie','5080 43rd Lane','Fairview','Texas','35489','7744194253','Marie@MMcKenzie.org',2),(65,'Marie','Collins','208 University Road','Fairview','Ohio','86952','7043529209','Marie@MCollins.org',2),(66,'Marie','Miller','3537 Miasma Loop','Springfield','Montana','48327','9365161986','Marie@MMiller.net',2),(67,'Marie','Phillips','171 Pegasus Circle','Franklin','Florida','49959','9366045975','Marie@MPhillips.us',3),(68,'Marie','Wilson','1096 Main Loop','Franklin','Montana','49607','7049527643','Marie@MWilson.com',1),(69,'Marie','Davis','5040 Orange Avenue','Franklin','Georgia','48639','7045924029','Marie@MDavis.com',2),(70,'Marie','Smith','2052 Newton Lane','Springfield','Alaska','77869','9362996228','Marie@MSmith.org',1),(71,'John','Harris','1060 Orange Place','Fairview','Montana','34337','9042702478','John@JHarris.com',1),(72,'John','Jones','2592 University Path','Springfield','Montana','35229','4071786448','John@JJones.org',2),(73,'John','Sizemore','5040 Duval Terrace','Fairview','Alaska','51523','9364355787','John@JSizemore.net',3),(74,'John','McKenzie','5140 Blue Court','Salem','Georgia','75669','8506329229','John@JMcKenzie.net',2),(75,'John','Collins','131 Newton Terrace','Greenville','Texas','51475','4072227735','John@JCollins.org',2),(76,'John','Miller','2000 University Place','Salem','Utah','34505','5722903454','John@JMiller.biz',2),(77,'John','Phillips','10149 Pegasus Avenue','Springfield','Texas','33509','4077806733','John@JPhillips.com',3),(78,'John','Wilson','1166 Miasma Court','Greenville','Georgia','26275','5724156252','John@JWilson.org',2),(79,'John','Davis','135 Main Lane','Franklin','Tennessee','50887','4074474197','John@JDavis.org',3),(80,'John','Smith','2004 University Lane','Springfield','Tennessee','48175','8505447017','John@JSmith.biz',3),(81,'Robert','Harris','2556 Pegasus Circle','Franklin','Tennessee','28655','4074833230','Robert@RHarris.com',3),(82,'Robert','Jones','1082 Elysian Lane','Franklin','Louisiana','49879','8503193274','Robert@RJones.org',3),(83,'Robert','Sizemore','2076 Pegasus Street','Greenville','Tennessee','48479','9044796781','Robert@RSizemore.net',1),(84,'Robert','McKenzie','3601 Main Place','Fairview','Georgia','49827','7046446725','Robert@RMcKenzie.biz',1),(85,'Robert','Collins','148 Duval Street','Salem','Texas','49423','5902249209','Robert@RCollins.us',3),(86,'Robert','Miller','10125 Elysian Circle','Greenville','Montana','85764','5909164101','Robert@RMiller.net',2),(87,'Robert','Phillips','10161 Newton Loop','Franklin','Florida','35657','5726484281','Robert@RPhillips.com',3),(88,'Robert','Wilson','1130 Miasma Road','Fairview','Ohio','35333','9043954077','Robert@RWilson.com',1),(89,'Robert','Davis','5040 Newton Loop','Franklin','Georgia','77573','4073673390','Robert@RDavis.biz',3),(90,'Robert','Smith','2512 Duval Court','Salem','Alaska','87284','7047524241','Robert@RSmith.us',3),(91,'William','Harris','140 Newton Street','Springfield','Texas','76449','5206082222','William@WHarris.org',2),(92,'William','Jones','1102 Duval Lane','Greenville','Ohio','25579','5726128873','William@WJones.com',2),(93,'William','Sizemore','1044 Duval Path','Salem','Alaska','84064','7043154361','William@WSizemore.biz',1),(94,'William','McKenzie','5112 University Terrace','Greenville','Florida','35937','7748007839','William@WMcKenzie.net',3),(95,'William','Collins','5100 Main Avenue','Salem','Ohio','84504','5204234253','William@WCollins.us',1),(96,'William','Miller','1154 Duval Street','Salem','California','49451','5203635911','William@WMiller.org',3),(97,'William','Phillips','1098 Elysian Road','Fairview','Tennessee','49171','5204249173','William@WPhillips.biz',2),(98,'William','Wilson','128 Pegasus Lane','Springfield','Alaska','84084','9363234061','William@WWilson.us',2),(99,'William','Davis','180 Elysian Court','Greenville','Alaska','34153','4079249061','William@WDavis.org',1),(100,'William','Smith','2500 University Lane','Fairview','Tennessee','25823','9044516777','William@WSmith.biz',2);
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
  `login` varchar(254) NOT NULL,
  `password` char(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2018-08-26 12:00:00','2018-08-31 12:31:10','Rick','Leinecker','rick@ucf.edu','4914EB016456C4E6E305965C0501864601003161BA6BAF760E7031A36BB10B99'),(2,'2018-08-26 12:00:00','2018-08-31 12:50:45','Bonfire','User','user@website.com','536ADEE40DB561FF9C3933B6946B7AD38EDBD87A058423EFF0B6583BCCE6514F'),(3,'2018-09-02 16:22:32','2018-09-02 16:22:32','Demo','Account','demo@demo.demo','4B5076657E2B30EBEAB5E2305F1519F3D197B09ACF03686452720B9A0D574EC0');
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

-- Dump completed on 2018-09-02 16:26:05
