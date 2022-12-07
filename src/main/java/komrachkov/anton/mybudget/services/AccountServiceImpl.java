package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import komrachkov.anton.mybudget.repositories.AccountRepository;

import java.util.Date;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
@Transactional(readOnly = true)
@Log4j2
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
        log.info("Save account to DB: " + account.getId());
    }

    @Override
    @Transactional
    public void deleteById(int accountId) {
        accountRepository.deleteById(accountId);
        log.info("Delete account from DB: " + accountId);
    }
}
