package sg.edu.nus.javalapsteam9;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;
import sg.edu.nus.javalapsteam9.model.PublicHoliday;
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
		
		PublicHoliday hol1 = new PublicHoliday(new java.util.Date(1546300800*1000L),new java.util.Date(1546387199*1000L),"NEW YEAR");
		PublicHoliday hol2 = new PublicHoliday(new java.util.Date(1549324800*1000L),new java.util.Date(1549497599*1000L),"CHINESE NEW YEAR");
		PublicHoliday hol3 = new PublicHoliday(new java.util.Date(1555632000*1000L),new java.util.Date(1555718399*1000L),"GOOD FRIDAY");
		PublicHoliday hol4 = new PublicHoliday(new java.util.Date(1556668800*1000L),new java.util.Date(1556755199*1000L),"LABOUR DAY");
		PublicHoliday hol5 = new PublicHoliday(new java.util.Date(1558310400*1000L),new java.util.Date(1558396799*1000L),"VESAK DAY");
		PublicHoliday hol6 = new PublicHoliday(new java.util.Date(1559692800*1000L),new java.util.Date(1559779199*1000L),"HARI RAYA PUASA");
		PublicHoliday hol7 = new PublicHoliday(new java.util.Date(1565308800*1000L),new java.util.Date(1565395199*1000L),"NATIONAL DAY");
		PublicHoliday hol8 = new PublicHoliday(new java.util.Date(1565568000*1000L),new java.util.Date(1565654399*1000L),"HARI RAYA HAJI");
		PublicHoliday hol9 = new PublicHoliday(new java.util.Date(1572220800*1000L),new java.util.Date(1572307199*1000L),"DEEPAVALI");
		PublicHoliday hol10 = new PublicHoliday(new java.util.Date(1577232000*1000L),new java.util.Date(1577318399*1000L),"CHRISTMAS DAY");
		
		List<PublicHoliday> hols = new ArrayList<PublicHoliday>();
		hols.add(hol1);
		hols.add(hol2);
		hols.add(hol3);
		hols.add(hol4);
		hols.add(hol5);
		hols.add(hol6);
		hols.add(hol7);
		hols.add(hol8);
		hols.add(hol9);
		hols.add(hol10);
		
		adminService.createPublicHolidays(hols);
	}

}
