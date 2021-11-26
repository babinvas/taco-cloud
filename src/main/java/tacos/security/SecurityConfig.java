package tacos.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// The passwords in the book don't contain "{noop}", but the app throws exception without it
		auth
			.inMemoryAuthentication()
				.withUser("vas")
					.password("{noop}vas")
					.authorities("ROLE_USER")
				.and()
				.withUser("bas")
					.password("{noop}bas")
					.authorities("ROLE_USER");
	}
}
