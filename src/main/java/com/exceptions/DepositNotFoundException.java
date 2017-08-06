package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 20.05.2017.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "deposit not found")
public class DepositNotFoundException extends RuntimeException {
}
