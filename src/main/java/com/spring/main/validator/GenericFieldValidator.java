package com.spring.main.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.spring.main.annotation.ValidateField;
import com.spring.main.constants.MasterConstants;
import com.spring.main.constants.RegexConstant;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenericFieldValidator implements ConstraintValidator<ValidateField, Object>{
	
	String fieldName;
	boolean required;
	int minLength;
	int maxLength;
	boolean checkMinLength;
	boolean checkMaxLength;
	RegexConstant regexPattern;
	
	private static final String OBJECT = "object";

	@Override
	public void initialize(ValidateField constraintAnnotation) {
		this.fieldName=constraintAnnotation.fieldName();
		this.required = constraintAnnotation.required();
		this.minLength = constraintAnnotation.minLength();
		this.maxLength = constraintAnnotation.maxLength();
		this.checkMinLength = constraintAnnotation.checkMinLength();
		this.checkMaxLength = constraintAnnotation.checkMaxLength();
		this.regexPattern = constraintAnnotation.regex();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		boolean flag = true;
		context.disableDefaultConstraintViolation();
		
		if (required && value == null) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.REQ_DATA);
			flag = false;
		}

		if (flag && value instanceof Number) {
			if (required && ((Number) value).doubleValue() == 0) {
				context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.REQ_DATA);
				flag = false;
			} else {
				flag = validateregex(value, flag, context);
			}
		}
		
		if (flag && value != null && StringUtils.isNotBlank(value.toString())) {
			String strvalue = value.toString();
			if(validateMinLength(flag, context, strvalue)) {
				flag = validateMaxLength(flag, context, strvalue);
			}
		}
		
		if(flag && !validateregex(value, flag, context)) {
			flag = false;
		} 
		
		return flag;
	}

	private boolean validateMaxLength(boolean formFlag, ConstraintValidatorContext context, String strvalue) {
		if (checkMaxLength && strvalue.length() > maxLength) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH);
			formFlag = false;
		}
		return formFlag;
	}

	private boolean validateMinLength(boolean formFlag, ConstraintValidatorContext context, String strvalue) {
		if (checkMinLength && strvalue.length() < minLength) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH);
			formFlag = false;
		}
		return formFlag;
	}

	private boolean validateregex(Object value, boolean formFlag, ConstraintValidatorContext context) {
		if (!String.valueOf(value).matches(regexPattern.getPattern())) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_TYPE);
			formFlag = false;
		}
		return formFlag;
	}

}
