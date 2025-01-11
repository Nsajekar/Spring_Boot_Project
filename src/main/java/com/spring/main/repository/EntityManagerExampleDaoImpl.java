package com.spring.main.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.main.annotation.Log;
import com.spring.main.model.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
/**
 * @author Nitesh
 */
@Log
@Repository
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EntityManagerExampleDaoImpl implements EntityManagerExampleDao {
	
	private static final String DELETE_BY_ID = "DELETE FROM Student Where id=:id";
	private static final String QUERY_FIND_ALL = "FROM Student ORDER BY firstName asc";
	private static final String QUERY_FIND_BY_LAST_NAME = "FROM Student WHERE lastName=:theData";
	
	EntityManager entityManager;
	
	public EntityManagerExampleDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	/**
	 * Create
	 */
	@Log(logParameter = true)
	@Override
	@Transactional
	public void save(Student student) {
		if(student != null) {
			entityManager.persist(student);
		}else {
			entityManager.merge(student);
		}
	}

	/**
	 * Read
	 */
	@Log(logParameter = true,logReturn = true)
	@Override
	public Student findById(long id) {
		return entityManager.find(Student.class, id);
	}

	@Log(logReturn = true)
	@Override
	public List<Student> findAll() {
		TypedQuery<Student> theQuery = entityManager.createQuery(QUERY_FIND_ALL, Student.class);
		return theQuery.getResultList();
	}

	@Log(logParameter = true,logReturn = true)
	@Override
	public List<Student> findByLastName(String lastName) {
		TypedQuery<Student> theQuery = entityManager.createQuery(QUERY_FIND_BY_LAST_NAME, Student.class);
		theQuery.setParameter("theData", lastName);
		return theQuery.getResultList();
	}

	/**
	 * Update
	 */
	@Log(logParameter = true)
	@Override
	@Transactional
	public void update(Student student) {
		entityManager.merge(student);
	}


	/**
	 * Delete
	 */
	@Log(logParameter = true)
	@Override
	@Transactional
	public void delete(int id) {
		Student student = entityManager.find(Student.class, id);
		entityManager.remove(student);
	}

	@Log(logParameter = true,logReturn = true)
	@Override
	@Transactional
	public int deleteById(int id) {
		Query theQuery = entityManager.createQuery(DELETE_BY_ID);
		theQuery.setParameter("id", id);
		return theQuery.executeUpdate();
	}
}
