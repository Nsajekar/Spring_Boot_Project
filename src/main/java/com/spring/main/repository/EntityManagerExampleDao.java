package com.spring.main.repository;

import com.spring.main.model.entity.Student;

public interface EntityManagerExampleDao {

	void save(Student student);
	
	Student findById(int id);
}
