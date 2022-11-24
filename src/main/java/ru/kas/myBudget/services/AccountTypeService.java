package ru.kas.myBudget.services;

import ru.kas.myBudget.models.AccountType;

import java.util.List;
import java.util.Optional;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface AccountTypeService {
    List<AccountType> findAll();

    Optional<AccountType> findById(int currencyId);
}
