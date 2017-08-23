package com.periodical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dao.entities.Currency;
import com.dao.entities.Rate;
import com.dao.entities.RateSource;
import com.dao.repositories.CurrencyRepository;
import com.dao.repositories.RateSourceRepository;
import com.dao.repositories.RatesRepository;
import com.factory.CurrencyFactory;
import com.factory.RateSourceFactory;
import com.config.properties.RatesUpdaterProperties;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.utils.RequestUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by Admin on 22.04.2017.
 */

@Log4j
@Component
@Scope("singleton")
@AllArgsConstructor
public class RatesUpdater implements Runnable {

    private final CurrencyRepository CurrencyRepository;
    private final RatesRepository ratesRepository;
    private final RateSourceRepository rateSourceRepository;
    private final RateSourceFactory rateSourceFactory;
    private final ScheduledExecutorService scheduledExecutorService;
    private final CurrencyFactory currencyFactory;
    private final RatesUpdaterProperties ratesUpdaterProperties;

    public void run() {
        try {
            log.info("Update rates started");

            //send request to NBY
            String response = RequestUtils.sendGetRequestToUrl(formatNBYUrl());

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<Rate> rates = ((List<Rate>)mapper.readValue(response, new TypeReference<List<Rate>>(){}))
                    .stream()
                    .filter(a-> (a.getName() != null && !a.getName().trim().isEmpty()))
                    .collect(Collectors.toList());

            checkAndUpdateCurrencies(rates);
            updateRates(rates, checkAndUpdateRateSource("NBY"));

            log.info("Update rates finished");
        } catch(Exception e) {
            log.error("Got error:" + e);
        }
    }

    @PostConstruct
    private void init() {
        long interval = ratesUpdaterProperties.getUpdateInterval();
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
        rates.stream().parallel()
                .filter(rate -> CurrencyRepository.findByName(rate.getName()) == null)
                .forEach(saveCurrency());
    }

    private void updateRates(List<Rate> rates, RateSource rateSource) {
        Date date = new Date();
        rates.stream().parallel()
                .filter(filterRateByCurrencyNameAndDate(date))
                .forEach(saveRate(date, rateSource));
    }

    private String getCurrentDate() {
        SimpleDateFormat dt = new SimpleDateFormat("YYYYMMdd");
        return dt.format(new Date());
    }

    private RateSource checkAndUpdateRateSource(String sourceName) {
        return Optional.ofNullable(rateSourceRepository.findByName(sourceName))
                .orElseGet(createSourceName(sourceName));
    }

    private Supplier<RateSource> createSourceName(String sourceName) {
        return () -> {
            RateSource rateSource = rateSourceFactory.create();
            rateSource.setName(sourceName);
            rateSourceRepository.save(rateSource);
            return rateSource;
        };
    }

    private Predicate<Rate> filterRateByCurrencyNameAndDate(Date date) {
        return rate -> ratesRepository.findByCurrency_Name_AndDate(rate.getName(), date) == null;
    }

    private Consumer<Rate> saveRate(Date date, RateSource rateSource) {
        return rate -> {
            String currencyName = rate.getName();
            Currency currency = CurrencyRepository.findByName(currencyName);
            if( currency == null ) {
                log.error("Cant find currency in db " + currencyName);
                return;
            }
            rate.setCurrency(currency);
            rate.setDate(date);
            rate.setSource(rateSource);
            log.info("Processing " + rate);
            ratesRepository.save(rate);
        };
    }

    private Consumer<Rate> saveCurrency() {
        return rate -> {
            Currency currency = currencyFactory.create();
            currency.setName(rate.getName());
            CurrencyRepository.save(currency);
        };
    }

    private String formatNBYUrl() {
        return ratesUpdaterProperties.getSource().getUrl() + "?date=" + getCurrentDate() + "&json";
    }
}
