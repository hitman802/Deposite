package initializer.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by sergeyg on 17.07.17.
 */
@Component
@ConfigurationProperties("db")
public class DbProperties {
    @Getter @Setter private String url;
    @Getter @Setter private String username;
    @Getter @Setter private String password;
}
