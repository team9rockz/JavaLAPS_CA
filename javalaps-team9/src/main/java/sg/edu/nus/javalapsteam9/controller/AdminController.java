package sg.edu.nus.javalapsteam9.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.javalapsteam9.model.User;
import sg.edu.nus.javalapsteam9.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/home")
	public String home(Model model) {
		
		List<User> users = new ArrayList<User>();
		users = adminService.getAllUsers();
		model.addAttribute("users", users);
		return "admin/home";
	}
	
	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addUser(Model model) {
		
		model.addAttribute("user", new User());
		model.addAttribute("managers", adminService.getAllManagers());
		return "admin/user_form";
	}

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public String saveProduct(User user) {
    	
        adminService.createUser(user);
        return "redirect:/admin/home";
    }
    
    @GetMapping("/user/details/{id}")
    public String userForm(Model model, @PathVariable("id") Integer id) {
    	
    	User user = adminService.findUserById(id);
    	model.addAttribute("user", user);
    	return "admin/user_detail";
    }
}
