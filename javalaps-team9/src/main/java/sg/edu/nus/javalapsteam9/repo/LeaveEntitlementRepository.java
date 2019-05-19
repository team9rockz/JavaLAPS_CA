package sg.edu.nus.javalapsteam9.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.nus.javalapsteam9.model.LeaveEntitlement;
import sg.edu.nus.javalapsteam9.model.LeaveEntitlementComposite;

public interface LeaveEntitlementRepository extends JpaRepository<LeaveEntitlement, LeaveEntitlementComposite>{

}
