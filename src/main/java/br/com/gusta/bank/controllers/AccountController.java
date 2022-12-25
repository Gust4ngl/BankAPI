package br.com.gusta.bank.controllers;

import br.com.gusta.bank.data.vo.v1.*;
import br.com.gusta.bank.services.AccountServices;
import br.com.gusta.bank.util.MediaType;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;

@Tag(name = "Account management", description = "Endpoints for managing account's")
@RestController
@RequestMapping("/api/bank/v1/")
public class AccountController {

	@Autowired
	private AccountServices service;
	
	@PostMapping(
			value = "create",
			produces = MediaType.APPLICATION_JSON,
			consumes = MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Create a account",
			tags = {"Account management"},
			responses = {
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content)
			}
	)
	public ResponseEntity<AccountVO> create(@RequestBody AccountVO accountVO){
		return new ResponseEntity<>(service.createAccount(accountVO), HttpStatus.CREATED);
	}

	@PutMapping(
			value = "deposit",
			produces = MediaType.APPLICATION_JSON,
			consumes = MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Deposit money in one account",
			tags = {"Account management"}
	)
	public ResponseEntity<String> deposit (@RequestBody DepositVO deposit) {
		return new ResponseEntity<>(service.deposit(deposit), HttpStatus.OK);
	}

	@PutMapping(
			value = "transfer",
			produces = MediaType.APPLICATION_JSON,
			consumes = MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Transfer money to one account",
			tags = {"Account management"}
	)
	public ResponseEntity<String> transfer(@RequestBody TransferVO transfer) {
		return new ResponseEntity<>(service.transfer(transfer), HttpStatus.OK);
	}

}
