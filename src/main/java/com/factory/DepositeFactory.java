package com.factory;

import com.dao.entities.Deposite;
import org.springframework.stereotype.Component;

/**
 * Created by SHonchar on 5/26/2017.
 */
@Component
public class DepositeFactory {
    public Deposite create() {
        return new Deposite();
    }
}
