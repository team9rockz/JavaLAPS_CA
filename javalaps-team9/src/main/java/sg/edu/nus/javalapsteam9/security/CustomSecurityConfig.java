package sg.edu.nus.javalapsteam9.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sg.edu.nus.javalapsteam9.enums.Roles;

@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
	
	  @Autowired private AccessDeniedHandler accessDeniedHandler;
	  
	  @Autowired private CustomAuthenticationProvider authProvider;
	  
	  @Autowired private RefererRedirectionAuthenticationSuccessHandler successHandler;
	  
	  @Autowired private CustomAuthenticationFailureHandler failureHandler;
	  
	  @Autowired private CustomLogoutSuccessHandler logoutSuccessHandler;
	  
	  @Autowired private CustomFilter filter;
	  
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/", "/index", "/login", "/css/**", "/js/**").permitAll()
				.antMatchers("/admin/**").hasAuthority(Roles.ADMIN.getRole()).antMatchers("/manager/**")
				.hasAuthority(Roles.MANAGER.getRole()).antMatchers("/employee/**").hasAuthority(Roles.STAFF.getRole())
				.antMatchers("/movementregister/**")
				.hasAnyAuthority(Roles.ADMIN.getRole(), Roles.MANAGER.getRole(), Roles.STAFF.getRole()).anyRequest()
				.authenticated().and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).formLogin()
				.loginPage("/signin").permitAll().successHandler(successHandler).failureHandler(failureHandler)
				.failureUrl("/").and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}

}
