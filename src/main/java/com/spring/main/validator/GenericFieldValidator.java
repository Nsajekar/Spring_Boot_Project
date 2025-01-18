package com.spring.main.validator;

import java.util.regex.Pattern;

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
	
	boolean required;
	int minLength;
	int maxLength;
	boolean checkMinLength;
	boolean checkMaxLength;
	RegexConstant regexPattern;
	
	@Override
	public void initialize(ValidateField constraintAnnotation) {
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
		//IF FIELD IS REQUIRED BY DEFAULT
		if (required && StringUtils.isBlank(value.toString())) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.REQ_DATA)
					.addConstraintViolation();
			flag = false;
		}
        //IF DATATYPE IN OF NUMBER
		if (flag && value instanceof Number) {
			if (required && ((Number) value).doubleValue() == 0) {
				context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.REQ_DATA)
						.addConstraintViolation();
				flag = false;
			} else {
				flag = validateregex(value, flag, context);
			}
		}
		//VALIDATE REGEX
		if(flag && !validateregex(value, flag, context)) {
			flag = false;
		} 
		//VALIDATE MIN AND MAX LENGTH
		if (flag && value != null && StringUtils.isNotBlank(value.toString())) {
			String strvalue = value.toString();
			flag = validateMinLength(flag, context, strvalue);
			if(flag) {
				flag = validateMaxLength(flag, context, strvalue);
			}
		}
		return flag;
	}

	private boolean validateMaxLength(boolean formFlag, ConstraintValidatorContext context, String strvalue) {
		if (checkMaxLength && strvalue.length() > maxLength) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH)
					.addConstraintViolation();
			formFlag = false;
		}
		return formFlag;
	}

	private boolean validateMinLength(boolean formFlag, ConstraintValidatorContext context, String strvalue) {
		if (checkMinLength && strvalue.length() < minLength) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_LENGTH)
					.addConstraintViolation();
			formFlag = false;
		}
		return formFlag;
	}

	private boolean validateregex(Object value, boolean formFlag, ConstraintValidatorContext context) {
		if (!Pattern.compile(regexPattern.getPattern()).matcher(String.valueOf(value)).matches()) {
			context.buildConstraintViolationWithTemplate(MasterConstants.ErrorResponseCodes.INVALID_DATA_TYPE)
					.addConstraintViolation();
			formFlag = false;
		}
		return formFlag;
	}

}
