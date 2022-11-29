package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.Bank;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface BankService {
    List<Bank> findAll();

    Optional<Bank> findById(int currencyId);
}
