package sg.edu.nus.javalapsteam9.enums;

public enum Roles {

	AMDIN("ADMIN"),
	MANAGER("MANAGER"),
	STAFF("STAFF");
	
	private String role;
	
	private Roles(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}

}
