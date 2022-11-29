package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.models.Currency;

import java.util.List;
import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface CurrencyService {
    List<Currency> findAll();

    Optional<Currency> findById(int currencyId);

    Optional<Currency> findByIsoCode(String isoCode);

    List<Currency> getReserveCurrencies();
}
