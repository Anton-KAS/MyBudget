package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import ru.kas.myBudget.models.Account;

public class AccountServiceImpl implements AccountService{

    private final AccountService accountService;

    @Autowired
    public AccountServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void save(Account account) {
        accountService.save(account);
    }
}
