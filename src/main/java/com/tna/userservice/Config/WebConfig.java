package com.tna.userservice.Config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Log4j2
public class WebConfig implements WebMvcConfigurer {
    @Value("${spring.application.name}")
    private String contextPath;
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        log.info(contextPath);
        configurer.addPathPrefix(contextPath, HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
