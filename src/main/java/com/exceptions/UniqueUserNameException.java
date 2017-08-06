package com.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Admin on 20.05.2017.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Name field is not unique")
public class UniqueUserNameException extends RuntimeException {
}
