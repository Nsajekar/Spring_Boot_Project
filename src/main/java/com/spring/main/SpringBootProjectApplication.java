package com.spring.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author nsajekar
 * 
 * Note :
 * FOR OFFICE LAPTOP
 * 1.Before Running Project Add -DLOG_PATH=C:/NITESH/MY_SPACE/SPRING_BOOT/logs To JVM Argument 
 * 2.Before Building Project Add clean install package -DLOG_PATH=C:/NITESH/MY_SPACE/SPRING_BOOT/logs To Goals
 * FOR HOME LAPTOP
 * 1.Before Running Project Add -DLOG_PATH=D:/Nitesh/LEARNINGS/MY_SPACE/SPRING_BOOT/logs To JVM Argument 
 * 2.Before Building Project Add clean install package -DLOG_PATH=D:/Nitesh/LEARNINGS/MY_SPACE/SPRING_BOOT/logs To Goals
 */
@SpringBootApplication
public class SpringBootProjectApplication{
	
	private static final Logger logger = LogManager.getLogger(SpringBootProjectApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectApplication.class, args);
		logger.info("INFO : Application Started..."); 
		logger.debug("DEBUG : Application Started...");
		logger.error("ERROR : Application Started...");
		logger.warn("WARN : Application Started...");
	}
	
}
