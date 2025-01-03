CREATE DATABASE IF NOT EXISTS `services_nitesh_db` /*!40100 DEFAULT CHARACTER SET latin1 */;
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

select partition_id,request_ref_no,request_time_stamp from api_audit;
	
