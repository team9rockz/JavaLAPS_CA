package sg.edu.nus.javalapsteam9.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import sg.edu.nus.javalapsteam9.enums.LeaveType;

public class LeaveTypeValidator implements ConstraintValidator<LeaveTypeConstraint, LeaveType> {
	
	@Override
	public void initialize(LeaveTypeConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
	@Override
	public boolean isValid(LeaveType value, ConstraintValidatorContext context) {
		return (value != null);
	}

}
