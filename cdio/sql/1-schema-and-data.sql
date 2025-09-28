-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `hotel`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `hotel` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `hotel`;

--
-- Table structure for table `blog_posts`
--

DROP TABLE IF EXISTS `blog_posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_posts` (
  `post_id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `title` varchar(200) NOT NULL,
  `slug` varchar(220) NOT NULL,
  `summary` varchar(500) DEFAULT NULL,
  `content` mediumtext NOT NULL,
  `cover_image` varchar(500) DEFAULT NULL,
  `status` enum('draft','published','archived') DEFAULT 'draft',
  `reading_time` int DEFAULT '0',
  `view_count` int DEFAULT '0',
  `published_at` datetime DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `slug` (`slug`),
  KEY `fk_blog_user` (`staff_id`),
  KEY `ix_blog_published` (`published_at`),
  CONSTRAINT `fk_blog_user` FOREIGN KEY (`staff_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) /*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog_posts`
--

LOCK TABLES `blog_posts` WRITE;
/*!40000 ALTER TABLE `blog_posts` DISABLE KEYS */;
/*!40000 ALTER TABLE `blog_posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `booking_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `room_id` int NOT NULL,
  `checkin_date` date NOT NULL,
  `checkout_date` date NOT NULL,
  `status` varchar(20) DEFAULT 'Booked',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`booking_id`),
  KEY `ix_booking_customer` (`customer_id`),
  KEY `ix_booking_room` (`room_id`),
  CONSTRAINT `fk_booking_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_booking_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
  `customer_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uq_customers_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'cap nhat id 1','/uploads/8bea7e3d-b88e-454e-adfe-fa64f2c84a10_2023_07_27_15_36_img_1306.jpg',NULL,'023042432','adsfadfadf',' cap nhat id 1',NULL,NULL,'2025-09-25 10:49:11'),(4,'22222',NULL,'thangcutehuhjkjklju2808@gmail.com','0204923023','DN','khachs vip',1,'2025-09-25 08:37:22','2025-09-25 16:10:52'),(7,'7fgdada',NULL,'skldfjsldfj lkfdjlakdfj@gmail.com','0204923023','DN','khachs vip',1,'2025-09-25 08:54:00','2025-09-25 16:10:52'),(10,'101341341','/uploads/4518f5c6-cd6f-4cd4-948d-a1a9fd01a77a_2023_07_07_00_06_img_0762.jpg','skldfjsld2rwekfdjlakdfj@gmail.com','0204923023','DN','khachs vip',1,'2025-09-25 08:55:05','2025-09-25 16:10:52'),(11,'11dfgsfds','/uploads/2917f638-6ad3-4968-9349-9d028e3bfee2_2023_07_18_04_18_img_1170.jpg','adfafdadfadadfadf@gmail.com','0204923023','DN','khachs vip',1,'2025-09-25 08:56:53','2025-09-25 16:10:52'),(12,'dai ca thang ca chep','/uploads/cd8d27bb-a499-4c1d-9222-7695fb83649f_2023_07_18_04_18_img_1170.jpg','adfafdadfadadfadfaddfadf@gmail.com','0204923023','DN','khachs vip',1,'2025-09-25 08:57:50','2025-09-25 08:57:50'),(13,'cap nhat id 1','/uploads/6b3f32b7-b4eb-4ccd-ab64-e8ae9ee689b4_2023_07_11_00_45_img_0944.jpeg',NULL,NULL,NULL,' cap nhat id 1',NULL,'2025-09-25 09:25:07','2025-09-25 09:25:07'),(14,'cap nhat id 1','/uploads/88d89de7-f5e1-42ec-93f0-8e452b8d0152_2023_07_27_15_36_img_1306.jpg',NULL,NULL,NULL,' cap nhat id 1',NULL,'2025-09-25 09:25:50','2025-09-25 09:25:50'),(15,'cap nhat id 1','/uploads/65e93d40-9161-4f56-bf0e-a5c5cd4ff9a5_2023_07_27_15_36_img_1306.jpg',NULL,NULL,NULL,' cap nhat id 1',NULL,'2025-09-25 09:28:38','2025-09-25 09:28:38'),(16,'cap nhat id 1','/uploads/8523b4e7-3b49-455e-bf99-dc8d3c1dcc33_2023_07_27_15_36_img_1306.jpg',NULL,NULL,NULL,' cap nhat id 1',NULL,'2025-09-25 09:30:51','2025-09-25 09:30:51'),(17,'cap nhat id 1','/uploads/d35a1d66-ce80-438c-b3eb-67923143ce39_2023_07_27_15_36_img_1306.jpg',NULL,NULL,NULL,' cap nhat id 1',NULL,'2025-09-25 09:31:18','2025-09-25 09:31:18');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_booking`
--

DROP TABLE IF EXISTS `detail_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detail_booking` (
  `detail_id` int NOT NULL AUTO_INCREMENT,
  `booking_id` int NOT NULL,
  `service_name` varchar(100) NOT NULL,
  `service_desc` varchar(255) DEFAULT NULL,
  `amount` decimal(12,2) DEFAULT '0.00',
  `new_checkout` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`detail_id`),
  KEY `fk_detail_booking` (`booking_id`),
  CONSTRAINT `fk_detail_booking` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`booking_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_booking`
--

LOCK TABLES `detail_booking` WRITE;
/*!40000 ALTER TABLE `detail_booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `detail_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice_items`
--

DROP TABLE IF EXISTS `invoice_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice_items` (
  `item_id` int NOT NULL AUTO_INCREMENT,
  `invoice_id` int NOT NULL,
  `item_type` enum('room','service','other') NOT NULL,
  `description` varchar(255) NOT NULL,
  `qty` decimal(10,2) NOT NULL DEFAULT '1.00',
  `unit_price` decimal(12,2) NOT NULL DEFAULT '0.00',
  `tax_rate` decimal(5,2) NOT NULL DEFAULT '0.00',
  `line_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `line_discount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`item_id`),
  KEY `ix_item_invoice` (`invoice_id`),
  KEY `ix_item_type` (`item_type`),
  CONSTRAINT `fk_item_invoice` FOREIGN KEY (`invoice_id`) REFERENCES `invoices` (`invoice_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_items`
--

LOCK TABLES `invoice_items` WRITE;
/*!40000 ALTER TABLE `invoice_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoices`
--

DROP TABLE IF EXISTS `invoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoices` (
  `invoice_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `staff_id` int DEFAULT NULL,
  `detail_id` int DEFAULT NULL,
  `issued_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('draft','unpaid','paid','refunded','void') DEFAULT 'paid',
  `subtotal` decimal(12,2) NOT NULL DEFAULT '0.00',
  `discount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00',
  `note` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`invoice_id`),
  KEY `fk_inv_customer` (`customer_id`),
  KEY `fk_inv_staff` (`staff_id`),
  KEY `fk_inv_detail` (`detail_id`),
  CONSTRAINT `fk_inv_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_inv_detail` FOREIGN KEY (`detail_id`) REFERENCES `detail_booking` (`detail_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_inv_staff` FOREIGN KEY (`staff_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoices`
--

LOCK TABLES `invoices` WRITE;
/*!40000 ALTER TABLE `invoices` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `management_customers`
--

DROP TABLE IF EXISTS `management_customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `management_customers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `customer_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_mgmt_staff_customer` (`staff_id`,`customer_id`),
  KEY `ix_mgmt_staff` (`staff_id`),
  KEY `ix_mgmt_customer` (`customer_id`),
  CONSTRAINT `fk_mgmt_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_mgmt_staff` FOREIGN KEY (`staff_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `management_customers`
--

LOCK TABLES `management_customers` WRITE;
/*!40000 ALTER TABLE `management_customers` DISABLE KEYS */;
INSERT INTO `management_customers` VALUES (1,34,10,'2025-09-25 16:04:44',NULL),(2,34,11,'2025-09-25 16:04:44',NULL),(3,34,12,'2025-09-25 16:04:44',NULL),(4,35,11,'2025-09-25 16:04:44',NULL),(5,35,12,'2025-09-25 16:04:44',NULL);
/*!40000 ALTER TABLE `management_customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `management_rooms`
--

DROP TABLE IF EXISTS `management_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `management_rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `staff_id` int NOT NULL,
  `room_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `staff_id` (`staff_id`),
  KEY `room_id` (`room_id`),
  CONSTRAINT `management_rooms_ibfk_1` FOREIGN KEY (`staff_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `management_rooms_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `management_rooms`
--

LOCK TABLES `management_rooms` WRITE;
/*!40000 ALTER TABLE `management_rooms` DISABLE KEYS */;
/*!40000 ALTER TABLE `management_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotions` (
  `promo_id` int NOT NULL AUTO_INCREMENT,
  `promo_name` varchar(150) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `discount_pct` decimal(5,2) DEFAULT '0.00',
  `discount_amt` decimal(12,2) DEFAULT '0.00',
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` enum('active','inactive','expired') DEFAULT 'active',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`promo_id`),
  KEY `ix_promotions_active_time` (`status`,`start_date`,`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_replies`
--

DROP TABLE IF EXISTS `review_replies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_replies` (
  `reply_id` int NOT NULL AUTO_INCREMENT,
  `review_id` int NOT NULL,
  `staff_id` int NOT NULL,
  `parent_id` int DEFAULT NULL,
  `content` text NOT NULL,
  `visibility` enum('public','internal') DEFAULT 'public',
  `status` enum('active','edited','deleted') DEFAULT 'active',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`reply_id`),
  KEY `fk_reply_review` (`review_id`),
  KEY `fk_reply_staff` (`staff_id`),
  CONSTRAINT `fk_reply_review` FOREIGN KEY (`review_id`) REFERENCES `reviews` (`review_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_reply_staff` FOREIGN KEY (`staff_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_replies`
--

LOCK TABLES `review_replies` WRITE;
/*!40000 ALTER TABLE `review_replies` DISABLE KEYS */;
/*!40000 ALTER TABLE `review_replies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `room_id` int NOT NULL,
  `rating` tinyint NOT NULL,
  `comment` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`review_id`),
  KEY `fk_review_customer` (`customer_id`),
  KEY `fk_review_room` (`room_id`),
  CONSTRAINT `fk_review_customer` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_review_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reviews_chk_1` CHECK ((`rating` between 1 and 5))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uq_roles_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'user','2025-09-17 03:57:24',NULL),(2,'staff','2025-09-17 03:57:24','2025-09-19 10:29:03'),(3,'admin','2025-09-19 10:09:38','2025-09-19 10:29:03');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_promotions`
--

DROP TABLE IF EXISTS `room_promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_promotions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `promo_id` int NOT NULL,
  `priority` int DEFAULT '0',
  `stackable` tinyint(1) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_rp_promo` (`promo_id`),
  KEY `ix_room_promotions_room` (`room_id`,`active`),
  CONSTRAINT `fk_rp_promo` FOREIGN KEY (`promo_id`) REFERENCES `promotions` (`promo_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_rp_room` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_promotions`
--

LOCK TABLES `room_promotions` WRITE;
/*!40000 ALTER TABLE `room_promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_types`
--

DROP TABLE IF EXISTS `room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_types` (
  `type_id` int NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) NOT NULL,
  `description` text,
  `room_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`type_id`),
  UNIQUE KEY `type_name` (`type_name`),
  KEY `fk_rooms_type` (`room_id`),
  CONSTRAINT `fk_rooms_type` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_types`
--

LOCK TABLES `room_types` WRITE;
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `title` varchar(150) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `guests` int DEFAULT '1',
  `size` int DEFAULT NULL,
  `beds` varchar(100) DEFAULT NULL,
  `view` varchar(100) DEFAULT NULL,
  `price` decimal(12,2) DEFAULT NULL,
  `old_price` decimal(12,2) DEFAULT NULL,
  `discount` int DEFAULT '0',
  `air_conditioning` tinyint(1) DEFAULT '0',
  `wifi` tinyint(1) DEFAULT '0',
  `hair_dryer` tinyint(1) DEFAULT '0',
  `pets_allowed` tinyint(1) DEFAULT '0',
  `non_smoking` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,'Suite City View','Suite City View','/uploads/6db856ef-2fb7-4309-985d-53ba7b1b74ab_hero-img.jpg','Hà Nội','Phòng rộng rãi với tầm nhìn ra thành phố, phù hợp cho gia đình','2025-09-26 23:06:59','2025-09-27 06:20:32',4,50,'2 Giường Queen size','Thành phố',3500000.00,4200000.00,10,1,1,1,1,0),(2,'Standard Garden View','Standard Garden View','/uploads/a1849dcd-2934-4d39-9ab0-a2992b1825bf_feature-2.avif','123 Le Loi, Hai Chau, Da Nang','Phòng thoải mái, view thành phố','2025-09-26 23:08:02','2025-09-27 06:20:32',3,28,'1 Giường đôi','Sông Hàn',1200000.00,250000.00,15,1,0,1,1,1),(3,'Deluxe City View','Deluxe City View','/uploads/959edbac-e833-4feb-b36e-7675a65fe3f0_feature-3.webp','45 Nguyen Van Linh, Da Nang','Phòng tiêu chuẩn với ban công nhìn ra vườn xanh mát','2025-09-26 23:10:11','2025-09-27 06:26:01',5,35,'1 Giường King','Toàn cảnh',2000000.00,66666666.00,10,1,1,1,1,1),(4,'Suite Luxury','Suite Luxury','/uploads/c4e881dc-dcd0-40df-b5a9-ee7a566a3f7c_feedback-avatar-1.png','5 Hai Ba Trung, Da Nang','Phòng tiêu chuẩn giá rẻ, phù hợp cho khách du lịch bụi','2025-09-26 23:10:29','2025-09-27 06:20:32',2,60,'3 Giường King','Sông Hàn',3500000.00,4200000.00,15,0,0,0,0,0),(5,'Presidential Suite','Presidential Suite','/uploads/2e8afef4-1736-4f5a-9bfc-9dd5f87a0052_feedback-avatar-3.jpg','5 Hai Ba Trung, Da Nang','Phòng thoải mái, view thành phố','2025-09-26 23:10:43','2025-09-27 06:20:32',4,45,'1 Giường King + 1 Giường đơn','Thành phố',2500000.00,4200000.00,10,1,1,1,1,1),(6,'Novotel','Novotel','/uploads/f5ce88d3-5d38-44c0-a717-8aa88d2f45d9_feedback-avatar-2.jpg','DN','Phòng suite cao cấp, nội thất sang trọng','2025-09-26 23:11:16','2025-09-27 06:20:32',4,120,'3 Giường King','Toàn cảnh',800000.00,250000.00,15,0,1,0,1,1);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roomtypes_prices`
--

DROP TABLE IF EXISTS `roomtypes_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roomtypes_prices` (
  `price_id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `currency` varchar(10) DEFAULT 'VND',
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`price_id`),
  KEY `fk_room_prices_type` (`type_id`),
  CONSTRAINT `fk_room_prices_type` FOREIGN KEY (`type_id`) REFERENCES `room_types` (`type_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roomtypes_prices`
--

LOCK TABLES `roomtypes_prices` WRITE;
/*!40000 ALTER TABLE `roomtypes_prices` DISABLE KEYS */;
/*!40000 ALTER TABLE `roomtypes_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `img` varchar(500) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  `facebook_account_id` int DEFAULT NULL,
  `google_account_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uq_users_email` (`email`),
  UNIQUE KEY `phone` (`phone`),
  KEY `idx_users_role_id` (`role_id`),
  CONSTRAINT `fk_users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (27,'ầdaf','a@gmail.com','0513453764','$2a$10$ONFg8Brg0fra17SkpHUbsuWAgo40e4RjuAsy22h8/pmSbj//yp9BG',NULL,1,1,0,0,'2025-09-19 04:01:25','2025-09-19 04:01:25'),(28,'Nguyen Van A','staff1@gmail.com','098765432124','$2a$10$IHXEJeO1oJn4CZbOQCB1OewxS.0GoI4YkM7vkXT4NNIp1CPzlUHzK',NULL,1,1,0,0,'2025-09-24 03:05:33','2025-09-25 15:48:57'),(29,'cho con','update@gmail.com','0324343545','$2a$10$0dR/3rAE8qQvTMZgk2HtWudpf8lgsDiPJWnk6LtKEDT81wylZ1yaS','/uploads/343b1fdf-ea2d-48a6-a7c8-e7c2d408c397_2023_10_11_18_59_img_2761.jpg',0,NULL,0,0,NULL,'2025-09-26 04:51:48'),(30,'nhan vien 3','staff3@gmail.com','092387653323','$2a$10$mGj4Oz0J9Hx1eqGkt5hd0Oph/mdVpLJ0cyAyucccgdyPmnYKEl4R.',NULL,1,2,0,0,'2025-09-24 03:06:08','2025-09-24 03:06:08'),(31,'nhan vien 4','staff4@gmail.com','0923876533323','$2a$10$Ke0EZfD3HcrzwBFx.ikAVuRET9BGTK4lXcgpafwF6woLBXXKaQNJW',NULL,1,2,0,0,'2025-09-24 03:06:19','2025-09-24 03:06:19'),(32,'pham cho','thang@gmail.com','0952333323','$2a$10$Q8Am9DQMf5Zfwl38uJK97um1WQwH95HPIRE2XyFp9QFmES4RJ3Ksi',NULL,1,1,0,0,'2025-09-24 03:06:39','2025-09-24 03:06:39'),(33,'pham cho 2','thang2@gmail.com','0952332333323','$2a$10$bz6TNMg/8swDvca9xdjs2OSo8656.wHizP.qloJA/ewZtRmyq/Ai2',NULL,1,1,0,0,'2025-09-24 03:06:50','2025-09-24 03:06:50'),(34,'pham thang','thangcho@gmail.com','0819972252','$2a$10$gvhdR4BUHpjQXF97v3AviuW9el6nj4JYlr2oQU3TLiGsL.Vd.F7HO',NULL,1,2,0,0,'2025-09-24 04:26:41','2025-09-24 12:31:47'),(35,'pham thang','admin@gmail.com','0702389151','$2a$10$PT7371FidbSlCA7QiVVE9eZH22RkMOp7OFHTmMejPL6/zJWgax.Le',NULL,1,3,0,0,'2025-09-25 08:50:03','2025-09-25 15:50:16'),(36,'le thi cam ly','ly@gmail.com','0320032005','$2a$10$ZWoUXkMCxJ7zZxh/S6NW1.jcwhl4ZBL1GdTw/bK5abEMS3O9cC7L2',NULL,1,1,0,0,'2025-09-26 07:39:27','2025-09-26 07:39:27'),(37,'pham thang','stafff4343@gmail.com','0702389151233','123456','/uploads/3602403a-6956-448d-b4f8-f3a6e44d4ccf_block-3.png',1,2,0,0,'2025-09-27 01:22:09','2025-09-27 01:22:09'),(38,'pham thang','stafff43443543@gmail.com','0702','123456','/uploads/83646909-3874-4e34-b0dd-0d3c32f552df_block-3.png',1,2,0,0,'2025-09-27 01:27:18','2025-09-27 01:27:18'),(39,'pham thang','stafff434233243543@gmail.com','034095340','123456','/uploads/22375597-36e0-4e81-a6ba-2cb24bfb38b1_block-3.png',1,2,0,0,'2025-09-27 01:29:34','2025-09-27 01:29:34'),(40,'pham thang','stafff434233afdafd243543@gmail.coma','0300249232332','123456','/uploads/63c7116f-ee79-4e10-875a-269d5f8ae5c5_block-3.png',1,2,0,0,'2025-09-27 01:35:04','2025-09-27 01:35:04'),(41,'pham thang','staajkfljad@gmail.com','004523003','123456','/uploads/884ce96c-9db2-412a-9385-213f84eefb02_block-3.png',1,2,0,0,'2025-09-27 01:37:33','2025-09-27 01:37:33'),(42,'pham thang','staajkfljad@gmail.com243223423','1413143','$2a$10$ccm/iNClb9qbnq1GeRPxUOZGcrNUA1.0dJxMKd0Tjo8MYRMrYVo3S','/uploads/3e9800d2-63b1-408b-841a-d4ae18d9d87f_block-3.png',1,2,0,0,'2025-09-27 01:44:39','2025-09-27 01:44:39'),(43,'pham thang','than62362@gmail.com','04444343543','$2a$10$Nmghu62huVDTBWjOobm8XeLRsMuE6Uj3jJqVIwyZyUK1f/jVnwX9e','/uploads/674f4fb0-874d-43c9-8de9-e6c862005522_block-3.png',1,2,0,0,'2025-09-27 01:51:45','2025-09-27 01:51:45'),(44,'pham thang','than623462@gmail.com','0444434354365','$2a$10$1B.HWohmv9xiLCvSQWauuepifyq.lbByM6stGKSeE2H.3YroxnDbS','/uploads/fb5cd757-b71d-42c8-99cf-f4c15095544c_block-3.png',1,2,0,0,'2025-09-27 01:53:35','2025-09-27 01:53:35'),(49,'pham thang','than62243462@gmail.com','04423424333','$2a$10$s7sS2orVqvAafVCzP089Fe/noH9iRQwcxxVOm/e8tObK7JOHuCaEq','/uploads/8fd6a7c9-3d77-466d-b2ed-752010e46b2a_block-3.png',1,2,0,0,'2025-09-27 01:59:04','2025-09-27 01:59:04'),(51,'pham thang','than6224335353543433435462@gmail.com','0489988443','$2a$10$HMVq5i2f4tGv85hrkT6UYeMU/PE0F2DAboDfZT9BOF0aCrQxd7pbG','/uploads/42c41bba-b423-48c8-b1c3-085e3a14e314_block-3.png',1,2,0,0,'2025-09-27 02:00:48','2025-09-27 02:00:48'),(52,'pham thang','th5462@gmail.com','00000034443','$2a$10$yS6Voieoe1aC7YxK92mdoePQ8Zz/Sos0uA4cs0sNrYJ4h3Smo9XWC','/uploads/189d35c3-2fed-4454-b79e-ed218274c1fc_block-3.png',1,2,0,0,'2025-09-27 02:01:11','2025-09-27 02:01:11');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'hotel'
--

--
-- Dumping routines for database 'hotel'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-28  8:51:07
