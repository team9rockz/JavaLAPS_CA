package sg.edu.nus.javalapsteam9.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.javalapsteam9.enums.Roles;
import sg.edu.nus.javalapsteam9.enums.Scheme;
import sg.edu.nus.javalapsteam9.model.PublicHoliday;
import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.service.AdminService;
import sg.edu.nus.javalapsteam9.validation.CustomFieldError;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/home")
	public String home(Model model) {
		
		List<User> users = new ArrayList<User>();
		List<PublicHoliday> publicHolidays=new ArrayList<PublicHoliday>();
		users = adminService.getAllUsers();
		publicHolidays=adminService.getAllPublicHolidays();
		model.addAttribute("users", users);
		model.addAttribute("publicHolidays", publicHolidays);
		return "admin/home";
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addUser(Model model) {
		
		model.addAttribute("user", new User());
		model.addAttribute("managers", adminService.getAllManagers());
		return "admin/user_form";
	}
    
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result) {

    	validateEmployeeForm(user, result);
    	
		if(result.hasErrors()) {
			return "admin/user_form";
		}
		
        adminService.createUser(user);
        return "redirect:/admin/home";
    }
    
    private void validateEmployeeForm(User user, BindingResult result) {
		
		if(user.getScheme() == Scheme.ADMINISTRATIVE && user.getRole() != Roles.ADMIN) {
					
			CustomFieldError cd = new CustomFieldError("user", "role", "Invalid role for Administrative scheme");
			result.addError(cd);
		}
		else if(user.getScheme() == Scheme.PROFESSIONAL && user.getRole() == Roles.ADMIN) {
			
			CustomFieldError cd = new CustomFieldError("user", "role", "Invalid role for Professional scheme");
			result.addError(cd);
		}
		
		if(adminService.findUserByUserId(user.getUserId()) != null) {
			CustomFieldError cd = new CustomFieldError("user", "userId", user.getUserId(), "User ID already exists");
			result.addError(cd);
		}
	}
    
    @GetMapping("/user/details/{id}")
    public String userForm(Model model, @PathVariable("id") Integer id) {
    	
    	User user = adminService.findUserById(id);
    	model.addAttribute("user", user);
    	return "admin/user_detail";
    }
    
    @RequestMapping(path = "/addPublicHoliday", method = RequestMethod.GET)
	public String addPublicHoliday(Model model) {
		
		model.addAttribute("publicHoliday", new PublicHoliday());
		return "admin/publicHoliday_form";
	}

    @RequestMapping(path = "/createPublicHoliday", method = RequestMethod.POST)
    public String savePublicHoliday(PublicHoliday publicHoliday) {
        adminService.createPublicHoliday(publicHoliday);
        return "redirect:/admin/home";
    }
    
    @GetMapping("/publicHoliday/holidayDetails/{name}")
    public String viewDetail(Model model, @PathVariable("name") String name) {
    	
    	PublicHoliday publicHoliday = adminService.findPublicHolidayByName(name);
    	model.addAttribute("publicHoliday", publicHoliday);
    	return "admin/publicHoliday_detail";
    }
    
    @PostMapping("/publicHoliday/edit/{name}")
    public String editDetail(Model model, @PathVariable("name") String name) {
    	
    	PublicHoliday publicHoliday = adminService.findPublicHolidayByName(name);
    	model.addAttribute("publicHoliday", publicHoliday);
    	return "admin/publicHoliday_edit";
    }
    @RequestMapping(path = "/updatePublicHoliday", method = RequestMethod.POST)
    public String saveUpdeatedPublicHoliday(PublicHoliday publicHoliday) {
        adminService.updatePublicHoliday(publicHoliday);
        return "redirect:/admin/home";
    }
    @PostMapping("/publicHoliday/delete/{name}")
    public String delete(Model model, @PathVariable("name") String name) {
    	
    	PublicHoliday publicHoliday = adminService.findPublicHolidayByName(name);
    	adminService.deletePublicHoliday(publicHoliday);
    	return "redirect:/admin/home";
    }
}
