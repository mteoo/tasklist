package com.matheus.dias.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaRepositories
@SpringBootApplication
public class TasklistApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TasklistApplication.class, args);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH").allowedOrigins("*")
                .allowedHeaders("*");
    }

}
