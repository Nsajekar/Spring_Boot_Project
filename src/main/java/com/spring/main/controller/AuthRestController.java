package com.spring.main.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthRestController {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//TODO -IN SPRING ADD SPRING DATA JPA && ENCRYPTION DECRYPTION 
	//TODO- IN ANGULAR ADD REACTIVE FORMS && VALIDATION
	
	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody Map<String,String> entity) {
		String userName = entity.get("username");
		String password = entity.get("password");
		if(userName.equals("Nitesh") && password.equals("Nitesh@123")) {
            return  ResponseEntity.ok("Login Successfull");
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credintials");
		}
	}
	
	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody Map<String,String> entity) {
		String userName = entity.get("username");
		String password = entity.get("password");
		try {
			if(StringUtils.isNoneBlank(userName) && StringUtils.isNoneBlank(password)) {
				int result = jdbcTemplate.update("INSERT INTO user_table(user_name,pass_word) VALUES (?,?)", userName,password);
	            if(result>0) {
	            	return ResponseEntity.status(HttpStatus.CREATED).body("User Registered Successfully");
	            }else {
	            	return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Not Modified");
	            }
			}else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Name / Password Is Blanck");
			}
		}catch (DuplicateKeyException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User Alredy Registered");
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error Occured!");
		}
	}
}
