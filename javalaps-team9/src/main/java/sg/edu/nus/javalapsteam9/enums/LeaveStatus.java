package sg.edu.nus.javalapsteam9.enums;

public enum LeaveStatus {
	
	APPLIED("APPLIED"),
	UPDATED("UPDATED"),
	DELETED("DELETED"),
	CANCELLED("CANCELLED"),
	APPROVED("APPROVED"),
	REJECTED("REJECTED");
	
	private String status;
	
	private LeaveStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
}
