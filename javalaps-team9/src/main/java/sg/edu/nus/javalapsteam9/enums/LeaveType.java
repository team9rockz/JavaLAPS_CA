package sg.edu.nus.javalapsteam9.enums;

public enum LeaveType {
	
	ANNUAL("ANNUAL"),
	MEDICAL("MEDICAL"),
	COMPENSATION("COMPENSATION");
	
	private String type;
	
	private LeaveType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
