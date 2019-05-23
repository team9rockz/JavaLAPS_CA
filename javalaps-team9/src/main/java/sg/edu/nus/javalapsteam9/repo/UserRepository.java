package sg.edu.nus.javalapsteam9.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.javalapsteam9.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findAllByReportTo(int id);
}
