USE `services_nitesh_db`;

--
-- Table structure for table `student`
--

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


ALTER TABLE student
ADD student_roll_number int NOT NULL DEFAULT 0, 
ADD is_allowed BOOLEAN NOT NULL;