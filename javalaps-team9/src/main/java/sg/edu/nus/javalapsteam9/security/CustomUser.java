package sg.edu.nus.javalapsteam9.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sg.edu.nus.javalapsteam9.enums.Roles;

public class CustomUser implements UserDetails {

	private static final long serialVersionUID = -8415787124664550223L;
	
	private int id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	private boolean accountNonExpired;
	
	private boolean accountNonLocked;
	
	private boolean credentialsNonExpired;
	
	private boolean enabled;
	
	private List<Authority> authorities;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CustomUser(String username, String password, String email, String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		authorities = new ArrayList<>();
		authorities.add(new Authority(role));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public String getEmail() {
		return email;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

}
