package br.com.gusta.bank.data.vo.v1;

import java.util.*;

public class DepositVO {

    private String accountName;
    private Double depositValue;

    public DepositVO() {
    }

    public DepositVO(String accountName, Double depositValue) {
        this.accountName = accountName;
        this.depositValue = depositValue;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getDepositValue() {
        return depositValue;
    }

    public void setDepositValue(Double depositValue) {
        this.depositValue = depositValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepositVO deposit = (DepositVO) o;
        return Objects.equals(accountName, deposit.accountName) && Objects.equals(depositValue, deposit.depositValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountName, depositValue);
    }
}
