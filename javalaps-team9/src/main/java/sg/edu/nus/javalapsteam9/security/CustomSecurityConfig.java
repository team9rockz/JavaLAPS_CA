package sg.edu.nus.javalapsteam9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
	
	  @Autowired private AccessDeniedHandler accessDeniedHandler;
	  
	  @Autowired private CustomAuthenticationProvider authProvider;
	  
	  @Autowired private CustomAuthenticationSuccessHandler successHandler;
	  
	  @Autowired private CustomAuthenticationFailureHandler failureHandler;
	  
	  @Autowired private CustomLogoutSuccessHandler logoutSuccessHandler;
	  
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/", "/index", "/home", "/welcome", "/login", "/css/**", "/js/**").permitAll()
				.antMatchers("/admin/**", "/movement/**").hasAuthority(Roles.ADMIN.getRole()).antMatchers("/manager/**","/movement/**")
				.hasAuthority(Roles.MANAGER.getRole()).antMatchers("/employee/**","/movement/**").hasAuthority(Roles.STAFF.getRole())
				.anyRequest().authenticated().and().formLogin().loginPage("/signin").permitAll()
				.successHandler(successHandler).failureHandler(failureHandler).failureUrl("/").and().logout()
				.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).and().exceptionHandling()
				.accessDeniedHandler(accessDeniedHandler);
	}

}
