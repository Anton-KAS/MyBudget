package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;

import java.util.List;
import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface AccountService {

    Optional<Account> findById(int accountId);

    void save(Account account);

    void deleteById(int accountId);
}
