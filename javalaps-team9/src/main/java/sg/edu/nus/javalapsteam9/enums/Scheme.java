package sg.edu.nus.javalapsteam9.enums;

public enum Scheme {
	
	PROFESSIONAL("PROFESSIONAL"),
	ADMINISTRATIVE("ADMINISTRATIVE");
	
	private String scheme;
	
	private Scheme(String scheme) {
		this.scheme = scheme;
	}

	public String getScheme() {
		return scheme;
	}
}
