package com.spring.main.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.main.annotation.Log;
import com.spring.main.model.entity.Student;

import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Log
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EntityManagerExampleDaoImpl implements EntityManagerExampleDao {
	
	EntityManager entityManager;
	
	public EntityManagerExampleDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Log(logParameter = true)
	@Override
	@Transactional
	public void save(Student student) {
		entityManager.persist(student);
	}

	@Log(logParameter = true,logReturn = true)
	@Override
	public Student findById(int id) {
		return entityManager.find(Student.class, id);
	}

}
