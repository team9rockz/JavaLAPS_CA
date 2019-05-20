package sg.edu.nus.javalapsteam9.validation;

import org.springframework.validation.FieldError;

public class CustomFieldError extends FieldError {
	
	private static final long serialVersionUID = 7461238877915791505L;

	private String objectName;
	
	private String field;
	
	private Object rejectedValue;
	
	private String defaultMessage;
	
	public CustomFieldError(String objectName, String field, String defaultMessage) {
		this(objectName, field, null, defaultMessage);
	}
	
	public CustomFieldError(String objectName, String field, Object rejectedValue, String defaultMessage) {
		super(objectName, field, rejectedValue, false, null, null, defaultMessage);
		this.objectName = objectName;
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.defaultMessage = defaultMessage;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getRejectedValue() {
		return rejectedValue;
	}

	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}

	public void setDefaultMessage(String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}

}
