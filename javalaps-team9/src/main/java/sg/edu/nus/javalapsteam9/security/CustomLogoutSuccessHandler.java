package sg.edu.nus.javalapsteam9.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import sg.edu.nus.javalapsteam9.util.SecurityUtil;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		request.getSession().invalidate();
		SecurityUtil.invalidate();
		response.sendRedirect(request.getContextPath());
	}

}
