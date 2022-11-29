package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.Account;

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
