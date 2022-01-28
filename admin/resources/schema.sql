CREATE DATABASE  IF NOT EXISTS `admin` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `admin`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: admin
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `adminName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `mobile` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`adminId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bank`
--

DROP TABLE IF EXISTS `bank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bank`
--

LOCK TABLES `bank` WRITE;
/*!40000 ALTER TABLE `bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `blood`
--

DROP TABLE IF EXISTS `blood`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blood` (
  `bloodId` bigint(128) NOT NULL AUTO_INCREMENT,
  `bloodName` varchar(256) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bloodId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blood`
--

LOCK TABLES `blood` WRITE;
/*!40000 ALTER TABLE `blood` DISABLE KEYS */;
/*!40000 ALTER TABLE `blood` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities`
--

DROP TABLE IF EXISTS `cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `state_id` int(11) NOT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `shortName` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `shortname` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `code` int(128) NOT NULL,
  `creationdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directcost`
--

DROP TABLE IF EXISTS `directcost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `directcost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `directCostSource` varchar(256) DEFAULT NULL,
  `directCostYear` bigint(128) DEFAULT NULL,
  `directCostAmount` bigint(128) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `directCostSourceId` bigint(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directcost`
--

LOCK TABLES `directcost` WRITE;
/*!40000 ALTER TABLE `directcost` DISABLE KEYS */;
/*!40000 ALTER TABLE `directcost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `directcostsource`
--

DROP TABLE IF EXISTS `directcostsource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `directcostsource` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `directcostsource`
--

LOCK TABLES `directcostsource` WRITE;
/*!40000 ALTER TABLE `directcostsource` DISABLE KEYS */;
/*!40000 ALTER TABLE `directcostsource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expenses`
--

DROP TABLE IF EXISTS `expenses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expenses` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `expensesSource` varchar(256) DEFAULT NULL,
  `expensesYear` bigint(128) DEFAULT NULL,
  `expensesAmount` bigint(128) DEFAULT NULL,
  `creationoDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `expenseSourceId` bigint(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expenses`
--

LOCK TABLES `expenses` WRITE;
/*!40000 ALTER TABLE `expenses` DISABLE KEYS */;
/*!40000 ALTER TABLE `expenses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expensesource`
--

DROP TABLE IF EXISTS `expensesource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expensesource` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expensesource`
--

LOCK TABLES `expensesource` WRITE;
/*!40000 ALTER TABLE `expensesource` DISABLE KEYS */;
/*!40000 ALTER TABLE `expensesource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `geocode`
--

DROP TABLE IF EXISTS `geocode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `geocode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `long` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `geocode`
--

LOCK TABLES `geocode` WRITE;
/*!40000 ALTER TABLE `geocode` DISABLE KEYS */;
/*!40000 ALTER TABLE `geocode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goodstype`
--

DROP TABLE IF EXISTS `goodstype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `goodstype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goodstype`
--

LOCK TABLES `goodstype` WRITE;
/*!40000 ALTER TABLE `goodstype` DISABLE KEYS */;
/*!40000 ALTER TABLE `goodstype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country` varchar(25) NOT NULL,
  `city` varchar(40) NOT NULL,
  `latitude` double(10,7) NOT NULL,
  `longitude` float(10,7) NOT NULL,
  `altitude` float(5,1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newvehicle`
--

DROP TABLE IF EXISTS `newvehicle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newvehicle` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vehicleCategoryId` int(11) DEFAULT NULL,
  `vehicleTypeId` int(11) DEFAULT NULL,
  `vehicleName` varchar(45) DEFAULT NULL,
  `model` varchar(45) DEFAULT NULL,
  `vehicleSpeed` varchar(45) DEFAULT NULL,
  `fuelTank` varchar(45) DEFAULT NULL,
  `grossVehicleWeight` varchar(45) DEFAULT NULL,
  `kerbWeight` varchar(45) DEFAULT NULL,
  `overallLength` int(11) DEFAULT NULL,
  `loadBodyLength` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `vehicleTypeId_idx` (`vehicleTypeId`),
  CONSTRAINT `vehicleTypeId` FOREIGN KEY (`vehicleTypeId`) REFERENCES `vehicletypes` (`vehicleTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newvehicle`
--

LOCK TABLES `newvehicle` WRITE;
/*!40000 ALTER TABLE `newvehicle` DISABLE KEYS */;
INSERT INTO `newvehicle` VALUES (1,1,1,'TATA ACE','2000','54 km/hr','250 L','30000 kg','20000 kg',10000,80000),(2,2,2,'Chevrolet','3100','80 km/hr','450 L','35000 kg','25000 kg',15000,10000),(3,2,3,'Mahindra','200','90 km/hr','500 L','45000 kg','38000 kg',15000,12000),(4,2,4,'TATA','3100','95 km/hr','450 L','35000 kg','25000 kg',15000,12000),(5,2,5,'Mahindra','400','95 km/hr','450 L','38000 kg','30000 kg',18000,12000),(6,3,6,'Ford','3120','98 km/hr','450 L','40000 kg','30000 kg',32000,25000),(7,3,7,'Mahinra','9000','75 km/hr','350 L','25000 kg','18000 kg',12000,15000),(8,3,8,'TATA','6000','85 km/hr','450 L','45000 kg','35000 kg',15000,13000),(9,3,9,'TATA','4500','80 km/hr','480 L','45000 kg','38000 kg',16000,12000),(10,3,10,'Mahindra','4500','84 km/hr','460 L','45000 kg','38000 kg',13000,11000),(11,3,11,'TATA','4500','70 km/hr','470 L','48000 kg','35000 kg',17000,15000),(12,4,12,'Mahindra','4500','78 km/hr','460 L','48000 kg','40000 kg',16000,15000),(13,4,13,'Mahindra','4500','84 km/hr','460 L','46000 kg','38000 kg',15000,12000),(14,4,14,'Mahindra','4822','95 km/hr','400 L','42000 kg','38000 kg',14000,12000);
/*!40000 ALTER TABLE `newvehicle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personnel`
--

DROP TABLE IF EXISTS `personnel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personnel` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `personnelSource` varchar(256) DEFAULT NULL,
  `personnelYear` bigint(128) DEFAULT NULL,
  `personnelAmount` bigint(128) DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnel`
--

LOCK TABLES `personnel` WRITE;
/*!40000 ALTER TABLE `personnel` DISABLE KEYS */;
/*!40000 ALTER TABLE `personnel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personnelsource`
--

DROP TABLE IF EXISTS `personnelsource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `personnelsource` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personnelsource`
--

LOCK TABLES `personnelsource` WRITE;
/*!40000 ALTER TABLE `personnelsource` DISABLE KEYS */;
/*!40000 ALTER TABLE `personnelsource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revenuesm`
--

DROP TABLE IF EXISTS `revenuesm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revenuesm` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `revenueSource` varchar(256) DEFAULT NULL,
  `revenueAmount` bigint(128) DEFAULT NULL,
  `revenueYear` bigint(128) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `revenueSourceId` bigint(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revenuesm`
--

LOCK TABLES `revenuesm` WRITE;
/*!40000 ALTER TABLE `revenuesm` DISABLE KEYS */;
/*!40000 ALTER TABLE `revenuesm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revenuesource`
--

DROP TABLE IF EXISTS `revenuesource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `revenuesource` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `source` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revenuesource`
--

LOCK TABLES `revenuesource` WRITE;
/*!40000 ALTER TABLE `revenuesource` DISABLE KEYS */;
/*!40000 ALTER TABLE `revenuesource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `states`
--

DROP TABLE IF EXISTS `states`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `states` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `country_id` int(11) NOT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  `shortName` varchar(5) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `states`
--

LOCK TABLES `states` WRITE;
/*!40000 ALTER TABLE `states` DISABLE KEYS */;
/*!40000 ALTER TABLE `states` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiclecategories`
--

DROP TABLE IF EXISTS `vehiclecategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiclecategories` (
  `vehicleCategoryId` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(45) DEFAULT NULL,
  `pricePerKm` double DEFAULT NULL,
  `pricePerMin` double DEFAULT NULL,
  `baseFare` double DEFAULT NULL,
  `commission` varchar(45) DEFAULT NULL,
  `capacity` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `vehicleSpeed` int(128) DEFAULT NULL,
  `fuelTank` int(128) DEFAULT NULL,
  `grossVehicleWeight` varchar(256) DEFAULT NULL,
  `kerbWeight` varchar(256) DEFAULT NULL,
  `overallLength` varchar(256) DEFAULT NULL,
  `loadBodyLength` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`vehicleCategoryId`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiclecategories`
--

LOCK TABLES `vehiclecategories` WRITE;
/*!40000 ALTER TABLE `vehiclecategories` DISABLE KEYS */;
INSERT INTO `vehiclecategories` VALUES (1,'open-ape',3,2,4,'20','100','2018-07-04 04:52:00','2018-07-04 04:52:00',30,150,'6000','1','12000','10000'),(2,'close-ape',3,3,6,'25','300','2018-07-04 04:56:53','2018-07-04 04:56:53',45,250,'8000','1','15000','12000'),(3,'LCV(light commertial vehicles)',4,4,8,'30','500','2018-07-04 04:58:21','2018-07-04 04:58:21',50,300,'10000','2','16000','13000'),(4,'close-LCV(light commertial vehicles)',6,6,10,'35','800','2018-07-04 05:31:12','2018-07-04 05:31:12',55,350,'12000','2','18000','16000'),(5,'canter 3mt 4wheel',8,8,12,'40','1000','2018-07-04 05:32:24','2018-07-04 05:32:24',60,400,'14000','3','20000','18000'),(6,'close-canter 3mt 4wheel',NULL,NULL,NULL,NULL,NULL,'2018-11-22 07:55:45','2018-11-22 07:55:45',NULL,NULL,NULL,'3',NULL,NULL),(7,'open-canter 5mt 4wheel.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:31:36','2018-11-22 09:31:36',NULL,NULL,NULL,'4',NULL,NULL),(8,'close-canter 5mt 4wheel',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:31:36','2018-11-22 09:31:36',NULL,NULL,NULL,'4',NULL,NULL),(9,'LCV-bolero',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:33:50','2018-11-22 09:33:50',NULL,NULL,NULL,'2',NULL,NULL),(10,'open-7 TONNER CARGO TRUCK',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:38:30','2018-11-22 09:38:30',NULL,NULL,NULL,'5',NULL,NULL),(11,'close-7 TONNER CARGO TRUCK',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:38:30','2018-11-22 09:38:30',NULL,NULL,NULL,'5',NULL,NULL),(12,'open-Truck LP Capacity 9MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:42:17','2018-11-22 09:42:17',NULL,NULL,NULL,'6',NULL,NULL),(13,'close-Truck LP Capacity 9MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:42:17','2018-11-22 09:42:17',NULL,NULL,NULL,'6',NULL,NULL),(14,'container 7MT Track -24 feet',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:42:17','2018-11-22 09:42:17',NULL,NULL,NULL,'5',NULL,NULL),(15,'ashok leyland-boss-12MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:46:28','2018-11-22 09:46:28',NULL,NULL,NULL,'7',NULL,NULL),(16,'close-bharat benz-md-1214re-12MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:46:28','2018-11-22 09:46:28',NULL,NULL,NULL,'7',NULL,NULL),(17,'eicher-pro-1110-xp-12MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:48:53','2018-11-22 09:48:53',NULL,NULL,NULL,'7',NULL,NULL),(18,'open-tata-ultra-15MT.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:53:24','2018-11-22 09:53:24',NULL,NULL,NULL,'8',NULL,NULL),(19,'Close -Eicher 15MT.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:53:24','2018-11-22 09:53:24',NULL,NULL,NULL,'8',NULL,NULL),(20,'close-body-container-21MT.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:53:24','2018-11-22 09:53:24',NULL,NULL,NULL,'9',NULL,NULL),(21,'20MT Taurus',NULL,NULL,NULL,NULL,NULL,'2018-11-22 09:53:24','2018-11-22 09:53:24',NULL,NULL,NULL,'9',NULL,NULL),(22,'truck 25MT 14 wheel.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:04:03','2018-11-22 10:04:03',NULL,NULL,NULL,'10',NULL,NULL),(23,'close-multiaxle-blazox-25MT.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:04:03','2018-11-22 10:04:03',NULL,NULL,NULL,'10',NULL,NULL),(24,'full-truck-load-28MT.png',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:04:03','2018-11-22 10:04:03',NULL,NULL,NULL,'11',NULL,NULL),(25,'trailer 28MT 18wheel.png',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:04:03','2018-11-22 10:04:03',NULL,NULL,NULL,'11',NULL,NULL),(26,'close-mahindra-truxo-above28MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:08:08','2018-11-22 10:08:08',NULL,NULL,NULL,'12',NULL,NULL),(27,'close-container-truck-above28MT',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:08:08','2018-11-22 10:08:08',NULL,NULL,NULL,'12',NULL,NULL),(28,'open-tata-lps-40MT.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:08:08','2018-11-22 10:08:08',NULL,NULL,NULL,'13',NULL,NULL),(29,'above40MT flatbed trailer.jpg',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:08:08','2018-11-22 10:08:08',NULL,NULL,NULL,'13',NULL,NULL),(30,'Above40MT Low bed trailers',NULL,NULL,NULL,NULL,NULL,'2018-11-22 10:08:08','2018-11-22 10:08:08',NULL,NULL,NULL,'30',NULL,NULL);
/*!40000 ALTER TABLE `vehiclecategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicleimages`
--

DROP TABLE IF EXISTS `vehicleimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicleimages` (
  `imageId` varchar(45) NOT NULL,
  `vehicleId` varchar(45) DEFAULT NULL,
  `vehicleCategoryId` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `size` blob,
  `path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`imageId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicleimages`
--

LOCK TABLES `vehicleimages` WRITE;
/*!40000 ALTER TABLE `vehicleimages` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicleimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicles` (
  `vehicleId` int(11) NOT NULL,
  `vehicleCategoryId` int(128) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `goodsTypeId` varchar(256) DEFAULT NULL,
  `kerbWeightId` varchar(256) DEFAULT NULL,
  `pricePerKm` double DEFAULT NULL,
  `pricePerMin` double DEFAULT NULL,
  `baseFare` double DEFAULT NULL,
  `commission` varchar(45) DEFAULT NULL,
  `capacity` varchar(45) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT NULL,
  `modificationDate` timestamp NULL DEFAULT NULL,
  `vehicleSpeed` int(128) DEFAULT NULL,
  `fuelTank` int(128) DEFAULT NULL,
  `grossVehicleWeight` varchar(256) DEFAULT NULL,
  `overallLength` varchar(256) DEFAULT NULL,
  `loadBodyLength` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`vehicleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicletypeimages`
--

DROP TABLE IF EXISTS `vehicletypeimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicletypeimages` (
  `imageId` varchar(45) NOT NULL,
  `vehicleId` varchar(45) DEFAULT NULL,
  `vehicleTypeId` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `size` blob,
  `path` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`imageId`),
  KEY `typeId_idx` (`vehicleTypeId`),
  CONSTRAINT `typeId` FOREIGN KEY (`vehicleTypeId`) REFERENCES `vehicletypes` (`vehicleTypeId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicletypeimages`
--

LOCK TABLES `vehicletypeimages` WRITE;
/*!40000 ALTER TABLE `vehicletypeimages` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehicletypeimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicletypes`
--

DROP TABLE IF EXISTS `vehicletypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehicletypes` (
  `vehicleTypeId` int(11) NOT NULL,
  `vehicleCategoryId` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `vehicleId` int(11) DEFAULT NULL,
  PRIMARY KEY (`vehicleTypeId`),
  KEY `vehicleCategoryId_idx` (`vehicleCategoryId`),
  CONSTRAINT `vehicleCategoryId` FOREIGN KEY (`vehicleCategoryId`) REFERENCES `vehiclecategories` (`vehicleCategoryId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicletypes`
--

LOCK TABLES `vehicletypes` WRITE;
/*!40000 ALTER TABLE `vehicletypes` DISABLE KEYS */;
INSERT INTO `vehicletypes` VALUES (1,1,'Mini Truck',1),(2,2,'Canopy Express',2),(3,2,'Pickup Truck',3),(4,2,'Tow Truck',4),(5,2,'Panel Van',5),(6,3,'Box Truck',6),(7,3,'Van',7),(8,3,'Flatbed Truck',8),(9,4,'Garbage Truck',9),(10,4,'Log Carrier',10),(11,4,'Refrigerator Truck',11),(12,4,'Tractor Unit',12),(13,4,'Tank Truck',13),(14,5,'Ballast Tractor',14);
/*!40000 ALTER TABLE `vehicletypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weight`
--

DROP TABLE IF EXISTS `weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weight` (
  `goodsId` int(11) NOT NULL,
  `weight` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weight`
--

LOCK TABLES `weight` WRITE;
/*!40000 ALTER TABLE `weight` DISABLE KEYS */;
INSERT INTO `weight` VALUES (1,'100 - 500MT'),(2,'500 - 1MT'),(3,'1 - 3MT'),(4,'3 - 5MT'),(5,'5 - 7MT'),(6,'7 - 9MT'),(7,'9 - 12MT'),(8,'12 - 15MT'),(9,'15 - 20MT'),(10,'20 - 25MT'),(11,'25 - 28MT'),(12,'Above 28 MT'),(13,'Above 40 MT'),(14,'Do Not Know');
/*!40000 ALTER TABLE `weight` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


CREATE TABLE `severity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(246) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;



INSERT INTO `severity` VALUES (5,'Urgent'),(6,'High'),(7,'Normal'),(8,'Low');


CREATE TABLE `service` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


INSERT INTO `service` VALUES (1,'MMG Industry'),(2,'MMG Cargo'),(3,'MMG Agri'),(4,'MMG Lite');
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-08 17:21:01
