package com.gusta.bank.services;

import com.gusta.bank.data.vo.v1.security.*;
import com.gusta.bank.exceptions.*;
import com.gusta.bank.mapper.DozerMapper;
import com.gusta.bank.model.*;
import com.gusta.bank.repositories.AccountRepository;
import com.gusta.bank.controllers.*;
import com.gusta.bank.data.vo.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Service
public class AccountServices implements UserDetailsService {

	@Autowired
	AccountRepository repository;
	AccountVO vo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var account = repository.findByUsername(username);
		if (account == null) throw new UsernameNotFoundException("User not found!");
		vo = new AccountVO(account.getAccountName(), account.getAccountBalance());
		return new CustomUserDetails(account);
	}

	public AccountVO createAccount(AccountVO accountVO) {
		if (accountVO.getAccountName() == null || accountVO.getAccountPassword() == null) throw new NullPointerException("Not possible create a account with null fields");
		if (accountVO.getAccountName().isBlank() || accountVO.getAccountPassword().isBlank()) throw new IllegalArgumentException("Not possible create a account with empty fields");
		if (accountVO.getAccountBalance() == null || accountVO.getAccountBalance().toString().isBlank()) accountVO.setAccountBalance(0D);

		var verifyDoesNotExists = repository.checkIfExists(accountVO.getAccountName());
		if(verifyDoesNotExists != null) throw new RepeatedAccountException();

		Account entity = DozerMapper.parseObject(accountVO, Account.class);
		entity.setPermissions(List.of(new Permission(1L)));
		entity.setAccountPassword(new BCryptPasswordEncoder(10).encode(accountVO.getAccountPassword()));
		repository.save(entity);

		accountVO
				.add(
						WebMvcLinkBuilder.linkTo(methodOn(AuthController.class)
							.login(new AccountCredentialsVO()))
							.withSelfRel()
				);
		accountVO
				.add(
						linkTo(methodOn(AccountController.class)
							.deposit(new DepositVO()))
							.withSelfRel()
				);

		return accountVO;
	}

	public String deposit(DepositVO deposit) {
		if (deposit.getAccountName() == null || deposit.getDepositValue() == null) throw new NullPointerException("Enter a value in the parameters");
		if (deposit.getAccountName().isBlank() || deposit.getDepositValue().toString().isBlank()) throw new IllegalArgumentException("Enter a valid value in the parameters");
		if (deposit.getDepositValue() <= 0) throw new InvalidValueException();

		var accountName = repository.checkIfExists(deposit.getAccountName());
		if (accountName == null) throw new RequiredObjectIsNullException("This account does not exists, please register in /api/bank/v1/create");
		Double accountBalance = repository.getAccountBalanceByUsername(accountName);
		accountBalance += deposit.getDepositValue();

		repository.updateBalanceByUsername(accountName, accountBalance);
		return "the deposit is completed";
	}// end of the deposit method

	public String transfer(TransferVO transfer) {

		if (transfer.getValueTransfer() <= 0 || transfer.getValueTransfer() > vo.getAccountBalance()) throw new InvalidValueException();

		if (transfer.getDestinyAccountName() == null || transfer.getValueTransfer() == null) throw new NullPointerException("Enter a value in the parameters");
		if (transfer.getDestinyAccountName().isBlank() || transfer.getValueTransfer().toString().isBlank()) throw new IllegalArgumentException("Enter a valid value in the parameters");
		if (transfer.getDestinyAccountName().equalsIgnoreCase(vo.getAccountName())) throw new IllegalArgumentException("Not possible transfer money to yourself");

		if (repository.checkIfExists(transfer.getDestinyAccountName()) == null) throw new RequiredObjectIsNullException("This account does not exists, please register in /api/bank/v1/create");
		Double accountBalance = repository.getAccountBalanceByUsername(transfer.getDestinyAccountName());
		AccountVO destinyEntity = new AccountVO(transfer.getDestinyAccountName(), accountBalance);

		vo.setAccountBalance(vo.getAccountBalance() - transfer.getValueTransfer());
		destinyEntity.setAccountBalance(destinyEntity.getAccountBalance() + transfer.getValueTransfer());
		repository.updateBalanceByUsername(vo.getAccountName(), vo.getAccountBalance());
		repository.updateBalanceByUsername(destinyEntity.getAccountName(), destinyEntity.getAccountBalance());
		return "Transfer successful";
	}// end of the transfer method

}