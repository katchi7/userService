package ma.tna.ebanking.userservice.config;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.annotations.CustomController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.SecureRandom;
import java.util.Random;

@Configuration
@Log4j2
public class WebConfig implements WebMvcConfigurer {
    @Value("${context-path}")
    private String contextPath;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        log.info(contextPath);
        configurer.addPathPrefix(contextPath, HandlerTypePredicate.forBasePackage("ma.tna.ebanking.userservice"));
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Random randomGenerator(){
        return new SecureRandom();
    }

}

