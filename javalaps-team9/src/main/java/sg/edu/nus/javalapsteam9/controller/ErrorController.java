package sg.edu.nus.javalapsteam9.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {
	
	private static final Logger LOG = LogManager.getLogger(ErrorController.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String exception(final Throwable throwable, final Model model) {
		LOG.error("Exception occured", throwable);
		model.addAttribute("errorMsg", throwable != null ? throwable.getMessage() : "Unknown Error");
		model.addAttribute("url", "/employee/home");
		return "error";
	}

}
