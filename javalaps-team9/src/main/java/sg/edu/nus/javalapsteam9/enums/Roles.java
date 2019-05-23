package sg.edu.nus.javalapsteam9.enums;

public enum Roles {

	STAFF("STAFF"),
	MANAGER("MANAGER"),
	ADMIN("ADMIN");
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

}
