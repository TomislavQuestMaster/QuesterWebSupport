package net.thequester.websupport.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tdubravcevic
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("tom").password("123456").roles("USER");
		auth.inMemoryAuthentication().withUser("bill").password("123456").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("james").password("123456").roles("SUPERADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin()
			.and()
			.authorizeRequests()
			.antMatchers("/app/**")
			.access("hasRole('ROLE_ADMIN')");

	}

}
