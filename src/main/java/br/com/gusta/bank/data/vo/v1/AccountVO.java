package br.com.gusta.bank.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

@JsonPropertyOrder({"accountName", "accountBalance"})
public class AccountVO extends RepresentationModel<AccountVO> {

	private String accountName;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String accountPassword;
	private Double accountBalance;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AccountVO vo = (AccountVO) o;
		return Objects.equals(accountName, vo.accountName) && Objects.equals(accountPassword, vo.accountPassword) && Objects.equals(accountBalance, vo.accountBalance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), accountName, accountPassword, accountBalance);
	}
}
