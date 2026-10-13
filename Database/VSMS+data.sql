CREATE DATABASE  IF NOT EXISTS `vsms` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `vsms`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: vsms
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `areaid` int NOT NULL AUTO_INCREMENT,
  `areaname` varchar(45) NOT NULL,
  `pincode` int NOT NULL,
  `cityid` int NOT NULL,
  PRIMARY KEY (`areaid`),
  KEY `cityid_idx` (`cityid`),
  CONSTRAINT `cityid` FOREIGN KEY (`cityid`) REFERENCES `city` (`cityid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'Shivajinagar',411005,1),(2,'Kothrud',411038,1),(3,'Karvenagar',411052,1),(4,'Baner',411045,1),(5,'Hadapsar',411013,1),(6,'Kharadi',411014,1),(7,'Wagholi',412207,1),(8,'Sinhgad',411041,1),(9,'Kondhwa',411048,1),(10,'Katraj',411037,1),(11,'Ameerpet',500016,2),(12,'Gachibowli',500032,2),(13,'Kukatpally',500072,2),(14,'Banjara Hills',500034,2),(15,'Madhapur',500081,2),(16,'Secunderabad',500003,2),(17,'Dilsukhnagar',500060,2),(18,'LB Nagar',500074,2),(19,'Miyapur',500049,2),(20,'Mehdipatnam',500028,2),(21,'Indiranagar',560038,3),(22,'Koramangala',560095,3),(23,'Jayanagar',560011,3),(24,'Whitefield',560066,3),(25,'Hebbal',560034,3),(26,'Electronic City',560100,3),(27,'Andheri',400053,4),(28,'Bandra',400050,4),(29,'Dadar',400014,4),(30,'Colaba',400005,4),(31,'Powai',400076,4),(32,'Thane',400601,4),(33,'Saket',110017,5),(34,'Karol Bagh',110005,5),(35,'Dwarka',110075,5),(36,'Rohini',110085,5),(37,'Lajpat Nagar',110024,5),(38,'Connaught Place',110001,5),(39,'T. Nagar',600017,6),(40,'Anna Nagar',600040,6),(41,'Adyar',600020,6),(42,'Velachery',600042,6),(43,'Porur',600116,6),(44,'Tambaram',600045,6);
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `brandid` int NOT NULL AUTO_INCREMENT,
  `bname` varchar(45) NOT NULL,
  PRIMARY KEY (`brandid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (1,'Maruti Suzuki'),(2,'Hyundai'),(3,'Kia'),(4,'Honda'),(5,'Tata'),(6,'Mahindra'),(7,'MG Motors'),(8,'Toyota'),(9,'Ford'),(10,'Renault');
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `cityid` int NOT NULL AUTO_INCREMENT,
  `cityname` varchar(45) NOT NULL,
  PRIMARY KEY (`cityid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Pune'),(2,'Hyderabad'),(3,'Bengaluru'),(4,'Mumbai'),(5,'Delhi'),(6,'Chennai');
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customerid` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `birthdate` datetime NOT NULL,
  `contactno` varchar(45) NOT NULL,
  `emailid` varchar(45) DEFAULT NULL,
  `lane` varchar(45) DEFAULT NULL,
  `areaid` int NOT NULL,
  `loginid` int NOT NULL,
  PRIMARY KEY (`customerid`),
  KEY `aid_idx` (`areaid`),
  KEY `lid_idx` (`loginid`),
  CONSTRAINT `aid` FOREIGN KEY (`areaid`) REFERENCES `area` (`areaid`),
  CONSTRAINT `lid` FOREIGN KEY (`loginid`) REFERENCES `login` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
-- Column order is (birthdate, contactno, emailid, lane). This row used to read
-- (birthdate, emailid, lane, contactno), which is the bug that shipped in the
-- original dump: on a fresh load every customer's phone, email and address
-- were rotated by one column.
INSERT INTO `customers` VALUES (23,'Shreyas','Malvadkar','1997-12-05 05:30:00','9850128014','shreyas@gmail.com','Flat No.8 Ganesh Vrund',2,55),(25,'Yuva','Chatla','2005-07-18 05:30:00','9876543210','g.wagon2005.pspk@gmail.com','hanamkonda',4,58),(26,'Ojas','Gambheera','2006-07-18 05:30:00','5644885125','ojas@gmail.com','hanamkonda',10,59);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facilities`
--

DROP TABLE IF EXISTS `facilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facilities` (
  `facilityid` int NOT NULL AUTO_INCREMENT,
  `facilityname` varchar(45) NOT NULL,
  PRIMARY KEY (`facilityid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facilities`
--

LOCK TABLES `facilities` WRITE;
/*!40000 ALTER TABLE `facilities` DISABLE KEYS */;
INSERT INTO `facilities` VALUES (1,'Engine Oi lCheck'),(2,'Coolant Check'),(3,'Battery Check'),(4,'Brake Fluid Check'),(5,'Lights Check'),(6,'Clutch Fluid Check'),(7,'Transmission Fluid Check'),(8,'Steering Check'),(9,'Wind Screen Washer'),(10,'Tyre Treads'),(11,'Horn Check'),(12,'Wheel Alignment'),(13,'Shocks Struts'),(14,'Differential Check'),(15,'Spark Plug'),(16,'Air Bag Check'),(17,'Engine Lubrication'),(18,'Exhaust Check'),(19,'Beering Check'),(20,'Transmission Belt Check'),(21,'Washing'),(22,'Internal Cleaing'),(23,'Plastic Parts Waxing');
/*!40000 ALTER TABLE `facilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `invoiceid` int NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) NOT NULL,
  `servicerequestid` int NOT NULL,
  `transactionid` int NOT NULL,
  PRIMARY KEY (`invoiceid`),
  KEY `trid_idx` (`transactionid`),
  KEY `sreqstid_idx` (`servicerequestid`),
  CONSTRAINT `sreqstid` FOREIGN KEY (`servicerequestid`) REFERENCES `servicerequests` (`servicerequestid`),
  CONSTRAINT `trid` FOREIGN KEY (`transactionid`) REFERENCES `transactions` (`transactionid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
INSERT INTO `invoices` VALUES (1,1,81,2);
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `loginid` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `answer` varchar(45) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `roleid` int NOT NULL,
  `questionid` int NOT NULL,
  PRIMARY KEY (`loginid`),
  UNIQUE KEY `userid_UNIQUE` (`userid`),
  KEY `rid_idx` (`roleid`),
  KEY `qid_idx` (`questionid`),
  CONSTRAINT `qid` FOREIGN KEY (`questionid`) REFERENCES `securityquestions` (`questionid`),
  CONSTRAINT `rid` FOREIGN KEY (`roleid`) REFERENCES `roles` (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
-- Every password here is `Vsms@123` (see DEPLOY.md s11). The original dump
-- shipped three different hashes from before the salt change, so a fresh
-- install could not sign in with the documented credentials.
INSERT INTO `login` VALUES (55,'Shreyas','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','Sachin',1,1,6),(56,'Admin','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','Poha',1,3,1),(57,'Tata Pune','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','Racing',1,2,5),(58,'ojas123','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','biryani',1,1,1),(59,'tata123','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','biryani',1,1,1),(60,'boku123','se6415NBQnj4JgXGflci7+f43H8vInFxAi3pUMHux1Y=','biryani',1,2,1);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packagedetails`
--

DROP TABLE IF EXISTS `packagedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagedetails` (
  `packagedetailsid` int NOT NULL AUTO_INCREMENT,
  `packagename` varchar(45) NOT NULL,
  `cost` decimal(9,2) NOT NULL,
  `servicecenterid` int NOT NULL,
  PRIMARY KEY (`packagedetailsid`),
  KEY `sid_idx` (`servicecenterid`),
  CONSTRAINT `sid` FOREIGN KEY (`servicecenterid`) REFERENCES `servicecenters` (`servicecenterid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packagedetails`
--

LOCK TABLES `packagedetails` WRITE;
/*!40000 ALTER TABLE `packagedetails` DISABLE KEYS */;
INSERT INTO `packagedetails` VALUES (1,'Basic',1890.00,8),(2,'Silver',2500.00,8),(3,'Gold',3450.00,8),(4,'Essential',1290.00,9),(5,'Premium',2990.00,9);
/*!40000 ALTER TABLE `packagedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `packageid` int NOT NULL AUTO_INCREMENT,
  `facilityid` int NOT NULL,
  PRIMARY KEY (`packageid`,`facilityid`),
  KEY `fid_idx` (`facilityid`),
  CONSTRAINT `fid` FOREIGN KEY (`facilityid`) REFERENCES `facilities` (`facilityid`),
  CONSTRAINT `pid` FOREIGN KEY (`packageid`) REFERENCES `packagedetails` (`packagedetailsid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES (2,1),(3,1),(2,2),(3,2),(2,3),(3,3),(3,4),(3,5),(3,6),(3,7),(2,20),(1,21),(2,21),(3,21),(1,22),(2,22),(3,22),(1,23),(2,23),(3,23),(4,1),(4,2),(4,3),(4,21),(4,22),(4,23),(5,1),(5,2),(5,3),(5,4),(5,5),(5,6),(5,20),(5,21),(5,22),(5,23);
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ratings` (
  `ratingid` int NOT NULL AUTO_INCREMENT,
  `rating` int DEFAULT NULL,
  `comment` varchar(45) DEFAULT NULL,
  `customerid` int DEFAULT NULL,
  `servicecenterid` int DEFAULT NULL,
  PRIMARY KEY (`ratingid`),
  KEY `servcid_idx` (`servicecenterid`),
  KEY `custid_idx` (`customerid`),
  CONSTRAINT `custid` FOREIGN KEY (`customerid`) REFERENCES `customers` (`customerid`),
  CONSTRAINT `servcid` FOREIGN KEY (`servicecenterid`) REFERENCES `servicecenters` (`servicecenterid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `roleid` int NOT NULL AUTO_INCREMENT,
  `rolename` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`roleid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Customer'),(2,'Service Center'),(3,'Admin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `securityquestions`
--

DROP TABLE IF EXISTS `securityquestions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `securityquestions` (
  `questionid` int NOT NULL AUTO_INCREMENT,
  `questiontext` varchar(45) NOT NULL,
  PRIMARY KEY (`questionid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `securityquestions`
--

LOCK TABLES `securityquestions` WRITE;
/*!40000 ALTER TABLE `securityquestions` DISABLE KEYS */;
INSERT INTO `securityquestions` VALUES (1,'What was your favorite food as a child?'),(2,'In what city were you born?'),(3,'What is the name of your favorite pet?'),(4,'What was the make of your first car?'),(5,'What is your favorite sport?'),(6,'Who was your childhood hero?');
/*!40000 ALTER TABLE `securityquestions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicecenters`
--

DROP TABLE IF EXISTS `servicecenters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicecenters` (
  `servicecenterid` int NOT NULL AUTO_INCREMENT,
  `scname` varchar(45) NOT NULL,
  `emailid` varchar(45) NOT NULL,
  `contactno` varchar(45) NOT NULL,
  `lane` varchar(45) NOT NULL,
  `brandid` int NOT NULL,
  `bookinglimit` int NOT NULL,
  `areaid` int NOT NULL,
  `loginid` int NOT NULL,
  PRIMARY KEY (`servicecenterid`),
  KEY `bid_idx` (`brandid`),
  KEY `aid_idx` (`areaid`),
  KEY `lid_idx` (`loginid`),
  CONSTRAINT `arid` FOREIGN KEY (`areaid`) REFERENCES `area` (`areaid`),
  CONSTRAINT `bid` FOREIGN KEY (`brandid`) REFERENCES `brands` (`brandid`),
  CONSTRAINT `loid` FOREIGN KEY (`loginid`) REFERENCES `login` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicecenters`
--

LOCK TABLES `servicecenters` WRITE;
/*!40000 ALTER TABLE `servicecenters` DISABLE KEYS */;
INSERT INTO `servicecenters` VALUES (8,'Tata Pune','tatapune@gmail.com','1234567890','near Kothrud Depto',5,25,2,57),(9,'Boku Bava Service','boku@gmail.com','8489489484','hanamkonda',8,15,10,60);
/*!40000 ALTER TABLE `servicecenters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `serviceprogress`
--

DROP TABLE IF EXISTS `serviceprogress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceprogress` (
  `serviceprogressid` int NOT NULL AUTO_INCREMENT,
  `checkin` datetime DEFAULT NULL,
  `stageone` datetime DEFAULT NULL,
  `stagetwo` datetime DEFAULT NULL,
  `checkout` datetime DEFAULT NULL,
  `delivered` tinyint(1) DEFAULT NULL,
  `servicerequestid` int NOT NULL,
  PRIMARY KEY (`serviceprogressid`),
  KEY `srqid_idx` (`servicerequestid`),
  CONSTRAINT `srqid` FOREIGN KEY (`servicerequestid`) REFERENCES `servicerequests` (`servicerequestid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `serviceprogress`
--

LOCK TABLES `serviceprogress` WRITE;
/*!40000 ALTER TABLE `serviceprogress` DISABLE KEYS */;
INSERT INTO `serviceprogress` VALUES (4,'2023-03-13 01:31:05','2023-03-13 01:31:05','2023-03-13 01:31:06','2023-03-13 01:31:25',1,81);
/*!40000 ALTER TABLE `serviceprogress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servicerequests`
--

DROP TABLE IF EXISTS `servicerequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicerequests` (
  `servicerequestid` int NOT NULL AUTO_INCREMENT,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `bookingdate` date NOT NULL,
  `pickuptime` time DEFAULT NULL,
  `servicdate` date NOT NULL,
  `vehicleid` int NOT NULL,
  `servicecenterid` int NOT NULL,
  `packageid` int NOT NULL,
  PRIMARY KEY (`servicerequestid`),
  UNIQUE KEY `servd_vehid_pkg_unique` (`servicdate`,`vehicleid`,`packageid`),
  KEY `vid_idx` (`vehicleid`),
  KEY `sccid_idx` (`servicecenterid`),
  KEY `pkid_idx` (`packageid`),
  CONSTRAINT `pkid` FOREIGN KEY (`packageid`) REFERENCES `packagedetails` (`packagedetailsid`),
  CONSTRAINT `sccid` FOREIGN KEY (`servicecenterid`) REFERENCES `servicecenters` (`servicecenterid`),
  CONSTRAINT `vid` FOREIGN KEY (`vehicleid`) REFERENCES `vehicles` (`vehicleid`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servicerequests`
--

LOCK TABLES `servicerequests` WRITE;
/*!40000 ALTER TABLE `servicerequests` DISABLE KEYS */;
INSERT INTO `servicerequests` VALUES (81,0,'2023-03-13','00:00:00','2023-03-13',23,8,1);
/*!40000 ALTER TABLE `servicerequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transactionid` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(9,2) NOT NULL,
  `date` datetime NOT NULL,
  `paymentmode` varchar(45) NOT NULL,
  `serviceprogressid` int NOT NULL,
  PRIMARY KEY (`transactionid`),
  KEY `serviceprogressid_idx` (`serviceprogressid`),
  CONSTRAINT `serviceprogressid` FOREIGN KEY (`serviceprogressid`) REFERENCES `serviceprogress` (`serviceprogressid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (2,1890.00,'2023-03-13 01:31:25','UPI',4);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicleid` int NOT NULL AUTO_INCREMENT,
  `vehiclenumber` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `fueltype` varchar(45) NOT NULL,
  `registrationyear` int NOT NULL,
  `brandid` int NOT NULL,
  `customerid` int NOT NULL,
  PRIMARY KEY (`vehicleid`),
  UNIQUE KEY `vehiclenumber_UNIQUE` (`vehiclenumber`),
  KEY `brid_idx` (`brandid`),
  KEY `cuid_idx` (`customerid`),
  CONSTRAINT `brid` FOREIGN KEY (`brandid`) REFERENCES `brands` (`brandid`),
  CONSTRAINT `cuid` FOREIGN KEY (`customerid`) REFERENCES `customers` (`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (23,'MH13DM5860','Altroz XZ','Petrol',2021,5,23);
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-13  8:47:25
