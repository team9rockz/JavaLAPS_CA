package sg.edu.nus.javalapsteam9.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = LeaveTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LeaveTypeConstraint {
	String message() default "Invalid Leave Type";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
