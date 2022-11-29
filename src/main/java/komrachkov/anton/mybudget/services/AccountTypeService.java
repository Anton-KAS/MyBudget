package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.AccountType;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface AccountTypeService {
    List<AccountType> findAll();

    Optional<AccountType> findById(int currencyId);
}
