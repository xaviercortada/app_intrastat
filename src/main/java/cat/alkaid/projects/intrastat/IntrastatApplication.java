package cat.alkaid.projects.intrastat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cat.alkaid.projects.intrastat.security.JWTAuthorizationFilter;

@SpringBootApplication
public class IntrastatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IntrastatApplication.class, args);
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/proveedores/**").permitAll()
				.antMatchers(HttpMethod.POST, "/accounts/**").permitAll()
				.anyRequest().authenticated();

			http.cors();
		}
	}
}
