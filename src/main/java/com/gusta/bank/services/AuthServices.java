package com.gusta.bank.services;

import com.gusta.bank.controllers.*;
import com.gusta.bank.data.vo.v1.*;
import com.gusta.bank.data.vo.v1.security.*;
import com.gusta.bank.exceptions.*;
import com.gusta.bank.model.*;
import com.gusta.bank.repositories.*;
import com.gusta.bank.security.jwt.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.hateoas.server.mvc.*;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AccountRepository repository;

    public TokenVO login(AccountCredentialsVO data) {
        try {
            String username = data.getAccountName();

            if (repository.checkIfExists(username) == null) {
                throw new RequiredObjectIsNullException("This account does not exists, please register in /api/bank/v1/create");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, data.getAccountPassword()));

            var tokenResponse = tokenProvider
                    .createAccessToken(username, roles(repository.getPermissionsByUsername(username)))
                    .add(WebMvcLinkBuilder.linkTo(methodOn(AccountController.class)
                            .transfer(new TransferVO()))
                            .withSelfRel());

            return tokenResponse;
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    //Method created to get just the descriptions of roles/permissions.
    private List<String> roles(List<Permission> permissionList) {
        List<String> roles = new ArrayList<>();
        for (Permission permission : permissionList) {
            roles.add(permission.getDescription());
        }
        return roles;
    }

}