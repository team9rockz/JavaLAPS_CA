package sg.edu.nus.javalapsteam9.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.repo.LeaveApplicationRepository;
import sg.edu.nus.javalapsteam9.repo.PublicHolidayRepository;
import sg.edu.nus.javalapsteam9.repo.UserRepository;
import sg.edu.nus.javalapsteam9.util.SecurityUtil;
import sg.edu.nus.javalapsteam9.util.Util;

@Service
public class StaffService {
	
	@Autowired
	private LeaveApplicationRepository leaveRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PublicHolidayRepository holidayRepo;
	
	public void createLeave(LeaveApplication leaveApplication) {
		
		int days = calculateLeavesBetweenDates(leaveApplication.getStartDate(), leaveApplication.getEndDate());
		User user = findUserById();
		leaveApplication.setUser(user);
		leaveApplication.setStartDate(Util.getUtcDate(leaveApplication.getStartDate()));
		leaveApplication.setEndDate(Util.getUtcDate(leaveApplication.getEndDate()));
		if(leaveApplication.getId() == null || leaveApplication.getId() == 0) {
			leaveApplication.setLeavePeriod(days);
			updateLeaves(leaveApplication.getLeaveType(), user, days);
		} else {
			LeaveApplication oldLeave = findLeaveById(leaveApplication.getId());
			if(oldLeave.getLeavePeriod() != days || oldLeave.getLeaveType() != leaveApplication.getLeaveType()) {
				revertLeavesAsEarlier(oldLeave.getLeaveType(), user, oldLeave.getLeavePeriod());
				updateLeaves(leaveApplication.getLeaveType(), user, days);
				leaveApplication.setLeavePeriod(days);
			}
		}
		
		leaveRepo.save(leaveApplication);
	}
	
	private void revertLeavesAsEarlier(LeaveType leaveType, User user, int days) {
		switch(leaveType) {
		case ANNUAL:
			user.setAnnualLeaveBalance(user.getAnnualLeaveBalance() + days);
			break;
			
		case MEDICAL:
			user.setMedicalLeaveBalance(user.getMedicalLeaveBalance() + days);
			break;
			
		case COMPENSATION:
			break;
			
		}
	}
	
	private void revertLeavesAsEarlier(LeaveType leaveType, LeaveApplication leaveApp) {
		switch(leaveType) {
		case ANNUAL:
			leaveApp.getUser().setAnnualLeaveBalance(leaveApp.getUser().getAnnualLeaveBalance() + leaveApp.getLeavePeriod());
			break;
			
		case MEDICAL:
			leaveApp.getUser().setMedicalLeaveBalance(leaveApp.getUser().getMedicalLeaveBalance() + leaveApp.getLeavePeriod());
			break;
			
		case COMPENSATION:
			break;
			
		}
	}
	
	private void updateLeaves(LeaveType leaveType, User user, int days) {
		switch(leaveType) {
		case ANNUAL:
			user.setAnnualLeaveBalance(user.getAnnualLeaveBalance() - days);
			break;
			
		case MEDICAL:
			user.setMedicalLeaveBalance(user.getMedicalLeaveBalance() - days);
			break;
			
		case COMPENSATION:
			break;
			
		}
	}
	
	public int calculateLeavesBetweenDates(Date startDate, Date endDate) {
		long days = Util.calculatePeriodBetweenDates(startDate, endDate);
		if (days > 0 && days <= 14) {
			days = Util.calculatePeriodBetweenDatesExcludeHolidays(startDate, endDate, holidayRepo.findAll());
		}
		return (int) days;
	}
	
	public List<LeaveApplication> findAllLeavesByUserId() {
		User user = findUserById();
		return leaveRepo.findAllByUser(user);
	}
	
	public List<LeaveApplication> findAllLeavesByUserOrderByAppliedDate() {
		User user = findUserById();
		return leaveRepo.findAllByUserOrderByAppliedDateDesc(user);
	}
	
	public LeaveApplication findLeaveById(Integer id) {
		return leaveRepo.findById(id).get();
	}
	
	public LeaveApplication findLeaveByIdToShow(Integer id) {
		LeaveApplication leave = findLeaveById(id);
		int days = (int) Util.calculatePeriodBetweenDates(new Date(), Util.parseFromUtcDate(leave.getStartDate()));
		leave.setExpired(days <= 0);
		return leave;
	}
	
	public void updateLeaveApplication(LeaveApplication leaveApplication) {
		leaveApplication.setStatus(LeaveStatus.UPDATED);
		createLeave(leaveApplication);
	}

	public void deleteLeaveApplication(Integer leaveId) {
		LeaveApplication leaveApp = findLeaveById(leaveId);
		leaveApp.setStatus(LeaveStatus.DELETED);
		revertLeavesAsEarlier(leaveApp.getLeaveType(), leaveApp);
		leaveRepo.save(leaveApp);
	}

	public void cancelLeaveApplication(Integer leaveId) {
		LeaveApplication leaveApp = findLeaveById(leaveId);
		leaveApp.setStatus(LeaveStatus.CANCELLED);
		revertLeavesAsEarlier(leaveApp.getLeaveType(), leaveApp);
		leaveRepo.save(leaveApp);
	}
	
	public User findUserById() {
		return userRepo.findById(SecurityUtil.getCurrentLoggedUserId()).get();
	}
	
	public boolean isNotValidDates(Date startDate, Date endDate) {
		List<LeaveApplication> existingLeaves = findAllLeavesByUserId();
		if(existingLeaves.isEmpty()) {
			return false;
		}
		
		HashSet<Date> existingLeavesSet = getAllAppliedLeaveDates(existingLeaves);
		if(startDate.compareTo(endDate) == 0) {
			return existingLeavesSet.contains(startDate);
		} else {
			HashSet<Date> newLeaveSet = Util.getAllDates(startDate, endDate);
			for(Date date : newLeaveSet) {
				if(existingLeavesSet.contains(date)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public HashSet<Date> getAllAppliedLeaveDates(List<LeaveApplication> leaves) {
		HashSet<Date> dateSet = new HashSet<>();
		for(LeaveApplication leave : leaves) {
			dateSet.addAll(Util.getAllDates(Util.parseFromUtcDate(leave.getStartDate()), Util.parseFromUtcDate(leave.getEndDate())));
		}
		return dateSet;
	}
	
	public boolean isValidStartDate(Date startDate) {
		return Util.isValidStartDate(startDate);
	}
	
	public boolean isValidEndDate(Date startDate, Date endDate) {
		return Util.isValidEndDate(startDate, endDate);
	}
	
	public boolean isHoliday(Date date) {
		LocalDate ldate = Util.parseDateToLocalDate(date, false);
		boolean isWeekend = Util.isHoliday(ldate);
		if(isWeekend)
			return true;
		return Util.isPublicHoliday(ldate, holidayRepo.findAll());
	}
	
	public User findManagerById(int userId) {
		return userRepo.findById(userId).get();
	}

}
