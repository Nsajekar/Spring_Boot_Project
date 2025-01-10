package com.spring.main.repository;

import java.util.List;

import com.spring.main.model.entity.Student;

public interface EntityManagerExampleDao {

	void save(Student student);
	
	Student findById(int id);
	
	List<Student> findAll();
	
	List<Student> findByLastName(String lastName);
	
	void update(Student student);
	
	/**
	 * Internal Method of Entity manager
	 * @param id
	 */
	void delete(int id);
	
	/**
	 * Using Query in Entity manager
	 * @param id
	 * @return
	 */
	int deleteById(int id);
}
