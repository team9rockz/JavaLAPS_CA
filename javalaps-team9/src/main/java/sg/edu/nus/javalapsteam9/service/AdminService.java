package sg.edu.nus.javalapsteam9.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.LeaveEntitlement;
import sg.edu.nus.javalapsteam9.model.LeaveEntitlementComposite;
import sg.edu.nus.javalapsteam9.model.PublicHoliday;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.LeaveApplicationRepository;
import sg.edu.nus.javalapsteam9.repo.LeaveEntitlementRepository;
import sg.edu.nus.javalapsteam9.repo.PublicHolidayRepository;
import sg.edu.nus.javalapsteam9.repo.UserRepository;
import sg.edu.nus.javalapsteam9.util.BCryptUtil;
import sg.edu.nus.javalapsteam9.util.Util;

@Service
public class AdminService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PublicHolidayRepository holidayRepo;

	@Autowired
	private LeaveApplicationRepository leaveRepo;

	@Autowired
	private LeaveEntitlementRepository lEntitleRepo;

	public void createLeaveEntitleMent(LeaveEntitlement leaveEntitlement) {
		lEntitleRepo.save(leaveEntitlement);
	}

	public User findUserById(int id) {

		return userRepo.findById(id).get();
	}

	public User findUserByUserId(String userId) {

		return userRepo.findByUserId(userId);
	}

	public List<User> getAllUsers() {

		List<User> users = userRepo.findAll();
		return users;
	}

	public List<User> getAllManagers() {

		List<User> users = userRepo.findAll();
		List<User> managers = users.stream().filter(u -> u.getRole().equals(Roles.MANAGER))
				.collect(Collectors.toList());

		return managers;

	}

	public void saveUser(User user) {

		Date joinDate = Util.getUtcDate(user.getJoinDate());
		if (user.getId() == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			@SuppressWarnings("deprecation")
			int nextYear = joinDate.getYear() + 1901;
			try {
				Date startOfNextYear = format.parse(nextYear + "-01-01");
				long workDaysOfYearMS = Math.abs(startOfNextYear.getTime() - user.getJoinDate().getTime());
				double workDaysOfYear = (double) TimeUnit.DAYS.convert(workDaysOfYearMS, TimeUnit.MILLISECONDS);

				int annualLeaveDays = lEntitleRepo
						.findById(new LeaveEntitlementComposite(user.getScheme(), LeaveType.ANNUAL)).get()
						.getNumberOfDays();
				int annualBalance = (int) ((workDaysOfYear / 365) * annualLeaveDays);
				int medicalLeaveBalance = lEntitleRepo
						.findById(new LeaveEntitlementComposite(user.getScheme(), LeaveType.MEDICAL)).get()
						.getNumberOfDays();
				user.setAnnualLeaveBalance(annualBalance);
				user.setMedicalLeaveBalance(medicalLeaveBalance);
				user.setPassword(BCryptUtil.hashPassword("12345"));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		userRepo.save(user);
	}

	public void deleteUser(User user) {
		userRepo.delete(user);
	}

	public PublicHoliday findPublicHolidayByName(String name) {

		return holidayRepo.findById(name).get();
	}

	public void createPublicHolidays(List<PublicHoliday> holidays) {
		holidayRepo.saveAll(holidays);
	}

	public void createPublicHoliday(PublicHoliday holiday) {
		holiday.setStartDate(Util.getUtcDate(holiday.getStartDate()));
		holiday.setEndDate(Util.getUtcDate(holiday.getEndDate()));
		holidayRepo.save(holiday);
	}

	public void deletePublicHoliday(PublicHoliday holiday) {

		holidayRepo.delete(holiday);
	}

	public List<PublicHoliday> getAllPublicHolidays() {
		List<PublicHoliday> publicHolidays = holidayRepo.findAll();
		publicHolidays.sort(Comparator.comparing(PublicHoliday::getStartDate));
		return publicHolidays;
	}

	public boolean checkValidPublicHolidayName(String phName) {
		List<PublicHoliday> publicHolidays = holidayRepo.findAll();
		boolean flag;

		flag = publicHolidays.stream().anyMatch(p -> p.getName().toUpperCase().equals(phName.toUpperCase()));

		return flag;
	}

	public boolean isValidStartDate(Date startDate) {
		return Util.isValidStartDate(startDate);
	}

	public boolean isValidEndDate(Date startDate, Date endDate) {
		return Util.isValidEndDate(startDate, endDate);
	}

	public void saveLeaveRecord(LeaveApplication lapp) {
		leaveRepo.save(lapp);
	}
}
