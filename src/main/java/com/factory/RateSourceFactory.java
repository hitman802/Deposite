package com.factory;

import com.dao.entities.RateSource;
import org.springframework.stereotype.Component;

/**
 * Created by Admin on 29.04.2017.
 */
@Component
public class RateSourceFactory {
    public RateSource create() {
        return new RateSource();
    }
}
