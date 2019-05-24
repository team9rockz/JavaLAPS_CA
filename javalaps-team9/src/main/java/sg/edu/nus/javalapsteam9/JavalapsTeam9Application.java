package sg.edu.nus.javalapsteam9;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
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

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		//Generate employees for testing
		User manager1 = new User(1, "manager1", "Cher", "Wah", "12345", "cherweh@gmail.com", Roles.MANAGER,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 2);
		adminService.saveUser(manager1);
		User manager2 = new User(2, "manager2", "Marcus", "Foo", "12345", "marcusfoo@gmail.com", Roles.MANAGER,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 2);
		adminService.saveUser(manager2);
		User manager3 = new User(3, "manager3", "Peter", "Lee", "12345", "peterlee@gmail.com", Roles.MANAGER,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 2);
		adminService.saveUser(manager3);
		User admin = new User(4, "admin1", "Suria", "R Asai", "12345", "suria@gmail.com", Roles.ADMIN,
				Scheme.ADMINISTRATIVE, format.parse("2019-01-02"), 14, 60, 1);
		adminService.saveUser(admin);
		User employee1 = new User(5, "emp1", "Dickson", "Lee", "12345", "dicksonlee@gmail.com", Roles.STAFF,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 1);
		adminService.saveUser(employee1);
		User employee2 = new User(6, "emp2", "Howard", "Teo", "12345", "howardteo@gmail.com", Roles.STAFF,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 1);
		adminService.saveUser(employee2);
		User employee3 = new User(7, "emp3", "Jane", "Tan", "12345", "janetan@gmail.com", Roles.STAFF,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 3);
		adminService.saveUser(employee3);
		User employee4 = new User(8, "emp4", "Celine", "Chee", "12345", "celinechee@gmail.com", Roles.STAFF,
				Scheme.PROFESSIONAL, format.parse("2019-01-02"), 18, 60, 3);
		adminService.saveUser(employee4);

		//Generate leave applications for testing
		LeaveApplication lap1 = new LeaveApplication(format.parse("2019-07-05"), format.parse("2019-07-12"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to India", "Handed tasks to intern", 5, null,
				LeaveStatus.APPLIED, null, false, manager3);
		adminService.saveLeaveRecord(lap1);
		LeaveApplication lap2 = new LeaveApplication(format.parse("2019-08-08"), format.parse("2019-08-13"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Maldives", "Handed tasks to intern", 4, null,
				LeaveStatus.APPLIED, null, false, manager1);
		adminService.saveLeaveRecord(lap2);
		LeaveApplication lap3 = new LeaveApplication(format.parse("2019-12-20"), format.parse("2019-12-27"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Phuket", "Handed tasks to intern", 6, null,
				LeaveStatus.APPLIED, null, false, employee1);
		adminService.saveLeaveRecord(lap3);
		LeaveApplication lap4 = new LeaveApplication(format.parse("2019-06-20"), format.parse("2019-06-21"),
				LeaveType.MEDICAL, format.parse("2019-01-01"), "Sick", "Work from home", 1, null, LeaveStatus.APPLIED,
				null, false, employee2);
		adminService.saveLeaveRecord(lap4);
		LeaveApplication lap5 = new LeaveApplication(format.parse("2019-06-11"), format.parse("2019-06-14"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Japan", "Handed tasks to intern", 4, null,
				LeaveStatus.UPDATED, null, false, employee3);
		adminService.saveLeaveRecord(lap5);
		LeaveApplication lap6 = new LeaveApplication(format.parse("2019-07-01"), format.parse("2019-07-05"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Korea", "Handed tasks to intern", 5, null,
				LeaveStatus.APPLIED, null, false, employee3);
		adminService.saveLeaveRecord(lap6);
		LeaveApplication lap7 = new LeaveApplication(format.parse("2019-05-01"), format.parse("2019-05-15"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Europe", "Handed tasks to intern", 15, null,
				LeaveStatus.APPROVED, null, false, admin);
		adminService.saveLeaveRecord(lap7);
		LeaveApplication lap8 = new LeaveApplication(format.parse("2019-11-01"), format.parse("2019-11-05"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to Bali", "Handed tasks to intern", 3, null,
				LeaveStatus.APPLIED, null, false, admin);
		adminService.saveLeaveRecord(lap8);
		LeaveApplication lap9 = new LeaveApplication(format.parse("2019-05-01"), format.parse("2019-05-15"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to USA", "Handed tasks to intern", 15, null,
				LeaveStatus.APPROVED, null, false, manager2);
		adminService.saveLeaveRecord(lap9);
		LeaveApplication lap10 = new LeaveApplication(format.parse("2019-11-01"), format.parse("2019-11-05"),
				LeaveType.ANNUAL, format.parse("2019-01-01"), "Holiday to KL", "Handed tasks to intern", 3, null,
				LeaveStatus.APPROVED, null, false, manager2);
		adminService.saveLeaveRecord(lap10);

		//Generate public holidays
		PublicHoliday hol1 = new PublicHoliday(new java.util.Date(1546300800 * 1000L),
				new java.util.Date(1546387199 * 1000L), "NEW YEAR");
		PublicHoliday hol2 = new PublicHoliday(new java.util.Date(1549324800 * 1000L),
				new java.util.Date(1549497599 * 1000L), "CHINESE NEW YEAR");
		PublicHoliday hol3 = new PublicHoliday(new java.util.Date(1555632000 * 1000L),
				new java.util.Date(1555718399 * 1000L), "GOOD FRIDAY");
		PublicHoliday hol4 = new PublicHoliday(new java.util.Date(1556668800 * 1000L),
				new java.util.Date(1556755199 * 1000L), "LABOUR DAY");
		PublicHoliday hol5 = new PublicHoliday(new java.util.Date(1558310400 * 1000L),
				new java.util.Date(1558396799 * 1000L), "VESAK DAY");
		PublicHoliday hol6 = new PublicHoliday(new java.util.Date(1559692800 * 1000L),
				new java.util.Date(1559779199 * 1000L), "HARI RAYA PUASA");
		PublicHoliday hol7 = new PublicHoliday(new java.util.Date(1565308800 * 1000L),
				new java.util.Date(1565395199 * 1000L), "NATIONAL DAY");
		PublicHoliday hol8 = new PublicHoliday(new java.util.Date(1565568000 * 1000L),
				new java.util.Date(1565654399 * 1000L), "HARI RAYA HAJI");
		PublicHoliday hol9 = new PublicHoliday(new java.util.Date(1572220800 * 1000L),
				new java.util.Date(1572307199 * 1000L), "DEEPAVALI");
		PublicHoliday hol10 = new PublicHoliday(new java.util.Date(1577232000 * 1000L),
				new java.util.Date(1577318399 * 1000L), "CHRISTMAS DAY");

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
