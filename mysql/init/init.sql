
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

/*!40000 DROP DATABASE IF EXISTS `auth_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `auth_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `auth_db`;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '加密密码(BCrypt)',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `role` varchar(20) NOT NULL DEFAULT 'USER' COMMENT '角色: USER/MERCHANT/ADMIN',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1启用 0禁用',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG','13800000000','admin@shopcoupon.com','ADMIN',1,'2026-06-12 12:15:19','2026-06-12 12:15:19'),(8,'shenxinyu','$2a$10$3ND7KgZUiFw4wLTdCQcY4elVYYa/JyQasYardUKG.plEb4XjdKN8W','','','MERCHANT',1,'2026-06-12 15:02:40','2026-06-12 15:02:40'),(9,'seckill001','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000001','seckill001@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(10,'seckill002','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000002','seckill002@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(11,'seckill003','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000003','seckill003@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(12,'seckill004','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000004','seckill004@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(13,'seckill005','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000005','seckill005@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(14,'seckill006','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000006','seckill006@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(15,'seckill007','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000007','seckill007@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(16,'seckill008','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000008','seckill008@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(17,'seckill009','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000009','seckill009@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(18,'seckill010','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000010','seckill010@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(19,'seckill011','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000011','seckill011@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(20,'seckill012','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000012','seckill012@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(21,'seckill013','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000013','seckill013@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(22,'seckill014','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000014','seckill014@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(23,'seckill015','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000015','seckill015@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(24,'seckill016','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000016','seckill016@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(25,'seckill017','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000017','seckill017@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(26,'seckill018','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000018','seckill018@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(27,'seckill019','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000019','seckill019@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(28,'seckill020','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000020','seckill020@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(29,'seckill021','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000021','seckill021@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(30,'seckill022','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000022','seckill022@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(31,'seckill023','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000023','seckill023@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(32,'seckill024','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000024','seckill024@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(33,'seckill025','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000025','seckill025@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(34,'seckill026','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000026','seckill026@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(35,'seckill027','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000027','seckill027@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(36,'seckill028','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000028','seckill028@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(37,'seckill029','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000029','seckill029@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(38,'seckill030','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000030','seckill030@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(39,'seckill031','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000031','seckill031@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(40,'seckill032','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000032','seckill032@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(41,'seckill033','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000033','seckill033@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(42,'seckill034','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000034','seckill034@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(43,'seckill035','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000035','seckill035@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(44,'seckill036','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000036','seckill036@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(45,'seckill037','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000037','seckill037@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(46,'seckill038','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000038','seckill038@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(47,'seckill039','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000039','seckill039@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(48,'seckill040','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000040','seckill040@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(49,'seckill041','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000041','seckill041@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(50,'seckill042','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000042','seckill042@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(51,'seckill043','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000043','seckill043@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(52,'seckill044','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000044','seckill044@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(53,'seckill045','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000045','seckill045@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(54,'seckill046','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000046','seckill046@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(55,'seckill047','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000047','seckill047@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(56,'seckill048','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000048','seckill048@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(57,'seckill049','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000049','seckill049@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(58,'seckill050','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000050','seckill050@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(59,'seckill051','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000051','seckill051@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(60,'seckill052','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000052','seckill052@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(61,'seckill053','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000053','seckill053@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(62,'seckill054','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000054','seckill054@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(63,'seckill055','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000055','seckill055@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(64,'seckill056','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000056','seckill056@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(65,'seckill057','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000057','seckill057@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(66,'seckill058','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000058','seckill058@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(67,'seckill059','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000059','seckill059@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(68,'seckill060','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000060','seckill060@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(69,'seckill061','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000061','seckill061@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(70,'seckill062','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000062','seckill062@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(71,'seckill063','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000063','seckill063@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(72,'seckill064','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000064','seckill064@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(73,'seckill065','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000065','seckill065@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(74,'seckill066','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000066','seckill066@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(75,'seckill067','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000067','seckill067@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(76,'seckill068','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000068','seckill068@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(77,'seckill069','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000069','seckill069@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(78,'seckill070','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000070','seckill070@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(79,'seckill071','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000071','seckill071@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(80,'seckill072','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000072','seckill072@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(81,'seckill073','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000073','seckill073@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(82,'seckill074','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000074','seckill074@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(83,'seckill075','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000075','seckill075@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(84,'seckill076','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000076','seckill076@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(85,'seckill077','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000077','seckill077@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(86,'seckill078','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000078','seckill078@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(87,'seckill079','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000079','seckill079@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(88,'seckill080','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000080','seckill080@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(89,'seckill081','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000081','seckill081@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(90,'seckill082','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000082','seckill082@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(91,'seckill083','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000083','seckill083@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(92,'seckill084','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000084','seckill084@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(93,'seckill085','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000085','seckill085@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(94,'seckill086','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000086','seckill086@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(95,'seckill087','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000087','seckill087@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(96,'seckill088','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000088','seckill088@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(97,'seckill089','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000089','seckill089@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(98,'seckill090','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000090','seckill090@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(99,'seckill091','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000091','seckill091@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(100,'seckill092','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000092','seckill092@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(101,'seckill093','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000093','seckill093@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(102,'seckill094','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000094','seckill094@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(103,'seckill095','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000095','seckill095@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(104,'seckill096','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000096','seckill096@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(105,'seckill097','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000097','seckill097@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(106,'seckill098','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000098','seckill098@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(107,'seckill099','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000099','seckill099@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(108,'seckill100','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000100','seckill100@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(109,'seckill101','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000101','seckill101@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(110,'seckill102','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000102','seckill102@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(111,'seckill103','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000103','seckill103@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(112,'seckill104','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000104','seckill104@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(113,'seckill105','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000105','seckill105@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(114,'seckill106','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000106','seckill106@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(115,'seckill107','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000107','seckill107@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(116,'seckill108','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000108','seckill108@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(117,'seckill109','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000109','seckill109@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(118,'seckill110','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000110','seckill110@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(119,'seckill111','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000111','seckill111@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(120,'seckill112','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000112','seckill112@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(121,'seckill113','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000113','seckill113@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(122,'seckill114','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000114','seckill114@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(123,'seckill115','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000115','seckill115@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(124,'seckill116','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000116','seckill116@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(125,'seckill117','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000117','seckill117@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(126,'seckill118','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000118','seckill118@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(127,'seckill119','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000119','seckill119@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(128,'seckill120','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000120','seckill120@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(129,'seckill121','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000121','seckill121@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(130,'seckill122','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000122','seckill122@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(131,'seckill123','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000123','seckill123@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(132,'seckill124','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000124','seckill124@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(133,'seckill125','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000125','seckill125@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(134,'seckill126','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000126','seckill126@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(135,'seckill127','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000127','seckill127@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(136,'seckill128','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000128','seckill128@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(137,'seckill129','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000129','seckill129@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(138,'seckill130','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000130','seckill130@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(139,'seckill131','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000131','seckill131@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(140,'seckill132','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000132','seckill132@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(141,'seckill133','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000133','seckill133@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(142,'seckill134','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000134','seckill134@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(143,'seckill135','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000135','seckill135@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(144,'seckill136','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000136','seckill136@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(145,'seckill137','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000137','seckill137@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(146,'seckill138','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000138','seckill138@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(147,'seckill139','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000139','seckill139@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(148,'seckill140','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000140','seckill140@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(149,'seckill141','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000141','seckill141@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(150,'seckill142','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000142','seckill142@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(151,'seckill143','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000143','seckill143@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(152,'seckill144','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000144','seckill144@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(153,'seckill145','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000145','seckill145@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(154,'seckill146','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000146','seckill146@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(155,'seckill147','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000147','seckill147@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(156,'seckill148','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000148','seckill148@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(157,'seckill149','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000149','seckill149@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(158,'seckill150','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000150','seckill150@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(159,'seckill151','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000151','seckill151@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(160,'seckill152','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000152','seckill152@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(161,'seckill153','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000153','seckill153@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(162,'seckill154','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000154','seckill154@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(163,'seckill155','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000155','seckill155@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(164,'seckill156','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000156','seckill156@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(165,'seckill157','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000157','seckill157@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(166,'seckill158','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000158','seckill158@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(167,'seckill159','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000159','seckill159@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(168,'seckill160','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000160','seckill160@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(169,'seckill161','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000161','seckill161@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(170,'seckill162','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000162','seckill162@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(171,'seckill163','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000163','seckill163@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(172,'seckill164','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000164','seckill164@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(173,'seckill165','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000165','seckill165@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(174,'seckill166','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000166','seckill166@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(175,'seckill167','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000167','seckill167@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(176,'seckill168','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000168','seckill168@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(177,'seckill169','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000169','seckill169@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(178,'seckill170','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000170','seckill170@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(179,'seckill171','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000171','seckill171@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(180,'seckill172','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000172','seckill172@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(181,'seckill173','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000173','seckill173@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(182,'seckill174','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000174','seckill174@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(183,'seckill175','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000175','seckill175@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(184,'seckill176','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000176','seckill176@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(185,'seckill177','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000177','seckill177@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(186,'seckill178','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000178','seckill178@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(187,'seckill179','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000179','seckill179@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(188,'seckill180','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000180','seckill180@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(189,'seckill181','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000181','seckill181@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(190,'seckill182','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000182','seckill182@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(191,'seckill183','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000183','seckill183@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(192,'seckill184','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000184','seckill184@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(193,'seckill185','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000185','seckill185@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(194,'seckill186','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000186','seckill186@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(195,'seckill187','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000187','seckill187@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(196,'seckill188','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000188','seckill188@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(197,'seckill189','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000189','seckill189@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(198,'seckill190','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000190','seckill190@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(199,'seckill191','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000191','seckill191@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(200,'seckill192','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000192','seckill192@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(201,'seckill193','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000193','seckill193@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(202,'seckill194','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000194','seckill194@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(203,'seckill195','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000195','seckill195@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(204,'seckill196','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000196','seckill196@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(205,'seckill197','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000197','seckill197@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(206,'seckill198','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000198','seckill198@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(207,'seckill199','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000199','seckill199@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36'),(208,'seckill200','$2a$10$OyGOJ.Ozu1ZwsL1wbNPURO.jaDwhOOXkaojjmYjIIdm6zsD6TsDhC','13810000200','seckill200@test.com','USER',1,'2026-06-12 15:08:36','2026-06-12 15:08:36');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 DROP DATABASE IF EXISTS `shop_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `shop_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `shop_db`;
DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL COMMENT '所属店铺ID',
  `name` varchar(200) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `price` decimal(10,2) NOT NULL COMMENT '商品原价',
  `category` varchar(50) DEFAULT NULL COMMENT '商品分类',
  `image_url` varchar(500) DEFAULT NULL COMMENT '商品主图',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1上架 0下架',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (8,5,'华硕电脑','',8900.00,'电子产品','/uploads/1781247805082-737xupgf.avif',1,'2026-06-12 15:03:31','2026-06-12 15:03:31'),(9,5,'机箱','',299.00,'电子产品','/uploads/1781247828998-xsqdha2k.avif',1,'2026-06-12 15:03:51','2026-06-12 15:03:51'),(10,5,'5090显卡','',3950.00,'电子产品','/uploads/1781247852715-4ydnmteo.avif',1,'2026-06-12 15:04:13','2026-06-12 15:04:13'),(11,5,'手机','',8900.00,'电子产品','/uploads/1781247876314-pa3sfbvf.avif',1,'2026-06-12 15:04:37','2026-06-12 15:04:37');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '店铺名称',
  `owner_id` bigint NOT NULL COMMENT '店主用户ID',
  `logo_url` varchar(500) DEFAULT NULL COMMENT '店铺Logo',
  `description` varchar(500) DEFAULT NULL COMMENT '店铺描述',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1营业 0歇业',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_owner_id` (`owner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='店铺表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `shop` WRITE;
/*!40000 ALTER TABLE `shop` DISABLE KEYS */;
INSERT INTO `shop` VALUES (5,'电脑专卖',8,'','',1,'2026-06-12 07:03:06','2026-06-12 07:03:06');
/*!40000 ALTER TABLE `shop` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 DROP DATABASE IF EXISTS `coupon_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `coupon_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `coupon_db`;
DROP TABLE IF EXISTS `coupon_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon_template` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shop_id` bigint NOT NULL COMMENT '所属店铺ID',
  `name` varchar(100) NOT NULL COMMENT '优惠券名称',
  `type` varchar(20) NOT NULL COMMENT '类型: FULL_REDUCTION/DISCOUNT/FIXED',
  `threshold_amount` decimal(10,2) DEFAULT '0.00' COMMENT '使用门槛金额',
  `discount_amount` decimal(10,2) NOT NULL COMMENT '优惠金额/折扣率',
  `total_count` int NOT NULL COMMENT '发放总量',
  `seckill_start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `seckill_end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `valid_days` int DEFAULT '7' COMMENT '领取后有效天数',
  `per_user_limit` int DEFAULT '1' COMMENT '每人限领数量',
  `status` tinyint DEFAULT '1' COMMENT '状态: 1有效 0失效',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_shop_id` (`shop_id`),
  KEY `idx_status_time` (`status`,`seckill_start_time`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='优惠券模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `coupon_template` WRITE;
/*!40000 ALTER TABLE `coupon_template` DISABLE KEYS */;
INSERT INTO `coupon_template` VALUES (4,5,'618满减券','FULL_REDUCTION',500.00,100.00,60,'2026-06-10 00:00:00','2026-07-10 00:00:00',7,1,1,'2026-06-12 15:05:08','2026-06-12 15:06:20'),(5,5,'618折扣券','DISCOUNT',0.00,0.90,60,'2026-06-10 00:00:00','2026-07-10 00:00:00',7,1,1,'2026-06-12 15:05:21','2026-06-12 15:06:24'),(6,5,'618定额券','FIXED',0.00,60.00,59,'2026-06-10 00:00:00','2026-07-10 00:00:00',7,1,1,'2026-06-12 15:05:33','2026-06-12 15:06:37');
/*!40000 ALTER TABLE `coupon_template` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `user_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_coupon` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `template_id` bigint NOT NULL COMMENT '优惠券模板ID',
  `coupon_code` varchar(50) NOT NULL COMMENT '券码',
  `status` varchar(20) NOT NULL DEFAULT 'UNUSED' COMMENT '状态: UNUSED/USED/EXPIRED',
  `acquired_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
  `used_at` datetime DEFAULT NULL COMMENT '使用时间',
  `expire_at` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `coupon_code` (`coupon_code`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_template_id` (`template_id`),
  KEY `idx_user_status` (`user_id`,`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户优惠券表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user_coupon` WRITE;
/*!40000 ALTER TABLE `user_coupon` DISABLE KEYS */;
INSERT INTO `user_coupon` VALUES (1,8,6,'2065329911764533248','USED','2026-06-12 07:06:38','2026-06-12 07:06:45','2026-06-19 07:06:38');
/*!40000 ALTER TABLE `user_coupon` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 DROP DATABASE IF EXISTS `order_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `order_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `order_db`;
DROP TABLE IF EXISTS `order_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `shop_id` bigint NOT NULL COMMENT '店铺ID',
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `product_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `product_price` decimal(10,2) NOT NULL COMMENT '商品单价',
  `quantity` int NOT NULL DEFAULT '1' COMMENT '购买数量',
  `original_amount` decimal(10,2) NOT NULL COMMENT '原价总金额',
  `discount_amount` decimal(10,2) DEFAULT '0.00' COMMENT '优惠抵扣金额',
  `final_amount` decimal(10,2) NOT NULL COMMENT '实付金额',
  `coupon_id` bigint DEFAULT NULL COMMENT '使用的优惠券ID',
  `status` varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态: PENDING/PAID/CANCELLED/COMPLETED',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `order_info` WRITE;
/*!40000 ALTER TABLE `order_info` DISABLE KEYS */;
INSERT INTO `order_info` VALUES (1,'340778828173545472',8,5,11,'手机',8900.00,1,8900.00,60.00,8840.00,1,'PAID','2026-06-12 07:06:45','2026-06-12 07:06:52');
/*!40000 ALTER TABLE `order_info` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 DROP DATABASE IF EXISTS `inventory_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `inventory_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `inventory_db`;
DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `total_stock` int NOT NULL DEFAULT '0' COMMENT '总库存',
  `available_stock` int NOT NULL DEFAULT '0' COMMENT '可用库存',
  `locked_stock` int NOT NULL DEFAULT '0' COMMENT '锁定库存',
  `version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (8,8,60,60,0,0,'2026-06-12 07:03:31','2026-06-12 07:03:31'),(9,9,50,50,0,0,'2026-06-12 07:03:52','2026-06-12 07:03:52'),(10,10,180,180,0,0,'2026-06-12 07:04:14','2026-06-12 07:04:14'),(11,11,979,979,0,2,'2026-06-12 07:04:38','2026-06-12 15:06:52');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `inventory_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint NOT NULL COMMENT '商品ID',
  `order_no` varchar(50) NOT NULL COMMENT '关联订单号',
  `change_type` varchar(20) NOT NULL COMMENT '变更类型: LOCK/CONFIRM/RELEASE',
  `change_count` int NOT NULL COMMENT '变更数量',
  `before_available` int NOT NULL COMMENT '变更前可用库存',
  `after_available` int NOT NULL COMMENT '变更后可用库存',
  `before_locked` int NOT NULL COMMENT '变更前锁定库存',
  `after_locked` int NOT NULL COMMENT '变更后锁定库存',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `inventory_log` WRITE;
/*!40000 ALTER TABLE `inventory_log` DISABLE KEYS */;
INSERT INTO `inventory_log` VALUES (1,11,'340778828173545472','LOCK',1,980,979,0,1,'2026-06-12 07:06:45'),(2,11,'340778828173545472','CONFIRM',1,1,0,980,979,'2026-06-12 07:06:52');
/*!40000 ALTER TABLE `inventory_log` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;

/*!40000 DROP DATABASE IF EXISTS `payment_db`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `payment_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `payment_db`;
DROP TABLE IF EXISTS `payment_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_flow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_no` varchar(50) NOT NULL COMMENT '支付单号',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `flow_type` varchar(20) NOT NULL COMMENT '流水类型: PAYMENT/REFUND',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_payment_no` (`payment_no`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `payment_flow` WRITE;
/*!40000 ALTER TABLE `payment_flow` DISABLE KEYS */;
INSERT INTO `payment_flow` VALUES (1,'340778854492807168','340778828173545472','PAYMENT',8840.00,'支付成功','2026-06-12 07:06:52');
/*!40000 ALTER TABLE `payment_flow` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `payment_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `payment_no` varchar(50) NOT NULL COMMENT '支付单号',
  `order_no` varchar(50) NOT NULL COMMENT '关联订单号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `payment_method` varchar(20) DEFAULT 'BALANCE' COMMENT '支付方式',
  `status` varchar(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态: PENDING/SUCCESS/FAILED/REFUNDED',
  `paid_at` datetime DEFAULT NULL COMMENT '支付时间',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_no` (`payment_no`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='支付记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `payment_record` WRITE;
/*!40000 ALTER TABLE `payment_record` DISABLE KEYS */;
INSERT INTO `payment_record` VALUES (1,'340778854492807168','340778828173545472',8,8840.00,'ALIPAY','SUCCESS','2026-06-12 07:06:52','2026-06-12 07:06:50','2026-06-12 07:06:52');
/*!40000 ALTER TABLE `payment_record` ENABLE KEYS */;
UNLOCK TABLES;
DROP TABLE IF EXISTS `undo_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `undo_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `branch_id` bigint NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Seata AT模式回滚日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `undo_log` WRITE;
/*!40000 ALTER TABLE `undo_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `undo_log` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

