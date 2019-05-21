package sg.edu.nus.javalapsteam9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.service.AdminService;

@SpringBootApplication
public class JavalapsTeam9Application implements CommandLineRunner {

	@Autowired
	private AdminService adminService;
	
	public static void main(String[] args) {
		SpringApplication.run(JavalapsTeam9Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User manager1 = new User(1,"manager1", "Cher", "Wah", "12345", "cherweh@gmail.com", Roles.MANAGER, Scheme.PROFESSIONAL, 18, 60, 2);
		adminService.createUser(manager1);
		
		User manager2 = new User(2,"manager2", "Marcus", "Foo", "12345", "marcusfoo@gmail.com", Roles.MANAGER, Scheme.PROFESSIONAL, 7, 59, 2);
		adminService.createUser(manager2);
		
		User admin = new User(3,"admin1", "Suria", "R Asai", "12345","suria@gmail.com", Roles.ADMIN, Scheme.ADMINISTRATIVE, 14, 60, 1);
		adminService.createUser(admin);
		
		User employee1 = new User(4,"emp1", "Dickson", "Lee", "12345", "dicksonlee@gmail.com", Roles.STAFF, Scheme.PROFESSIONAL, 12, 58, 1);
		adminService.createUser(employee1);
		
		User employee2 = new User(5,"emp2", "Howard", "Teo", "12345", "howardteo@gmail.com", Roles.STAFF, Scheme.PROFESSIONAL, 13, 60, 1);
		adminService.createUser(employee2);
	}

}
