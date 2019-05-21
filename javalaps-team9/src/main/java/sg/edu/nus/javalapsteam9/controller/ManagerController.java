package sg.edu.nus.javalapsteam9.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.javalapsteam9.model.LeaveApplication;
import sg.edu.nus.javalapsteam9.service.ManagerService;

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
	 
}
