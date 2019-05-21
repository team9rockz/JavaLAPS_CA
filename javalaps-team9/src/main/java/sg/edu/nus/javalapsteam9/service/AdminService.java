package sg.edu.nus.javalapsteam9.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.model.PublicHoliday;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.PublicHolidayRepository;
import sg.edu.nus.javalapsteam9.repo.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PublicHolidayRepository holidayRepo;
	
	public void createUser(User user) {
		user.setPassword(null == user.getPassword()? "12345" : user.getPassword());
		userRepo.save(user);
	}
	
	public User findUserById(int id) {

		return userRepo.findById(id).get();
	}
	
	public List<User> getAllUsers(){
		
		List<User> users = userRepo.findAll();
		return users;
	}
	
	public List<User> getAllManagers(){
		
		List<User> users = userRepo.findAll();
		List<User> managers = users.stream()
				.filter(u -> u.getRole().equals(Roles.MANAGER))
				.collect(Collectors.toList());
		
		return managers;
		
	}
	
	public void createPublicHolidays(List<PublicHoliday> holidays){
		holidayRepo.saveAll(holidays);
	}
}
