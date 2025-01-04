package com.spring.main.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.main.model.entity.Student;
import com.spring.main.repository.EntityManagerExampleDao;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

/**
 * @author Nitesh
 * 
 * @since 04-01-2025
 * 
 * @version 1.0
 * 
 * {@summary - }
 * #(I)EntityManager (INTERFACE) ->
 * 1.Low Level Control And Flexibility
 * 2.Want to write custom queries
 * 3.Provide low level access to JPA and work directly with JAP entities
 * 4.Complex Queries That Required advance features like native SQL Queries or stored Procedure
 * 5.When We have Custom Requirements that are not easily handled by high level abstraction 
 * 
 * #(II)JPARepository (INTERFACE) ->
 * 1.It Is Used For High Level Of Abstraction
 * 2.Provide Commonly Used CRUD Operations thus reducing code
 * 3.Additional features such as pagination or sorting
 * 4.generate queries based on method Names
 * 5.can also create custom queries using @Query
 * 
 * #(III)JPA Query Language
 * 1.Similar to SQL concepts i.e where ,like,join ,in etc
 * 2.based on entity name and entity fields 
 */
@RestController
@Tag(name = "Spring Rest Data JPA Controller", description = "Spring Boot Rest API Data JPA")
@RequestMapping(value = "/spring/datajpa")
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SpringDataJPAController {
	
	EntityManagerExampleDao entityManagerExampleDao;
	
	public SpringDataJPAController(EntityManagerExampleDao entityManagerExampleDao) {
		super();
		this.entityManagerExampleDao = entityManagerExampleDao;
	}

	@GetMapping("/findById/{id}")
	public Student findById(@PathVariable int id) {
		return entityManagerExampleDao.findById(id);
	}
	
	@GetMapping("/findAll")
	public List<Student> findAll() {
		return entityManagerExampleDao.findAll();
	}
	
	@GetMapping("/findByLastName/{lastName}")
	public List<Student> findByLastName(@PathVariable String lastName) {
		return entityManagerExampleDao.findByLastName(lastName);
	}
	
	@PostMapping("/addStudent")
	public String addStudent(@RequestBody Student entity) {
		entityManagerExampleDao.save(entity);
		return String.valueOf(entity.getId());
	}
	
	@PutMapping("/updateStudent/{id}")
	public Student updateStudent(@PathVariable int id,@RequestParam(required = true) String lastName) {
		Student student = entityManagerExampleDao.findById(id);
		student.setLastName(lastName);
		entityManagerExampleDao.update(student);
		return entityManagerExampleDao.findById(id);
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public String updateStudent(@PathVariable int id) {
		entityManagerExampleDao.delete(id);
		return String.valueOf(id);
	}
	
	@DeleteMapping("/deleteStudentByID/{id}")
	public int deleteStudentByID(@PathVariable int id) {
		return entityManagerExampleDao.deleteById(id);
	}
	
}
