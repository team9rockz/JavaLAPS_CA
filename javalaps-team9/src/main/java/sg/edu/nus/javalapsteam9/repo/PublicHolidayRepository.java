package sg.edu.nus.javalapsteam9.repo;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.javalapsteam9.model.PublicHoliday;

public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, String>{

}
