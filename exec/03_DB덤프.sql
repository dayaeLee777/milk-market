CREATE DATABASE  IF NOT EXISTS `milkmarket_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `milkmarket_db`;
-- MySQL dump 10.13  Distrib 5.7.35, for Win64 (x86_64)
--
-- Host: j6a504.p.ssafy.io    Database: milkmarket_db
-- ------------------------------------------------------
-- Server version	5.7.37

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` varchar(36) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `reg_time` datetime(6) DEFAULT NULL,
  `community_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbwy7a8hwdjqbm26qadw82ikqg` (`community_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKbwy7a8hwdjqbm26qadw82ikqg` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('2a301589-bc37-485d-9ed9-9b14d49cf7f2','반갑습니다!',0,'2022-04-07 06:39:03.147059','0e107d70-e215-4384-b472-2a1807edf55c','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('51d688a5-e515-4e84-9670-887d3e52fd5e','안녕하세요\n',0,'2022-04-07 10:20:28.126690','12fadadc-d608-480a-90bc-fa323b60bf28','677e1dbc-f813-4e6c-b26a-abf3294d510f'),('59d51485-c9cb-4ccc-9567-6f22dc295e8f','안녕하세요',0,'2022-04-06 19:51:41.822608','0e107d70-e215-4384-b472-2a1807edf55c','2b761905-18b8-4b4e-bfab-8fbf2a7de21f'),('cbd94a28-9bb6-4e79-92a0-770b75a6cd91','안녕하세요',1,'2022-04-07 14:04:03.386349','12fadadc-d608-480a-90bc-fa323b60bf28','ff230458-d9d0-44fb-9081-115c8d38febe');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community` (
  `id` varchar(36) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `hit` int(11) NOT NULL,
  `reg_time` datetime(6) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfsfwlfb2ummfsb30q78wo6se0` (`user_id`),
  CONSTRAINT `FKfsfwlfb2ummfsb30q78wo6se0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES ('0e107d70-e215-4384-b472-2a1807edf55c','잘 부탁드립니다.',0,4,'2022-04-06 19:48:55.852519','안녕하세요 새로 가입했습니다.','2b761905-18b8-4b4e-bfab-8fbf2a7de21f'),('12fadadc-d608-480a-90bc-fa323b60bf28','안녕하세요 아이들에게 좋지 않은 10가지 음식이 KBC방송을 통해 소개되어 유명해졌는데요 .\n참 좋은 것들만 아이들에게 주고 싶은데 의도와는 다르게 악영향을 끼칠 수 있다니 걱정이 드네요.\n항상 아이들에게 음식을 사줄 때 검색해보는 습관을 가져야 될 것 같습니다.\n아이들에게 좋지 않은 10가지 음식에 대해 알아보았는데요.\n여러분들도 음식 사주실 때 조심하시고 검색하는 습관을 가지셨으면 좋겠습니다!',0,5,'2022-04-07 06:48:56.447363','아이들에게 좋지 않은 10가지 음식','84cd6c17-c785-476d-b92e-1c8a6df5f1c4'),('3685e7b0-5b02-4208-9fb0-d2859c28a86a','지역축제에 참여하시는분?',0,2,'2022-04-06 19:51:34.476242','지역 축제 참여하시는 분 계신가요?','2b761905-18b8-4b4e-bfab-8fbf2a7de21f'),('4f0f9526-ef8e-4737-b0ec-ec699057946a','메가커피에서 모임할 예정입니다. 참여하실 분들 댓글 남겨 주세요',0,5,'2022-04-07 06:42:54.058594','잠실 어머니 모임 참여하실분.','2b75e975-393b-4ad7-9720-eab69c295faf'),('5d1b4b34-43d8-46b7-922a-810e070236a5','장소 : 역삼역 멀티캠퍼스 건물\n시간 : 오후 4시\n\n아이들을 더 잘 키우기 위해 강의가 필요하신 어머니들을 위해 유명 강사님을 초청했습니다.\n다과 제공 예정이며 4시 1층 홀에서 모여서 이동할 예정입니다.\n4시에 출발하니 늦지않게 오시길 바랍니다. ',0,13,'2022-04-07 06:41:19.500357','삼성 어머니 모임 포럼을 진행할 예정입니다.','d601b09b-3e14-4be1-ac47-7e9af161ac86');
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email`
--

DROP TABLE IF EXISTS `email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `email_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email`
--

LOCK TABLES `email` WRITE;
/*!40000 ALTER TABLE `email` DISABLE KEYS */;
/*!40000 ALTER TABLE `email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interest` (
  `id` varchar(36) NOT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `interest_like` bit(1) DEFAULT NULL,
  `item_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg07i1shpll2a8qbj3w6ysg7bq` (`item_id`),
  KEY `FKdg0cowio10tq086oaj1uxv7oe` (`user_id`),
  CONSTRAINT `FKdg0cowio10tq086oaj1uxv7oe` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg07i1shpll2a8qbj3w6ysg7bq` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
INSERT INTO `interest` VALUES ('a4ca6d3f-fb78-449a-9d5f-374ef86b50e1',0,_binary '','c082834e-8044-45c5-af23-a65b58aabf97','ff230458-d9d0-44fb-9081-115c8d38febe'),('ac0c5bcc-041a-4461-8dbf-0b6e020f49af',0,_binary '','5056325e-f30b-4357-8936-9513b46ae758','ff230458-d9d0-44fb-9081-115c8d38febe'),('c009679e-88db-4a0b-9ef5-9f274b2d2aaf',0,_binary '','de1ef662-c122-4bee-9bdd-1af44abc24d1','ff230458-d9d0-44fb-9081-115c8d38febe'),('c8d47f8e-e4be-4a51-a5c1-00cbecbc6d60',0,_binary '','a91b846d-bf07-43f0-8d46-7ebd21b51209','ff230458-d9d0-44fb-9081-115c8d38febe'),('cd998fac-912f-484d-9ca6-3aab0856ee5d',0,_binary '','0042701c-a086-4246-80e4-b8a916a44e4f','ff230458-d9d0-44fb-9081-115c8d38febe');
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` varchar(36) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `division` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL,
  `reg_date` datetime(6) DEFAULT NULL,
  `rent_end_date` datetime(6) DEFAULT NULL,
  `rent_start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh4epdoqikj4sfedlxcc9dwwnl` (`user_id`),
  CONSTRAINT `FKh4epdoqikj4sfedlxcc9dwwnl` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES ('0042701c-a086-4246-80e4-b8a916a44e4f','B03',0,'아기 카시트입니다. 2년 사용했지만 상태는 괜찮습니다. 동네 거래만 받습니다.','A02','아기 카시트',2,'2022-04-07 06:36:12.111543',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('3fb5b49e-8e33-43d6-9e04-e88e148b3874','B09',0,'집안 정리할 때까지 잠시 갖고 계실 분 찾습니다.','A01','4인 식탁 세트',1,'2022-04-07 06:18:40.265669','2022-05-01 03:17:00.000000','2022-04-07 15:17:00.000000','C01','2b75e975-393b-4ad7-9720-eab69c295faf'),('4dada2b7-87cb-44e2-adb1-b72ec30933e4','B15',0,'아이를 가졌을 때 읽었던 태교책 묶음 싸게 팝니다. 달의 아이 1,2,3,4 전편입니다.','A02','임산부 태교책',1,'2022-04-07 06:25:45.332123',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('5056325e-f30b-4357-8936-9513b46ae758','B02',0,'크로텍웨건 유아용 웨건 유모차 팝니다. 2~3세 아이로 3명까지 탑승할 수 있습니다.','A02','크로텍웨건 유모차',3,'2022-04-07 06:21:08.313673',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('53039549-386d-4de1-8702-449917e6c5cc','B02',0,'다이치 앨리 유모차입니다. 중고이며 사용한 기간은 1년입니다. 깨끗하게 사용했고 기능에 문제는 없습니다.','A02','다이치 앨리 유모차',1,'2022-04-07 06:04:19.783365',NULL,NULL,'C01','84cd6c17-c785-476d-b92e-1c8a6df5f1c4'),('59f4e018-3ff1-4204-bff4-541cce3b1f68','B02',0,'쥬베스 영유어 디럭스 고급형 유모차-Q2567DR 팝니다. 선물받은 상품이고요, 미개봉해서 새거나 마찬가지입니다. 기존 가격보다 조금 쌉니다.','A02','쥬베스',10,'2022-04-07 06:15:37.290838',NULL,NULL,'C01','2b75e975-393b-4ad7-9720-eab69c295faf'),('a2da753f-8859-4f3b-ae3c-1887b3338a50','B02',0,'영국 유모차에요','A02','영국 유모차',10,'2022-04-07 02:15:28.730500',NULL,NULL,'C01','ff230458-d9d0-44fb-9081-115c8d38febe'),('a91b846d-bf07-43f0-8d46-7ebd21b51209','B09',0,'보니스마트 제조사의 원목침대 팝니다. 시트는 사용했기 때문에 바꿔주셔야 될 것 같네요. 싸게 팝니다.','A02','아기 침대 팝니다.',1,'2022-04-07 06:05:37.507853',NULL,NULL,'C01','84cd6c17-c785-476d-b92e-1c8a6df5f1c4'),('c082834e-8044-45c5-af23-a65b58aabf97','B01',0,'아기용 상품입니다. 저자극 Noodle Boo사것입니다.','A02','아기 샴푸 저자극',1,'2022-04-07 06:33:10.700688',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('cc1ba5f1-29a6-4412-a466-3c8ee613a056','B09',0,'와이드 테이블 선물 받은 제품 팝니다. 미개봉 제품입니다.','A02','와이드 테이블 2인용',2,'2022-04-07 06:06:25.942424',NULL,NULL,'C01','84cd6c17-c785-476d-b92e-1c8a6df5f1c4'),('d0041f4b-a3d6-4492-89b3-314db97ef2ee','B10',0,'선물받은 임산부용 시애틀 샴푸입니다. loma제품입니다.','A02','임산부용 시애틀 샴푸',1,'2022-04-07 06:29:48.684213',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('d73cfae6-04d6-464b-a034-ee0143d1c801','B10',0,'임산부용 샴푸입니다. 3개중 1개 사용하고 남은 2통 팝니다. 빅그린 제품입니다.','A02','임산부 전용 샴푸',1,'2022-04-07 06:31:36.590783',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('de1ef662-c122-4bee-9bdd-1af44abc24d1','B02',0,'맘앤리틀 로이스 트라이크 프로 유모카 팝니다. 3년 사용해서 깨끗하지는 않아요. 기능은 잘 굴러갑니다. 필요하신 분 연락 주세요','A02','맘엔 리틀 로이스 유모차',1,'2022-04-07 06:22:58.106944',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('e527d484-1703-4045-9ae8-d95191a254a5','B15',0,'임산부용 바디 필로우 팝니다. 사용하지 않은 새 제품입니다. ','A02','임산부 바디 필로우',1,'2022-04-07 06:16:28.606361',NULL,NULL,'C01','2b75e975-393b-4ad7-9720-eab69c295faf'),('eac0a106-ff69-46a3-ac92-8d2956876f5b','B14',0,'저자극 물티슈 100매짜리 5세트 팝니다','A02','저자극 물티슈 팝니다.',2,'2022-04-07 06:37:41.286957',NULL,NULL,'C01','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('ead75c99-c1bf-48ec-93e0-d8e00394cc73','B09',0,'귀여운 아기 토끼 책상 팝니다. 사용 기간은 2개월 입니다. 깨끗하게 사용해서 기능에 문제 없습니다. ','A02','아기 토끼 책상 팝니다',3,'2022-04-07 06:07:33.700828',NULL,NULL,'C01','84cd6c17-c785-476d-b92e-1c8a6df5f1c4'),('f2110bef-e528-4eb2-9ee1-427ec8eb41bc','B09',0,'엘사 그려진 볼링 세트 팝니다.','A02','겨울왕국 볼링 세트 팝니다',1,'2022-04-07 06:13:17.221346',NULL,NULL,'C01','84cd6c17-c785-476d-b92e-1c8a6df5f1c4');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_image`
--

DROP TABLE IF EXISTS `item_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_image` (
  `id` varchar(36) NOT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `new_file_name` varchar(255) DEFAULT NULL,
  `origin_file_name` varchar(255) DEFAULT NULL,
  `item_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKta6kqet3u8mv95y7jwtgwqpys` (`item_id`),
  CONSTRAINT `FKta6kqet3u8mv95y7jwtgwqpys` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_image`
--

LOCK TABLES `item_image` WRITE;
/*!40000 ALTER TABLE `item_image` DISABLE KEYS */;
INSERT INTO `item_image` VALUES ('074d7b05-0127-48d1-8c43-e4887aabf8ee',0,'be5a35c8-3441-4d19-ae96-4712e1516bdf.jpg','크로텍웨건 유아용 웨건.jpg','5056325e-f30b-4357-8936-9513b46ae758'),('1d066ea4-e775-48a7-a254-2c7872b6a385',0,'b90c0619-c021-42a7-aa09-850341dfca52.jpg','임산부 바디 필로우.jpg','e527d484-1703-4045-9ae8-d95191a254a5'),('2c88bf0f-8e82-4424-82b6-65ef464ccc60',0,'1f061821-4bc5-42d5-8519-45ab092d94ee.jpg','다이치 앨리 유모차.jpg','53039549-386d-4de1-8702-449917e6c5cc'),('2eca527e-4e96-440c-929f-e8d810be3e38',0,'96e4d33e-6774-4520-bf8f-326565ca8dcc.jpg','유모차.jpg','a2da753f-8859-4f3b-ae3c-1887b3338a50'),('3b363e45-008e-48e7-8c64-b8c97c7aaa17',0,'64e65315-4be9-4766-9bc6-8e2fd589c2a9.jpg','쥬베스 영유어 디럭스 고급형 유모차-Q2567DR.jpg','59f4e018-3ff1-4204-bff4-541cce3b1f68'),('48958af4-659f-4f22-8159-115a7284a3ce',0,'483995c0-2689-4ad2-ae60-ed568b3b5ca0.jpg','noodleboo.jpg','c082834e-8044-45c5-af23-a65b58aabf97'),('61053fb6-f04c-4a3e-ab0b-e7c139b135cc',0,'e1fa41ce-f60b-470b-a6b7-30bba441f652.jpg','img.jpg','d0041f4b-a3d6-4492-89b3-314db97ef2ee'),('716fa0ad-0c9e-46f3-af1c-3a1eda215c93',0,'bb85eb8a-c0ed-4eb1-9d90-da27d4dac555.jpg','저자극 물티슈.jpg','eac0a106-ff69-46a3-ac92-8d2956876f5b'),('727d8408-eb48-4e20-b348-190183b2ba8e',0,'60b99503-7982-4920-993d-9d1f263f480b.jpg','유아책상 와이드 테이블_.jpg','cc1ba5f1-29a6-4412-a466-3c8ee613a056'),('7c979765-8b80-44e3-acff-86b94da7ed0c',0,'5b359c3c-4f1e-42e6-8424-383cdca3b98e.jpg','보니스마트 유아원목침대.jpg','a91b846d-bf07-43f0-8d46-7ebd21b51209'),('8aca4ad4-0509-4dfd-b2d7-a182c967b174',0,'3fb4a3af-d669-47cd-9535-4eb692f9f86d.jpg','장난감 볼링 엘사.jpg','f2110bef-e528-4eb2-9ee1-427ec8eb41bc'),('a5d94069-bb4c-4587-a940-f48a4d8284f6',0,'cf546150-54b0-40e4-83f7-85bc7fc060ca.jpg','맘앤리틀 로이스 트라이크 프로 유모카.jpg','de1ef662-c122-4bee-9bdd-1af44abc24d1'),('ad9fb9bd-be76-4bb8-b5a1-5646dc8148ab',0,'996ed3bb-f271-4433-bd2e-7e4858dcc13d.png','아기카시트.png','0042701c-a086-4246-80e4-b8a916a44e4f'),('bf32d314-23f5-4303-88fd-39ad95e74f77',0,'62760105-812f-4d11-a862-06849011a316.jpg','2인 유아 책상.jpg','ead75c99-c1bf-48ec-93e0-d8e00394cc73'),('db8e36fb-df16-4b5c-8830-08431c828419',0,'5e884494-246a-49b9-9e6d-878bb945eceb.jpg','4인테이블.jpg','3fb5b49e-8e33-43d6-9e04-e88e148b3874'),('ef6644ac-c394-4b28-bc95-80060a5f75da',0,'fd69c960-8001-48bf-81ed-e5e09753bea0.jpg','임산부 샴푸.jpg','d73cfae6-04d6-464b-a034-ee0143d1c801'),('f90d52bb-cfcb-40c1-b1a6-0c266006ad83',0,'7d4f6e8f-d9a2-470c-be0e-04e6a7cbf0c9.jpg','태교책.jpg','4dada2b7-87cb-44e2-adb1-b72ec30933e4');
/*!40000 ALTER TABLE `item_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_image`
--

DROP TABLE IF EXISTS `profile_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profile_image` (
  `id` varchar(36) NOT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `new_file_name` varchar(255) DEFAULT NULL,
  `origin_file_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7c5ge678vgxydo2sepdmrj6ge` (`user_id`),
  CONSTRAINT `FK7c5ge678vgxydo2sepdmrj6ge` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_image`
--

LOCK TABLES `profile_image` WRITE;
/*!40000 ALTER TABLE `profile_image` DISABLE KEYS */;
INSERT INTO `profile_image` VALUES ('138ce9df-657f-486a-9e5c-1967e6abb61b',0,'9fa79541-ec87-4763-9685-c56f21b5f56a.JPG','캡처.JPG','d601b09b-3e14-4be1-ac47-7e9af161ac86'),('42ebdd93-84bc-4cbe-85ab-05a9a1015689',0,'04cb4558-2087-45ad-966a-1eda48cd13dd.jpg','blackcat-1024x576.jpg','2b761905-18b8-4b4e-bfab-8fbf2a7de21f'),('60c2b5a6-3045-4611-a2b7-eea68d25377f',0,'90237ed8-2e21-42fb-b00d-245319895e39.jpg','IMG_20171008_123001.jpg','67595eee-e81d-413e-ab24-113ebdd8dabe');
/*!40000 ALTER TABLE `profile_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `id` varchar(36) NOT NULL,
  `item_id` varchar(255) DEFAULT NULL,
  `order_item_name` varchar(255) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86i0stm7cqsglqptdvjij1k3m` (`user_id`),
  CONSTRAINT `FK86i0stm7cqsglqptdvjij1k3m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(36) NOT NULL,
  `bcode` varchar(255) DEFAULT NULL,
  `bname` varchar(255) DEFAULT NULL,
  `del_yn` tinyint(1) DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_description` varchar(255) DEFAULT NULL,
  `profile_image` varchar(255) DEFAULT NULL,
  `sigungu` varchar(255) DEFAULT NULL,
  `wallet_address` varchar(255) DEFAULT NULL,
  `wallet_private_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_n4swgcf30j6bmtb4l4cjryuym` (`nickname`),
  UNIQUE KEY `UK_9ga8tj7f3cpyntf1xebugca74` (`wallet_address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('2b75e975-393b-4ad7-9720-eab69c295faf','4113511000','백현동',0,'dodo0094@naver.com','도도스','{bcrypt}$2a$10$.qvCDbFinJINOthBD/omzuSenXDlG.B69HZTCAD5MDqvO8NnIJl7a','','','성남시 분당구',NULL,NULL),('2b761905-18b8-4b4e-bfab-8fbf2a7de21f','3011010700','판암동',0,'yhy0818@gmail.com','희구글','{bcrypt}$2a$10$3lyMZq2NUICkYEUOZuQnP.Y1cSM4k0geNCR.ENqmrJYmRbBZBUs02','','https://milkmarket-bucket.s3.ap-northeast-2.amazonaws.com/04cb4558-2087-45ad-966a-1eda48cd13dd.jpg','동구',NULL,NULL),('32ef2324-34ca-498e-9a23-03329063bb01',NULL,NULL,0,'fsa3806@naver.com','권도혁','{bcrypt}$2a$10$O97NgrtvsvpZXfk11K8BueLGlOPVChQKDxcOnpBwY0llwEkvzpjyO','','http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg',NULL,NULL,NULL),('53ea94d8-8be6-464c-95f3-72f74258614a','1168010600','대치동',0,'vnvn@naver.com','푸푸린','{bcrypt}$2a$10$/ShmxOvPESKYofDRJgS96u2QzzNEPODMj9KGIEgW3SLOkNQDdFELa','','','강남구',NULL,NULL),('67595eee-e81d-413e-ab24-113ebdd8dabe','1162010200','신림동',0,'yunhy96@naver.com','네희버','{bcrypt}$2a$10$fRMJUvmTUZAhqeFWZxTf9uUJ5BtNo29jACAJ.ivLogJmJNJ0wP/.a','','https://milkmarket-bucket.s3.ap-northeast-2.amazonaws.com/90237ed8-2e21-42fb-b00d-245319895e39.jpg','관악구',NULL,NULL),('677e1dbc-f813-4e6c-b26a-abf3294d510f','1138010700','응암동',0,'sahngrhee@gmail.com','sahngrhee','{bcrypt}$2a$10$5bqhgt9h9ltziGFfb0SqAOVneS9re9WgC5.7ZY0WWJ.E5FiPEISb.','','','은평구',NULL,NULL),('76ed7cea-dec9-4e3e-a475-2456b8647248',NULL,NULL,0,'ldy1853@naver.com','이다예','{bcrypt}$2a$10$vaMzMf5Y3DsMOnQ.JSoL6.zoy9LvchskC/9dAORMi31YJ7nwg5Gja','','http://k.kakaocdn.net/dn/4lAlm/btrtb3BodOU/PDAoZwowZJWxF442erKR5K/img_110x110.jpg',NULL,NULL,NULL),('84cd6c17-c785-476d-b92e-1c8a6df5f1c4','5013025031','무릉리',0,'fsa3800@naver.com','바나나하바나','{bcrypt}$2a$10$XSvrk7Ecne79usWtxz4wP.v6EG4z41B0eS0NV/CoyDiie4UcdMoA6','','','서귀포시',NULL,NULL),('d601b09b-3e14-4be1-ac47-7e9af161ac86','1171010100','잠실동',0,'ssafy3806@naver.com','삼성맨','{bcrypt}$2a$10$W20AywSHJfWaVE2VOqjdyeFGdxhcBzV6KjeDY3sYIGVaTQVCrlruy','','https://milkmarket-bucket.s3.ap-northeast-2.amazonaws.com/9fa79541-ec87-4763-9685-c56f21b5f56a.JPG','송파구',NULL,NULL),('e4c9220c-9215-47cb-87cf-f2fce809026b','4117310100','비산동',0,'okqwaszx12@naver.com','야채부락리123','{bcrypt}$2a$10$WgAJFrbV2QNXsfo0/g4Lrek.Q8V/J2gD97BgcbIhVbhRIVXfgFWpW','','','안양시 동안구','0x727680Ae24855FA5c10340C2E22AF3bC14efd97E','{bcrypt}$2a$10$DBvOxaJVUa1RuSUJWTT7g./jbOU7FMLk3.HV2eBDD3FCxxNIIc7.K'),('ff230458-d9d0-44fb-9081-115c8d38febe','4113510800','판교동',0,'test@naver.com','호근맘','{bcrypt}$2a$10$1g3ub65t7RjiQHXt0wqKM.sb7pWum1wa75bCBAEY1tQgTcYOLMPue','','','성남시 분당구',NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` varchar(36) NOT NULL,
  `roles` varchar(255) DEFAULT NULL,
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('e4c9220c-9215-47cb-87cf-f2fce809026b','ROLE_USER'),('2b761905-18b8-4b4e-bfab-8fbf2a7de21f','ROLE_USER'),('67595eee-e81d-413e-ab24-113ebdd8dabe','ROLE_USER'),('ff230458-d9d0-44fb-9081-115c8d38febe','ROLE_USER'),('84cd6c17-c785-476d-b92e-1c8a6df5f1c4','ROLE_USER'),('2b75e975-393b-4ad7-9720-eab69c295faf','ROLE_USER'),('d601b09b-3e14-4be1-ac47-7e9af161ac86','ROLE_USER'),('677e1dbc-f813-4e6c-b26a-abf3294d510f','ROLE_USER'),('53ea94d8-8be6-464c-95f3-72f74258614a','ROLE_USER'),('76ed7cea-dec9-4e3e-a475-2456b8647248','ROLE_USER'),('32ef2324-34ca-498e-9a23-03329063bb01','ROLE_USER');
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

-- Dump completed on 2022-04-07 15:13:27
