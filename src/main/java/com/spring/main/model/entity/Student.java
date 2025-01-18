package com.spring.main.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.spring.main.annotation.ValidateField;
import com.spring.main.constants.RegexConstant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
/**
 * @author Nitesh
 */
@Data
@NoArgsConstructor
@Entity
@Table(name="student")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	long id;
	
	@Column(name = "first_name")
	@ValidateField(required = true,minLength = 5,maxLength = 45,checkMaxLength = true,checkMinLength = true,regex = RegexConstant.ALPHANUMERIC)
	String firstName;
	
	@Column(name = "last_name")
	@ValidateField(required = true,minLength = 5,maxLength = 45,checkMaxLength = true,checkMinLength = true,regex = RegexConstant.ALPHANUMERIC)
	String lastName;
	
	@Column(name = "email")
	@ValidateField(required = true,minLength = 5,maxLength = 45,checkMaxLength = true,checkMinLength = true,regex = RegexConstant.EMAIL)
	String email;
	
	@Column(name = "student_roll_number")
    int student_roll_number;
    
	@Column(name = "is_allowed")
	@JsonProperty("is_allowed")
    boolean is_allowed;

	public Student(long id, String firstName, String lastName, String email, int student_roll_number,
			boolean is_allowed) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.student_roll_number = student_roll_number;
		this.is_allowed = is_allowed;
	}
	
	@Override
	public String toString() {
		try {
			return new Gson().toJson(this);
		}catch(Exception e) {
			return "";
		}
	}

}
