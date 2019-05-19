package sg.edu.nus.javalapsteam9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepo;
	
	public void createUser(User user) {
		userRepo.save(user);
	}
}
