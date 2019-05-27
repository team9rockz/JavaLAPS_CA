package sg.edu.nus.javalapsteam9.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.service.EmailService;
import sg.edu.nus.javalapsteam9.service.StaffService;
import sg.edu.nus.javalapsteam9.util.SecurityUtil;
import sg.edu.nus.javalapsteam9.validation.CustomFieldError;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private static final String HOME = "/employee/home";
	
	private static final Logger LOG = LogManager.getLogger(EmployeeController.class);
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private EmailService emailService;
	
	@GetMapping("/")
	public String defaultPage(Model model) {
		LOG.info("default route");
		model.addAttribute("homeurl", HOME);
		return "redirect:home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<LeaveApplication> leaves = staffService.findAllLeavesByUserOrderByAppliedDate();
		User user = staffService.findUserById();
		model.addAttribute("homeurl", HOME);
		model.addAttribute("leaves", leaves);
		model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("annual_leave", user.getAnnualLeaveBalance());
		model.addAttribute("medical_leave", user.getMedicalLeaveBalance());
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "employee/home";
	}
	
	@GetMapping("/leaveform")
	public String leaveForm(Model model) {
		model.addAttribute("homeurl", HOME);
		model.addAttribute("form", new LeaveApplication());
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "employee/leave_form";
	}

	@PostMapping("/leave/create")
	public String createLeave(Model model, @Valid @ModelAttribute("form") LeaveApplication leave, BindingResult result) {
		
		validateForm(leave, result);
		
		model.addAttribute("homeurl", HOME);
		if(result.hasErrors()) {
			return "employee/leave_form";
		}
		
		staffService.createLeave(leave);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		sendEmail(leave);
		
		return "redirect:/employee/home";
	}
	
	public String sendEmail(LeaveApplication leave) {
		
		int managerId = leave.getUser().getReportTo();
		String directLink = "Process it here: http://localhost:8080/laps/manager/details/" + leave.getId().toString();
		emailService.sendSimpleMessage(staffService.findManagerById(managerId).getEmail(),"New leave application for processing",directLink);
		
		return "redirect:/employee/home";
	}
	
	private void validateForm(LeaveApplication leave, BindingResult result) {
		
		if(leave.getStartDate() != null) {
			
			boolean isValidDate = staffService.isValidStartDate(leave.getStartDate());
			if(!isValidDate) {
				CustomFieldError cd = new CustomFieldError("form", "startDate", leave.getStartDate(), "Invalid start date");
				result.addError(cd);
			} else {
				isValidDate = staffService.isHoliday(leave.getStartDate());
				if(isValidDate) {
					CustomFieldError cd = new CustomFieldError("form", "startDate", leave.getStartDate(), "must be working day");
					result.addError(cd);
				}
			}
			
			if(leave.getEndDate() != null) {
				isValidDate = staffService.isValidEndDate(leave.getStartDate(), leave.getEndDate());
				if(!isValidDate) {
					CustomFieldError cd = new CustomFieldError("form", "endDate", leave.getEndDate(), "Invalid end date");
					result.addError(cd);
				} else {
					isValidDate = staffService.isHoliday(leave.getEndDate());
					if(isValidDate) {
						CustomFieldError cd = new CustomFieldError("form", "endDate", leave.getEndDate(), "must be working day");
						result.addError(cd);
					}
				}
				
				boolean isNotValidDates = staffService.isNotValidDates(leave.getStartDate(), leave.getEndDate(), leave.getId());
				if(isNotValidDates) {
					CustomFieldError cd = new CustomFieldError("form", "startDate", leave.getStartDate(), "Leaves matched with your previous history");
					result.addError(cd);
				}
				
				int days = staffService.calculateLeavesBetweenDates(leave.getStartDate(), leave.getEndDate());
				if(!result.hasFieldErrors("leaveType")) {
					User user = staffService.findUserById();
					LeaveApplication old = null;
					if(leave.getId() != null) {
						old = staffService.findLeaveById(leave.getId());
					}
					switch(leave.getLeaveType()) {
					case ANNUAL:
						if(old == null && days > user.getAnnualLeaveBalance()) {
							CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
							result.addError(cd);
						} else if(old != null && old.getLeaveType() == leave.getLeaveType()) {
							if(days != old.getLeavePeriod() && days > (user.getAnnualLeaveBalance() + old.getLeavePeriod())) {
								CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
								result.addError(cd);
							}
						} else if(old != null && old.getLeaveType() != leave.getLeaveType()) {
							if(days > user.getAnnualLeaveBalance()) {
								CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
								result.addError(cd);
							}
						}
						break;
					case MEDICAL:
						if(old == null && days > user.getMedicalLeaveBalance()) {
							CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
							result.addError(cd);
						} else if(old != null && old.getLeaveType() == leave.getLeaveType()) {
							if(days != old.getLeavePeriod() && days > (user.getMedicalLeaveBalance() + old.getLeavePeriod())) {
								CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
								result.addError(cd);
							}
						} else if(old != null && old.getLeaveType() != leave.getLeaveType()) {
							if(days > user.getMedicalLeaveBalance()) {
								CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
								result.addError(cd);
							}
						}
						break;
					case COMPENSATION:
						CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "compensation leave not allowed");
						result.addError(cd);
						break;
					}
				}
			}
			
		}
		
		if (leave.isOverseasTrip() && (leave.getContactDetails() == null
				|| leave.getContactDetails().trim().isEmpty())) {
			if(!result.hasFieldErrors("contactDetails")) {
				CustomFieldError cd = new CustomFieldError("form", "contactDetails", "must not be empty");
				result.addError(cd);
			}
		}
		
	}
	
	@GetMapping("/leave/view/{leaveid}")
	public String leaveForm(Model model, @PathVariable("leaveid") Integer leaveId) {
		LeaveApplication leave = staffService.findLeaveByIdToShow(leaveId);
		model.addAttribute("form", leave);
		model.addAttribute("homeurl", HOME);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "employee/view";
	}

	@PostMapping("/leave/edit")
	public String editForm(Model model, LeaveApplication leaveApp) {

		LeaveApplication leave = staffService.findLeaveById(leaveApp.getId());
		model.addAttribute("form", leave);
		model.addAttribute("homeurl", HOME);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "employee/edit";
	}

	@PostMapping("/leave/update")
	public String updateLeave(Model model, @Valid @ModelAttribute("form") LeaveApplication leave, BindingResult result) {
		
		validateForm(leave, result);
		
		model.addAttribute("homeurl", HOME);
		if(result.hasErrors()) {
			return "employee/edit";
		}
		
		staffService.updateLeaveApplication(leave);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "redirect:/employee/home";
	}

	@PostMapping("/leave/delete")
	public String deleteLeave(LeaveApplication leaveApp) {
		staffService.deleteLeaveApplication(leaveApp.getId());
		return "redirect:/employee/home";
	}

	@PostMapping("/leave/cancel")
	public String cancelLeave(LeaveApplication leaveApp) {
		staffService.cancelLeaveApplication(leaveApp.getId());
		return "redirect:/employee/home";
	}

}
