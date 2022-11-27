package ru.kas.myBudget.services;

import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.TelegramUser;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface AccountService {

    Optional<Account> findById(int accountId);

    void save(Account account);

    void deleteById(int accountId);
}
