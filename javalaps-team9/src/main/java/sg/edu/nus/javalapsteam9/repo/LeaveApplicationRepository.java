package sg.edu.nus.javalapsteam9.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.User;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer>{
	
	List<LeaveApplication> findAllByUser(User user);
	
	List<LeaveApplication> findAllByUserOrderByAppliedDateDesc(User user);
	
	List<LeaveApplication> findByStatus(LeaveStatus status);
	
	LeaveApplication findById(int id);

}
