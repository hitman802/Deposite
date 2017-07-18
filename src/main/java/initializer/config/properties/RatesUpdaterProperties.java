package initializer.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeyg on 17.07.17.
 */
@Component
@ConfigurationProperties("rates.updater")
public class RatesUpdaterProperties {
    @Getter @Setter private long updateInterval;
    @Getter @Setter private RateConfig source;
}
