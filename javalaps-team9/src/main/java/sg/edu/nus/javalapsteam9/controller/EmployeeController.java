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
import sg.edu.nus.javalapsteam9.service.StaffService;
import sg.edu.nus.javalapsteam9.util.Util;
import sg.edu.nus.javalapsteam9.validation.CustomFieldError;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	private static final String HOME = "/employee/home";
	
	private static final Logger LOG = LogManager.getLogger(EmployeeController.class);
	
	@Autowired
	private StaffService staffService;
	
	@GetMapping("/")
	public String defaultPage(Model model) {
		LOG.info("default route");
		model.addAttribute("homepath", HOME);
		return "redirect:home";
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<LeaveApplication> leaves = staffService.findAllLeavesByUserOrderByAppliedDate();
		User user = staffService.findUserById(Util.TEST_EMP_ID);
		model.addAttribute("leaves", leaves);
		model.addAttribute("name", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("annual_leave", user.getAnnualLeaveBalance());
		model.addAttribute("medical_leave", user.getMedicalLeaveBalance());
		return "employee/home";
	}
	
	@GetMapping("/leaveform")
	public String leaveForm(Model model) {
		model.addAttribute("form", new LeaveApplication());
		return "employee/leave_form";
	}

	@PostMapping("/leave/create")
	public String createLeave(Model model, @Valid @ModelAttribute("form") LeaveApplication leave, BindingResult result) {
		
		validateForm(leave, result);
		
		if(result.hasErrors()) {
			return "employee/leave_form";
		}
		
		staffService.createLeave(leave);
		
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
				
				boolean isNotValidDates = staffService.isNotValidDates(leave.getStartDate(), leave.getEndDate());
				if(isNotValidDates) {
					CustomFieldError cd = new CustomFieldError("form", "startDate", leave.getStartDate(), "Leaves matched with your previous history");
					result.addError(cd);
				}
				
				int days = staffService.calculateLeavesBetweenDates(leave.getStartDate(), leave.getEndDate());
				if(!result.hasFieldErrors("leaveType")) {
					User user = staffService.findUserById(Util.TEST_EMP_ID);
					switch(leave.getLeaveType()) {
					case ANNUAL:
						if(days > user.getAnnualLeaveBalance()) {
							CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
							result.addError(cd);
						}
						break;
					case MEDICAL:
						if(days > user.getMedicalLeaveBalance()) {
							CustomFieldError cd = new CustomFieldError("form", "leaveType", leave.getLeaveType(), "no leaves available");
							result.addError(cd);
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
		LeaveApplication leave = staffService.findLeaveById(leaveId);
		model.addAttribute("form", leave);
		return "employee/view";
	}

	@PostMapping("/leave/edit")
	public String editForm(Model model, LeaveApplication leaveApp) {

		LeaveApplication leave = staffService.findLeaveById(leaveApp.getId());
		model.addAttribute("form", leave);
		
		return "employee/edit";
	}

	@PostMapping("/leave/update")
	public String updateLeave(Model model, @Valid @ModelAttribute("form") LeaveApplication leave, BindingResult result) {
		
		validateForm(leave, result);
		
		if(result.hasErrors()) {
			return "employee/edit";
		}
		
		staffService.updateLeaveApplication(leave);
		
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
