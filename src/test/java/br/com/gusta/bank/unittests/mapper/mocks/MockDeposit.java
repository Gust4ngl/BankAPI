package br.com.gusta.bank.unittests.mapper.mocks;

import br.com.gusta.bank.data.vo.v1.*;

public class MockDeposit {

    public DepositVO mockDeposit() {
        return mockDeposit(0);
    }

    public DepositVO mockDeposit(Integer number) {
        DepositVO deposit = new DepositVO();
        deposit.setAccountName("Account name " + number);
        deposit.setDepositValue(10D);
        return deposit;
    }


}