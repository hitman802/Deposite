package com.period;

import com.BaseUnitTest;
import com.config.properties.RateConfig;
import com.config.properties.RatesUpdaterProperties;
import com.dao.entities.Currency;
import com.dao.entities.Rate;
import com.dao.entities.RateSource;
import com.dao.repositories.CurrencyRepository;
import com.dao.repositories.RateSourceRepository;
import com.dao.repositories.RatesRepository;
import com.factory.CurrencyFactory;
import com.factory.RateSourceFactory;
import com.periodical.RatesUpdater;
import com.utils.RequestUtils;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.Test;

import java.util.concurrent.ScheduledExecutorService;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by sergeyg on 13.07.17.
 */
@PrepareForTest(RequestUtils.class)
public class RatesUpdaterTest extends BaseUnitTest {

    private RatesUpdater ratesUpdater;
    private CurrencyRepository currencyRepository;
    private RatesRepository ratesRepository;
    private RateSourceRepository rateSourceRepository;
    private RateSourceFactory rateSourceFactory;
    private CurrencyFactory currencyFactory;

    @Test
    public void testRun() throws Exception {
        ratesUpdater.run();

        verify(currencyRepository, times(6)).findByName(any());
        verify(currencyRepository, times(2)).save(any(Currency.class));
        verify(currencyFactory, times(2)).create();

        verify(rateSourceRepository, times(1)).findByName(any());
        verify(rateSourceFactory, times(1)).create();
        verify(rateSourceRepository, times(1)).save(any(RateSource.class));

        verify(ratesRepository, times(3)).findByCurrency_Name_AndDate(any(), any());
        verify(ratesRepository, times(3)).save(any(Rate.class));
    }

    @Override
    public void prepareMocks() {

        currencyRepository = Mockito.mock(CurrencyRepository.class);
        ratesRepository = Mockito.mock(RatesRepository.class);
        rateSourceRepository = Mockito.mock(RateSourceRepository.class);

        ScheduledExecutorService scheduledExecutorService = Mockito.mock(ScheduledExecutorService.class);
        rateSourceFactory = Mockito.mock(RateSourceFactory.class);
        currencyFactory = Mockito.mock(CurrencyFactory.class);
        RatesUpdaterProperties ratesUpdaterProperties = Mockito.mock(RatesUpdaterProperties.class);
        PowerMockito.mockStatic(RequestUtils.class);

        ratesUpdater = new RatesUpdater(currencyRepository, ratesRepository,
                rateSourceRepository, rateSourceFactory, scheduledExecutorService, currencyFactory, ratesUpdaterProperties);

        RateConfig mockRateConfig = new RateConfig();
        mockRateConfig.setName("NBY");
        mockRateConfig.setUrl("url");
        when(ratesUpdaterProperties.getSource()).thenReturn(mockRateConfig);

        String responseFromNBY = "[{\"cc\":\"HRK\",\"exchangedate\":\"13.07.2017\"}" +
                ",{\"r030\":203,\"txt\":\"Чеська крона\",\"rate\":1.13616,\"cc\":\"CZK\",\"exchangedate\":\"13.07.2017\"}" +
                ",{\"r030\":208,\"txt\":\"Данська крона\",\"rate\":3.988911,\"cc\":\"DKK\",\"exchangedate\":\"13.07.2017\"}]";
        when(RequestUtils.sendGetRequestToUrl(any())).thenReturn(responseFromNBY);
        when(currencyFactory.create()).thenCallRealMethod();
        when(rateSourceFactory.create()).thenCallRealMethod();

        when(currencyRepository.findByName(any()))
                .thenReturn(new Currency())
                .thenReturn(null)
                .thenReturn(null)
                .thenReturn(new Currency());
    }
}
