package sg.edu.nus.javalapsteam9.controller;

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
import sg.edu.nus.javalapsteam9.util.SecurityUtil;
import sg.edu.nus.javalapsteam9.validation.CustomFieldError;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	private static final String HOME = "/admin/home";
	
	@GetMapping("/home")
	public String home(Model model) {
		
		List<User> users = new ArrayList<User>();
		List<PublicHoliday> publicHolidays=new ArrayList<PublicHoliday>();
		users = adminService.getAllUsers();
		publicHolidays=adminService.getAllPublicHolidays();
		model.addAttribute("homeurl", HOME);
		model.addAttribute("users", users);
		model.addAttribute("publicHolidays", publicHolidays);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "admin/home";
		
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addUser(Model model) {
		
		model.addAttribute("homeurl", HOME);
		model.addAttribute("user", new User());
		model.addAttribute("managers", adminService.getAllManagers());
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "admin/user_form";
	}
    
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if(adminService.findUserByUserId(user.getUserId()) != null) {
			CustomFieldError cd = new CustomFieldError("user", "userId", user.getUserId(), "User ID already exists");
			result.addError(cd);
		}
		model.addAttribute("homeurl", HOME);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
    	
    	validateEmployeeForm(user, result);
    	
		if(result.hasErrors()) {
			
			model.addAttribute("managers", adminService.getAllManagers());
			return "admin/user_form";
		}
		
        adminService.saveUser(user);
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
	}
    
    @GetMapping("/user/details/{id}")
    public String userDetail(Model model, @PathVariable("id") Integer id) {
    	
    	User user = adminService.findUserById(id);
		model.addAttribute("homeurl", HOME);
    	model.addAttribute("user", user);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
    	return "admin/user_detail";
    }
    
    @PostMapping("/user/edit/{id}")
    public String editUserDetail(Model model, @PathVariable("id") int id) {
    	
    	User user = adminService.findUserById(id);
		model.addAttribute("homeurl", HOME);
    	model.addAttribute("user", user);
    	model.addAttribute("managers", adminService.getAllManagers());
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
    	return "admin/user_edit";
    }
    
    @RequestMapping(path = "/updateUser", method = RequestMethod.POST)
    public String saveUpdatedUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
    	
    	validateEmployeeForm(user, result);
    	
		model.addAttribute("homeurl", HOME);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		if(result.hasErrors()) {
			
			model.addAttribute("managers", adminService.getAllManagers());
			return "admin/user_edit";
		}
    	
        adminService.saveUser(user);
        return "redirect:/admin/home";
    }
    
    @RequestMapping(path = "/user/delete/{id}", method = RequestMethod.POST)
    public String deleteUser(@PathVariable("id") int id) {
        adminService.deleteUser(adminService.findUserById(id));
        return "redirect:/admin/home";
    }
    
    @RequestMapping(path = "/addPublicHoliday", method = RequestMethod.GET)
	public String addPublicHoliday(Model model) {
		
		model.addAttribute("homeurl", HOME);
		model.addAttribute("publicHoliday", new PublicHoliday());
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
		return "admin/publicHoliday_form";
	}

    @RequestMapping(path = "/createPublicHoliday", method = RequestMethod.POST)
    public String savePublicHoliday(Model model, @Valid @ModelAttribute("publicHoliday") PublicHoliday publicHoliday, BindingResult result) {
    	validate(publicHoliday,result);
		model.addAttribute("homeurl", HOME);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
    	if(result.hasErrors()) {
    		return "admin/publicHoliday_form";
    	}
        adminService.createPublicHoliday(publicHoliday);
        return "redirect:/admin/home";
    }
    
    @GetMapping("/publicHoliday/holidayDetails/{name}")
    public String viewDetail(Model model, @PathVariable("name") String name) {
    	
    	PublicHoliday publicHoliday = adminService.findPublicHolidayByName(name);
		model.addAttribute("homeurl", HOME);
    	model.addAttribute("publicHoliday", publicHoliday);
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());
    	return "admin/publicHoliday_detail";
    }
    

    @PostMapping("/publicHoliday/delete/{name}")
    public String delete(Model model, @PathVariable("name") String name) {
    	
    	PublicHoliday publicHoliday = adminService.findPublicHolidayByName(name);
    	adminService.deletePublicHoliday(publicHoliday);
    	return "redirect:/admin/home";
    }
    
    private void validate(PublicHoliday publicHoliday, BindingResult result) {
		if(publicHoliday.getName()!=null) {
			
			boolean notAvailableName=adminService.checkValidPublicHolidayName(publicHoliday.getName());
			if(notAvailableName) {
				CustomFieldError cd=new CustomFieldError("publicHoliday","name",publicHoliday.getName(),"Invalid Name");
				result.addError(cd);
			}
		}
		if (publicHoliday.getStartDate() != null) {
			boolean isValidDate;
			if (publicHoliday.getEndDate() != null) {
				isValidDate = adminService.isValidEndDate(publicHoliday.getStartDate(), publicHoliday.getEndDate());
				if (!isValidDate) {
					CustomFieldError cd = new CustomFieldError("publicHoliday", "endDate", publicHoliday.getEndDate(),
							"Invalid end date");
					result.addError(cd);
				}
			}
		}
	}
}
