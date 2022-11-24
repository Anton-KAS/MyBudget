package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.repositories.AccountRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> findById(int accountId) {
        return accountRepository.findById(accountId);
    }

    @Override
    @Transactional
    public void save(Account account) {
        if (account.getCreatedAt() == null) account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteById(int accountId) {
        accountRepository.deleteById(accountId);
    }
}
