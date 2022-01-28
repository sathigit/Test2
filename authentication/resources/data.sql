CREATE DATABASE  IF NOT EXISTS `auth` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `auth`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: auth
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
-- Table structure for table `drivertype`
--

DROP TABLE IF EXISTS `drivertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `drivertype` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `driverType` varchar(256) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `drivertype`
--

LOCK TABLES `drivertype` WRITE;
/*!40000 ALTER TABLE `drivertype` DISABLE KEYS */;
INSERT INTO `drivertype` VALUES (1,'fullTime','2018-04-29 04:54:03','2018-04-29 04:54:03',0),(2,'partTime','2018-04-29 04:54:03','2018-04-29 04:54:03',0);
/*!40000 ALTER TABLE `drivertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `licencecategory`
--

DROP TABLE IF EXISTS `licencecategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `licencecategory` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `licenceCategoryName` varchar(256) DEFAULT NULL,
  `creationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `licencecategory`
--

LOCK TABLES `licencecategory` WRITE;
/*!40000 ALTER TABLE `licencecategory` DISABLE KEYS */;
INSERT INTO `licencecategory` VALUES (1,'LHV','2018-04-30 14:18:01','2018-04-30 14:18:01',0),(2,'HMV','2018-04-30 14:18:01','2018-04-30 14:18:01',0),(3,'HTV','2018-04-30 14:18:01','2018-04-30 14:18:01',0);
/*!40000 ALTER TABLE `licencecategory` ENABLE KEYS */;
UNLOCK TABLES;

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
INSERT INTO `menus` VALUES (1,1,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus\",\n			\"link\" : \"bookNow\",\n			\"menuId\" : \"0\",\n			\"name\" : \"Book Now\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus\",\n			\"link\" : \"customerMyTrip\",\n			\"menuId\" : \"1\",\n			\"name\" : \"My Trip\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus\",\n			\"link\" : \"priceChart\",\n			\"menuId\" : \"2\",\n			\"name\" : \"Price Chart\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus\",\n			\"link\" : \"helpLine\",\n			\"menuId\" : \"4\",\n			\"name\" : \"Help Line\",\n			\"selected\" : \"true\"\n		}\n	],\n	\"name\" : \"Customer\",\n	\"roleId\" : \"1\"\n}','Customer'),(2,2,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"driverDashboard\",\n			\"menuId\" : \"dr-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-address-card fa-2x\",\n			\"link\" : \"driverMyTrips\",\n			\"menuId\" : \"dr-mt\",\n			\"name\" : \"My Trip\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-star-half-o fa-2x\",\n			\"link\" : \"rating\",\n			\"menuId\" : \"dr-rt\",\n			\"name\" : \"Ratings\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-question-circle fa-2x\",\n			\"link\" : \"/helpLine\",\n			\"menuId\" : \"ow-db\",\n			\"name\" : \"Help Line\",\n			\"selected\" : \"true\"\n		}\n	],\n	\"name\" : \"driver\",\n	\"roleId\" : \"2\"\n}','Driver'),(3,9,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"franchiseDashboard\",\n			\"menuId\" : \"fr-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-address-card fa-2x\",\n			\"link\" : \"franchiseBookingList\",\n			\"menuId\" : \"fr-bk\",\n			\"name\" : \"Bookings\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-secret fa-2x\",\n			\"menuId\" : \"fr-di\",\n			\"name\" : \"Driver\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseDriverRegistration\",\n					\"menuId\" : \"fr-di-dr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseDriverList\",\n					\"menuId\" : \"fr-di-dl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-male fa-2x\",\n			\"menuId\" : \"fr-lb\",\n			\"name\" : \"Labour\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseLabourRegistration\",\n					\"menuId\" : \"fr-lb-lr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseLabourList\",\n					\"menuId\" : \"fr-lb-ll\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-bus fa-2x\",\n			\"menuId\" : \"fr-vh\",\n			\"name\" : \"Vechicle\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseVehicleRegistration\",\n					\"menuId\" : \"fr-vh-vr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseVehicleList\",\n					\"menuId\" : \"fr-vh-vl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-users fa-2x\",\n			\"menuId\" : \"fr-fc\",\n			\"name\" : \"Frequent Customer\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseCustomerRegistration\",\n					\"menuId\" : \"fr-fc-cr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseFrequentCustomerList\",\n					\"menuId\" : \"fr-fc-cl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseFrequentCustomerTrip\",\n					\"menuId\" : \"fr-fc-tr\",\n					\"name\" : \"Frequent Trip\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-users fa-2x\",\n			\"menuId\" : \"fr-fc\",\n			\"name\" : \"Payment\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"franchiseIncome\",\n					\"menuId\" : \"fr-fc-cr\",\n					\"name\" : \"Franchise Income\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"driverPayment\",\n					\"menuId\" : \"fr-fc-cl\",\n					\"name\" : \"Driver Payment\",\n					\"selected\" : \"true\"\n				}\n			]\n		}\n	],\n	\"name\" : \"franchies\",\n	\"roleId\" : \"9\"\n}','Franchies'),(4,7,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsDashboard\",\n			\"menuId\" : \"tp-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsFleetOperatorDashboard\",\n			\"menuId\" : \"tp-fo\",\n			\"name\" : \"Fleet Operator\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFleetOperatorRegistration\",\n					\"menuId\" : \"tp-fo-fr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFleetOperatorList\",\n					\"menuId\" : \"tp-fo-fl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsWarehouseDashboard\",\n			\"menuId\" : \"tp-wh\",\n			\"name\" : \"Warehouse\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsWarehouseRegistration\",\n					\"menuId\" : \"tp-wh-fr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsWarehouseList\",\n					\"menuId\" : \"tp-wh-fl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsFranchiseDashboard\",\n			\"menuId\" : \"tp-fr\",\n			\"name\" : \"Franchise\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFranchiseRegistration\",\n					\"menuId\" : \"tp-fr-fr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsFranchiseList\",\n					\"menuId\" : \"tp-fr-fl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"tieupsInsuranceDashboard\",\n			\"menuId\" : \"tp-is\",\n			\"name\" : \"Insurance\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceRegistration\",\n					\"menuId\" : \"tp-is-fr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsurancePlanList\",\n					\"menuId\" : \"tp-is-pl\",\n					\"name\" : \"Plan List\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceList\",\n					\"menuId\" : \"tp-is-fl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"menuId\" : \"tp-ts\",\n			\"name\" : \"Terms and Conditions\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsTermsAndConditionList\",\n					\"menuId\" : \"tp-ts-fl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"tieupsInsuranceInsuranceCompanyList\",\n					\"menuId\" : \"tp-ts-cl\",\n					\"name\" : \"Insurance Company\",\n					\"selected\" : \"true\"\n				}\n			]\n		}\n	],\n	\"name\" : \"tieups\",\n	\"roleId\" : \"7\"\n}','Tieups'),(5,3,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"fleetOperatorDashboard\",\n			\"menuId\" : \"fr-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"fleetOperatorBookingList\",\n			\"menuId\" : \"fr-bk\",\n			\"name\" : \"Bookings\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"menuId\" : \"fr-di\",\n			\"name\" : \"Driver\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetOperatorDriverRegistration\",\n					\"menuId\" : \"fr-di-dr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetOperatorDriverList\",\n					\"menuId\" : \"fr-di-dl\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				},\n{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"engagedDriver\",\n					\"menuId\" : \"fr-di-dl\",\n					\"name\" : \"Engaged Driver\",\n					\"selected\" : \"true\"\n				}\n\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"menuId\" : \"fr-lb\",\n			\"name\" : \"Vechicle\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetOperatorVehicleRegistration\",\n					\"menuId\" : \"fr-lb-lr\",\n					\"name\" : \"Registration\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetOperatorVehicleList\",\n					\"menuId\" : \"fr-lb-ll\",\n					\"name\" : \"List\",\n					\"selected\" : \"true\"\n				},\n{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"engagedVehicle\",\n					\"menuId\" : \"fr-di-dl\",\n					\"name\" : \"Engaged Vehicle\",\n					\"selected\" : \"true\"\n				}\n\n			]\n		}\n	],\n	\"name\" : \"fleetOperator\",\n	\"roleId\" : \"3\"\n}','Fleet Operator'),(6,11,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"fleetOperatorDriverDashboard\",\n			\"menuId\" : \"fodr-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"fleetOperatorDriverMyTrips\",\n			\"menuId\" : \"fodr-mt\",\n			\"name\" : \"My Trip\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"rating\",\n			\"menuId\" : \"fodr-rt\",\n			\"name\" : \"Ratings\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"link\" : \"helpLine\",\n			\"menuId\" : \"fodr-hc\",\n			\"name\" : \"Help Line\",\n			\"selected\" : \"true\"\n		}\n	],\n	\"name\" : \"fleetDriver\",\n	\"roleId\" : \"11\"\n}','Fleet Driver'),(7,5,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"ownerDashboard\",\n			\"menuId\" : \"ow-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-plus fa-2x\",\n			\"menuId\" : \"ow-gn\",\n			\"name\" : \"General\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"fa fa-truck fa-2x\",\n					\"menuId\" : \"ow-gn-vm\",\n					\"name\" : \"Vehicle Management\",\n					\"selected\" : \"true\",\n					\"subSubMenus\" : [\n						{\n							\"active\" : \"false\",\n							\"desc\" : \"null\",\n							\"icon\" : \"null\",\n							\"link\" : \"vehicleType\",\n							\"menuId\" : \"ow-gn-vm-vc\",\n							\"name\" : \"Vehicle Type\",\n							\"selected\" : \"true\"\n						},\n						{\n							\"active\" : \"false\",\n							\"desc\" : \"null\",\n							\"icon\" : \"null\",\n							\"link\" : \"vehicles\",\n							\"menuId\" : \"ow-gn-vm-vs\",\n							\"name\" : \"Vehicle Category\",\n							\"selected\" : \"true\"\n						}\n					]\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"menuId\" : \"ow-gn-lm\",\n					\"name\" : \"Location Management\",\n					\"selected\" : \"true\",\n					\"subSubMenus\" : [\n						{\n							\"active\" : \"false\",\n							\"desc\" : \"null\",\n							\"icon\" : \"null\",\n							\"link\" : \"country\",\n							\"menuId\" : \"ow-gn-vm-cn\",\n							\"name\" : \"Country\",\n							\"selected\" : \"true\"\n						},\n						{\n							\"active\" : \"false\",\n							\"desc\" : \"null\",\n							\"icon\" : \"null\",\n							\"link\" : \"state\",\n							\"menuId\" : \"ow-gn-vm-st\",\n							\"name\" : \"State\",\n							\"selected\" : \"true\"\n						},\n						{\n							\"active\" : \"false\",\n							\"desc\" : \"null\",\n							\"icon\" : \"null\",\n							\"link\" : \"city\",\n							\"menuId\" : \"ow-gn-vm-ct\",\n							\"name\" : \"City\",\n							\"selected\" : \"true\"\n						}\n					]\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"organizationalType\",\n					\"menuId\" : \"ow-gn-ot\",\n					\"name\" : \"Organizational Type\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"bank\",\n					\"menuId\" : \"ow-gn-bk\",\n					\"name\" : \"Bank\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-building-o fa-2x\",\n			\"link\" : \"ownerFleetOperatorDashboard\",\n			\"menuId\" : \"ow-fo\",\n			\"name\" : \"Fleet Operator\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetDriverManagement\",\n					\"menuId\" : \"ow-fo-dm\",\n					\"name\" : \"Driver Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fleetVehicleManagement\",\n					\"menuId\" : \"ow-fo-vm\",\n					\"name\" : \"Vehicle Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFleetTrip\",\n					\"menuId\" : \"ow-fo-gt\",\n					\"name\" : \"Trips\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-building fa-2x\",\n			\"link\" : \"ownerFranchisedashboard\",\n			\"menuId\" : \"ow-fr\",\n			\"name\" : \"Franchise\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFranchiseLabourManagement\",\n					\"menuId\" : \"ow-fr-ld\",\n					\"name\" : \"Labour Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFranchiseDriverManagement\",\n					\"menuId\" : \"ow-fr-dm\",\n					\"name\" : \"Driver Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFranchiseVehicleManagement\",\n					\"menuId\" : \"ow-fr-vm\",\n					\"name\" : \"Vehicle Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFranchiseTrip\",\n					\"menuId\" : \"ow-fr-gt\",\n					\"name\" : \"Trips\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerFranchiseFrequentCustomer\",\n					\"menuId\" : \"ow-fr-fd\",\n					\"name\" : \"Frequent Customer\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-link fa-2x\",\n			\"link\" : \"ownerTieupsDashboard\",\n			\"menuId\" : \"ow-ti\",\n			\"name\" : \"Tieups\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerTieupsFleet\",\n					\"menuId\" : \"ow-ti-fo\",\n					\"name\" : \"FleetOperator Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerTieupsWarehouse\",\n					\"menuId\" : \"ow-ti-wh\",\n					\"name\" : \"Warehouse Management\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"ownerTieupsFranchise\",\n					\"menuId\" : \"ow-ti-fm\",\n					\"name\" : \"Franchise Management\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-rupee fa-2x\",\n			\"menuId\" : \"ow-ff\",\n			\"name\" : \"Finance and Forecast\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"revenue\",\n					\"menuId\" : \"ow-ff-re\",\n					\"name\" : \"Revenue\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"expense\",\n					\"menuId\" : \"ow-ff-ex\",\n					\"name\" : \"Expense\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"directCost\",\n					\"menuId\" : \"ow-ff-dc\",\n					\"name\" : \"Direct Cost\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"personnel\",\n					\"menuId\" : \"ow-ff-ps\",\n					\"name\" : \"Personnel\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-money fa-2x\",\n			\"link\" : \"fareestimationCalculation\",\n			\"menuId\" : \"ow-fa\",\n			\"name\" : \"Fare and Environments\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"catalog\",\n					\"menuId\" : \"ow-fa-ca\",\n					\"name\" : \"Catalog\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"vehicleFares\",\n					\"menuId\" : \"ow-fa-vf\",\n					\"name\" : \"Vehicle Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"fuelFares\",\n					\"menuId\" : \"ow-fa-ff\",\n					\"name\" : \"Fuel Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"taxFares\",\n					\"menuId\" : \"ow-fa-tf\",\n					\"name\" : \"Tax Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"goodsFares\",\n					\"menuId\" : \"ow-fa-gf\",\n					\"name\" : \"Goods Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"insuranceFares\",\n					\"menuId\" : \"ow-fa-if\",\n					\"name\" : \"Insurance Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"promoFares\",\n					\"menuId\" : \"ow-fa-pf\",\n					\"name\" : \"Promo Code Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"labourFares\",\n					\"menuId\" : \"ow-fa-lf\",\n					\"name\" : \"Labours Fares\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"cancellationFares\",\n					\"menuId\" : \"ow-fa-cf\",\n					\"name\" : \"Cancellation Fares\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user fa-2x\",\n			\"menuId\" : \"ow-ad\",\n			\"name\" : \"Admin\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"admin\",\n					\"menuId\" : \"ow-ad-if\",\n					\"name\" : \"Admin Information\",\n					\"selected\" : \"true\"\n				}\n			]\n		}\n	],\n	\"name\" : \"owner\",\n	\"roleId\" : \"5\"\n}','Owner'),(8,4,'{\n	\"menus\" : [\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-dashboard fa-2x\",\n			\"link\" : \"warehouseDashboard\",\n			\"menuId\" : \"wh-db\",\n			\"name\" : \"Dashboard\",\n			\"selected\" : \"true\"\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-secret fa-2x\",\n			\"menuId\" : \"wh-ag\",\n			\"name\" : \"Arrived Goods\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"addArrivedGoods\",\n					\"menuId\" : \"wh-ag-gd\",\n					\"name\" : \"Add Arrived Goods\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"viewArrivedGoods\",\n					\"menuId\" : \"wh-ag-vg\",\n					\"name\" : \"View Arrived Goods\",\n					\"selected\" : \"true\"\n				}\n			]\n		},\n		{\n			\"active\" : \"false\",\n			\"desc\" : \"null\",\n			\"icon\" : \"fa fa-user-secret fa-2x\",\n			\"menuId\" : \"wh-dg\",\n			\"name\" : \"Dispatched Goods\",\n			\"selected\" : \"true\",\n			\"subMenus\" : [\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"addDispatchedGoods\",\n					\"menuId\" : \"wh-dg-gd\",\n					\"name\" : \"Add Dispatched Goods\",\n					\"selected\" : \"true\"\n				},\n				{\n					\"active\" : \"false\",\n					\"desc\" : \"null\",\n					\"icon\" : \"null\",\n					\"link\" : \"viewDispatchedGoods\",\n					\"menuId\" : \"wh-dg-vg\",\n					\"name\" : \"View Dispatched Goods\",\n					\"selected\" : \"true\"\n				}\n			]\n		}\n	],\n	\"name\" : \"warehouse\",\n	\"roleId\" : \"4\"\n}','Warehouse');
/*!40000 ALTER TABLE `menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(256) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,'total Bookings Customer','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(2,'cancelled Bookings','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(3,'ongoning Orders','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(4,'book A Trip','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(5,'offers','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(6,'help Center','2018-04-21 10:34:53','2018-04-21 10:34:53',0),(7,'Total Users','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(8,'Total Drivers','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(9,'Total Trucks','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(10,'Total Operatrs','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(11,'Total Warehouse','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(12,'Driver Bookings','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(13,'Number Of Kms Drivern','2018-05-05 01:59:54','2018-05-05 01:59:54',0),(14,'Total earnings','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(15,'My ratings','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(17,'Driver On duty','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(19,'Total Bookings Fleet','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(20,'Total labours','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(21,'Engaged trucks','2018-05-05 01:59:55','2018-05-05 01:59:55',0),(22,'Engaged driver','2018-05-05 01:59:55','2018-05-05 01:59:55',0);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile` (
  `id` int(128) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `middleName` varchar(256) DEFAULT NULL,
  `roleId` int(128) DEFAULT NULL,
  `genderId` int(128) DEFAULT NULL,
  `countryId` int(128) DEFAULT NULL,
  `stateId` int(128) DEFAULT NULL,
  `cityId` int(128) DEFAULT NULL,
  `street` varchar(256) DEFAULT NULL,
  `doorNumber` varchar(256) DEFAULT NULL,
  `mobileNumber` bigint(128) DEFAULT NULL,
  `alternativeNumber` bigint(128) DEFAULT NULL,
  `emailId` varchar(256) DEFAULT NULL,
  `pincode` bigint(128) DEFAULT NULL,
  `dob` varchar(256) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `confirmPassword` varchar(256) DEFAULT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES (1,'admin','admin','admin',5,1,101,17,1750,'SaiRam Building 1st Floor JCST Layout Near Alahabad Bank M-Block,Kuvempu Nagara','36',9739686180,9739686180,'admin@gmail.com',570023,'2017-10-31','21232f297a57a5a743894a0e4a801fc3','21232f297a57a5a743894a0e4a801fc3','2018-12-08 07:31:19','2018-12-08 07:31:19',0),(2,'tieups','admin','tieups',7,1,101,17,1750,'SaiRam Building 1st Floor JCST Layout Near Alahabad Bank M-Block,Kuvempu Nagara','36',9739686180,9739686180,'tieups@gmail.com',570023,'2017-10-31','419b1a75c38b6aa24ff29b31930ae704','419b1a75c38b6aa24ff29b31930ae704','2018-12-08 07:31:45','2018-12-08 07:31:45',0),(7,'ganesh',NULL,NULL,9,0,101,17,48321,NULL,'0',9008223292,9008223292,'ganesh@gmail.com',570008,NULL,'fa1d87bc7f85769ea9dee2e4957321ae','fa1d87bc7f85769ea9dee2e4957321ae','2018-12-10 08:10:46','2018-12-10 08:10:46',0),(8,'Jayaram','B',NULL,1,1,101,17,1750,'vidyaranyapuram','901',919035006593,9035006593,'jayaramb094@gmail.com',570008,'1994-09-09T18:30:00.000Z','9230b44a28a7f98344cf4643eff8e8b8','9230b44a28a7f98344cf4643eff8e8b8','2018-12-11 05:41:33','2018-12-11 05:41:33',0),(9,'Sanjana','L',NULL,2,2,101,17,1750,'Kuvempunagar','1353',8904268728,8904268728,'sanjanaabole28@gmail.com',5700023,'1995-01-27T18:30:00.000Z','9a2c251ad99d3e5cde9f1a73ae5caaae','9a2c251ad99d3e5cde9f1a73ae5caaae','2018-12-11 07:02:20','2018-12-11 07:02:20',0),(15,'pooja',NULL,NULL,3,0,101,17,1750,NULL,'0',7406275983,8784412454,'mohidu786@gmail.com',589787,NULL,'9cbb6aebcf5ae14a9248b4c08165212e','9cbb6aebcf5ae14a9248b4c08165212e','2018-12-11 13:32:35','2018-12-11 13:32:35',0),(16,'Pooja','R B',NULL,2,2,101,17,1750,'Ragibommanahalli','100',7406275983,7406275983,'poojarb408@gmail.com',571430,'1995-07-24T18:30:00.000Z','64c63b96fd42eb4502e72bbe23b6fd5b','64c63b96fd42eb4502e72bbe23b6fd5b','2018-12-11 18:10:53','2018-12-11 18:10:53',0),(17,'Bhavya',NULL,NULL,4,0,101,17,1709,NULL,'0',8951719265,9663044832,'bhavyashree.nl@gmail.com',NULL,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 06:56:05','2018-12-12 06:56:05',0),(18,'Bhavya',NULL,NULL,4,0,101,17,1709,'Jaynagar','0',8951719265,9663044832,'bhavyashree.nl@gmail.com',571301,NULL,'1ce927f875864094e3906a4a0b5ece68','1ce927f875864094e3906a4a0b5ece68','2018-12-12 07:24:36','2018-12-12 07:24:36',0),(19,'Bhavya',NULL,NULL,4,0,101,17,1709,'Jaynagar','0',8951719265,9663044832,'bhavyashree.nl@gmail.com',571301,NULL,'1ce927f875864094e3906a4a0b5ece68','1ce927f875864094e3906a4a0b5ece68','2018-12-12 07:32:54','2018-12-12 07:32:54',0),(20,'Bhavya',NULL,NULL,4,0,101,17,1709,'Jaynagar','0',8951719265,9663044832,'bhavyashree.nl@gmail.com',571301,NULL,'1ce927f875864094e3906a4a0b5ece68','1ce927f875864094e3906a4a0b5ece68','2018-12-12 07:41:43','2018-12-12 07:41:43',0),(21,'ATPL',NULL,NULL,3,0,101,17,1709,NULL,'0',8951719265,8951719265,'bhavyashree.nl@gmail.com',571301,NULL,'698d51a19d8a121ce581499d7b701668','698d51a19d8a121ce581499d7b701668','2018-12-12 07:54:29','2018-12-12 07:54:29',0),(22,'Bhavya',NULL,NULL,4,0,101,17,1709,'Jaynagar','0',8951719265,8951719265,'bhavyashree.nl@gmail.com',571301,NULL,'2e65f2f2fdaf6c699b223c61b1b5ab89','2e65f2f2fdaf6c699b223c61b1b5ab89','2018-12-12 09:37:04','2018-12-12 09:37:04',0),(23,'Ram',NULL,NULL,9,0,101,17,1558,NULL,'0',9008223292,9008223292,'jpjbengaluru@gmail.com',650001,NULL,'6b2fdf5f0f337ff08c4d9e9491c2c2de','6b2fdf5f0f337ff08c4d9e9491c2c2de','2018-12-12 09:43:34','2018-12-12 09:43:34',0),(24,'jayaram',NULL,NULL,9,0,101,17,1750,NULL,'0',7406275983,8945215522,'mohidu786@gmail.com',587855,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 09:57:24','2018-12-12 09:57:24',0),(25,'jayaram',NULL,NULL,9,0,101,17,1750,NULL,'0',7406275983,8945215522,'mohidu786@gmail.com',587855,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:01:21','2018-12-12 10:01:21',0),(26,'jayaram',NULL,NULL,9,0,101,17,1750,NULL,'0',7406275983,8945215522,'mohidu786@gmail.com',587855,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:16:22','2018-12-12 10:16:22',0),(27,'jayaram',NULL,NULL,9,0,101,17,1750,NULL,'0',7406275983,8945215522,'mohidu786@gmail.com',587855,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:23:25','2018-12-12 10:23:25',0),(28,'pooja',NULL,NULL,4,0,101,17,1750,'Kuvempunagar','0',7406275983,9035006593,'poojarb408@gmail.com',574848,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:28:56','2018-12-12 10:28:56',0),(29,'pooja',NULL,NULL,4,0,101,17,1750,'kuvempunagar','0',9035006593,9035006593,'poojarb408@gmail.com',587494,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:32:55','2018-12-12 10:32:55',1),(30,'Mohidu','s',NULL,3,0,101,17,48321,NULL,'0',9008223292,9035006593,'mohidu786@gmail.com',57008,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-12 10:37:56','2018-12-12 10:37:56',0),(31,'pooja','b',NULL,11,0,101,17,1709,'mysore','455',8618695718,8861947646,'mohidu786@gmail.com',574884,'2018-12-19T18:30:00.000Z','202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-17 18:10:12','2018-12-17 18:10:12',0),(32,'Kiran','Kiru',NULL,2,0,101,17,1750,'mysore','456',8618695718,8861947646,'mohidu786@gmail.com',574884,'1994-12-19T18:30:00.000Z','202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-19 07:50:58','2018-12-19 07:50:58',0),(33,'Kiran','Kiru',NULL,2,0,101,17,1750,'mysore','456',8618695718,8861947646,'mohidu786@gmail.com',574884,'1994-12-19T18:30:00.000Z','202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-19 07:52:02','2018-12-19 07:52:02',0),(34,'Hithesh','m',NULL,11,0,101,17,1558,'banglore','458',8951137328,8951137328,'m.n.hithesh26@gmail.com',560022,'1994-12-18T18:30:00.000Z','3b113c66e6f25111dcb440792dd1b4f9','3b113c66e6f25111dcb440792dd1b4f9','2018-12-19 08:12:12','2018-12-19 08:12:12',0),(35,'Mohidu',NULL,NULL,9,0,101,17,1752,NULL,'0',8618695718,8861947646,'mohidu786@gmail.com',571301,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-21 17:07:57','2018-12-21 17:07:57',0),(36,'mohidu',NULL,NULL,9,0,101,17,1709,NULL,'0',8618695718,8861947646,'mohidu786@gmail.com',577118,NULL,'202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70','2018-12-21 17:28:50','2018-12-21 17:28:50',0),(37,'bhavya','shree',NULL,11,0,101,17,1709,'Jaynagar','456',8951719265,9663044832,'mohidu786@gmail.com',577115,'2018-12-11T18:30:00.000Z','8a7978b1c5f2425e9e1c81a4486e5f6f','8a7978b1c5f2425e9e1c81a4486e5f6f','2018-12-21 19:05:09','2018-12-21 19:05:09',0),(38,'R1o2s3e','J4a5c6k',NULL,2,1,101,17,1750,'mysore','786',8618695718,8861947646,'jayaramb094$gmail.com',577115,'1994-09-09T18:30:00.000Z','6773b2aa65a89de2a512051a3a044f1a','6773b2aa65a89de2a512051a3a044f1a','2018-12-22 06:13:02','2018-12-22 06:13:02',0),(39,'14476','J4a5c6k',NULL,2,1,101,17,1750,'mysore','786',8618695718,8861947646,'jayaramb094$gmail.com',577115,'1994-09-09T18:30:00.000Z','6773b2aa65a89de2a512051a3a044f1a','6773b2aa65a89de2a512051a3a044f1a','2018-12-22 06:14:01','2018-12-22 06:14:01',0),(40,'Prajwal',NULL,NULL,9,0,101,17,48321,NULL,'0',8660420669,9886939248,'prajwal@gmail.com',570001,NULL,'967454173c7637d071a8bd30224407e3','967454173c7637d071a8bd30224407e3','2019-01-02 10:42:43','2019-01-02 10:42:43',1),(44,'Chinmaya group',NULL,NULL,3,0,101,17,48321,'Saraswathi puram','0',8951719225,9663044832,'chinmayagroup@gmail.com',5711101,NULL,'36037b6687fd2d3ec5960cb38a55af28','36037b6687fd2d3ec5960cb38a55af28','2019-01-02 10:58:46','2019-01-02 10:58:46',1),(45,'John',NULL,NULL,4,0,101,17,1750,'Mysore','0',9591996593,1235478999,'johndoe@gmail.com',570023,NULL,'6579e96f76baa00787a28653876c6127','6579e96f76baa00787a28653876c6127','2019-01-02 11:05:25','2019-01-02 11:05:25',0),(46,'Kumari','sindhu',NULL,2,2,101,17,1750,'Srirampura','1366',9113586480,9901907386,'kumarisindhu@gmail.com',5700023,'1992-06-30','9e1950f08fbd1a5f6a0e7cd335febc2b','9e1950f08fbd1a5f6a0e7cd335febc2b','2019-01-02 11:31:36','2019-01-02 11:31:36',0),(47,'Kumari','Sindhu',NULL,2,2,101,17,1750,'kuvempunagar','1325',9113586480,NULL,'kumarisindhu@gmail.com',570021,'2019-01-11','9e1950f08fbd1a5f6a0e7cd335febc2b','9e1950f08fbd1a5f6a0e7cd335febc2b','2019-01-02 11:42:07','2019-01-02 11:42:07',0),(48,'Charan','kumar',NULL,2,1,101,17,1750,'jp nagara','408',9591996596,9035006593,'charankumar@gmail.com',570012,'2019-01-12','ca5168676897378ece2c6d5f2379d1e7','ca5168676897378ece2c6d5f2379d1e7','2019-01-02 11:49:01','2019-01-02 11:49:01',0);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prole`
--

DROP TABLE IF EXISTS `prole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prole` (
  `id` bigint(128) NOT NULL AUTO_INCREMENT,
  `permissionId` bigint(128) NOT NULL,
  `roleId` bigint(128) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modificationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `prole_fk1` (`roleId`),
  CONSTRAINT `prole_fk1` FOREIGN KEY (`roleId`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prole`
--

LOCK TABLES `prole` WRITE;
/*!40000 ALTER TABLE `prole` DISABLE KEYS */;
INSERT INTO `prole` VALUES (1,1,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(2,2,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(3,3,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(4,4,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(5,5,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(6,6,1,'2018-04-21 10:35:58','2018-04-21 10:35:58',0),(7,7,5,'2018-05-05 02:02:29','2018-05-05 02:02:29',0),(8,8,5,'2018-05-05 02:02:29','2018-05-05 02:02:29',0),(9,9,5,'2018-05-05 02:02:29','2018-05-05 02:02:29',0),(10,10,5,'2018-05-05 02:02:29','2018-05-05 02:02:29',0),(11,11,5,'2018-05-05 02:02:29','2018-05-05 02:02:29',0),(12,12,2,'2018-05-05 02:08:09','2018-05-05 02:08:09',0),(13,13,2,'2018-05-05 02:08:09','2018-05-05 02:08:09',0),(14,14,2,'2018-05-05 02:08:09','2018-05-05 02:08:09',0),(15,15,2,'2018-05-05 02:08:09','2018-05-05 02:08:09',0),(16,6,2,'2018-05-05 02:08:09','2018-05-05 02:08:09',0),(17,17,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0),(18,19,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0),(19,20,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0),(20,21,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0),(21,22,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0),(22,9,3,'2018-05-05 02:10:22','2018-05-05 02:10:22',0);
/*!40000 ALTER TABLE `prole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(128) NOT NULL,
  `roleName` varchar(256) NOT NULL,
  `creationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
INSERT INTO `role` VALUES (1,'customer','2018-04-21 09:02:54','2018-04-21 09:02:54',0,'bookNow'),(2,'driver','2018-04-21 09:02:54','2018-04-21 09:02:54',0,'driverDashboard'),(3,'fleetOperator','2018-04-21 09:02:54','2018-04-21 09:02:54',0,'fleetOperatorDashboard'),(4,'warehouse','2018-05-04 07:39:13','2018-05-04 07:39:13',0,'warehouseDashboard'),(5,'owner','2018-05-05 02:02:02','2018-05-05 02:02:02',0,'ownerDashboard'),(6,'employee','2018-05-09 01:33:02','2018-05-09 01:33:02',0,NULL),(7,'tieups','2018-05-09 03:10:42','2018-05-09 03:10:42',0,'tieupsDashboard'),(8,'fareestimation','2018-05-13 10:05:39','2018-05-13 10:05:39',0,NULL),(9,'franchies','2018-06-19 18:49:14','2018-06-19 18:49:14',0,'franchiseDashboard'),(10,'oragnization','2018-07-03 12:14:03','2018-07-03 12:14:03',0,NULL),(11,'fleetDriver','2018-09-27 11:48:14','2018-09-27 11:48:14',0,'fleetOperatorDriverDashboard');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-02 19:01:05
