package sg.edu.nus.javalapsteam9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.repo.LeaveApplicationRepository;

@Service
public class StaffService {
	
	@Autowired
	private LeaveApplicationRepository leaveRepo;
	
	public void createLeave(LeaveApplication leaveApplication) {
		leaveRepo.save(leaveApplication);
	}
}
