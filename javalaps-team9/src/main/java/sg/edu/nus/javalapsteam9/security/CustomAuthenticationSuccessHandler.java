package sg.edu.nus.javalapsteam9.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final Logger LOG = LogManager.getLogger(CustomAuthenticationSuccessHandler.class);
	
	private static final String HOME_PATH = "/home";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		handle(request, response, authentication);
		
	}
	
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		if(response.isCommitted()) {
			LOG.debug("Response is already committed. Unable to redirect");
			return;
		}
		
		String url = getTargetUrl(request, authentication);
		
		response.sendRedirect(url);
	}
	
	protected String getTargetUrl(HttpServletRequest request, Authentication authentication) {
		
		String url = request.getContextPath();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LOG.info("User " + auth.getName() + " successfully logged");
		@SuppressWarnings("unchecked")
		Authority authority = ((List<Authority>)auth.getAuthorities()).get(0);
		
		switch(Roles.valueOf(authority.getAuthority())) {
		case ADMIN:
			url += "/admin" + HOME_PATH;
			break;
		case MANAGER:
			url += "/manager" + HOME_PATH;
			break;
		case STAFF:
			url += "/employee" + HOME_PATH;
			break;
		default:
			url += "logout";
			break;
		}
		
		return url;
	}
	
	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null)
			return;
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}
