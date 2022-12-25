package br.com.gusta.bank.unittests.mapper.mocks;

import br.com.gusta.bank.data.vo.v1.*;

public class MockTransfer {

    public TransferVO mockTransfer() {
        return mockTransfer(0);
    }

    public TransferVO mockTransfer(Integer number) {
        TransferVO transfer = new TransferVO();
        transfer.setDestinyAccountName("Destiny account " + number);
        transfer.setValueTransfer(10D);
        return transfer;
    }


}