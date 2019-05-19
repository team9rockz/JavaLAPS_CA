package sg.edu.nus.javalapsteam9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.javalapsteam9.service.StaffService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private StaffService staffService;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("msg", "Employee");
		return "employee/home";
	}

}
