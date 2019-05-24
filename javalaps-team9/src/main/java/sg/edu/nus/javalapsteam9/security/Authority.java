package sg.edu.nus.javalapsteam9.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 769283446487580865L;
	
	private String name;
	
	public Authority(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return name;
	}

}
