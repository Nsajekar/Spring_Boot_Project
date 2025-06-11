USE `services_nitesh_db`;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;


CREATE TABLE `user_table` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) DEFAULT NULL,
  `pass_word` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `student_roll_number` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`, `user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
