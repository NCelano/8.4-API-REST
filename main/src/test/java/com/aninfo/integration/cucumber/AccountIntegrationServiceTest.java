package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.model.*;
import com.aninfo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    private String Withdraw = "withdraw";
    private String Deposit = "deposit";

    Account createAccount(Double balance) {
        return accountService.createAccount(new Account(balance));
    }

    Account createWithdrawTransaction(Double sum, Long cbu){
        return transactionService.createTransaction(new Transaction(Withdraw, sum), cbu);
    }

    Account createDepositTransaction(Double sum, Long cbu){
        return transactionService.createTransaction(new Transaction(Deposit, sum), cbu);
    }

}
