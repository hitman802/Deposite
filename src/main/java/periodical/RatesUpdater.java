package periodical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.entities.RateSource;
import dao.repository.CurrencyRepository;
import dao.repository.RateSourceRepository;
import dao.repository.RatesRepository;
import dao.entities.Rate;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utils.RequestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by Admin on 22.04.2017.
 */

@Log4j
@NoArgsConstructor
@Component
@Scope("singleton")
public class RatesUpdater implements Runnable {

    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private RatesRepository ratesRepository;
    @Autowired
    private RateSourceRepository rateSourceRepository;

    @Value("${ratesUpdateInterval}")
    private long interval;

    @Value("${urlNBY}")
    private String urlNBY;

    @Autowired
    private ScheduledExecutorService scheduledExecutorService;

    public void run() {
        try {
            log.info("Update rates started");

            //send request to NBY
            String response = RequestUtils.sendGetRequestToUrl(
                    urlNBY + "?date=" + getCurrentDate() + "&json");

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Rate> rates = mapper.readValue(response, new TypeReference<List<Rate>>(){});
            rates = rates
                    .stream()
                    .filter(a-> (a.getName() != null && !a.getName().trim().isEmpty()))
                    .collect(Collectors.toList());
            currencyRepository.checkAndUpdateCurrencies(rates);
            RateSource rateSource = rateSourceRepository.updateSource("NBY");
            ratesRepository.updateRates(rates, rateSource);

            log.info("Update rates finished");
        } catch(Exception e) {
            log.error("Got error:" + e);
        }
    }

    @PostConstruct
    private void init() {
        log.info("Starting rates updated with interval " + interval + " sec");
        scheduledExecutorService.scheduleAtFixedRate(this, 0, interval, TimeUnit.SECONDS);
    }

    @PreDestroy
    private void destroy() {
        log.info("Shutting down");
        scheduledExecutorService.shutdown();
        log.info("Shutting down complete");
    }

    private String getCurrentDate() {
        SimpleDateFormat dt = new SimpleDateFormat("YYYYMMdd");
        return dt.format(new Date());
    }
}
