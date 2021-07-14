package ma.tna.ebanking.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class UserserviceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
