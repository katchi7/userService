package ma.tna.ebanking.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
@EnableFeignClients
@Import(SpringDataRestConfiguration.class)
@EnableHystrix
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
		basePackages = {"ma.tna.ebanking.userservice.repositories"})
public class UserserviceApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

}
