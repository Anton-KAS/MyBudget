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

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByCurrencyEnContains(String currencyEn);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByCurrencyRuContains(String currencyRu);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllBySymbolContains(String symbol);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> findAllByIsoCodeContains(String isoCode);

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    List<Currency> getCurrenciesBySearchWord(String searchWord);
}
