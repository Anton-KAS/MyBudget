package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.Account;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService{

    private final AccountService accountService;

    @Autowired
    public AccountServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountService.save(account);
    }
}
