package br.com.gusta.bank.unittests.mapper.mocks;

import br.com.gusta.bank.data.vo.v1.AccountVO;
import br.com.gusta.bank.model.Account;

public class MockAccount {

    public Account mockEntity() {
        return mockEntity(0);
    }

    public AccountVO mockVO() {
        return mockVO(0);
    }

    public Account mockEntity(Integer number) {
        Account account = new Account();
        account.setId(number.longValue());
        account.setAccountName("Account name " + number);
        account.setAccountPassword("Account password " + number);
        account.setAccountBalance(100D);
        return account;
    }

    public AccountVO mockVO(Integer number) {
        AccountVO accountVO = new AccountVO();
        accountVO.setAccountName("Account name " + number);
        accountVO.setAccountPassword("Account password " + number);
        accountVO.setAccountBalance(100D);
        return accountVO;
    }
}