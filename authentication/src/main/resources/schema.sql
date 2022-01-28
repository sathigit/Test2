
CREATE DATABASE  IF NOT EXISTS `auth` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auth`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: auth
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `uuid` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `address1` text,
  `address2` text,
  `landmark` text,
  `countryId` int(128) DEFAULT NULL,
  `stateId` int(128) DEFAULT NULL,
  `cityId` int(128) DEFAULT NULL,
  `pincode` bigint(50) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `roleId` int(11) DEFAULT '0',
  `isActive` tinyint(1) DEFAULT '0',
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `address_cDate_idx` (`createdDate`),
  KEY `address_city_idx` (`cityId`),
  KEY `address_state_idx` (`stateId`)
   KEY `address_role_idx` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `auditId` int(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(64) NOT NULL,
  `roleId` int(11) DEFAULT '0',
  `tableName` varchar(64) NOT NULL,
  `fieldName` varchar(64) NOT NULL,
  `activity` varchar(64) NOT NULL,
  `oldValue` longtext,
  `newValue` longtext,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`auditId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bank_account` (
  `uuid` varchar(50) NOT NULL,
  `profileId` varchar(56) DEFAULT NULL,
  `accountNumber` bigint(128) DEFAULT NULL,
  `bankId` int(128) DEFAULT NULL,
  `ifscCode` varchar(256) DEFAULT NULL,
  `branchName` varchar(256) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`),
    UNIQUE KEY `accountNumber_UNIQUE` (`accountNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `bdm`
--

DROP TABLE IF EXISTS `bdm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdm` (
  `bdmId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`bdmId`),
  KEY `cp_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `bdo`
--

DROP TABLE IF EXISTS `bdo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bdo` (
  `bdoId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `bdmId` varchar(45) DEFAULT NULL,
   `franchiseId` varchar(45) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`bdoId`),
  KEY `bdo_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `channelpartner`
--

DROP TABLE IF EXISTS `channelpartner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channelpartner` (
  `channelPartnerId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `bdmId` varchar(50) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`channelPartnerId`),
  KEY `cp_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coordinator`
--

DROP TABLE IF EXISTS `coordinator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coordinator` (
  `coordinatorId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `franchiseId` varchar(50) DEFAULT NULL,
    `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `isAgency` tinyint(4) DEFAULT '0',
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`coordinatorId`),
  KEY `co_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `isTermsAndCondition` tinyint(1) DEFAULT NULL,
  `termsAndConditionsId` varchar(150) DEFAULT NULL,
  `referenceId` varchar(45) DEFAULT NULL,
  `gstNo` varchar(45) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  KEY `customer_cDate_idx` (`createdDate`),
  UNIQUE KEY `gstNo_UNIQUE` (`gstNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `enterprise`
--

