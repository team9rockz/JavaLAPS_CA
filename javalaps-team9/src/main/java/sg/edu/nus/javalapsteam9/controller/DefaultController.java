package sg.edu.nus.javalapsteam9.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Controller
public class DefaultController {
	
	private static final Logger LOG = LogManager.getLogger(DefaultController.class);
	
	private static final String LANDING_PAGE = "login";

	@RequestMapping("/")
	public String defaultRoute() {
		return LANDING_PAGE;
	}
	
	@RequestMapping("/index")
	public String index() {
		return LANDING_PAGE;
	}
	
	@PostMapping("/login")
	public String loginRoute(Roles role) {
		return "login";
	}

	@GetMapping("/signin")
	public String signInRoute() {
		return "redirect:index";
	}

	@GetMapping("/accessdenied")
	public String unauthorized() {
		LOG.error("403");
		return "errors/accessdenied";
	}

}
