package sg.edu.nus.javalapsteam9.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.nus.javalapsteam9.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findAllByReportTo(int id);
	
	User findByUserId(String userId);
	
	@Query(value = "SELECT staff_id from Leave_Application WHERE id = ?", nativeQuery = true)
	int findStaffIdByLeaveId(int leaveId);
	
}
