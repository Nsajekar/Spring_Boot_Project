CREATE DATABASE IF NOT EXISTS `services_nitesh_db_01`
USE `services_nitesh_db`;

-- Dumping structure for table services_prezzy.api_audit
CREATE TABLE IF NOT EXISTS `api_audit` (
  `partition_id` varchar(255) NOT NULL,
  `request_ref_no` varchar(255) NOT NULL,
  `request_time_stamp` varchar(255) NOT NULL,
  `request_data` text DEFAULT NULL,
  `response_data` longtext DEFAULT NULL,
  `response_code` varchar(255) DEFAULT NULL,
  `channel_id` varchar(255) DEFAULT NULL
);

INSERT INTO `api_audit` (`partition_id`, `request_ref_no`,`request_time_stamp`) 
VALUES ('REN', '172898710044','17-12-2024');

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name`varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `student_roll_number` int NOT NULL DEFAULT 0, 
  `is_allowed` BOOLEAN NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user_table`;

CREATE TABLE `user_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `pass_word` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `student_roll_number` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`, `user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



	
