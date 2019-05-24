package sg.edu.nus.javalapsteam9.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private static final Logger LOG = LogManager.getLogger(CustomAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		LOG.error("Exception occured -- {}", accessDeniedException);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			LOG.info("User " + auth.getName() + " attempted to access the protected URL : " + request.getRequestURI());
		}
		
		response.sendRedirect(request.getContextPath() + "/accessdenied");
	}

}
