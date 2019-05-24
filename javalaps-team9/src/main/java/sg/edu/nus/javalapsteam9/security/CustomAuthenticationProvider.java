package sg.edu.nus.javalapsteam9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired private CustomUserDetailsService userDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authenticateUser(authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (authentication.equals(UsernamePasswordAuthenticationToken.class));
	}

	private Authentication authenticateUser(Authentication authentication) {
		CustomUser user = (CustomUser) userDetailService.loadUserByUsername(authentication.getName());
		
		if (user == null) {
			throw new BadCredentialsException("Username not found");
		}

		String password = (String) authentication.getCredentials();

		if (!user.getPassword().equalsIgnoreCase(password)) {
			throw new BadCredentialsException("Incorrect Password");
		}

		return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

	}

}
