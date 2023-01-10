package br.com.gusta.bank.model;


import javax.persistence.*;
import java.util.*;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "username", nullable = false, length = 20)
	private String accountName;
	
	@Column(name = "password", nullable = false, length = 20)
	private String accountPassword;
	private Double accountBalance;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "account_permission",
			joinColumns = {@JoinColumn (name = "id_account")},
			inverseJoinColumns = {@JoinColumn (name = "id_permission")}
	)
	private List<Permission> permissions;
	
	public Account() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Account account = (Account) o;
		return Objects.equals(id, account.id) && Objects.equals(accountName, account.accountName) && Objects.equals(accountPassword, account.accountPassword) && Objects.equals(accountBalance, account.accountBalance) && Objects.equals(permissions, account.permissions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, accountName, accountPassword, accountBalance, permissions);
	}
}
