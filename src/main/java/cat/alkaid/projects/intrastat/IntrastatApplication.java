package cat.alkaid.projects.intrastat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class IntrastatApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(IntrastatApplication.class, args);
	}

}
