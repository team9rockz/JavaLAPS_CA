package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.enums.Scheme;

@Embeddable
public class LeaveEntitlementComposite implements Serializable {

	private static final long serialVersionUID = -4629485007620259128L;

	@Enumerated(EnumType.STRING)
	private Scheme scheme;
	
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;
	
	public LeaveEntitlementComposite(Scheme scheme, LeaveType leaveType) {
		super();
		this.scheme = scheme;
		this.leaveType = leaveType;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public LeaveEntitlementComposite() {
		super();
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

}
