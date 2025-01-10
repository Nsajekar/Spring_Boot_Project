package com.spring.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.main.model.entity.Student;

public interface JpaRepositoryExample extends JpaRepository<Student, Integer>{

}
