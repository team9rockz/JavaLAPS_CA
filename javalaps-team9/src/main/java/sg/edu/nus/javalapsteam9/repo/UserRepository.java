package sg.edu.nus.javalapsteam9.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.javalapsteam9.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
