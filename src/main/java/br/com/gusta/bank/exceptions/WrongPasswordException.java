package br.com.gusta.bank.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String str) {
        super(str);
    }
    public WrongPasswordException() {
        super("The password that was entered does not match with the registered!");
    }

}
