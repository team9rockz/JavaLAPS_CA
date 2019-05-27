package sg.edu.nus.javalapsteam9.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.javalapsteam9.service.ManagerService;
import sg.edu.nus.javalapsteam9.util.SecurityUtil;
import sg.edu.nus.javalapsteam9.util.Util;

@Controller
@RequestMapping("/movementregister")
public class MovementController {
	
	@Autowired
	private ManagerService managerService;

	@GetMapping("/{month}")
	public String viewStaffMovementForSelectedMonth(Model model, @RequestParam("month") String selectedMonth) {

		List<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		model.addAttribute("months", months);

		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();

		int startMonthInNum = months.indexOf(selectedMonth) + 1;
		int endMonthInNum = months.indexOf(selectedMonth) + 1;

		model.addAttribute("movements",
				managerService.getLeavesByMonthYear(startMonthInNum, currentYear, endMonthInNum, currentYear));
		model.addAttribute("selectedMonth", selectedMonth);
		model.addAttribute("homeurl", Util.getHomeUrlByRole(SecurityUtil.getCurrentLoggedUserRole()));
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());

		return "manager/movement_register";
	}

	@GetMapping("/")
	public String viewStaffMovement(Model model) {

		List<String> months = new ArrayList<String>();
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		model.addAttribute("months", months);

		int startMonthInNum, endMonthInNum;

		LocalDate currentDate = LocalDate.now();
		String currentMonth = currentDate.getMonth().toString();
		String currentMonthFormatted;
		currentMonthFormatted = currentMonth.substring(0, 1)
				+ currentMonth.substring(1, currentMonth.length()).toLowerCase();
		startMonthInNum = months.indexOf(currentMonthFormatted) + 1;
		endMonthInNum = months.indexOf(currentMonthFormatted) + 1;
		int currentYear = currentDate.getYear();

		model.addAttribute("selectedMonth", currentMonthFormatted);
		model.addAttribute("currentYear", currentYear);
		model.addAttribute("movements",
				managerService.getLeavesByMonthYear(startMonthInNum, currentYear, endMonthInNum, currentYear));
		model.addAttribute("homeurl", Util.getHomeUrlByRole(SecurityUtil.getCurrentLoggedUserRole()));
		model.addAttribute("role", SecurityUtil.getCurrentLoggedUserRole());

		return "manager/movement_register";
	}
	
}
