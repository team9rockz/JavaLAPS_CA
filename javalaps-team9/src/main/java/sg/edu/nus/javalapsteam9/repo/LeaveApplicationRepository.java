package sg.edu.nus.javalapsteam9.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.User;

public interface LeaveApplicationRepository
		extends JpaRepository<LeaveApplication, Integer>, CrudRepository<LeaveApplication, Integer> {

	List<LeaveApplication> findAllByUser(User user);

	List<LeaveApplication> findAllByUserAndStatusNot(User user, LeaveStatus status);

	List<LeaveApplication> findAllByUserOrderByAppliedDateDesc(User user);

	List<LeaveApplication> findByStatus(LeaveStatus status);

	LeaveApplication findById(int id);

	List<LeaveApplication> findAllByUserAndStatusOrderByStartDate(User user, LeaveStatus status);

	@Query(value = "SELECT * from Leave_Application l, User u WHERE l.staff_id = u.id AND u.id != u.report_to AND u.report_to = ? AND l.status in ('APPLIED','UPDATED') ORDER BY l.applied_date", nativeQuery = true)
	List<LeaveApplication> findSubordinatesOutstandingLeavesOrderByAppliedDate(int managerid);

	@Query(value = "SELECT* from Leave_Application WHERE ((MONTH(start_date) = ? and YEAR(start_date) = ?) OR (MONTH(end_date) = ? and YEAR(end_date) = ?)) AND status='APPROVED' ORDER BY start_date", nativeQuery = true)
	List<LeaveApplication> findByStartAndEndMonth(int startMonth, int currentYear1, int endMonth, int currentYear2);
}
