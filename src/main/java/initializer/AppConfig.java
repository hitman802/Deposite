package initializer;


import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.apache.catalina.session.PersistentManager;
import org.apache.catalina.session.StandardManager;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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
@PropertySources({
        @PropertySource("classpath:properties.props"),
})
@ComponentScan({"initializer", "periodical", "dao", "factory", "service", "controllers", "validation"})
@EnableTransactionManagement
public class AppConfig {

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

    //to enable jpa and transactions
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/DepositesDB");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
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

    @Bean
    public JpaTransactionManager jpaTransactionManager(){
        JpaTransactionManager jpa = new JpaTransactionManager();
        jpa.setDataSource(dataSource());
        return jpa;
    }
    //to enable jpa

    //to make jsp working
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    //to make jsp working

    @Bean(name = "deposit")
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }
}
