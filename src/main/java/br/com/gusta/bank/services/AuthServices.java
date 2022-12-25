package br.com.gusta.bank.services;

import br.com.gusta.bank.controllers.*;
import br.com.gusta.bank.data.vo.v1.*;
import br.com.gusta.bank.data.vo.v1.security.*;
import br.com.gusta.bank.repositories.*;
import br.com.gusta.bank.security.jwt.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

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
            var username = data.getAccountName();
            var password = data.getAccountPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            var user = repository.findByUsername(username);

            var tokenResponse = new TokenVO();
            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + " not found!");

            }
            tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            tokenResponse
                    .add(linkTo(methodOn(AccountController.class)
                            .transfer(new TransferVO()))
                            .withSelfRel());

            return tokenResponse;
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}