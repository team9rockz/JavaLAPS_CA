
package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import sg.edu.nus.javalapsteam9.enums.LeaveStatus;
import sg.edu.nus.javalapsteam9.enums.LeaveType;
import sg.edu.nus.javalapsteam9.util.Util;
import sg.edu.nus.javalapsteam9.validation.LeaveTypeConstraint;

@Entity
public class LeaveApplication implements Serializable, Comparable<LeaveApplication> {

	private static final long serialVersionUID = -2833426574597193027L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@LeaveTypeConstraint
	@Enumerated(EnumType.STRING)
	private LeaveType leaveType;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date appliedDate;

	@NotEmpty
	private String reason;

	@NotEmpty
	private String workDissemination;

	private int leavePeriod;

	private String contactDetails;

	@Enumerated(EnumType.STRING)
	private LeaveStatus status;

	private String comment;

	private boolean overseasTrip;

	@ManyToOne
	@JoinColumn(name = "staff_id")
	private User user;

	@Transient
	private boolean expired;

	public LeaveApplication() {
		super();
		this.status = LeaveStatus.APPLIED;
		this.appliedDate = Util.now();
	}

	public LeaveApplication(@NotNull Date startDate, @NotNull Date endDate, LeaveType leaveType, Date appliedDate,
			@NotEmpty String reason, @NotEmpty String workDissemination, int leavePeriod, String contactDetails,
			LeaveStatus status, String comment, boolean overseasTrip, User user) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.appliedDate = appliedDate;
		this.reason = reason;
		this.workDissemination = workDissemination;
		this.leavePeriod = leavePeriod;
		this.contactDetails = contactDetails;
		this.status = status;
		this.comment = comment;
		this.overseasTrip = overseasTrip;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public LeaveType getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(LeaveType leaveType) {
		this.leaveType = leaveType;
	}

	public boolean isOverseasTrip() {
		return overseasTrip;
	}

	public void setOverseasTrip(boolean overseasTrip) {
		this.overseasTrip = overseasTrip;
	}

	public Date getAppliedDate() {
		return appliedDate;
	}

	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWorkDissemination() {
		return workDissemination;
	}

	public void setWorkDissemination(String workDissemination) {
		this.workDissemination = workDissemination;
	}

	public int getLeavePeriod() {
		return leavePeriod;
	}

	public void setLeavePeriod(int leavePeriod) {
		this.leavePeriod = leavePeriod;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LeaveApplication other = (LeaveApplication) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(LeaveApplication o) {
		return this.getAppliedDate().compareTo(o.getAppliedDate());
	}

}
