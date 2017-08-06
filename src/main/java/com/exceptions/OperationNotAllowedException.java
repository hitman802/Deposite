package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 20.05.2017.
 */
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Operation not allowed")
public class OperationNotAllowedException extends RuntimeException {
}
