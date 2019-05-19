package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.enums.Roles;

@Embeddable
public class LeaveEntitlementComposite implements Serializable {

	private static final long serialVersionUID = -4629485007620259128L;

	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

}
