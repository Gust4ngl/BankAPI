package br.com.gusta.bank.exceptions;

import org.springframework.http.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJWTAuthException extends AuthenticationException {

    public InvalidJWTAuthException(String str) {
        super(str);
    }

}
