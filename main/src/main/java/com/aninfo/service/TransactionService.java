package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.exceptions.UnsupportedTransactionTypeException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private String Withdraw = "withdraw";
    private String Deposit = "deposit";

    private Double promoMinimumDepositSum = Double.valueOf(2000);
    private Double promoBonusPercentage = Double.valueOf(10);
    private Double promoBonusCap = Double.valueOf(500);



    public Account createTransaction(Transaction transaction, Long cbu) {
        transaction.setAccountCbu(cbu);
        Double transactionSum = transaction.getSum();
        String transactionType = transaction.getType();
        Account account = accountRepository.findAccountByCbu(cbu);

        if(transactionType.equals(Withdraw)) {
            applyWithdraw(account, accountRepository, transactionSum);
        }
        else if(transactionType.equals(Deposit)) {
            applyDeposit(account, accountRepository, transactionSum);
        }
        else{
            throw new UnsupportedTransactionTypeException("Transaction is neither a withdraw nor a deposit");
        }
        transactionRepository.save(transaction);
        return account;
    }

    public Collection<Transaction> getTransactions(Long cbu) {
        return transactionRepository.findAllByAccountCbu(cbu);
    }

    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    public void deleteById(Long id) {
        transactionRepository.deleteById(id);
    }

    private void applyWithdraw(Account account, AccountRepository accountRepository, Double transactionSum){
        Double accountBalance = account.getBalance();
        if (accountBalance < transactionSum) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        account.setBalance(accountBalance - transactionSum);
        accountRepository.save(account);
    }

    private void applyDeposit(Account account, AccountRepository accountRepository, Double transactionSum){
        Double accountBalance = account.getBalance();
        if (transactionSum <= 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }
        Double extraSum = calculateExtraSum(transactionSum);
        account.setBalance(accountBalance + transactionSum + extraSum);
        accountRepository.save(account);
    }

    private Double calculateExtraSum(Double transactionSum) {
        Double extraSum = Double.valueOf(0);
        if(transactionSum >= promoMinimumDepositSum){
            extraSum = (promoBonusPercentage * transactionSum / 100);
            if(extraSum >= promoBonusCap) { extraSum = Double.valueOf(promoBonusCap); }
        }
        return extraSum;
    }
}
