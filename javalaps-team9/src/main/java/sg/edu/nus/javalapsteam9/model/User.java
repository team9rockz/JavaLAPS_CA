package sg.edu.nus.javalapsteam9.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 941454760630567685L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty
	private String userId;
	
	@NotEmpty
	private String firstName;
	
	@NotEmpty
	private String lastName;
	
	private String password;
	
	@Email
	@NotEmpty
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Enumerated(EnumType.STRING)
	private Scheme scheme;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date joinDate;

	private int annualLeaveBalance;
	
	private int medicalLeaveBalance;
	
	private int reportTo;

	@OneToMany(targetEntity = LeaveApplication.class, mappedBy = "user", cascade = CascadeType.ALL)
	private Collection<LeaveApplication> leaves;
	
	public User() {
		super();
	}

	//For testing
	public User(int id, String userId, String firstName, String lastName, String password, String email, Roles role,
			Scheme scheme, @NotNull Date joinDate, int annualLeaveBalance, int medicalLeaveBalance, int reportTo) {
		super();
		this.id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.role = role;
		this.scheme = scheme;
		this.joinDate = joinDate;
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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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

	public Collection<LeaveApplication> getLeaves() {
		return leaves;
	}

	public void setLeaves(Collection<LeaveApplication> leaves) {
		this.leaves = leaves;
	}
}