DROP TABLE IF EXISTS `enterprise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enterprise` (
  `enterpriseId` bigint(128) NOT NULL,
  `companyName` varchar(255) DEFAULT NULL,
  `gstNo` varchar(255) DEFAULT NULL,
  `licenseNo` varchar(255) DEFAULT NULL,
  `entrepreneurName` varchar(255) DEFAULT NULL,
  `profileId` varchar(45) DEFAULT NULL,
  `organisationType` varchar(45) DEFAULT NULL,
  `yearOfContract` varchar(45) DEFAULT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `zone` varchar(255) DEFAULT 'NA',
  `cin` varchar(45) DEFAULT 'NA',
  `tin` varchar(45) DEFAULT 'NA',
  `area` varchar(255) DEFAULT 'NA',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `franchiseId` varchar(45) DEFAULT NULL,
  `boardingEnquiryId` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`enterpriseId`),
  UNIQUE KEY `gstNo_UNIQUE` (`gstNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fieldofficer`
--

DROP TABLE IF EXISTS `fieldofficer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldofficer` (
  `fieldOfficerId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `franchiseId` varchar(50) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`fieldOfficerId`),
  KEY `field_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fleetoperator`
--

DROP TABLE IF EXISTS `fleetoperator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fleetoperator` (
  `fleetId` varchar(49) NOT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `yearOfContract` int(128) DEFAULT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `gstNo` varchar(256) DEFAULT NULL,
  `profileId` varchar(45) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `franchiseId` varchar(45) DEFAULT NULL,
  `boardingEnquiryId` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`fleetId`),
  UNIQUE KEY `gstNo_UNIQUE` (`gstNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `franchise`
--

DROP TABLE IF EXISTS `franchise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `franchise` (
  `franchiseId` varchar(256) NOT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `yearOfContract` int(11) DEFAULT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `gstNo` varchar(256) DEFAULT NULL,
  `licenseNo` varchar(256) DEFAULT NULL,
  `proprietorName` varchar(256) DEFAULT NULL,
  `organisationType` int(11) DEFAULT NULL,
  `profileId` varchar(45) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `boardingEnquiryId` varchar(50) DEFAULT NULL,
  `channelPartnerId` varchar(50) DEFAULT NULL,
   `isTag` tinyint(4) DEFAULT '0',
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`franchiseId`),
  UNIQUE KEY `gstNo_UNIQUE` (`gstNo`),
  UNIQUE KEY `licenceNo_UNIQUE` (`licenseNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reason`
--

DROP TABLE IF EXISTS `reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reason` (
  `uuid` varchar(50) NOT NULL,
  `profileId` varchar(56) DEFAULT NULL,
  `reason` varchar(56) DEFAULT NULL,
  `roleId` int(123) DEFAULT NULL,
  `previousStatus` varchar(45) NOT NULL,
  `changedStatus` varchar(45) DEFAULT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `userRoleId` int(123) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `menus`
--

DROP TABLE IF EXISTS `menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menus` (
  `menusId` int(11) NOT NULL,
  `roleId` int(11) DEFAULT NULL,
  `menus` blob,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`menusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus`
--

LOCK TABLES `menus` WRITE;
/*!40000 ALTER TABLE `menus` DISABLE KEYS */;
INSERT INTO `menus` VALUES (1,1,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus\",\n \"link\" : \"bookNow\",\n \"menuId\" : \"0\",\n \"name\" : \"Book Now\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus\",\n \"link\" : \"customerMyTrip\",\n \"menuId\" : \"1\",\n \"name\" : \"My Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus\",\n \"link\" : \"priceChart\",\n \"menuId\" : \"2\",\n \"name\" : \"Price Chart\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"helpLine\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Help Center\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"Customer\",\n \"roleId\" : \"1\"\n}','Customer'),(2,2,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"driverDashboard\",\n \"menuId\" : \"dr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"link\" : \"driverMyTrips\",\n \"menuId\" : \"dr-mt\",\n \"name\" : \"My Trip\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-star-half-o fa-2x\",\n \"link\" : \"driverRatings\",\n \"menuId\" : \"dr-rt\",\n \"name\" : \"Ratings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"earnings\",\n \"menuId\" : \"dr-rt\",\n \"name\" : \"My Earnings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question-circle fa-2x\",\n \"menuId\" : \"fr-fc\",\n \"name\" : \"Help Center\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"helpCenter\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Add Issues\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"helpCenterList\",\n \"menuId\" : \"fr-fc-cl\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n }\n ],\n \"name\" : \"driver\",\n \"roleId\" : \"2\"\n}','Driver'),(3,9,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"franchiseDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"link\" : \"franchiseBookingList\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-registered fa-2x\",\n \"link\" : \"franchiseRegistration\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-users fa-2x\",\n \"link\" : \"franchiseEmployees\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Employees\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"franchisePayment\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Payment\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question-circle fa-2x\",\n \"menuId\" : \"fr-fc\",\n \"name\" : \"Help Center\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseHelpCenter\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Add Issues\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseHelpCenterList\",\n \"menuId\" : \"fr-fc-cl\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"menuId\" : \"fr-fc\",\n \"name\" : \"Bidding\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseBidding\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Add Bidding\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseViewBidding\",\n \"menuId\" : \"fr-fc-cl\",\n \"name\" : \"View Bidding\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"link\" : \"franchiseDirectBookings\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Direct Bookings\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"franchies\",\n \"roleId\" : \"9\"\n}','Franchies'),(4,7,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsDashboard\",\n			\"menuId\" : \"tp-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsFleetOperatorDashboard\",\n			\"menuId\" : \"tp-fo\",\n			\"name\" : \"Fleet Operator\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFleetOperatorUploadedList\",\n					\"menuId\" : \"tp-fo-fl\",\n					\"name\" : \"Uploaded Details\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsWarehouseDashboard\",\n			\"menuId\" : \"tp-wh\",\n			\"name\" : \"Warehouse\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsFranchiseDashboard\",\n			\"menuId\" : \"tp-fr\",\n			\"name\" : \"Franchise\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsInsuranceDashboard\",\n			\"menuId\" : \"tp-is\",\n			\"name\" : \"Insurance\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceRegistration\",\n					\"menuId\" : \"tp-is-fr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsurancePlanList\",\n					\"menuId\" : \"tp-is-pl\",\n					\"name\" : \"Plan List\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceList\",\n					\"menuId\" : \"tp-is-fl\",\n					\"name\" : \"View\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsOperationalTeamDashboard\",\n			\"menuId\" : \"tp-ot\",\n			\"name\" : \"Operational Team\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamRegistration\",\n					\"menuId\" : \"tp-ot-rg\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamList\",\n					\"menuId\" : \"tp-ot-lt\",\n					\"name\" : \"View\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"menuId\" : \"tp-ts\",\n			\"name\" : \"Terms and Conditions\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsTermsAndConditionList\",\n					\"menuId\" : \"tp-ts-fl\",\n					\"name\" : \"View\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceInsuranceCompanyList\",\n					\"menuId\" : \"tp-ts-cl\",\n					\"name\" : \"Insurance Company\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"onboardingDashboard\",\n			\"menuId\" : \"tp-ts\",\n			\"name\" : \"Onboarding\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFranchiseEnquiry\",\n					\"menuId\" : \"tp-tr-eq\",\n					\"name\" : \"Franchise Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFleetEnquiry\",\n					\"menuId\" : \"tp-fl-eq\",\n					\"name\" : \" Fleet Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsWarehouseEnquiry\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \" Warehouse Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsDriverRequest\",\n					\"menuId\" : \"tp-dr-eq\",\n					\"name\" : \"  Driver Request\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsVehicleRequest\",\n					\"menuId\" : \"tp-vh-eq\",\n					\"name\" : \"  Vehicle Request\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"EnterpriseEnquiry\",\n					\"menuId\" : \"tp-er-eq\",\n					\"name\" : \" Enterprise Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsLabourRequest\",\n					\"menuId\" : \"tp-lr-eq\",\n					\"name\" : \" Labour Request\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsDirectBookings\",\n			\"menuId\" : \"tp-db\",\n			\"name\" : \"Direct Bookings\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsBidding\",\n			\"menuId\" : \"tp-db\",\n			\"name\" : \"Bidding\",\n			\"selected\" : \"true\"\n		}\n	],\n	\"name\" : \"tieups\",\n	\"roleId\" : \"7\"\n}','Tieups'),(5,3,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"fleetOperatorDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"fleetOperatorBookingList\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"menuId\" : \"fr-di\",\n \"name\" : \"Driver\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetOperatorDriverRegistration\",\n \"menuId\" : \"fr-di-dr\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetOperatorDriverList\",\n \"menuId\" : \"fr-di-dl\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"engagedDriver\",\n \"menuId\" : \"fr-di-dl\",\n \"name\" : \"Engaged Driver\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"menuId\" : \"fr-lb\",\n \"name\" : \"Vechicle\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetOperatorVehicleRegistration\",\n \"menuId\" : \"fr-lb-lr\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetOperatorVehicleList\",\n \"menuId\" : \"fr-lb-ll\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"engagedVehicle\",\n \"menuId\" : \"fr-di-dl\",\n \"name\" : \"Engaged Vehicle\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question-circle fa-2x\",\n \"menuId\" : \"fr-fc\",\n \"name\" : \"Help Center\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetHelpCenter\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Add Issues\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetHelpCenterList\",\n \"menuId\" : \"fr-fc-cl\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n }\n ],\n \"name\" : \"fleetOperator\",\n \"roleId\" : \"3\"\n}','Fleet Operator'),(6,11,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"fleetOperatorDriverDashboard\",\n \"menuId\" : \"fodr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"fleetOperatorDriverMyTrips\",\n \"menuId\" : \"fodr-mt\",\n \"name\" : \"My Trip\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"rating\",\n \"menuId\" : \"fodr-rt\",\n \"name\" : \"Ratings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question-circle fa-2x\",\n \"menuId\" : \"fr-fc\",\n \"name\" : \"Help Center\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetDriverHelpCenter\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Add Issues\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetDriverHelpCenterList\",\n \"menuId\" : \"fr-fc-cl\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n }\n ],\n \"name\" : \"fleetDriver\",\n \"roleId\" : \"11\"\n}','Fleet Driver'),(7,5,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"ownerDashboard\",\n \"menuId\" : \"ow-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"menuId\" : \"ow-gn\",\n \"name\" : \"General\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-truck fa-2x\",\n \"menuId\" : \"ow-gn-vm\",\n \"name\" : \"Vehicle Management\",\n \"selected\" : \"true\",\n \"subSubMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"vehicles\",\n \"menuId\" : \"ow-gn-vm-vs\",\n \"name\" : \"Vehicle Category\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"agriVehciles\",\n \"menuId\" : \"ow-gn-vm-vs\",\n \"name\" : \"Agri  & Movers Vehcile\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"specialVehicle\",\n \"menuId\" : \"ow-gn-vm-vs\",\n \"name\" : \"Special Vehicle\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"menuId\" : \"ow-gn-lm\",\n \"name\" : \"Location Management\",\n \"selected\" : \"true\",\n \"subSubMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"country\",\n \"menuId\" : \"ow-gn-vm-cn\",\n \"name\" : \"Country\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"state\",\n \"menuId\" : \"ow-gn-vm-st\",\n \"name\" : \"State\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"city\",\n \"menuId\" : \"ow-gn-vm-ct\",\n \"name\" : \"City\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"organizationalType\",\n \"menuId\" : \"ow-gn-ot\",\n \"name\" : \"Organizational Type\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"bank\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Bank\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"customerIssue\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Customer Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseIssue\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Franchise Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetIssue\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Fleet Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"severity\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Severity\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"role\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Role\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"goodsType\",\n \"menuId\" : \"ow-gn-bk\",\n \"name\" : \"Goods Type\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-building-o fa-2x\",\n \"link\" : \"ownerFleetOperatorDashboard\",\n \"menuId\" : \"ow-fo\",\n \"name\" : \"Fleet Operator\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetDriverManagement\",\n \"menuId\" : \"ow-fo-dm\",\n \"name\" : \"Driver Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fleetVehicleManagement\",\n \"menuId\" : \"ow-fo-vm\",\n \"name\" : \"Vehicle Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFleetTrip\",\n \"menuId\" : \"ow-fo-gt\",\n \"name\" : \"Trips\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-building fa-2x\",\n \"link\" : \"ownerFranchisedashboard\",\n \"menuId\" : \"ow-fr\",\n \"name\" : \"Franchise\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFranchiseLabourManagement\",\n \"menuId\" : \"ow-fr-ld\",\n \"name\" : \"Labour Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFranchiseDriverManagement\",\n \"menuId\" : \"ow-fr-dm\",\n \"name\" : \"Driver Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFranchiseVehicleManagement\",\n \"menuId\" : \"ow-fr-vm\",\n \"name\" : \"Vehicle Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFranchiseTrip\",\n \"menuId\" : \"ow-fr-gt\",\n \"name\" : \"Trips\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerFranchiseFrequentCustomer\",\n \"menuId\" : \"ow-fr-fd\",\n \"name\" : \"Frequent Customer\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-link fa-2x\",\n \"link\" : \"ownerTieupsDashboard\",\n \"menuId\" : \"ow-ti\",\n \"name\" : \"Tieups\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerTieupsFleet\",\n \"menuId\" : \"ow-ti-fo\",\n \"name\" : \"FleetOperator Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerTieupsWarehouse\",\n \"menuId\" : \"ow-ti-wh\",\n \"name\" : \"Warehouse Management\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerTieupsFranchise\",\n \"menuId\" : \"ow-ti-fm\",\n \"name\" : \"Franchise Management\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-rupee fa-2x\",\n \"menuId\" : \"ow-ff\",\n \"name\" : \"Finance and Forecast\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"revenue\",\n \"menuId\" : \"ow-ff-re\",\n \"name\" : \"Revenue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"expense\",\n \"menuId\" : \"ow-ff-ex\",\n \"name\" : \"Expense\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"directCost\",\n \"menuId\" : \"ow-ff-dc\",\n \"name\" : \"Direct Cost\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"personnel\",\n \"menuId\" : \"ow-ff-ps\",\n \"name\" : \"Personnel\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"fareestimationCalculation\",\n \"menuId\" : \"ow-fa\",\n \"name\" : \"Fare and Environments\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"catalog\",\n \"menuId\" : \"ow-fa-ca\",\n \"name\" : \"Catalog\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"vehicleFares\",\n \"menuId\" : \"ow-fa-vf\",\n \"name\" : \"Vehicle Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"fuelFares\",\n \"menuId\" : \"ow-fa-ff\",\n \"name\" : \"Fuel Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"taxFares\",\n \"menuId\" : \"ow-fa-tf\",\n \"name\" : \"Tax Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"goodsFares\",\n \"menuId\" : \"ow-fa-gf\",\n \"name\" : \"Goods Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"insuranceFares\",\n \"menuId\" : \"ow-fa-if\",\n \"name\" : \"Insurance Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"promoFares\",\n \"menuId\" : \"ow-fa-pf\",\n \"name\" : \"Promo Code Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"labourFares\",\n \"menuId\" : \"ow-fa-lf\",\n \"name\" : \"Labours Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"cancellationFares\",\n \"menuId\" : \"ow-fa-cf\",\n \"name\" : \"Cancellation Fares\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"distribution\",\n \"menuId\" : \"ow-fa-d\",\n \"name\" : \"Distribution\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user fa-2x\",\n \"menuId\" : \"ow-ad\",\n \"name\" : \"Employee\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"ownerEmployeeRegistration\",\n \"menuId\" : \"ow-ad-if\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"employeeList\",\n \"menuId\" : \"ow-ad-if\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"customerList\",\n \"menuId\" : \"ow-cl\",\n \"name\" : \"Customer\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"franchiseTransaction\",\n \"menuId\" : \"ow-ft\",\n \"name\" : \"Franchise Transaction\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"pendingCollection\",\n \"menuId\" : \"ow-ft-pd\",\n \"name\" : \"Pending Collection\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-5x\",\n \"link\" : \"ownerBookings\",\n \"menuId\" : \"ow-db-fr\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"franchiseBookings\",\n \"menuId\" : \"ow-ft-pd\",\n \"name\" : \"Franchise Bookings\",\n \"selected\" : \"true\"\n }\n ]\n }\n ],\n \"name\" : \"owner\",\n \"roleId\" : \"5\"\n}','Owner'),(8,4,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"warehouseDashboard\",\n \"menuId\" : \"wh-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-secret fa-2x\",\n \"menuId\" : \"wh-ag\",\n \"name\" : \"Arrived Goods\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"addArrivedGoods\",\n \"menuId\" : \"wh-ag-gd\",\n \"name\" : \"Add Arrived Goods\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"viewArrivedGoods\",\n \"menuId\" : \"wh-ag-vg\",\n \"name\" : \"View Arrived Goods\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-secret fa-2x\",\n \"menuId\" : \"wh-dg\",\n \"name\" : \"Dispatched Goods\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"addDispatchedGoods\",\n \"menuId\" : \"wh-dg-gd\",\n \"name\" : \"Add Dispatched Goods\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"viewDispatchedGoods\",\n \"menuId\" : \"wh-dg-vg\",\n \"name\" : \"View Dispatched Goods\",\n \"selected\" : \"true\"\n }\n ]\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user\",\n \"menuId\" : \"wh-lb\",\n \"name\" : \"Labour\",\n \"selected\" : \"true\",\n \"subMenus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"warehouseLabourRegistration\",\n \"menuId\" : \"wh-lb-rg\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"null\",\n \"link\" : \"warehouseLabourList\",\n \"menuId\" : \"wh-lb-lt\",\n \"name\" : \"View\",\n \"selected\" : \"true\"\n }\n ]\n }\n ],\n \"name\" : \"warehouse\",\n \"roleId\" : \"4\"\n}','Warehouse'),(9,15,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"helpCenterDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"customerLevel1Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Customer Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"driverLevel1Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Driver Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"franchiseLevel1Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Franchise Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"fleetLevel1Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Fleet Issue\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"helpCenter\",\n \"roleId\" : \"15\"\n}','Help Center'),(10,12,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"coordinatorDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"link\" : \"franchiseBookingList\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-registered fa-2x\",\n \"link\" : \"franchiseRegistration\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Registration\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"franchiesCoordinator\",\n \"roleId\" : \"12\"\n}','Franchise Coordinator'),(11,13,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"fieldOfficerDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-2x\",\n \"link\" : \"fieldOfficer\",\n \"menuId\" : \"fr-bk\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"franchiseFieldOfficer\",\n \"roleId\" : \"13\"\n}','Franchise Field Officer'),(12,14,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"level2IssuesDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"customerLevel2Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Customer Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"driverLevel2Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Driver Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"franchiseLevel2Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Franchise Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"fleetLevel2Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Fleet Issue\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"helpCenterLevel2\",\n \"roleId\" : \"14\"\n}','Help Center Level2'),(13,16,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-dashboard fa-2x\",\n \"link\" : \"level3IssuesDashboard\",\n \"menuId\" : \"fr-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"customerLevel3Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Customer Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"driverLevel3Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Driver Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"franchiseLevel3Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Franchise Issue\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-question fa-2x\",\n \"link\" : \"fleetLevel3Issue\",\n \"menuId\" : \"fr-fc-cr\",\n \"name\" : \"Fleet Issue\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"helpCenterLevel3\",\n \"roleId\" : \"16\"\n}','help Center Level3'),(14,18,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamDashboard\",\n			\"menuId\" : \"tp-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamFleetOperatorDashboard\",\n			\"menuId\" : \"tp-fo\",\n			\"name\" : \"Fleet Operator\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"opsFleetOperatorUploadedList\",\n					\"menuId\" : \"tp-fo-fl\",\n					\"name\" : \"Uploaded Details\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamWarehouseDashboard\",\n			\"menuId\" : \"tp-wh\",\n			\"name\" : \"Warehouse\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamFranchiseDashboard\",\n			\"menuId\" : \"tp-fr\",\n			\"name\" : \"Franchise\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamOnBoardingDashboard\",\n			\"menuId\" : \"tp-ts\",\n			\"name\" : \"On Boarding\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamFranchiseEnquiry\",\n					\"menuId\" : \"tp-tr-eq\",\n					\"name\" : \"Franchise Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamFleetEnquiry\",\n					\"menuId\" : \"tp-fl-eq\",\n					\"name\" : \" Fleet Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamWarehouseEnquiry\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \" Warehouse Enquiry\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamDriverRequest\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \"  Driver Request\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamVehicleRequest\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \"  Vehicle Request\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamLabourRequest\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \" Labour Request\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"opsEnterpriseEnquiry\",\n					\"menuId\" : \"tp-wh-eq\",\n					\"name\" : \" Enterprise Request\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-money fa-2x\",\n			\"link\" : \"operationalTeamFareCalculation\",\n			\"menuId\" : \"tp-db-fr\",\n			\"name\" : \"Fare\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-address-card fa-5x\",\n			\"link\" : \"operationalTeamBookings\",\n			\"menuId\" : \"tp-db-fr\",\n			\"name\" : \"Bookings\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamAllBookings\",\n					\"menuId\" : \"tp-db-ab\",\n					\"name\" : \"All Bookings\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-address-card fa-5x\",\n			\"link\" : \"operationalTeamOfflineBookings\",\n			\"menuId\" : \"tp-db-fr\",\n			\"name\" : \"Offline Booking\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-address-card fa-5x\",\n			\"link\" : \"opsBidding\",\n			\"menuId\" : \"tp-db-fr\",\n			\"name\" : \"Bidding\",\n			\"selected\" : \"true\"\n		}\n	],\n	\"name\" : \"operationalTeam\",\n	\"roleId\" : \"18\"\n}','Operational Team'),(15,20,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"bdmDashboard\",\n \"menuId\" : \"tp-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"ViewFranchise\",\n \"menuId\" : \"tp-ts\",\n \"name\" : \"Franchise\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"bdmFare\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Fare\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-line-chart fa-2x\",\n \"link\" : \"bdmCrm\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"CRM\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"bdmEnterpriselist\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Enterprise\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user fa-5x\",\n \"link\" : \"bdmFreqCustomerList\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Frequent Customer\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-5x\",\n \"link\" : \"bdmOfflineBookings\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Offline Booking\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-5x\",\n \"link\" : \"bdmBidding\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Bidding\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"bdmDashboard\",\n \"roleId\" : \"20\"\n}','Business Development Manager'),(16,21,'{\n \"menus\" : [\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"bdoDashboard\",\n \"menuId\" : \"tp-db\",\n \"name\" : \"Dashboard\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user-plus fa-2x\",\n \"link\" : \"bdobookings\",\n \"menuId\" : \"tp-ts\",\n \"name\" : \"Bookings\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"bdoFare\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Fare\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-line-chart fa-2x\",\n \"link\" : \"bdoCrm\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"CRM\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-money fa-2x\",\n \"link\" : \"bdoEnterpriselist\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Enterprise\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-user fa-5x\",\n \"link\" : \"bdoFreqCustomerList\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Frequent Customer\",\n \"selected\" : \"true\"\n },\n {\n \"active\" : \"false\",\n \"desc\" : \"null\",\n \"icon\" : \"fa fa-address-card fa-5x\",\n \"link\" : \"bdoOfflineBookings\",\n \"menuId\" : \"tp-db-fr\",\n \"name\" : \"Offline Booking\",\n \"selected\" : \"true\"\n }\n ],\n \"name\" : \"bdoDashboard\",\n \"roleId\" : \"21\"\n}','Business Development Officer'),(17,22,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamDashboard\",\n			\"menuId\" : \"be-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamFranchiseDashboard\",\n			\"menuId\" : \"be-fr\",\n			\"name\" : \"Franchise\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"operationalTeamOnBoardingDashboard\",\n			\"menuId\" : \"be-ts\",\n			\"name\" : \"On Boarding\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"operationalTeamFranchiseEnquiry\",\n					\"menuId\" : \"be-tr-eq\",\n					\"name\" : \"Franchise Enquiry\",\n					\"selected\" : \"true\"\n				}\n			]\n		}\n	\n	],\n	\"name\" : \"businessExecutive\",\n	\"roleId\" : \"22\"\n}','Business Executive');
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oauth_access_token`
--

DROP TABLE IF EXISTS `oauth_access_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_access_token` (
  `authentication_id` varchar(256) NOT NULL,
  `token_id` varchar(256) NOT NULL,
  `token` blob NOT NULL,
  `user_name` varchar(256) NOT NULL,
  `client_id` varchar(256) NOT NULL,
  `authentication` blob NOT NULL,
  `refresh_token` varchar(256) NOT NULL,
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `oauth_refresh_token`
--

DROP TABLE IF EXISTS `oauth_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) NOT NULL,
  `token` blob NOT NULL,
  `authentication` blob NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operational_team`
--

DROP TABLE IF EXISTS `operational_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operational_team` (
  `operationalTeamId` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `assignedStateId` tinyint(1) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`operationalTeamId`),
  KEY `ot_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otp`
--

DROP TABLE IF EXISTS `otp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `mobileNumber` varchar(32) DEFAULT NULL,
  `emailId` varchar(32) DEFAULT NULL,
  `otp` varchar(10) DEFAULT NULL,
  `isChecked` tinyint(1) DEFAULT '0',
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `otp_moblie` (`mobileNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` varchar(50) NOT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `mobileNumber` varchar(50) DEFAULT NULL,
  `alternativeNumber` varchar(50) DEFAULT NULL,
  `emailId` varchar(256) DEFAULT NULL,
  `defaultroleId` int(128) DEFAULT NULL,
  `genderId` int(10) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `confirmPassword` varchar(256) DEFAULT NULL,
  `aadharNumber` bigint(128) DEFAULT NULL,
  `panNumber` varchar(256) DEFAULT NULL,
  `profileSource` varchar(10) DEFAULT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modifiedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `profile_mobile` (`mobileNumber`),
  UNIQUE KEY `emailId_UNIQUE` (`emailId`),
  KEY `profile_cDate_idx` (`createdDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profileimage`
--

DROP TABLE IF EXISTS `profileimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profileimage` (
  `uuid` varchar(256) NOT NULL,
  `roleId` int(128) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `type` varchar(256) DEFAULT NULL,
  `size` double DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `category` varchar(256) DEFAULT NULL,
  `profileId` varchar(256) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profilerole`
--

DROP TABLE IF EXISTS `profilerole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profilerole` (
  `uuid` varchar(50) NOT NULL,
  `profileId` varchar(50) DEFAULT NULL,
  `roleId` int(128) DEFAULT NULL,
  `appTokenId` varchar(256) DEFAULT NULL,
  `webTokenId` varchar(256) DEFAULT NULL,
  `isActive` tinyint(1) DEFAULT '1',
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(128) NOT NULL,
  `roleName` varchar(256) NOT NULL,
  `createdDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `path` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--
LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'customer','2018-04-20 22:02:54','2018-04-20 22:02:54',1,'bookNow'),(2,'driver','2018-04-20 22:02:54','2018-04-20 22:02:54',1,'driverDashboard'),(3,'fleetOperator','2018-04-20 22:02:54','2018-04-20 22:02:54',1,'fleetOperatorDashboard'),(4,'warehouse','2018-05-03 20:39:13','2018-05-03 20:39:13',1,'warehouseDashboard'),(5,'owner','2018-05-04 15:02:02','2018-05-04 15:02:02',1,'ownerDashboard'),(6,'employee','2018-05-08 14:33:02','2018-05-08 14:33:02',0,NULL),(7,'tieups','2018-05-08 16:10:42','2018-05-08 16:10:42',1,'tieupsDashboard'),(8,'fareestimation','2018-05-12 23:05:39','2018-05-12 23:05:39',0,NULL),(9,'Franchise','2018-06-19 07:49:14','2018-06-19 07:49:14',1,'franchiseDashboard'),(11,'fleetDriver','2018-09-27 00:48:14','2018-09-27 00:48:14',0,'fleetOperatorDriverDashboard'),(12,'franchiseCoordinator','2019-04-04 22:16:51','2019-04-04 22:16:51',1,'coordinatorDashboard'),(13,'franchiseFieldOfficer','2019-04-04 22:16:51','2019-04-04 22:16:51',1,'fieldOfficerDashboard'),(14,'helpCenterLevel2','2019-04-28 20:12:37','2019-04-28 20:12:37',0,'level2IssuesDashboard'),(15,'helpCenter','2019-03-18 21:39:59','2019-03-18 21:39:59',0,'helpCenterDashboard'),(16,'helpCenterLevel3','2019-04-28 20:12:37','2019-04-28 20:12:37',0,'level3IssuesDashboard'),(18,'Operational Team','2019-08-26 23:43:47','2019-08-26 23:43:47',1,'tieupsOperationalTeamDashboard'),(19,'Enterprise','2019-08-31 00:14:16','2019-08-31 00:14:16',1,NULL),(20,'BDM','2019-11-06 23:37:32','2019-11-06 23:37:32',1,'bdmDashboard'),(21,'BDO','2019-11-11 14:48:59','2019-11-11 14:48:59',1,'bdoDashboard'),(22,'Channel partner','2020-07-02 08:01:57','2020-07-02 08:01:57',1,'executiveDashboard'),(23,'test1','2020-09-28 10:04:25','2020-09-28 10:04:25',0,NULL);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `session` (
  `sessionId` bigint(128) NOT NULL,
  `userId` varchar(45) DEFAULT NULL,
  `roleId` int(128) DEFAULT 0,
  `lastAccessTime` date DEFAULT NULL,
  `emailId` varchar(45) DEFAULT NULL,
  `isActive` tinyint(4) DEFAULT NULL,
  `accessToken` text,
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `wareHouseId` varchar(49) NOT NULL,
  `profileId` varchar(45) DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `yearOfContract` int(11) DEFAULT NULL,
  `startDate` timestamp NULL DEFAULT NULL,
  `endDate` timestamp NULL DEFAULT NULL,
  `maxCapacity` int(128) DEFAULT NULL,
  `registrationNumber` varchar(256) DEFAULT NULL,
  `mdName` varchar(256) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `franchiseId` varchar(45) DEFAULT NULL,
  `boardingEnquiryId` varchar(50) DEFAULT NULL,
  `createdDate` datetime DEFAULT NULL,
  `modifiedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`wareHouseId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `boardingrequest`
--

DROP TABLE IF EXISTS `boardingrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `boardingrequest` (
  `uuid` varchar(255) NOT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `mobileNumber` varchar(128) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `roleId` int(128) DEFAULT NULL,
  `message` text,
  `countryId` int(11) DEFAULT NULL,
  `stateId` int(11) DEFAULT NULL,
  `cityId` int(11) DEFAULT NULL,
  `requestNumber` varchar(55) DEFAULT NULL,
  `refferenceCode` varchar(45) DEFAULT NULL,
  `validateEnquiry` varchar(255) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  `status` varchar(244) NOT NULL DEFAULT 'PENDING',
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `requestNumber_UNIQUE` (`requestNumber`),
  KEY `boardingrequest_status_idx` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `boardingrequest`
--

LOCK TABLES `boardingrequest` WRITE;
/*!40000 ALTER TABLE `boardingrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `boardingrequest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

DROP TABLE IF EXISTS `enquiryreason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

CREATE TABLE `enquiryreason` (
  `uuid` varchar(255) NOT NULL,
  `userId` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `enquiryId` varchar(255) DEFAULT NULL,
  `previousStatus` varchar(45) DEFAULT NULL,
  `changedStatus` varchar(45) DEFAULT NULL,
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enquiryreason`
--

LOCK TABLES `enquiryreason` WRITE;
/*!40000 ALTER TABLE `enquiryreason` DISABLE KEYS */;
/*!40000 ALTER TABLE `enquiryreason` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;


--
-- Table structure for table `customerlead`
--

DROP TABLE IF EXISTS `customerlead`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customerlead` (
  `uuid` varchar(50) NOT NULL,
  `platform` varchar(256) DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `doorNumber` varchar(256) DEFAULT NULL,
  `street` varchar(256) DEFAULT NULL,
  `cityId` int(128) DEFAULT NULL,
  `stateId` int(128) DEFAULT NULL,
  `countryId` int(128) DEFAULT NULL,
  `pincode` bigint(128) DEFAULT NULL,
  `mobileNumber` bigint(128) DEFAULT NULL,
  `emailId` varchar(256) DEFAULT NULL,
  `leadStatusId` varchar(256) DEFAULT NULL,
  `leadProfessionId` varchar(256) DEFAULT NULL,
  `leadRemarksId` varchar(256) DEFAULT NULL,
  `comment` varchar(256) DEFAULT NULL,
  `uploadedById` varchar(256) DEFAULT NULL,
  `assignedId` varchar(256) DEFAULT NULL,
  `assignedDate` timestamp NULL DEFAULT NULL,
  `callDate` timestamp NULL DEFAULT NULL,
  `status` varchar(256) DEFAULT 'PENDING',
  `createdDate` timestamp NULL DEFAULT NULL,
  `modifiedDate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `customerlead`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-09-09 10:52:24