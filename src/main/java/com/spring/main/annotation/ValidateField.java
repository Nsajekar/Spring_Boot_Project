package com.spring.main.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.spring.main.constants.RegexConstant;
import com.spring.main.validator.GenericFieldValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * @author Nitesh
 * 
 * @since 11-01-2025
 * 
 * @version 1.0
 * 
 * {@summary - }
 * I.This Annotation Validates Values In field Based On Parameters pass to it.
 * II.This Annotation Must Be Only Be used For Data Type i.e String,boolean & Numeric Type Like integer,long,double,float 
 */
@Constraint(validatedBy = GenericFieldValidator.class)
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateField {
	String fieldName()default "";

	boolean required() default true;

	int minLength() default 0;

	int maxLength() default Integer.MAX_VALUE;

	boolean checkMinLength() default true;

	boolean checkMaxLength() default true;

	RegexConstant regex() default RegexConstant.DEFAULT;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
