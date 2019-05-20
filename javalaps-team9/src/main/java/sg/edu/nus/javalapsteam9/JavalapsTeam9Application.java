package sg.edu.nus.javalapsteam9;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.javalapsteam9.enums.Roles;
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

		User admin = new User("admin1", "Suria", "R Asai", "12345",
				"suria@gmail.com", Roles.ADMIN, 14, 60, "");
		adminService.createUser(admin);
	}

}
