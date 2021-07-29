package ma.tna.ebanking.userservice.config;

import lombok.extern.log4j.Log4j2;
import ma.tna.ebanking.userservice.model.Benef;
import ma.tna.ebanking.userservice.services.AuditorAwareImpl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@Log4j2
public class WebConfig implements WebMvcConfigurer {
    @Value("${context-path}")
    private String contextPath;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        String appBasePackage = ma.tna.ebanking.userservice.UserserviceApplication.class.getPackage().getName();
        configurer.addPathPrefix(contextPath, HandlerTypePredicate.forBasePackage(appBasePackage));
        Logger logger = Logger.getLogger("com.microsoft.sqlserver.jdbc");
        logger.setLevel(Level.SEVERE);
        LoggerFactory.getLogger(getClass().getName()).info("App Started");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public Random random(){
        return new SecureRandom();
    }

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }

}

