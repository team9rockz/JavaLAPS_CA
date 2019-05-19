package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class LeaveEntitlement implements Serializable {
	
	private static final long serialVersionUID = 8689557405346310001L;

	@EmbeddedId
	private LeaveEntitlementComposite compositeId;
	
	private int numberOfDays;

	public LeaveEntitlementComposite getCompositeId() {
		return compositeId;
	}

	public void setCompositeId(LeaveEntitlementComposite compositeId) {
		this.compositeId = compositeId;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

}
