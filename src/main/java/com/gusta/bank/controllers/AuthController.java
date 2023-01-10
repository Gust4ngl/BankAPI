package com.gusta.bank.controllers;

import com.gusta.bank.data.vo.v1.security.*;
import com.gusta.bank.services.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/api/bank/v1/")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @Operation(
            summary = "Authenticates a user and returns a token",
            tags = {"Authentication Endpoint"}
    )
    @PostMapping(value = "login")
    public ResponseEntity<TokenVO> login(@RequestBody AccountCredentialsVO data) {
        if (checkIfParamsIsNotNull(data))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        var token = authServices.login(data);
        if (token == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
        return data == null || data.getAccountName() == null || data.getAccountName().isBlank()
                || data.getAccountPassword() == null || data.getAccountPassword().isBlank();
    }
}