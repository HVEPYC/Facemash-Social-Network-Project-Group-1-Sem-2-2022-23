CREATE DATABASE  IF NOT EXISTS `facemash` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `facemash`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: facemash
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `relationship_status`
--

DROP TABLE IF EXISTS `relationship_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relationship_status` (
  `id` int NOT NULL,
  `status` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userrelation_idx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relationship_status`
--

LOCK TABLES `relationship_status` WRITE;
/*!40000 ALTER TABLE `relationship_status` DISABLE KEYS */;
INSERT INTO `relationship_status` VALUES (1,'Single'),(2,'Committed Relationship'),(3,'Married'),(4,'It\'s Complicated');
/*!40000 ALTER TABLE `relationship_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idx` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (0,'Admin'),(1,'User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `isDeleted` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'John Smith','1990-05-15','123 Main St','Male',0),(2,'Jane Doe','1988-09-23','456 Elm St','Female',0),(3,'Michael Johnson','1995-02-10','789 Oak St','Male',0),(4,'Emily Wilson','1992-12-01','321 Pine St','Female',0),(5,'David Anderson','1987-06-08','987 Maple Ave','Male',0),(6,'Sarah Davis','1991-08-17','654 Birch Ln','Female',0),(7,'Daniel Rodriguez','1994-03-27','159 Oak St','Male',0),(8,'Sophia Martinez','1993-07-04','753 Cedar Dr','Female',0),(9,'Joseph Thompson','1989-11-19','951 Elm St','Male',0),(10,'Olivia Clark','1996-01-12','357 Maple Ave','Female',0),(11,'Andrew Lewis','1990-04-06','753 Oak St','Male',0),(12,'Isabella Walker','1988-12-30','159 Pine St','Female',0),(13,'Matthew Hall','1992-06-20','456 Birch Ln','Male',0),(14,'Ava Young','1991-09-08','753 Oak St','Female',0),(15,'Christopher Hernandez','1987-05-03','123 Cedar Dr','Male',0),(16,'Mia Green','1993-02-17','456 Elm St','Female',0),(17,'James Perez','1990-08-01','951 Oak St','Male',0),(18,'Charlotte Turner','1989-03-12','321 Pine St','Female',0),(19,'Benjamin Hill','1995-11-27','753 Maple Ave','Male',0),(20,'Amelia Scott','1994-07-14','951 Cedar Dr','Female',0),(21,'Alexander Adams','1991-01-21','159 Oak St','Male',0),(22,'Evelyn Baker','1988-06-07','753 Pine St','Female',0),(23,'William Turner','1993-09-25','951 Birch Ln','Male',0),(24,'Sofia White','1990-12-10','123 Oak St','Female',0),(25,'Daniel Martin','1987-07-18','951 Cedar Dr','Male',0),(26,'Abigail Lee','1992-04-05','159 Elm St','Female',0),(27,'Joseph Davis','1991-08-23','753 Pine St','Male',0),(28,'Grace Martinez','1994-01-29','951 Elm St','Female',0),(29,'David Thompson','1989-05-16','159 Cedar Dr','Male',0),(30,'Emma Harris','1996-10-03','753 Oak St','Female',0),(31,'Magnus Ericsonn','1988-06-05','4th Street, 2nd Road, ABC Land','Male',0),(32,'Barney The Clown','1905-07-06','Barney Land Remastered, Barney Land','Female',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_credential`
--

DROP TABLE IF EXISTS `user_credential`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_credential` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `contact` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` longtext NOT NULL,
  `creation_date` date DEFAULT NULL,
  `last_modfied_date` date DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userIdFk_idx` (`user_id`),
  CONSTRAINT `userIdFk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_credential`
--

LOCK TABLES `user_credential` WRITE;
/*!40000 ALTER TABLE `user_credential` DISABLE KEYS */;
INSERT INTO `user_credential` VALUES (1,'johnsmith','1234567890','johnsmith@example.com','gwnJzlNxx0gGDPSDRuXjyA==','2023-06-01','2023-06-01',1),(2,'janedoe','9876543210','janedoe@example.com','m/uQhTX3yMwI6FjdcB02ig==','2023-06-01','2023-06-01',2),(3,'mjohnson','4567890123','mjohnson@example.com','BacJwyYE99D5C1tWAJ4kAg==','2023-06-01','2023-06-01',3),(4,'ewilson','7890123456','ewilson@example.com','CC6U+zxuE0BbVaUInVQPAQ==','2023-06-01','2023-06-01',4),(5,'danderson','2345678901','danderson@example.com','n6TgiJ05FMBJNCSIehYNVA==','2023-06-01','2023-06-01',5),(6,'sdavis','9012345678','sdavis@example.com','BtOdbNxvebCOhw5q9IZEgg==','2023-06-01','2023-06-01',6),(7,'drodriguez','3456789012','drodriguez@example.com','ccma8eT0oMzl27cmuoaiaQ==','2023-06-01','2023-06-01',7),(8,'smartinez','6789012345','smartinez@example.com','UNsDEr8IM2Pzr527zhEgIg==','2023-06-01','2023-06-01',8),(9,'jthompson','0123456789','jthompson@example.com','ulVyb3cVdz5uQBjDLusE1g==','2023-06-01','2023-06-01',9),(10,'oclark','5432109876','oclark@example.com','hMrvdAFcBJmYKu6/7Ba6iQ==','2023-06-01','2023-06-01',10),(11,'alewis','2109876543','alewis@example.com','4s2l5breDJnrDCr4wZOVEw==','2023-06-01','2023-06-01',11),(12,'iwalker','4567890123','iwalker@example.com','phVLgvM7xqYw+juGdARMEg==','2023-06-01','2023-06-01',12),(13,'mhall','7890123456','mhall@example.com','7Pk84bhPJCcQTQZYcy5iYA==','2023-06-01','2023-06-01',13),(14,'ayoung','1234567890','ayoung@example.com','9GoLX6S03aJrpxuGa9KNIw==','2023-06-01','2023-06-01',14),(15,'chernandez','2345678901','chernandez@example.com','AUNrErDYU+3RLV48hhiI2Q==','2023-06-01','2023-06-01',15),(16,'mgreen','5678901234','mgreen@example.com','iNk8/udV+7DtI6N3QyesmQ==','2023-06-01','2023-06-01',16),(17,'jperez','8901234567','jperez@example.com','QR199b1RExaRu4I/lEMKcA==','2023-06-01','2023-06-01',17),(18,'cturner','0123456789','cturner@example.com','75TU2tHW4+sngFOyFs/oaw==','2023-06-01','2023-06-01',18),(19,'bhill','3456789012','bhill@example.com','a2xXIO8kYaVnvnOOsLQKlg==','2023-06-01','2023-06-01',19),(20,'ascott','6789012345','ascott@example.com','eOliQczaHhuSy0troznPuw==','2023-06-01','2023-06-01',20),(21,'aadams','9012345678','aadams@example.com','pmEq/1OGwvT5FnDJMvT/3Q==','2023-06-01','2023-06-01',21),(22,'ebaker','2345678901','ebaker@example.com','ANchzPtcRUNwLNhtI8eTxQ==','2023-06-01','2023-06-01',22),(23,'wturner','5678901234','wturner@example.com','AC9lgUNyeJOW9vczbzeU1w==','2023-06-01','2023-06-01',23),(24,'swhite','8901234567','swhite@example.com','cH8guufcwWHJBrOXBckK6g==','2023-06-01','2023-06-01',24),(25,'dmartin','0123456789','dmartin@example.com','xyw16rAykPk4DedHhnhajA==','2023-06-01','2023-06-01',25),(26,'alee','3456789012','alee@example.com','gPSYGr1EXS28phf3fo4PRA==','2023-06-01','2023-06-01',26),(27,'jdavis','6789012345','jdavis@example.com','yIOutu1wtamAFU2w7De3AQ==','2023-06-01','2023-06-01',27),(28,'gmartinez','9012345678','gmartinez@example.com','2HMrwqesW+0PJtG1KxiRVA==','2023-06-01','2023-06-01',28),(29,'dthompson','2345678901','dthompson@example.com','d20QVfy8mEYV1HWIoIMpuA==','2023-06-01','2023-06-01',29),(30,'eharris','5678901234','eharris@example.com','RMaewnZa8dZf2/5wJ2XLYA==','2023-06-01','2023-06-01',30),(31,'MagnusEricsonn','123456789','magnus@example.com','ZmEV36n5xOhDqr2BETwPkg==','2023-06-18','2023-06-18',31),(32,'BarneyClown','1120034592','barney@gmail.com','Algz6Kx7vmjQT6ifYtJNtw==','2023-06-15','2023-06-15',32);
/*!40000 ALTER TABLE `user_credential` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_friend_requests`
--

DROP TABLE IF EXISTS `user_friend_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_friend_requests` (
  `from_id` int NOT NULL,
  `to_id` int NOT NULL,
  PRIMARY KEY (`from_id`,`to_id`),
  KEY `friend_id_idx` (`to_id`),
  CONSTRAINT `friend_id` FOREIGN KEY (`to_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`from_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_friend_requests`
--

LOCK TABLES `user_friend_requests` WRITE;
/*!40000 ALTER TABLE `user_friend_requests` DISABLE KEYS */;
INSERT INTO `user_friend_requests` VALUES (1,2),(1,6),(31,6),(31,7),(1,10),(1,28);
/*!40000 ALTER TABLE `user_friend_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_friends`
--

DROP TABLE IF EXISTS `user_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_friends` (
  `user_id` int NOT NULL,
  `friend_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`friend_id`),
  KEY `friendIdFk_idx` (`friend_id`),
  CONSTRAINT `user_friendsFriendIdFk` FOREIGN KEY (`friend_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_friendsUserIdFk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_friends`
--

LOCK TABLES `user_friends` WRITE;
/*!40000 ALTER TABLE `user_friends` DISABLE KEYS */;
INSERT INTO `user_friends` VALUES (2,1),(5,1),(7,1),(9,1),(15,1),(20,1),(25,1),(30,1),(6,3),(9,3),(32,3),(7,6),(2,9),(3,10),(1,11);
/*!40000 ALTER TABLE `user_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_hobbies`
--

DROP TABLE IF EXISTS `user_hobbies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_hobbies` (
  `user_id` int NOT NULL,
  `hobbies` longtext NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_hobbiesUserIdFK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_hobbies`
--

LOCK TABLES `user_hobbies` WRITE;
/*!40000 ALTER TABLE `user_hobbies` DISABLE KEYS */;
INSERT INTO `user_hobbies` VALUES (1,'Reading, Cooking'),(2,'Photography, Traveling'),(3,'Gaming, Tennis'),(4,'Painting, Dancing'),(5,'Hiking, Cycling'),(6,'Singing, Yoga'),(7,'Football, Movies'),(8,'Swimming, Photography'),(9,'Chess, Music'),(10,'Writing, Gardening'),(11,'Basketball, Painting'),(12,'Photography, Cooking'),(13,'Gaming, Dancing'),(14,'Singing, Traveling'),(15,'Football, Reading'),(16,'Photography, Yoga'),(17,'Tennis, Movies'),(18,'Writing, Cycling'),(19,'Painting, Hiking'),(20,'Cooking, Music'),(21,'Basketball, Gardening'),(22,'Photography, Yoga'),(23,'Football, Traveling'),(24,'Singing, Chess'),(25,'Dancing, Swimming'),(26,'Reading, Photography'),(27,'Gaming, Music'),(28,'Writing, Movies'),(29,'Hiking, Tennis'),(30,'singing, dancing, reading'),(31,'Chess, Video Games, Drama Club'),(32,'Dancing, Being Barney, Being Awesome');
/*!40000 ALTER TABLE `user_hobbies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_jobs`
--

DROP TABLE IF EXISTS `user_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_jobs` (
  `user_id` int NOT NULL,
  `jobtitle` longtext NOT NULL,
  `company` longtext NOT NULL,
  `started_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_jobsUserIdFK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_jobs`
--

LOCK TABLES `user_jobs` WRITE;
/*!40000 ALTER TABLE `user_jobs` DISABLE KEYS */;
INSERT INTO `user_jobs` VALUES (1,'Software Engineer','ABC Company','2010-01-01','2015-12-31'),(2,'Marketing Specialist','XYZ Corporation','2012-05-15','2019-09-30'),(3,'Sales Manager','123 Industries','2014-03-10','2020-06-30'),(4,'Graphic Designer','Acme Design Studio','2013-08-01','2018-11-30'),(5,'Financial Analyst','Global Investments','2011-02-15','2016-07-31'),(6,'Teacher','City High School','2015-06-01','2022-03-31'),(7,'Project Manager','Tech Solutions Inc.','2016-09-01','2023-01-31'),(8,'Accountant','Number Crunchers LLP','2012-11-15','2019-07-31'),(9,'Human Resources Coordinator','People First Corp.','2014-07-01','2020-12-31'),(10,'Web Developer','Digital Solutions Ltd.','2017-01-01','2023-05-31'),(11,'Product Manager','Innovative Products LLC','2010-03-15','2017-08-31'),(12,'Content Writer','Creative Writing Agency','2013-12-01','2019-06-30'),(13,'Business Analyst','Enterprise Solutions','2015-07-15','2021-10-31'),(14,'Sales Representative','Salesforce Inc.','2012-09-01','2019-04-30'),(15,'Software Developer','Tech Innovators','2011-04-15','2016-09-30'),(16,'Marketing Manager','Digital Marketing Agency','2016-02-01','2022-07-31'),(17,'Financial Advisor','Money Matters Advisors','2013-07-15','2018-12-31'),(18,'Copywriter','Creative Ad Agency','2014-12-01','2021-03-31'),(19,'Project Coordinator','Project Management Inc.','2016-11-01','2023-04-30'),(20,'Software Engineer','Tech Solutions Ltd.','2015-02-15','2020-07-31'),(21,'Marketing Coordinator','Marketing Solutions Corp.','2012-06-01','2019-11-30'),(22,'Art Director','Design Studio X','2014-08-15','2021-01-31'),(23,'Sales Executive','Sales R Us','2013-03-10','2018-06-30'),(24,'Graphic Designer','Creative Design Agency','2015-07-01','2022-10-31'),(25,'Financial Analyst','Investment Bankers Inc.','2012-10-15','2019-03-31'),(26,'Teacher','Primary School ABC','2014-12-01','2021-05-31'),(27,'Project Manager','Tech Projects Ltd.','2016-03-01','2022-08-31'),(28,'Accountant','Financial Services LLC','2013-08-15','2019-01-31'),(29,'Human Resources Manager','HR Solutions Ltd.','2015-09-01','2021-04-30'),(30,'Unemployed','Unemployed','2022-12-03','2023-10-06'),(31,'Pro Chess Player','Chess League','2009-09-05','2023-06-04'),(32,'Pro Costume Maker','BarneyLand','1975-08-04','1983-09-15');
/*!40000 ALTER TABLE `user_jobs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_partner`
--

DROP TABLE IF EXISTS `user_partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_partner` (
  `user_id` int NOT NULL,
  `partner_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`partner_id`),
  KEY `user_partnerPartnerIdFK_idx` (`partner_id`),
  CONSTRAINT `user_partnerPartnerIdFK` FOREIGN KEY (`partner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_partnerUserIdFK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_partner`
--

LOCK TABLES `user_partner` WRITE;
/*!40000 ALTER TABLE `user_partner` DISABLE KEYS */;
INSERT INTO `user_partner` VALUES (2,1),(32,3),(6,5),(9,8),(12,11),(15,14),(18,17),(21,20),(24,23),(27,26),(30,29);
/*!40000 ALTER TABLE `user_partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_relationships`
--

DROP TABLE IF EXISTS `user_relationships`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_relationships` (
  `user_id` int NOT NULL,
  `relationship_status_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`relationship_status_id`),
  KEY `user_relationshipRelationshipStatusIdFK_idx` (`relationship_status_id`),
  CONSTRAINT `user_relationshipsRelationshipStatusIdFK` FOREIGN KEY (`relationship_status_id`) REFERENCES `relationship_status` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_relationshipsUserIdFK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_relationships`
--

LOCK TABLES `user_relationships` WRITE;
/*!40000 ALTER TABLE `user_relationships` DISABLE KEYS */;
INSERT INTO `user_relationships` VALUES (4,1),(7,1),(10,1),(13,1),(16,1),(19,1),(22,1),(25,1),(28,1),(31,1),(1,2),(2,2),(3,2),(8,2),(9,2),(14,2),(15,2),(20,2),(21,2),(26,2),(27,2),(32,2),(5,3),(6,3),(11,3),(12,3),(17,3),(18,3),(23,3),(24,3),(29,3),(30,3);
/*!40000 ALTER TABLE `user_relationships` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_rolesRoleIdFK_idx` (`role_id`),
  CONSTRAINT `user_rolesRoleIdFK` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_rolesUserIdFK` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,0),(2,0),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,1),(32,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-18 21:11:16
