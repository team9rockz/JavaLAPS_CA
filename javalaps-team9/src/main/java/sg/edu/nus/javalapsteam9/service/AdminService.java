package sg.edu.nus.javalapsteam9.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepo;
	
	public void createUser(User user) {
		user.setPassword(null == user.getPassword()? "12345" : user.getPassword());
		userRepo.save(user);
	}
	
	public List<User> getAllUsers(){
		
		List<User> users = userRepo.findAll();
		return users;
	}
	
	public List<String> getAllManagers(){
		
		List<User> users = userRepo.findAll();
		List<String> managers = users.stream()
				.filter(u -> u.getRole().equals(Roles.MANAGER))
				.map(m -> m.getUserId())
				.collect(Collectors.toList());
		
		return managers;
		
	}
}
