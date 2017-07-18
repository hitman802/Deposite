package initializer.config;


import initializer.config.properties.DbProperties;
import net.sf.log4jdbc.DriverSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Admin on 22.04.2017.
 */
@Configuration
@ComponentScan({"initializer", "periodical", "dao", "factory", "service", "controllers", "validation", "deposit"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "dao.repositories")
public class AppConfig {

    @Autowired
    private DbProperties dbProperties;

    //to read property files
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    //excutor for rates updater
    @Bean
    @Scope("prototype")
    public ScheduledExecutorService scheduledSingleThreadExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    //to enable jpa and transactions or SPRING-DATA
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DriverSpy.class.getName());
        dataSource.setUrl(dbProperties.getUrl());
        dataSource.setUsername(dbProperties.getUsername());
        dataSource.setPassword(dbProperties.getPassword());
        return dataSource;
    }
    @Bean
    public EntityManager entityManager() {
        return entityManagerFactory().getObject().createEntityManager();
    }
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        return em;
    }
    @Bean(name = "transactionManager")
    public JpaTransactionManager jpaTransactionManager(){
        JpaTransactionManager jpa = new JpaTransactionManager();
        jpa.setDataSource(dataSource());
        return jpa;
    }
    //to enable jpa or SPRING-DATA

    @Bean(name = "deposit")
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }

    @Bean(name = "calculator")
    public SimpleDateFormat simpleDateFormatCalculator() {
        return new SimpleDateFormat("yyyy-dd-MM");
    }

}
