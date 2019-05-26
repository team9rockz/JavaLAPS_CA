package sg.edu.nus.javalapsteam9.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class CustomFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		String url = req.getRequestURI().substring(req.getContextPath().length(), req.getRequestURI().length());
		if(!url.contains("home") && (url.startsWith("/employee") || url.startsWith("/manager") || url.startsWith("/admin"))) {
			session.setAttribute("url_redirect", url);
		}
		
		chain.doFilter(req, res);
	}

}
