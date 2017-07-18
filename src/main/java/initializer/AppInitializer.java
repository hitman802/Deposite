package initializer;


import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Admin on 22.04.2017.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
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
