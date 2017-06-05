package periodical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.entities.Currency;
import dao.entities.Rate;
import dao.entities.RateSource;
import dao.repositories.CurrencyRepository;
import dao.repositories.RateSourceRepository;
import dao.repositories.RatesRepository;
import factory.CurrencyFactory;
import factory.RateSourceFactory;
import lombok.extern.log4j.Log4j;
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
@Component
@Scope("singleton")
public class RatesUpdater implements Runnable {

    private final CurrencyRepository currencyRepository;
    private final RatesRepository ratesRepository;
    private final RateSourceRepository rateSourceRepository;
    private final ScheduledExecutorService scheduledExecutorService;

    private final CurrencyFactory currencyFactory;
    private final RateSourceFactory rateSourceFactory;

    @Value("${ratesUpdateInterval}")
    private long interval;

    @Value("${urlNBY}")
    private String urlNBY;

    public RatesUpdater(CurrencyRepository currencyRepository, RatesRepository ratesRepository, RateSourceRepository rateSourceRepository, RateSourceFactory rateSourceFactory, ScheduledExecutorService scheduledExecutorService, CurrencyFactory currencyFactory) {
        this.currencyRepository = currencyRepository;
        this.ratesRepository = ratesRepository;
        this.rateSourceRepository = rateSourceRepository;
        this.rateSourceFactory = rateSourceFactory;
        this.scheduledExecutorService = scheduledExecutorService;
        this.currencyFactory = currencyFactory;
    }

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
            checkAndUpdateCurrencies(rates);

            RateSource rateSource = checkAndUpdateRateSource("NBY");
            updateRates(rates, rateSource);

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

    private void checkAndUpdateCurrencies(List<Rate> rates) {
        rates.forEach(k -> {
            String name = k.getName();
            Currency currency = currencyRepository.findByName(name);
            if( currency == null ) {
                currency = currencyFactory.create();
                currency.setName(name);
                currencyRepository.save(currency);
            }
        });
    }

    private void updateRates(List<Rate> rates, RateSource rateSource) {
        rates.forEach( rate -> {

            String currencyName = rate.getName();
            Date date = new Date();

            //check if rate already exist on given date, if yes - skip
            if( ratesRepository.findByCurrency_Name_AndDate(currencyName, date) != null ) {
                return;
            }

            Currency currency = currencyRepository.findByName(currencyName);
            if( currency == null ) {
                log.error("Cant find currency in db " + currencyName);
                return;
            }
            rate.setCurrency(currency);
            rate.setDate(date);
            rate.setSource(rateSource);
            log.info("Processing " + rate);
            ratesRepository.save(rate);
        });
    }

    private String getCurrentDate() {
        SimpleDateFormat dt = new SimpleDateFormat("YYYYMMdd");
        return dt.format(new Date());
    }

    private RateSource checkAndUpdateRateSource(String sourceName) {
        RateSource rateSource = rateSourceRepository.findByName(sourceName);
        if( rateSource == null ) {
            rateSource = rateSourceFactory.create();
            rateSourceRepository.save(rateSource);
        }
        return rateSource;
    }
}
