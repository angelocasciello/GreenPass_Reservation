package net.codejava;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private DataSource dataSource;
	
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	

	@Bean
	public BCryptPasswordEncoder passwordEncoder( ) {
		return new BCryptPasswordEncoder ();
	}
	
	@Bean 
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		 	.antMatchers("/dashboard_cliente").access("hasRole('ROLE_CLIENTE')")
		 	.antMatchers("/dashboard_gestore").access("hasRole('ROLE_GESTORE')")
			.anyRequest().permitAll()
			.and()
			.formLogin()
					.usernameParameter("email")
				.successHandler(authenticationSuccessHandler)
					.permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/pagina_inaccessibile")
			.and()
			.logout().logoutSuccessUrl("/").permitAll();
	}
}
