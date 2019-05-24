package sg.edu.nus.javalapsteam9.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import sg.edu.nus.javalapsteam9.security.CustomUser;

public final class SecurityUtil {
	
	public static int getCurrentLoggedUserId() {
		return getCurrentLoggedUser().getId();
	}
	
	public static CustomUser getCurrentLoggedUser() {
		return (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static void invalidate() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if(ctx != null)
			ctx.setAuthentication(null);
	}

}
