package com;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Admin on 22.04.2017.
 */
@SpringBootApplication
@EnableTransactionManagement
@ConfigurationProperties
@Log4j
public class AppInitializer extends SpringBootServletInitializer {

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(AppInitializer.class, args);
        log.info("Application started");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AppInitializer.class);
    }
}
