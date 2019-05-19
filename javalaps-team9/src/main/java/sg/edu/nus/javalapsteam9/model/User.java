package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 941454760630567685L;
	
	@Id
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	private int annualLeaveBalance;
	
	private int medicalLeaveBalance;
	
	private String reportTo;

	public User() {
		super();
	}

	//For testing
	public User(String userId, String firstName, String lastName, String password, String email, Roles role,
			int annualLeaveBalance, int medicalLeaveBalance, String reportTo) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.annualLeaveBalance = annualLeaveBalance;
		this.medicalLeaveBalance = medicalLeaveBalance;
		this.reportTo = reportTo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public int getAnnualLeaveBalance() {
		return annualLeaveBalance;
	}

	public void setAnnualLeaveBalance(int annualLeaveBalance) {
		this.annualLeaveBalance = annualLeaveBalance;
	}

	public int getMedicalLeaveBalance() {
		return medicalLeaveBalance;
	}

	public void setMedicalLeaveBalance(int medicalLeaveBalance) {
		this.medicalLeaveBalance = medicalLeaveBalance;
	}

	public String getReportTo() {
		return reportTo;
	}

	public void setReportTo(String reportTo) {
		this.reportTo = reportTo;
	}

}
