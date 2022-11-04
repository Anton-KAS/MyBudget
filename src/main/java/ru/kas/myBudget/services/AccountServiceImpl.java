package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.repositories.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> findAllByTelegramUserId(long telegramUserId) {
        return accountRepository.findAllByTelegramUser(telegramUserId);
    }
}
