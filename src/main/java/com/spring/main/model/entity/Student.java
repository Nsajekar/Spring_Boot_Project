package com.spring.main.model.entity;

import com.google.gson.Gson;
import com.spring.main.constants.MasterConstants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@Size(min = 2,max = 45,message = MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH)
	String firstName;
	
	@Column(name = "last_name")
	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@Size(min = 2,max = 45,message = MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH)
	String lastName;
	
	@Column(name = "email")
	@NotNull(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@NotBlank(message = MasterConstants.ErrorResponseCodes.REQ_DATA)
	@Size(min = 2,max = 45,message = MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH)
	@Email(message = MasterConstants.ErrorResponseCodes.INVALID_DATA_TYPE)
	String email;

	public Student(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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
