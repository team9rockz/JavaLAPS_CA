package sg.edu.nus.javalapsteam9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired private UserRepository userRepo;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByUserId(username);

		if (user == null)
			return null;

		CustomUser details = new CustomUser(user.getUserId(), user.getPassword(), user.getEmail(),
				user.getRole().getRole());
		details.setId(user.getId());
		details.setRole(user.getRole());
		return details;
		
	}

}
