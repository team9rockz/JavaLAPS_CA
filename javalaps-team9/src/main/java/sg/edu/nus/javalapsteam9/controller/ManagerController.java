package sg.edu.nus.javalapsteam9.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.service.ManagerService;
import sg.edu.nus.javalapsteam9.validation.CustomFieldError;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	
	@Autowired
	private ManagerService managerService;
	
	@GetMapping("/home")
	public String outstandingLeaves(Model model) {
		
		//To update method to retrieve both "APPLIED" and "OUTSTANDING" leaves
		//Also, to show leaves for employees whose reportsTo = manager's staffId (not done yet)
		List<LeaveApplication> leaves = managerService.findAllOutstandingLeaves();
		model.addAttribute("leaves", leaves);
		return "manager/home";
	}
	
	 @GetMapping("/details/{id}")
	 public String leaveDetails(Model model, @PathVariable("id") Integer id) {
		 
		 LeaveApplication leave = managerService.findLeaveById(id);
		 model.addAttribute("leave", leave); 
		 return "manager/leave_details";
	 }
	 
	 @GetMapping("/approve/{id}")
	 public String approveLeave(@PathVariable("id") Integer id, @RequestParam(name = "comment", required = false) String comment) {
		 managerService.approveLeave(id, comment);
		 return "redirect:/manager/home";
	 }
	 
	 @PostMapping("/reject")
	 public String rejectLeave(@Valid @ModelAttribute("leave") LeaveApplication leave, BindingResult result) {
		 
		 if(leave.getComment() == null || leave.getComment().isEmpty()) {
			 CustomFieldError err = new CustomFieldError("leave", "comment", "Please provide comments for rejection");
			 result.addError(err);
		 }
		 
		 if(result.hasFieldErrors("comment")) {
			 return "manager/leave_details";
		 }
		 
		 managerService.rejectLeave(leave.getId(), leave.getComment());
		 return "redirect:/manager/home";
	 }
	 
	 @GetMapping("/sub_leave_history")
	 public String viewSubLeaveHist(Model model) {
		 List<List<LeaveApplication>> subLeaves = managerService.getSubLeaveHistory();	
		 model.addAttribute("subLeaves", subLeaves);
		 List<User> subordinates = managerService.getSub();
		 model.addAttribute("subordinates",subordinates); 
		 return "manager/sub_leave_history";
	 }
	 
	 @GetMapping("/sub_leave_history/view/{leaveid}")
	 public String viewLeaveId(Model model, @PathVariable("leaveid") Integer leaveId) {
		 	LeaveApplication leave = managerService.findLeaveById(leaveId);
			model.addAttribute("form", leave);
			
			return "manager/view_leave";
			
			
	 }
}
