package com.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Admin on 22.04.2017.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.dao.repositories")
public class AppConfig {

    //excutor for rates updater
    @Bean
    @Scope("prototype")
    public ScheduledExecutorService scheduledSingleThreadExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean(name = "deposit")
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }

    @Bean(name = "calculator")
    public SimpleDateFormat simpleDateFormatCalculator() {
        return new SimpleDateFormat("yyyy-dd-MM");
    }

}
