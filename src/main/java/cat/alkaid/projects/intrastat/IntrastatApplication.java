package cat.alkaid.projects.intrastat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.alkaid.projects.intrastat.security.AppUserDetailsService;
import cat.alkaid.projects.intrastat.security.JwtAuthorizationFilter;

@SpringBootApplication
public class IntrastatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IntrastatApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Autowired
		private JwtAuthorizationFilter jwtAuthorizationFilter;

		@Autowired
		private AppUserDetailsService appUserDetailService;

		@Bean
		protected PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(appUserDetailService);
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/proveedores/**").permitAll()
					.antMatchers(HttpMethod.POST, "/accounts/**").permitAll()
					.antMatchers(HttpMethod.POST, "/auth/check").authenticated()
					.antMatchers(HttpMethod.POST, "/auth/**").permitAll()
					.anyRequest().authenticated();

			http.cors();
		}
	}
}
