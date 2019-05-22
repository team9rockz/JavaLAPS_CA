package sg.edu.nus.javalapsteam9.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.repo.LeaveApplicationRepository;
import sg.edu.nus.javalapsteam9.repo.PublicHolidayRepository;

@Service
public class ManagerService {

	@Autowired
	private LeaveApplicationRepository leaveRepo;

	@Autowired
	private PublicHolidayRepository holidayRepo;
	
	public List<LeaveApplication> findAllOutstandingLeaves() {
		
		List<LeaveApplication> appliedLeaves = leaveRepo.findByStatus(LeaveStatus.APPLIED);
		List<LeaveApplication> updatedLeaves = leaveRepo.findByStatus(LeaveStatus.UPDATED);
		List<LeaveApplication> outstandingLeaves = new ArrayList<LeaveApplication>();
		outstandingLeaves.addAll(appliedLeaves);
		outstandingLeaves.addAll(updatedLeaves);
		
		return outstandingLeaves;
	}
	
	public LeaveApplication findLeaveById(int id) {
		
		return leaveRepo.findById(id);
	}
	
	public void approveLeave(int id,String comment) {
		
		LeaveApplication leave = leaveRepo.findById(id);
		leave.setStatus(LeaveStatus.APPROVED);
		leave.setComment(comment);
		leaveRepo.save(leave);
	}
	
	public void rejectLeave(int id,String comment) {
		
		LeaveApplication leave = leaveRepo.findById(id);
		leave.setStatus(LeaveStatus.REJECTED);
		leave.setComment(comment);
		
		//To return the number of leave days applied 
		if(leave.getLeaveType() == LeaveType.ANNUAL) {
			int adjustedLeaveBalance = leave.getUser().getAnnualLeaveBalance() + leave.getLeavePeriod();
			leave.getUser().setAnnualLeaveBalance(adjustedLeaveBalance);
		}
		else if (leave.getLeaveType() == LeaveType.MEDICAL) {
			int adjustedLeaveBalance = leave.getUser().getMedicalLeaveBalance() + leave.getLeavePeriod();
			leave.getUser().setMedicalLeaveBalance(adjustedLeaveBalance);
		}
			
		leaveRepo.save(leave);
	}
}
