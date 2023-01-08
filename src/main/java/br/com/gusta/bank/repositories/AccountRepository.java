package br.com.gusta.bank.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import br.com.gusta.bank.model.Account;

import javax.transaction.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query("SELECT a FROM Account a WHERE a.accountName =:accountName")
	Account findByUsername(@Param("accountName") String accountName);

	@Query("SELECT a.accountName FROM Account a WHERE a.accountName =:accountName")
	String checkIfExists(@Param("accountName") String accountName);

	@Query("SELECT a.accountBalance FROM Account a WHERE a.accountName =:accountName")
	Double getAccountBalanceByUsername(@Param("accountName") String accountName);

	@Transactional
	@Modifying
	@Query("UPDATE Account a SET a.accountBalance =:accountBalance WHERE a.accountName =:accountName")
	void updateBalanceByUsername(@Param("accountName") String accountName, @Param("accountBalance") Double accountBalance);
}