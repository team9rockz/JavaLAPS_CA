package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 941454760630567685L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Enumerated(EnumType.STRING)
	private Scheme scheme;
	
	private int annualLeaveBalance;
	
	private int medicalLeaveBalance;
	
	private int reportTo;

	public User() {
		super();
	}

	//For testing
	public User(int id, String userId, String firstName, String lastName, String password, String email, Roles role, Scheme scheme,
			int annualLeaveBalance, int medicalLeaveBalance, int reportTo) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.scheme = scheme;
		this.annualLeaveBalance = annualLeaveBalance;
		this.medicalLeaveBalance = medicalLeaveBalance;
		this.reportTo = reportTo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
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

	public int getReportTo() {
		return reportTo;
	}

	public void setReportTo(int reportTo) {
		this.reportTo = reportTo;
	}

}
