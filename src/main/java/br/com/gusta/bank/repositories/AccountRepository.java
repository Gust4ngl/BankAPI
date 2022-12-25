package br.com.gusta.bank.repositories;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import br.com.gusta.bank.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	@Query("SELECT a FROM Account a WHERE a.accountName =:accountName")
	Account findByUsername(@Param("accountName") String accountName);
}
