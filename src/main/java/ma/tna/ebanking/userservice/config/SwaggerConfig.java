package ma.tna.ebanking.userservice.config;
import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.model.Benef;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Import;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebMvc
@Log4j2
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class SwaggerConfig implements WebMvcConfigurer {
    @Value("${context-path}")
    private String contextPath;

    @Bean
    public Docket postsApi() {
        String basePackage = ma.tna.ebanking.userservice.UserserviceApplication.class.getPackage().getName();
        log.info(basePackage);
        return  new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/ebanking/userService/**"))
                .build();
    }

}
