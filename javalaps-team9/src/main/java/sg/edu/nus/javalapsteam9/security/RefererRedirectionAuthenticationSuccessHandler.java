package sg.edu.nus.javalapsteam9.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Component
public class RefererRedirectionAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final String HOME_PATH = "/home";
	
	public RefererRedirectionAuthenticationSuccessHandler() {
		super();
		setUseReferer(true);
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		handle(request, response, authentication);
		
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
		
		if(response.isCommitted()) {
			return;
		}
		
		String url = getTargetUrl(request, authentication);
		
		response.sendRedirect(url);
	}
	
	protected String getTargetUrl(HttpServletRequest request, Authentication authentication) {
		
		String url = request.getContextPath();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		@SuppressWarnings("unchecked")
		Authority authority = ((List<Authority>)auth.getAuthorities()).get(0);
		Roles role = Roles.valueOf(authority.getAuthority());
		String rolename = role.getRole();
		if(role == Roles.STAFF) {
			rolename = "employee";
		}
		boolean isRedirect = false;
		
		HttpSession session = request.getSession(false);
		if (session != null) {
		    String redirectUrl = (String) request.getSession().getAttribute("url_redirect");
		    if(redirectUrl != null && !redirectUrl.isEmpty() && redirectUrl.startsWith("/"+rolename)) {
		    	url += redirectUrl;
		    	session.removeAttribute("url_redirect");
		    	isRedirect = true;
		    }
		}
		
		if(isRedirect) {
			return url;
		}
		
		switch(role) {
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
}
